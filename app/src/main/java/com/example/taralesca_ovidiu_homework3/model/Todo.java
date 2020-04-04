package com.example.taralesca_ovidiu_homework3.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Todo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public Todo() {
    }

    public Todo(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    public Todo fromJSON(JSONObject data) throws JSONException {
        this.id = data.getInt("id");
        this.title = data.getString("title");
        this.completed = data.getBoolean("completed");
        return this;
    }
}
