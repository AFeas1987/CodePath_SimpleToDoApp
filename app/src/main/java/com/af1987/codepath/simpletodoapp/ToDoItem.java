package com.af1987.codepath.simpletodoapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "db_items")
public class ToDoItem {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo (name = "starred")
    private boolean starred;

//    @ColumnInfo(title = "modified_at")
//    @TypeConverters({TimestampConverter.class})
//    private Date modifiedAt;

    @ColumnInfo (name = "body")
    private String body;


    public ToDoItem(String title, String body, boolean starred) {
        this.body = body;
        this.title = title;
        this.starred = starred;
    }


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getBody() {return body;}
    void setBody(String body) {this.body = body;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public boolean isStarred() {return starred;}
    void setStarred(boolean starred) {this.starred = starred;}
    public void toggleStarred() {starred = !starred;}
}
