package com.example.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<NotesEntity> data = new ArrayList<>();

    //Алаптер принимает на вход массив заметок
    public void setData(List<NotesEntity> notes) {
        data = notes;
        //c спомощью этого метода адаптер уведомляет сам себя,
        //что его данные изменились
        notifyDataSetChanged();
    }

    //создание вьюшки
    //ViewHolder хранит ссылки на каждый элемент списка
    // (как во фрагментах мы искали заметки по Id)
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
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
