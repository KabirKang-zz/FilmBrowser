package com.kabirkang.filmbrowser.film;

/**
 * Created by kabirkang on 11/29/16.
 */

public class FilmReview {
    String id;
    String author;
    String content;
    String url;

    public FilmReview(String id, String content, String author, String url) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.url = url;
    }
}
