package com.af1987.codepath.simpletodoapp.util;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.af1987.codepath.simpletodoapp.ToDoItem;
import com.af1987.codepath.simpletodoapp.room.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

        private ItemRepository repo;
        private LiveData<List<ToDoItem>> items;

        public ItemViewModel (Application app) {
            super(app);
            repo = new ItemRepository(app);
            items = repo.getItems();
        }

        LiveData<List<ToDoItem>> getAllItems() { return items; }

        public void insert(ToDoItem item) { repo.insertItem(item); }
    }
