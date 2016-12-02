package com.kabirkang.filmbrowser.film;

import java.util.List;

/**
 * Created by kabir on 12/2/2016.
 */

public class FilmListResult {
    private List<Film> results;

    public List<Film> getMovieList() {
        return results;
    }

    public void setMovieList(List<Film> movieList) {
        results = movieList;
    }
}
