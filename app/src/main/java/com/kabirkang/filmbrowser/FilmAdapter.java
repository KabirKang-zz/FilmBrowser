package com.kabirkang.filmbrowser;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Kabir on 9/26/2016.
 */
public class FilmAdapter extends ArrayAdapter<String> {
    private static final String LOG_TAG = FilmAdapter.class.getSimpleName();
    public FilmAdapter(Activity context, List<String> films) {
        super(context, 0, films);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String posterUrl = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.film_item, parent, false);
        }

        ImageView filmPoster = (ImageView) convertView.findViewById(R.id.film_poster);
        TextView filmTitle = (TextView) convertView.findViewById(R.id.film_title);
        filmTitle.setText("TITLE");
        Picasso.with(getContext()).load(posterUrl).into(filmPoster);
        Log.d(LOG_TAG, "GOT THROUGHHH" + posterUrl);
        return convertView;
    }
}
