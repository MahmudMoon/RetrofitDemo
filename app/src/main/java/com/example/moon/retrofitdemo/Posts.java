package com.example.moon.retrofitdemo;

public class Posts {
    private int id;
    private int userId;
    private String title;
    private String body;

    public Posts(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
