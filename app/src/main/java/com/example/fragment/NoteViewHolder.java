package com.example.fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener {

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

        //добавляем контекстное меню в элемент списка(itemView) c командой удалить
        itemView.setOnCreateContextMenuListener((contextMenu, view, contextMenuInfo) -> {
            contextMenu.setHeaderTitle("Сделай свой выбор");
            contextMenu.add("Удалить").setOnMenuItemClickListener(this);
        });

        //обработчик нажатия на элемент списка
        itemView.setOnClickListener(v -> {
            itemView.showContextMenu();
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(itemView.getContext(), noteEntity.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
