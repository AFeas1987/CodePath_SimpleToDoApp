package com.af1987.codepath.simpletodoapp.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.af1987.codepath.simpletodoapp.ToDoItem;


@Database(entities = {ToDoItem.class}, version = 1, exportSchema = false)
public abstract class ItemRoom extends RoomDatabase {

    public abstract RoomAccessor accessRoom();

    private static final String DATABASE_NAME = "db_items";
    private static volatile ItemRoom INSTANCE;
    private static RoomDatabase.Callback roomCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    Log.d("AF2019", "@@@@@@@@@@@@   Database is open!!!  @@@@@@@@@@@@@@");
                }
            };

    static ItemRoom getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ItemRoom.class, DATABASE_NAME)
                                .fallbackToDestructiveMigration()
                                .addCallback(roomCallback)
                                .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

//        private final RoomAccessor accessor;

//        PopulateDbAsync(ItemRoom room) {
//            accessor = room.accessRoom();
//        }

//        @Override
//        protected Void doInBackground(final Void... params) {
//            accessor.deleteAll();
//            ToDoItem item = new ToDoItem("Hello");
//            accessor.insert(item);
//            item = new Word("World");
//            accessor.insert(item);
//            return null;
//        }
//    }
}
