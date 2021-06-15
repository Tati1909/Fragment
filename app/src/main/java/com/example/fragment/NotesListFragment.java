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

import java.util.ArrayList;
import java.util.List;

public class NotesListFragment extends Fragment {
    private final ArrayList<NotesEntity> noteList = new ArrayList<>();
    private Button buttonCreateNote;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;

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
        buttonCreateNote = view.findViewById(R.id.create_a_note);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        renderList(noteList);

        buttonCreateNote.setOnClickListener(v -> {
            getContract().createNewNote();
        });
    }

    public void addNote(NotesEntity newNote) {
        //при редактировании заметки проверяем новая это заметка или старая
        NotesEntity sameNote = findNoteWithId(newNote.id);
        //если такая заметка есть, то удаляем ее
        if (sameNote != null) {
            noteList.remove(sameNote);
        }

        // и добавляем новую заметку
        noteList.add(newNote);
        renderList(noteList);
    }

    //если находим совпадающие id, то возвращаем их в заметку
    @Nullable
    private NotesEntity findNoteWithId(String id) {
        for (NotesEntity note : noteList) {
            if (note.id.equals(id)) {
                return note;
            }
        }
        return null;
    }

    //при добавлении новой заметки
    //добавляется кнопка с названием заметки в список
    private void renderList(List<NotesEntity> notes) {
        adapter.setData(notes);
    }

    void deleteNote(String id) {
        for (NotesEntity note : noteList) {
            if (note.getId().equals(id)) {
                noteList.remove(note);
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
