package com.example.fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseNotesRepo {

    private static final String NOTES_TABLE_TITLE = "notes";
    private final List<Runnable> subscribe = new ArrayList<>();
    private final FirebaseFirestore db;
    private List<NotesEntity> cache = new ArrayList<>();

    public FirebaseNotesRepo() {
        db = FirebaseFirestore.getInstance();
        //получаем данные из FireStore
        db.collection(NOTES_TABLE_TITLE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                cache = new ArrayList<>();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    cache.add(document.toObject(NotesEntity.class));
                }
            }
        });
        
    }

    public static void sendNote(NotesEntity note) {
        (FirebaseFirestore.getInstance()).collection(NOTES_TABLE_TITLE).add(note);
    }

    List<NotesEntity> getNotes() {
        return cache;
    }

    void subscribe(Runnable subscriber) {
    }

    void unsubscribe(Runnable subscriber) {

    }
}
