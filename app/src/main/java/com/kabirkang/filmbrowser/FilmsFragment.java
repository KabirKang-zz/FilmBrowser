package com.kabirkang.filmbrowser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;


public class FilmsFragment extends Fragment {
    ArrayAdapter<String> mFilmsAdapter;

    public FilmsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mFilmsAdapter = new ArrayAdapter<String>()
//    }

    private void updateFilms() {
        FetchFilmsTask filmsTask = new FetchFilmsTask();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateFilms();
    }

    public class FetchFilmsTask extends AsyncTask<String, Void, String []> {
        @Override
        protected String[] doInBackground(String... params) {
            // Implement background task to pull movies
            return new String[0];
        }
    }
}
