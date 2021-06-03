package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class NotesFragment extends Fragment {
    private DossierEntity dossier = null;

    private EditText titleN;
    private EditText descriptionN;
    private EditText dateN;

    public NotesFragment(DossierEntity dossierEntity) {
        dossier = dossierEntity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //вьюшка создалась и раздулась
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, null);
        return view;
    }

    //вызывается, когда вьюшки уже были созданы
    //инициализация вьюшек EditText для передачи во фрагмент при нажатии на кнопку
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        titleN = view.findViewById(R.id.note_title);
        descriptionN = view.findViewById(R.id.note_description);
        dateN = view.findViewById(R.id.date_of_creation);

        //передаем данные заметок во фрагмент
        titleN.setText(dossier.title);
        descriptionN.setText(dossier.description);
        dateN.setText(dossier.date);

    }
}
