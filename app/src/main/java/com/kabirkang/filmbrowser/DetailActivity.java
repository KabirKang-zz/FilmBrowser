package com.kabirkang.filmbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    public static class DetailFragment extends Fragment {
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private String mTitleStr;
        private String mOverviewStr;
        private String mPosterStr;
        private String mReleaseStr;
        private String mRatingStr;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            if (intent != null && intent.hasExtra(getString(R.string.film_extra))) {
                Film film = intent.getParcelableExtra(getString(R.string.film_extra));
                mTitleStr = film.getTitle();
                mOverviewStr = film.getOverview();
                mPosterStr = "http://image.tmdb.org/t/p/w185" + film.getPosterPath();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                mReleaseStr = "Release Date: " + df.format(film.getReleaseDate()).toString();
                mRatingStr = "Vote Average: " + String.valueOf(film.getVoteAverage());
                ((TextView) rootView.findViewById(R.id.detail_title)).setText(mTitleStr);
                ((TextView) rootView.findViewById(R.id.detail_overview)).setText(mOverviewStr);
                ((TextView) rootView.findViewById(R.id.detail_date)).setText(mReleaseStr);
                ((TextView) rootView.findViewById(R.id.detail_rating)).setText(mRatingStr);
                Picasso.with(getContext()).load(mPosterStr).into((ImageView) rootView.findViewById(R.id.detail_poster));
            }
            return rootView;
        }
    }
}
