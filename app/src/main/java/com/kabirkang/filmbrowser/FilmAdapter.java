package com.kabirkang.filmbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

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

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.film_item, parent, false);
        }

        ImageView filmPoster = (ImageView) convertView.findViewById(R.id.film_poster);

        return convertView;
    }
}
