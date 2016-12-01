package com.kabirkang.filmbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.kabirkang.filmbrowser.film.Film;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Kabir on 9/26/2016.
 */
public class FilmAdapter extends ArrayAdapter<Film> {
    private static final String LOG_TAG = FilmAdapter.class.getSimpleName();
    public FilmAdapter(Activity context, List<Film> films) {
        super(context, 0, films);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Film film = getItem(position);
        String posterUrl = film.getPosterPath();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.film_item, parent, false);
        }

        ImageView filmPoster = (ImageView) convertView.findViewById(R.id.film_poster);
        Picasso.with(getContext()).load(posterUrl).into(filmPoster);
        return convertView;
    }
}
