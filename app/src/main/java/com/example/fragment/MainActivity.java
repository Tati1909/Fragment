package com.example.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NotesFragment.Controller {
    public TextView resultTextView;
    private DossierEntity myDossier = new DossierEntity("День рождения",
            "брат", "12.06");
    private NotesFragment notesFragment = NotesFragment.newInstance(myDossier);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_fragment_button).setOnClickListener(v -> {
            //показываем фрагмент через getSupportFragmentManager()
            getSupportFragmentManager()
                    .beginTransaction()
                    //добавляем во FrameLayout из activity_main.xml наш фрагмент notesFragment
                    .add(R.id.fragment_container, notesFragment)
                    .commit();
        });
    }

    //метод интерфйса для передачи данных
    //из фрагмента в resultTextView (майнактивити)
    @Override
    public void saveResult(DossierEntity dossier) {
        myDossier = dossier;
        resultTextView.setText(String.format("%s %s %s",
                dossier.title,
                dossier.description,
                dossier.date));
    }
}