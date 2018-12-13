package com.af1987.codepath.simpletodoapp.util;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.af1987.codepath.simpletodoapp.R;
import com.af1987.codepath.simpletodoapp.ToDoItem;
import com.af1987.codepath.simpletodoapp.room.ItemRepository;
import com.af1987.codepath.simpletodoapp.util.RecyclerClickListener;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<ToDoItem> items;
    private RecyclerClickListener listener;

    public ItemsAdapter(RecyclerClickListener listener) {
        this.listener = listener;
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
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvBody.setText(item.getBody());
        viewHolder.btnStar.setChecked(item.isStarred());
        viewHolder.btnStar.setOnClickListener(view -> {
            item.toggleStarred();
            notifyItemChanged(i);
        });
        viewHolder.itemView.setOnClickListener(view -> {
            listener.onItemClick(item, i);
        });
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
            tvName = itemView.findViewById(R.id.tvName);
            tvBody = itemView.findViewById(R.id.tvBody);
            btnStar = itemView.findViewById(R.id.btnStarRV);
        }
    }

    public static class ItemViewModel extends AndroidViewModel {

            private ItemRepository repo;
            private LiveData<List<ToDoItem>> items;

            public ItemViewModel (Application app) {
                super(app);
                repo = new ItemRepository(app);
                items = repo.getItems();
            }

            public LiveData<List<ToDoItem>> getAllItems() {return items;}

            public void insert(ToDoItem item) {repo.insertItem(item);}
        }
}
