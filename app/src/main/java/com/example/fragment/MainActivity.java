package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NotesFragment.Controller, NotesListFragment.Controller {
    private FloatingActionButton buttonCreateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //заменяем ActionBar на ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //добавила кнопку плюс для создания новой заметки
        buttonCreateNote = findViewById(R.id.create_a_note);
        buttonCreateNote.setOnClickListener(v ->
                Toast.makeText(this, "Создаем папку", Toast.LENGTH_LONG).show());

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.list_container, new NotesListFragment()).
                commit();
    }

    private void showPopup() {
        PopupMenu popupMenu = new PopupMenu(this, buttonCreateNote);
        popupMenu.inflate(R.menu.main_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            onOptionsItemSelected(menuItem);
            return false;
        });
        popupMenu.show();
    }

    //определяем верхнее меню приложения
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

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
    public void saveResult(NotesEntity dossier) {
        //todo
    }

    //метод интерфейса
    @Override
    public void openNotesScreen(NotesEntity dossier) {
        //если ориентация горизонтальная(boolean),то досье будем ложить в detail_container_land
        boolean isLandScape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager().
                beginTransaction().
                add(isLandScape ? R.id.detail_container_land : R.id.list_container, NotesFragment.newInstance(dossier)).
                //при нажатии назад, приложение переходит на первый фрагмент
                        addToBackStack(null).
                commit();

    }
}