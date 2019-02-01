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

import com.af1987.codepath.simpletodoapp.util.ItemViewModel;
import com.af1987.codepath.simpletodoapp.util.ItemsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ToDoItem> items;
    private ItemsAdapter itemsAdapter;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.getAllItems().observe(this, toDoItems -> {
            items = toDoItems;
            itemsAdapter.setItems(toDoItems);
        });
        itemsAdapter = new ItemsAdapter(this::showItemDialog, (item, i) -> {
            item.toggleStarred();
            viewModel.update(item);
        });
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
                viewModel.delete(items.remove(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(rvItems);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showItemDialog(null, null));
    }

    private void showItemDialog(@Nullable ToDoItem item, @Nullable Integer i) {
        View dialogView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.dialog_add, null);
        final EditText etTitle = dialogView.findViewById(R.id.etTitle);
        final EditText etBody = dialogView.findViewById(R.id.etBody);
        final CheckBox btnStar = dialogView.findViewById(R.id.btnStarDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("To-do item:")
                .setMessage((item == null ? "Create" : "Edit") + " your item.")
                .setView(dialogView)
                .setPositiveButton(item == null ? "Create" : "Save", null)
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        if (item != null) {
            etTitle.setText(item.getTitle());
            etBody.setText(item.getBody());
            btnStar.setChecked(item.isStarred());
        }
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(alertView -> {
                    if (etTitle.getText().toString().equals("") ||
                            etBody.getText().toString().equals("")) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).startAnimation(
                                AnimationUtils.loadAnimation(this, R.anim.shake));
                        Snackbar.make(dialogView, "Empty field detected", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    else if (item == null){
                        final boolean starred = btnStar.isChecked();
                        items.add(new ToDoItem(etTitle.getText().toString(),
                                etBody.getText().toString(), starred));
                        viewModel.insert(items.get(items.size() - 1));
                        dialog.dismiss();
                    }
                    else {
                        item.setTitle(etTitle.getText().toString());
                        item.setBody(etBody.getText().toString());
                        item.setStarred(btnStar.isChecked());
                        viewModel.update(item);
                        Toast.makeText(this, "Item updated.", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
    }
}
