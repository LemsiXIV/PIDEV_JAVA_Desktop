package com.webandit.webuild.controllers.mouna.front;

public class PostDetails {
    private static PostDetails instance;
    private int idPost;

    public PostDetails() {
    }
    public static synchronized PostDetails getInstance() {
        if (instance == null) {
            instance = new PostDetails();
        }
        return instance;
    }
    public void setIdPost(int id) {
        this.idPost = id;
    }

    public int getIdPost() {
        return idPost;
    }

    public void clearSession() {
        idPost = 0;

    }
}
