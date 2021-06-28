package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class NotesListFragment extends Fragment {
    private final List<NotesEntity> noteList = new ArrayList<>();
    private final String uid = UUID.randomUUID().toString();
    private Button buttonCreateNote;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private FirebaseNotesRepo repo;
    private final Runnable subscriber = () -> {
        updateAllNotes(noteList);
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //делаем проверку, что активити имплементирует интерфейс Contract
        if (!(context instanceof Contract)) {
            throw new RuntimeException("Activity must implement NotesListController");
        }
    }

    //метод для инициализации наших вьюшек
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        //кнопка создания новой заметки
        buttonCreateNote = view.findViewById(R.id.create_a_note_button);
        recyclerView = view.findViewById(R.id.list_recycler_view);

        // Добавим разделитель карточек (декоратор) к нашему списку
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    //этот метод для обработки кнопок и другой работы
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        adapter = new NotesAdapter();
        adapter.setOnItemClickListener(getContract()::editNote);

        //для списка объявляем layoutManager как StaggeredGridLayoutManager с 2 столбцами(плитка)
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        repo = new FirebaseNotesRepo();

        updateAllNotes(noteList);

        buttonCreateNote.setOnClickListener(v -> {
            getContract().createNewNote();
        });

        repo.subscribe(subscriber);
    }

    public void addNote(NotesEntity newNote) {
        //при редактировании заметки проверяем новая это заметка или старая
        NotesEntity sameNote = findNoteWithId(newNote.uid);
        //если такая заметка есть, то удаляем ее
        if (sameNote != null) {
            noteList.remove(sameNote);
            //и нужно удалить ее в базе
        }

        // и добавляем новую заметку
        noteList.add(newNote);
        //отправляем заметку в FireStore
        FirebaseNotesRepo.sendNote(newNote);
        updateAllNotes(noteList);
    }

    //если находим совпадающие id, то возвращаем их в заметку
    @Nullable
    private NotesEntity findNoteWithId(String id) {
        for (NotesEntity note : noteList) {
            if (note.uid.equals(id)) {
                return note;
            }
        }
        return null;
    }

    //при добавлении новой заметки
    //добавляется элемент списка с названием заметки и кратким описанием
    private void updateAllNotes(List<NotesEntity> notes) {
        adapter.setData(notes, uid);
        List<NotesEntity> sortedNotes = repo.getNotes();
        Collections.sort(sortedNotes, new Comparator<NotesEntity>() {
            @Override
            public int compare(NotesEntity o1, NotesEntity o2) {
                return o1.getCreationDate() > o2.getCreationDate() ? 1 : -1;
            }
        });
        adapter.setData(sortedNotes, uid);
        adapter.notifyDataSetChanged();
    }

    //этот метод вызываем в холдере
    void deleteNote(NotesEntity noteEntity) {
        for (NotesEntity note : noteList) {
            if (noteEntity.getUid().equals(note.uid)) {
                noteList.remove(note);
                FirebaseNotesRepo.deleteNoteInFirestore(note);
                updateAllNotes(noteList);
                break;
            }
        }
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    //с пом. этого интерфейса
    //можно создать новую заметку и отредактировать старые
    // (фрагмент ничего не должен знать об активити и других фрагментах)
    interface Contract {
        void createNewNote();

        //когда мы нажимаем на заметку в списке, то можем ее отредактировать
        void editNote(NotesEntity note);
    }
}
