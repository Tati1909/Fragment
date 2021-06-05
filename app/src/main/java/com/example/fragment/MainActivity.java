package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NotesFragment.Controller, NotesListFragment.Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //заменяем ActionBar на ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.container, new NotesListFragment()).
                commit();
    }

    //определяем меню приложения
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        //нужно как-то впихнуть в бар кнопку с разворотм папок
        MenuItem folders = menu.findItem(R.id.folders);
        return true;
    }

    //обработка выбора пункта меню (аналог SetListener)
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.display_type:
                Toast.makeText(this, "Плитка или список", Toast.LENGTH_LONG).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "Настройки приложения", Toast.LENGTH_LONG).show();
                break;
            case R.id.folders:
                Toast.makeText(this, "Посмотреть все папки", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
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