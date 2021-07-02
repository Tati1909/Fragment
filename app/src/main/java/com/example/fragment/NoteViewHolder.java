package com.example.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener, NotesAdapter.OnDeleteListener {

    private final TextView titleTextView;
    private final TextView descriptionTextView;
    private final CardView cardView;
    private NotesEntity noteEntity;

    public NoteViewHolder(@NonNull ViewGroup parent,
                          @Nullable NotesAdapter.OnItemClickListener clickListener) {
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

        //обработчик длинного нажатия на элемент списка
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemView.showContextMenu();
                return true;
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //вызываем AlertDialog с подтверждением удаления заметки
        showAlertDialog();
        return true;
    }

    @Override
    public void deleteNote(NotesEntity note) {
        this.noteEntity = note;
        FirebaseNotesRepo.deleteNoteInFirestore(noteEntity);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(itemView.getContext())
                .setTitle("Внимание")
                .setMessage("Вы действительно хотите удалить заметку?")
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deleteNote(noteEntity);
                        // Toast.makeText(itemView.getContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(itemView.getContext(), "Заметка сохранена", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(R.drawable.ic_alert_delete)
                .show();
    }
}
