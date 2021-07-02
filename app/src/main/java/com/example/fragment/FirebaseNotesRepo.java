package com.example.fragment;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseNotesRepo {

    private static final String NOTES_TABLE_TITLE = "notes";
    private static List<NotesEntity> cache = new ArrayList<>();
    private final FirebaseFirestore db;
    private final List<Runnable> subscribers = new ArrayList<>();

    public FirebaseNotesRepo() {
        db = FirebaseFirestore.getInstance();

        //кэшируем заметки при отсутствии интернета(стоит вроде по умолчанию)
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        db.setFirestoreSettings(settings);

        //здесь мы будем читать данные из FireStore
        //заполняем кэш
        db.collection(NOTES_TABLE_TITLE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                refillCache(queryDocumentSnapshots);
            }
        });
        //узнаем, что в FireStore что-то изменилось(подписываемся на каждое обновление)
        db.collection(NOTES_TABLE_TITLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                refillCache(value);
            }
        });
    }

    //отправляем заметки в FireStore
    public static void sendNote(NotesEntity note) {
        FirebaseFirestore.getInstance().collection(NOTES_TABLE_TITLE).add(note);
    }

    //удаляем заметку в FireStore
    public static void deleteNoteInFirestore(NotesEntity note) {
        (FirebaseFirestore.getInstance()).collection(NOTES_TABLE_TITLE).document(note.uid).delete();
    }

    //получаем обновленный кэш
    List<NotesEntity> getNotes() {
        return cache;
    }

    //вынесли повторы в отдельный метод
    private void refillCache(@Nullable QuerySnapshot snapshot) {
        if (snapshot == null) return;
        cache = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            cache.add(document.toObject(NotesEntity.class));
        }
        notifySubscribers();
    }

    //уведомляем всех пользователей об изменениях
    private void notifySubscribers() {
        for (Runnable subscriber : subscribers) {
            subscriber.run();
        }
    }

    //подписаться
    void subscribe(Runnable subscriber) {
        subscribers.add(subscriber);
    }

    //отписаться
    void unsubscribe(Runnable subscriber) {
        subscribers.remove(subscriber);
    }
}
