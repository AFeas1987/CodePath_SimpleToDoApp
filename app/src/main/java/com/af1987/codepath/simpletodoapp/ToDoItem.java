package com.af1987.codepath.simpletodoapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "db_items")
public class ToDoItem {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "item_name")
    private String name;

    @ColumnInfo (name = "starred")
    private boolean starred;

//    @ColumnInfo(name = "modified_at")
//    @TypeConverters({TimestampConverter.class})
//    private Date modifiedAt;

    private String body;


    public ToDoItem(String name, String body, boolean starred) {
        this.body = body;
        this.name = name;
        this.starred = starred;
    }


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getBody() {return body;}
    void setBody(String body) {this.body = body;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public boolean isStarred() {return starred;}
    void setStarred(boolean starred) {this.starred = starred;}
    public void toggleStarred() {starred = !starred;}
}
