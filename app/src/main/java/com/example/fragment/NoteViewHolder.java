package com.example.fragment;

import android.app.AlertDialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void bind(NotesEntity notesEntity, String uid) {
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
                itemView.showContextMenu(10, 10);
                return true;
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //вызываем AlertDialog
        showAlertDialog();
        //здесь нужно удалить заметку
        return true;
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(itemView.getContext())
                .setTitle("Внимание")
                .setMessage("Вы действительно хотите удалить заметку?")
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (d, i) -> {
                    Toast.makeText(itemView.getContext(), R.string.yes, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, (d, i) -> {
                    Toast.makeText(itemView.getContext(), R.string.no, Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}
