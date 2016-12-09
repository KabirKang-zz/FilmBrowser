package com.kabirkang.filmbrowser;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kabirkang.filmbrowser.api.ApiClient;
import com.kabirkang.filmbrowser.api.MovieDBService;
import com.kabirkang.filmbrowser.film.Film;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kabirkang.filmbrowser.BuildConfig.MOVIE_DB_API_KEY;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.film_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.popular_menu_option:
                updateFilms(R.string.pref_search_popular);
                return true;
            case R.id.rating_menu_option:
                updateFilms(R.string.pref_search_rated);
                return true;
            default:
                break;
        }

        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_films, container, false);
        mFilmsAdapter = new FilmAdapter(getActivity(), new ArrayList<Film>());
        mFilmsAdapter.setNotifyOnChange(true);
        GridView gridView = (GridView) rootView.findViewById(R.id.films_grid);
        gridView.setAdapter(mFilmsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Film film = mFilmsAdapter.getItem(i);
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class).putExtra(getString(R.string.film_extra), film);
                startActivity(detailIntent);
            }
        });
        return rootView;
    }

    private void updateFilms(int searchType) {
        String search = getString(searchType);
        final Type listType = new TypeToken<ArrayList<Film>>(){}.getType();

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(listType, new Film.FilmsDeserializer())
                .create();
        MovieDBService service = ApiClient.getClient().create(MovieDBService.class);
        Call<JsonObject> call = (searchType == R.string.pref_search_popular) ? service.listPopularFilms() : service.listTopRatedFilms();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    List<Film> films = gson.fromJson(response.body(), listType);
                    if (!films.isEmpty()) {
                        Log.d(LOG_TAG, "NOT EMPTY");
                        mFilmsAdapter.clear();
                        for (Film film : films) {
                            Log.d(LOG_TAG, film.getTitle());
                            mFilmsAdapter.add(film);
                        }
                        mFilmsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.results_error,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(LOG_TAG, "FAILURE");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        updateFilms(R.string.pref_search_popular);
    }

    public class FetchFilmsTask extends AsyncTask<String, Void, Film[]> {
        private final String LOG_TAG = FetchFilmsTask.class.getSimpleName();

        private Film[] getFilmDataFromJson(String filmsJsonStr) throws JSONException {
            final String FILM_LIST = "results";
            final String ID = "id";
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
                String rating = film.getString(USER_RATING);
                String id = film.getString(ID);
                String releaseDate = film.getString(RELEASE_DATE);
                results[i] = new Film(id, path, title, overview, rating, releaseDate);

            }

            return results;
        }

        @Override
        protected Film[] doInBackground(String... params) {
            if (params.length == 0 || !isNetworkConnected()) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String filmsJsonStr = null;

            try {
                final String API_PARAM = "api_key";
                final String MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/" + params[0];
                Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                        .appendQueryParameter(API_PARAM, MOVIE_DB_API_KEY)
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
            try {
                return getFilmDataFromJson(filmsJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Film[] result) {
            if (result != null) {
                mFilmsAdapter.clear();
                for (Film film : result) {
                    mFilmsAdapter.add(film);
                }
            } else {
                Toast.makeText(getActivity(), R.string.results_error,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }
}
