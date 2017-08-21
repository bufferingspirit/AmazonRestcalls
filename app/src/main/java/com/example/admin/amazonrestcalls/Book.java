package com.example.admin.amazonrestcalls;

import java.io.Serializable;

/**
 * Created by Admin on 8/20/2017.
 */

public class Book implements Serializable{

    String Title;
    String Author;
    String Picture;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public Book(String title, String author, String picture) {

        Title = title;
        Author = author;
        Picture = picture;
    }
}
