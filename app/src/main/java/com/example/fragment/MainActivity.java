package com.example.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DossierEntity myDossier = new DossierEntity("День рождения",
            "у брата, поздравить", "12.06.");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_fragment_button).setOnClickListener(v -> {
            NotesFragment notesFragment = NotesFragment.newInstance(myDossier);
            //показываем фрагмент через getSupportFragmentManager()
            getSupportFragmentManager()
                    .beginTransaction()
                    //добавляем во FrameLayout из activity_main.xml наш фрагмент notesFragment
                    .add(R.id.fragment_container, notesFragment)
                    .commit();
        });
    }
}