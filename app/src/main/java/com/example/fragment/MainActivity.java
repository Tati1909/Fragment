package com.example.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements EditNoteFragment.Controller, NotesListFragment.Contract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //заменяем ActionBar на ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showNotesListFragment();
    }

    //добавляем наш фрагмент в контейнер
    private void showNotesListFragment() {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.container, new NotesListFragment()).
                commit();
    }

    private void showEditNoteFragment() {
        getSupportFragmentManager().
                beginTransaction().
                addToBackStack(null).
                add(R.id.container, new EditNoteFragment()).
                commit();
    }


   /* private void showPopup() {
        PopupMenu popupMenu = new PopupMenu(this, buttonCreateNote);
        popupMenu.inflate(R.menu.main_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            onOptionsItemSelected(menuItem);
            return false;
        });
        popupMenu.show();
    } */

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

    //метод интерфейса должен создать новую детальную заметку
    // с возможностью редактирования
    @Override
    public void createNewNote() {
        showEditNoteFragment();
        /*
        //если ориентация горизонтальная(boolean),то досье будем ложить в detail_container_land
        boolean isLandScape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager().
                beginTransaction().
                //при нажатии назад, приложение переходит на первый фрагмент
                        addToBackStack(null).
                //replace вместо add, чтобы фрагменты не накладывались друг на друга
                        replace(isLandScape ? R.id.detail_container_land : R.id.container,
                        EditNoteFragment.newInstance(dossier)).
                commit();
*/
    }
}
