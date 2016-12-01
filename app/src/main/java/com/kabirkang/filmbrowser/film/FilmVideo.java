package com.kabirkang.filmbrowser.film;

/**
 * Created by kabirkang on 11/29/16.
 */

public class FilmVideo {
    String id;
    String iso_639_1;
    String iso_3166_1;
    String key;
    String name;
    String site;
    String size;
    int type;

    public FilmVideo(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, String size, int type) {
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }
}
