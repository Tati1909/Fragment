package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements EditNoteFragment.Contract, NotesListFragment.Contract {
    private static final String NOTES_LIST_FRAGMENT_TAG = "NOTES_LIST_FRAGMENT_TAG";

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
                add(R.id.container, new NotesListFragment(), NOTES_LIST_FRAGMENT_TAG).
                commit();
    }

    private void showEditNoteFragment() {
        showEditNoteFragment(null);
    }

    //Nullable может быть нулем
    private void showEditNoteFragment(@Nullable NotesEntity note) {
        //если ориентация горизонтальная(boolean),то досье будем ложить в detail_container_land
        boolean isLandScape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager().
                beginTransaction().
                addToBackStack(null).
                replace(isLandScape ? R.id.detail_container_land : R.id.container, EditNoteFragment.newInstance(note)).
                commit();
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
    public void saveNote(NotesEntity note) {
        getSupportFragmentManager().popBackStack();
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager().findFragmentByTag(NOTES_LIST_FRAGMENT_TAG);
        notesListFragment.addNote(note);
    }

    //метод интерфейса должен создать новую детальную заметку
    // с возможностью редактирования
    @Override
    public void createNewNote() {
        showEditNoteFragment();
    }

    //метод интерфейса
    // когда мы нажимаем на заметку в списке, то можем ее отредактировать
    @Override
    public void editNote(NotesEntity note) {
        showEditNoteFragment(note);

    }

    @Override
    public void deleteNote(NotesEntity note) {
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager()
                .findFragmentByTag(NOTES_LIST_FRAGMENT_TAG);
        if (notesListFragment != null) {
            notesListFragment.deleteNote(note);
        }
    }
}
