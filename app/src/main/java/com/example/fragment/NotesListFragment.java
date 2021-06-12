package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class NotesListFragment extends Fragment {
    private final ArrayList<NotesEntity> noteList = new ArrayList<>();
    private Button buttonCreateNote;
    /*
        private final NotesEntity dossier1 = new NotesEntity("юбилей", "мама", "15.03");
        private final NotesEntity dossier2 = new NotesEntity("ДР", "пап", "17.04");
        private final NotesEntity dossier3 = new NotesEntity("годовщина", "брат", "21.03");
        private final NotesEntity dossier4 = new NotesEntity("юбилей", "доча", "6.08");
        private final NotesEntity dossier5 = new NotesEntity("ДР", "мурзик", "2.08");
    */
    private LinearLayout listLinearLayout;

   /* private void addNotesToList(NotesEntity notesEntity) {
        Button button = new Button(getContext());
        button.setText(notesEntity.toString());
        button.setOnClickListener(v -> {
            //при нажатии на кнопку контроллер должен открыть кнопку
            //и передать в кнопку досье
            ((Contract) getActivity()).createNewNote(notesEntity);
        });

        listLinearLayout.addView(button);
    }); */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //делаем проверку, что активити имплементирует интерфейс Contract
        if (!(context instanceof Contract)) {
            throw new RuntimeException("Activity must implement NotesListController");
        }
    }

    //метод для инициализации наших вьюшек
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        //кнопка создания новой заметки
        buttonCreateNote = view.findViewById(R.id.create_a_note);
        listLinearLayout = view.findViewById(R.id.list_linear_layout);
        return view;
    }

    //этот метод для обработки кнопок и другой работы
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        renderList(noteList);
        buttonCreateNote.setOnClickListener(v -> {
            getContract().createNewNote();
        });
    }

    public void addNote(NotesEntity note) {
        //добавляем в массив заметок новую заметку
        noteList.add(note);
        renderList(noteList);
    }

    private void renderList(List<NotesEntity> notes) {
        listLinearLayout.removeAllViews();
        for (NotesEntity note : notes) {
            Button button = new Button(getContext());
            button.setText(note.title);
            listLinearLayout.addView(button);
        }
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    //с пом. этого интерфейса
    //контракт создаст новую заметку
    // (фрагмент ничего не должен знать об активити и других фрагментах)
    interface Contract {
        void createNewNote();
    }
}
