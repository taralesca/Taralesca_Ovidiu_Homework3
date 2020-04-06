package com.example.taralesca_ovidiu_homework3.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;

    public User() {
    }

    public User(int id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User fromJSON(JSONObject data) throws JSONException {
        this.id = data.getInt("id");
        this.name = data.getString("name");
        this.username = data.getString("username");
        this.email = data.getString("email");
        return this;
    }
}
