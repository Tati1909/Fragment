package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditNoteFragment extends Fragment {
    //создаем ключ для передачи данных во фрагмент(используем Parcelable)
    public static final String NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY";

    @Nullable
    private NotesEntity note = null;

    private EditText title;
    private EditText description;
    private Button saveButton;

    //статический метод, который возвращает EditNotesFragment
    //при его помощи мы будем передавать(ложить) данные во фрагмент
    public static EditNoteFragment newInstance(@Nullable NotesEntity note) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_EXTRA_KEY, note);
        //передаем аргументы во фрагмент
        editNoteFragment.setArguments(args);
        return editNoteFragment;
    }

    //первый метод жизненнного цикла фрагмента
    //в этом методе мы будем получать данные из досье
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new RuntimeException("Activity must implement NotesController");
        }
        //не забываем проверять на null
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE_EXTRA_KEY);
        }
    }

    //вьюшка создалась и раздулась, здесь мы инициализируем наши вьюшки
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_detail, container, false);

        //инициализация вьюшек EditText для передачи во фрагмент при нажатии на кнопку
        title = view.findViewById(R.id.note_title);
        description = view.findViewById(R.id.note_description);
        saveButton = view.findViewById(R.id.save_button);

        return view;
    }

    //вызывается, когда вьюшки уже были созданы
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        note = getArguments().getParcelable(NOTE_EXTRA_KEY);
        getActivity().setTitle(note == null ? R.string.create_note_title : R.string.edit_note_title);

        fillNote(note);

        saveButton.setOnClickListener(v -> {
            getContract().saveNote(gatherNote());
        });
    }

    private void fillNote(NotesEntity note) {
        if (note == null) return;
        //передаем данные заметок во фрагмент
        title.setText(note.title);
        description.setText(note.description);

    }

    //когда мы редактируем старую заметку
    //то оставляем id и старую дату создания заметки
    private NotesEntity gatherNote() {
        return new NotesEntity(
                note == null ? NotesEntity.generateNewId() : note.uid,
                title.getText().toString(),
                description.getText().toString()
                // note == null ? NotesEntity.getCurrentDate() : note.creationDate
        );
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        //с помощью этого интерфейса будем предавать данные из
        //2 фрагмента в 1 фрагмент
        void saveNote(NotesEntity note);
    }
}
