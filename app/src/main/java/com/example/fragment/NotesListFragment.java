package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class NotesListFragment extends Fragment {
    private final NotesEntity dossier1 = new NotesEntity("юбилей", "мама", "15.03");
    private final NotesEntity dossier2 = new NotesEntity("ДР", "пап", "17.04");
    private final NotesEntity dossier3 = new NotesEntity("годовщина", "брат", "21.03");
    private final NotesEntity dossier4 = new NotesEntity("юбилей", "доча", "6.08");
    private final NotesEntity dossier5 = new NotesEntity("ДР", "мурзик", "2.08");
    private LinearLayout linearLayout;

    private void addNotesToList(NotesEntity notesEntity) {
        Button button = new Button(getContext());
        button.setText(notesEntity.toString());
        button.setOnClickListener(v -> {
            //при нажатии на кнопку контроллер должен открыть кнопку
            //и передать в кнопку досье
            ((Controller) getActivity()).openNotesScreen(notesEntity);
        });

        linearLayout.addView(button);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, null);
    }

    //делаем проверку, что активити имплементирует интерфейс Controller
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement NotesListController");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.linear);

        addNotesToList(dossier1);
        addNotesToList(dossier2);
        addNotesToList(dossier3);
        addNotesToList(dossier4);
        addNotesToList(dossier5);
    }

    //с пом. этого интерфейса
    //контроллер должен открывать кнопку фрагмента
    //и передавать в нее досье (фрагмент ничего не должен знать об активити и других фрагментах)
    interface Controller {
        void openNotesScreen(NotesEntity dossier);
    }
}
