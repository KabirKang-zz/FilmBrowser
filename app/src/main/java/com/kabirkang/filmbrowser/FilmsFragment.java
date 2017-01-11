package com.kabirkang.filmbrowser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kabirkang.filmbrowser.BuildConfig.MOVIE_DB_API_KEY;

public class FilmsFragment extends Fragment {
    private final String LOG_TAG = FilmsFragment.class.getSimpleName();
    private FilmAdapter mFilmsAdapter;
    private ArrayList<Film> filmsBundle;

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
        int preference;
        switch (item.getItemId()) {
            case R.id.popular_menu_option:
                preference = R.string.pref_search_popular;
                updateFilms(preference);
                setSortPreference(preference);
                return true;
            case R.id.rating_menu_option:
                preference = R.string.pref_search_rated;
                updateFilms(preference);
                setSortPreference(preference);
                return true;
            case R.id.favorites_menu_option:
                preference = R.string.pref_search_favorites;
                viewFavorites();
                setSortPreference(preference);
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

        if (savedInstanceState != null) {
            filmsBundle = savedInstanceState.getParcelableArrayList(getString(R.string.films_bundle));
            mFilmsAdapter.addAll(filmsBundle);
        } else {
            filmsBundle = new ArrayList<>();
            int preference = getSortPreference();
            if (preference == R.string.pref_search_favorites) {
                viewFavorites();
            } else {
                updateFilms(getSortPreference());
            }
        }

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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(getString(R.string.films_bundle), filmsBundle);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setSortPreference(int preference) {
        String sortKey = getString(R.string.sort_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(sortKey, preference);
        editor.commit();
    }

    private int getSortPreference() {
        String sortKey = getString(R.string.sort_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        return prefs.getInt(getString(R.string.sort_key), R.string.pref_search_popular);
    }

    private void updateFilms(int searchType) {
        if (!isNetworkConnected()) {
            return;
        }
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
                        mFilmsAdapter.clear();
                        for (Film film : films) {
                            mFilmsAdapter.add(film);
                            filmsBundle.add(film);
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

    private void viewFavorites() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> favorites = prefs.getStringSet(getString(R.string.pref_search_favorites), new HashSet<String>());
        mFilmsAdapter.clear();

        final Gson gson = new GsonBuilder()
                .create();
        MovieDBService service = ApiClient.getClient().create(MovieDBService.class);
        for (String filmID : favorites) {
            Call<JsonObject> call = service.getMovie(filmID);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        Film film = gson.fromJson(response.body(), Film.class);
                        mFilmsAdapter.add(film);
                        filmsBundle.add(film);
                        mFilmsAdapter.notifyDataSetChanged();
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
