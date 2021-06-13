package com.example.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleTextView;
    private final TextView descriptionTextView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.subject_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
    }

    //привязать/задать значения
    public void bind(NotesEntity notesEntity) {
        titleTextView.setText(notesEntity.title);
        descriptionTextView.setText(notesEntity.description);
    }
}
