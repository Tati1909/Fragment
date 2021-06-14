package com.example.fragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<NotesEntity> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //создание вьюшки
    //ViewHolder хранит ссылки на каждый элемент списка
    // (как во фрагментах мы искали заметки по Id)
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent, onItemClickListener);
    }

    //Алаптер принимает на вход массив заметок
    public void setData(List<NotesEntity> notes) {
        data = notes;
        //c спомощью этого метода адаптер уведомляет сам себя,
        //что его данные изменились
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClick(NotesEntity note);
    }

    //в нашу вьюшку мы кладем значения
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        //кладем данные
        holder.bind(data.get(position));
    }

    //получение количества элементов
    @Override
    public int getItemCount() {
        return data.size();
    }
}
