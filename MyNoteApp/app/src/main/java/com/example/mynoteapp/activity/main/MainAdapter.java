package com.example.mynoteapp.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynoteapp.R;
import com.example.mynoteapp.model.Note;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter> {
    private Context context;
    private List<Note> items;
    private ItemClickListener itemClickListener;

    public MainAdapter(Context context, List<Note> items, ItemClickListener itemClickListener) {
        this.context = context;
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MainAdapter.RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.RecyclerViewAdapter holder, int position) {
        Note item = items.get(position);
        holder.tv_title.setText(item.getTitle());
        holder.tv_note.setText(item.getNote());
        holder.tv_date.setText(item.getDate());
        holder.card_item.setCardBackgroundColor(item.getColor());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements ItemClickListener, View.OnClickListener {
        TextView tv_title, tv_note, tv_date;
        CardView card_item;
        ItemClickListener itemClickListener;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            tv_note = itemView.findViewById(R.id.note);
            tv_date = itemView.findViewById(R.id.date);
            card_item = itemView.findViewById(R.id.card_item);
            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
