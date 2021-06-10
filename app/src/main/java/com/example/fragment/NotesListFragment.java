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
    private final DossierEntity dossier1 = new DossierEntity("юбилей", "мама", "15.03");
    private final DossierEntity dossier2 = new DossierEntity("ДР", "пап", "02.10");
    private final DossierEntity dossier3 = new DossierEntity("ДР", "брат", "01.06");
    private final DossierEntity dossier4 = new DossierEntity("юбилей", "доча", "28.04");
    private final DossierEntity dossier5 = new DossierEntity("ДР", "мурзик", "2.08");
    private LinearLayout linearLayout;

    private void addDossierToList(DossierEntity dossierEntity) {
        Button button = new Button(getContext());
        button.setText(dossierEntity.toString());
        button.setOnClickListener(v -> {
            //при нажатии на кнопку контроллер должен открыть кнопку
            //и передать в кнопку досье
            ((Controller) getActivity()).openNotesScreen(dossierEntity);
        });

        linearLayout.addView(button);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //делаем проверку, что активити имплементирует интерфейс Controller
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement NotesListController");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        linearLayout = view.findViewById(R.id.linear);

        addDossierToList(dossier1);
        addDossierToList(dossier2);
        addDossierToList(dossier3);
        addDossierToList(dossier4);
        addDossierToList(dossier5);
    }

    //с пом. этого интерфейса
    //контроллер должен открывать кнопку фрагмента
    //и передавать в нее досье (фрагмент ничего не должен знать об активити и других фрагментах)
    interface Controller {
        void openNotesScreen(DossierEntity dossier);
    }
}
