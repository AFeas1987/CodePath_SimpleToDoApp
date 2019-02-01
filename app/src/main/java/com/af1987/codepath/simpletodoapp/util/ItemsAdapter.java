package com.af1987.codepath.simpletodoapp.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.af1987.codepath.simpletodoapp.R;
import com.af1987.codepath.simpletodoapp.ToDoItem;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<ToDoItem> items;
    private RecyclerClickListener clickListener;
    private RecyclerClickListener starListener;



    public ItemsAdapter(RecyclerClickListener clickListener, RecyclerClickListener starListener) {
        this.clickListener = clickListener;
        this.starListener = starListener;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ToDoItem item = items.get(i);
        viewHolder.tvName.setText(item.getTitle());
        viewHolder.tvBody.setText(item.getBody());
        viewHolder.btnStar.setChecked(item.isStarred());
        viewHolder.btnStar.setOnClickListener(view -> starListener.onItemClick(item, i));
        viewHolder.itemView.setOnClickListener(view -> clickListener.onItemClick(item, i));
    }

    @Override
    public int getItemCount() {return items != null ? items.size() : 0;}

    public void setItems(List<ToDoItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvBody;
        CheckBox btnStar;


        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            btnStar = itemView.findViewById(R.id.btnStarRV);
        }
    }
}
