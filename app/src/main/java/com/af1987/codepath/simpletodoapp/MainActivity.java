package com.af1987.codepath.simpletodoapp;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.af1987.codepath.simpletodoapp.room.ItemRepository;
import com.af1987.codepath.simpletodoapp.util.ItemsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<ToDoItem> items;
    private ItemsAdapter itemsAdapter;
    private static ItemRepository itemRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemRepo = new ItemRepository(getApplication());
        setContentView(R.layout.activity_main);
        ItemsAdapter.ItemViewModel viewModel = ViewModelProviders.of(this).get(ItemsAdapter.ItemViewModel.class);
        viewModel.getAllItems().observe(this, toDoItems -> {
            items = toDoItems;
            itemsAdapter.setItems(toDoItems);
        });
        itemsAdapter = new ItemsAdapter(this::showItemDialog);
        RecyclerView rvItems = findViewById(R.id.rvItems);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                        @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.START | ItemTouchHelper.END);}

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {return true;}

            @Override
            public void onMoved(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, int fromPos,
                                @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
                items.add(toPos, items.remove(fromPos));
                itemsAdapter.notifyItemMoved(fromPos, toPos);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                itemRepo.deleteItem(items.remove(viewHolder.getAdapterPosition()));
                itemsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rvItems);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            showItemDialog(null, null);
        });
    }

    private void showItemDialog(@Nullable ToDoItem item, @Nullable Integer i) {
        View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_add, null);
        final EditText etName = dialogView.findViewById(R.id.etName);
        final EditText etBody = dialogView.findViewById(R.id.etBody);
        final CheckBox btnStar = dialogView.findViewById(R.id.btnStarDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("To-do item:")
                .setMessage((item == null ? "Create" : "Edit") + " your item.")
                .setView(dialogView)
                .setPositiveButton(item == null ? "Ok" : "Save", null)
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        if (item != null) {
            etName.setText(item.getName());
            etBody.setText(item.getBody());
            btnStar.setChecked(item.isStarred());
        }
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(alertView -> {
                    if (etName.getText().toString().equals("") ||
                            etBody.getText().toString().equals("")) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).startAnimation(
                                AnimationUtils.loadAnimation(this, R.anim.shake));
                        Snackbar.make(dialogView, "Empty field detected", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    else if (item == null){
                        final boolean starred = btnStar.isChecked();
                        items.add(new ToDoItem(etName.getText().toString(),
                                etBody.getText().toString(), starred));
                        itemRepo.insertItem(items.get(items.size() - 1));
                        itemsAdapter.notifyItemInserted(items.size() - 1);
                        dialog.dismiss();
                    }
                    else {
                        item.setName(etName.getText().toString());
                        item.setBody(etBody.getText().toString());
                        item.setStarred(btnStar.isChecked());
                        itemRepo.updateItem(item);
                        itemsAdapter.notifyItemChanged(i);
                        Toast.makeText(this, "Item updated.", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
    }
}
