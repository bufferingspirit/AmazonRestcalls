package com.example.admin.amazonrestcalls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile implements Serializable{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;
    @SerializedName("author")
    @Expose
    private String author;

    public Profile(String title, String Author, String URL){
        this.title = title;
        this.author = Author;
        this.imageURL = URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
