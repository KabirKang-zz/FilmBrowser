package com.kabirkang.filmbrowser;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FilmsFragment extends Fragment {
    private final String LOG_TAG = FilmsFragment.class.getSimpleName();
    private FilmAdapter mFilmsAdapter;

    public FilmsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_films, container, false);
        mFilmsAdapter = new FilmAdapter(getActivity(), new ArrayList<Film>());

        GridView gridView = (GridView) rootView.findViewById(R.id.films_grid);
        gridView.setAdapter(mFilmsAdapter);
        return rootView;
    }

    private void updateFilms() {
        FetchFilmsTask filmsTask = new FetchFilmsTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String searchType = prefs.getString("searchType", "popular");
        filmsTask.execute(searchType);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateFilms();
    }

    public class FetchFilmsTask extends AsyncTask<String, Void, Film []> {
        private final String LOG_TAG = FetchFilmsTask.class.getSimpleName();

        private Film[] getFilmDataFromJson(String filmsJsonStr) throws JSONException {
            final String FILM_LIST = "results";
            final String ORIGINAL_TITLE = "original_title";
            final String POSTER_PATH = "poster_path";
            final String PLOT_SYNOPSIS = "overview";
            final String USER_RATING = "vote_average";
            final String RELEASE_DATE = "release_date";

            JSONObject filmsJson = new JSONObject(filmsJsonStr);
            JSONArray filmsArray = filmsJson.getJSONArray(FILM_LIST);
            Film[] results = new Film[filmsArray.length()];
            /*
            Needs to handle each JSON object
             */

            for (int i = 0; i < filmsArray.length(); i++) {
                JSONObject film = filmsArray.getJSONObject(i);
                String path = "http://image.tmdb.org/t/p/w185" + film.getString(POSTER_PATH);
                String title = film.getString(ORIGINAL_TITLE);
                String overview = film.getString(PLOT_SYNOPSIS);
                double rating = film.getDouble(USER_RATING);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date releaseDate = format.parse(film.getString(RELEASE_DATE));
                    results[i] = new Film(path, title, overview, rating, releaseDate);
                } catch (ParseException e) {
                    results[i] = new Film(path, title, overview, rating, null);
                }
            }

            return results;
        }

        @Override
        protected Film[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String filmsJsonStr = null;

            try {
                final String API_PARAM = "api_key";
                final String MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/" + params[0];
                Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                        .appendQueryParameter(API_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "/n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                filmsJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            // Implement background task to pull movies
           try {
               return getFilmDataFromJson(filmsJsonStr);
           } catch (JSONException e) {
               Log.e(LOG_TAG, e.getMessage(), e);
               e.printStackTrace();
           }
            return null;
        }

        @Override
        protected void onPostExecute(Film [] result) {
            if (result != null) {
                mFilmsAdapter.clear();
                for (Film film : result) {
                    mFilmsAdapter.add(film);
                }
            }
        }
    }
}
