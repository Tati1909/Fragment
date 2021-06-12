package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class EditNoteFragment extends Fragment {
    //создаем ключ для передачи данных во фрагмент(используем Parcelable)
    public static final String DOSSIER_ARGS_KEY = "DOSSIER_ARGS_KEY";

    private NotesEntity dossier = null;
    private EditText titleN;
    private EditText descriptionN;
    private EditText dateN;
    private Button saveButton;

    //конструктор должен быть без аргуметов, т к программа упадет после поворота экрана
    public EditNoteFragment() {

    }

    //статический метод, который возвращает NotesFragment
    //при его помощи мы будем передавать(ложить) данные во фрагмент
    public static EditNoteFragment newInstance(NotesEntity notesEntity) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle args = new Bundle();

        args.putParcelable(DOSSIER_ARGS_KEY, notesEntity);
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
            dossier = getArguments().getParcelable(DOSSIER_ARGS_KEY);
        }
    }

    //вьюшка создалась и раздулась, здесь мы инициализируем наши вьюшки
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_detail, container, false);

        //инициализация вьюшек EditText для передачи во фрагмент при нажатии на кнопку
        titleN = view.findViewById(R.id.note_title);
        descriptionN = view.findViewById(R.id.note_description);
        dateN = view.findViewById(R.id.date_of_creation);
        saveButton = view.findViewById(R.id.save_button);

        return view;
    }

    //вызывается, когда вьюшки уже были созданы
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        saveButton.setOnClickListener(v -> {
            getContract().saveNote(gatherNote());
        });

        /* //передаем данные заметок во фрагмент
        titleN.setText(dossier.title);
        descriptionN.setText(dossier.description);
        dateN.setText(dossier.date);  */
    }

    private NotesEntity gatherNote() {

        return new NotesEntity(
                titleN.getText().toString(),
                descriptionN.getText().toString(),
                NotesEntity.getCurrentDate()
        );
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    interface Contract {
        //с помощью этого интерфейса будем предавать данные из
        //фрагмента в майнактивити
        void saveNote(NotesEntity note);
    }
}
