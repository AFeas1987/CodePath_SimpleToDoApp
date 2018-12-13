package com.af1987.codepath.simpletodoapp.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.af1987.codepath.simpletodoapp.ToDoItem;

import java.util.List;

public class ItemRepository {

        private ItemRoom itemRoom;
        private LiveData<List<ToDoItem>> items;

        public ItemRepository(Application app) {
            itemRoom = ItemRoom.getAppDatabase(app);
            items = itemRoom.accessRoom().fetchAllItems();
        }

        public void insertItem(String name,
                               String body) {
            insertItem(name, body, false);
        }

        public void insertItem(String name,
                                String body,
                                boolean starred) {
            ToDoItem note = new ToDoItem(name, body, starred);
            insertItem(note);
        }

        public void insertItem(final ToDoItem item) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    itemRoom.accessRoom().insertItem(item);
                    return null;
                }
            }.execute();
        }

        public void updateItem(final ToDoItem item) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    itemRoom.accessRoom().updateItem(item);
                    return null;
                }
            }.execute();
        }

        public void deleteItem(final int id) {
            final LiveData<ToDoItem> item = getItem(id);
            if(item != null) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        itemRoom.accessRoom().deleteItem(item.getValue());
                        return null;
                    }
                }.execute();
            }
        }

        public void deleteItem(final ToDoItem item) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    itemRoom.accessRoom().deleteItem(item);
                    return null;
                }
            }.execute();
        }

        public LiveData<ToDoItem> getItem(int id) {
            return itemRoom.accessRoom().getItem(id);
        }

        public LiveData<List<ToDoItem>> getItems() {
            return items;
        }

}
