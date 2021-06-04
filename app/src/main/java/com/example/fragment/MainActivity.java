package com.example.fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NotesFragment.Controller, NotesListFragment.Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.container, new NotesListFragment()).
                commit();
    }

    //метод интерфйса для передачи данных
    //из фрагмента в resultTextView (майнактивити)
    @Override
    public void saveResult(DossierEntity dossier) {
        //todo
    }

    //метод интерфейса
    @Override
    public void openNotesScreen(DossierEntity dossier) {
        //если ориентация горизонтальная(boolean),то досье будем ложить в detail_container
        boolean isLandScape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager().
                beginTransaction().
                add(isLandScape ? R.id.detail_container : R.id.container, NotesFragment.newInstance(dossier)).
                //при нажатии назад, приложение переходит на первый фрагмент
                        addToBackStack(null).
                commit();

    }
}