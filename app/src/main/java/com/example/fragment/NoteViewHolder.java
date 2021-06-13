package com.example.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleTextView;
    private final TextView descriptionTextView;
    private final CardView cardView;
    private NotesEntity noteEntity;

    public NoteViewHolder(@NonNull ViewGroup parent, @Nullable NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        cardView = (CardView) itemView;
        titleTextView = itemView.findViewById(R.id.subject_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
        cardView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(noteEntity);
            }
        });
    }

    //привязать/задать значения
    public void bind(NotesEntity notesEntity) {
        this.noteEntity = notesEntity;
        titleTextView.setText(notesEntity.title);
        descriptionTextView.setText(notesEntity.description);
    }
}
