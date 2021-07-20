package com.example.moviedetails;

public class Modal {
    long id;
    String title,path,vote,releaseDate;

    public Modal(long id, String title, String path,String vote, String releaseDate) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.vote = vote;
        this.releaseDate = releaseDate;

    }
}
