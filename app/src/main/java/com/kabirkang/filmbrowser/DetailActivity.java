package com.kabirkang.filmbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kabirkang.filmbrowser.api.ApiClient;
import com.kabirkang.filmbrowser.api.MovieDBService;
import com.kabirkang.filmbrowser.film.Film;
import com.kabirkang.filmbrowser.film.RelatedVideo;
import com.kabirkang.filmbrowser.film.Review;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        private RelatedVideoAdapter mRelatedVideoAdapter;
        private ReviewAdapter mReviewAdapter;
        private String mIdStr;
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
                mRelatedVideoAdapter = new RelatedVideoAdapter(getActivity(), new ArrayList<RelatedVideo>());
                mRelatedVideoAdapter.setNotifyOnChange(true);
                mReviewAdapter = new ReviewAdapter(getActivity(), new ArrayList<Review>());
                mReviewAdapter.setNotifyOnChange(true);
                Film film = intent.getParcelableExtra(getString(R.string.film_extra));
                mIdStr = film.getmId();
                mTitleStr = film.getTitle();
                mOverviewStr = film.getOverview();
                mPosterStr = "http://image.tmdb.org/t/p/w185" + film.getPosterPath();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                mReleaseStr = "Release Date: " + film.getReleaseDate();
                mRatingStr = "Vote Average: " + film.getVoteAverage();
                ListView videoList = (ListView) rootView.findViewById(R.id.related_videos_list);
                ListView reviewList = (ListView) rootView.findViewById(R.id.reviews_list);

                videoList.setAdapter(mRelatedVideoAdapter);
                reviewList.setAdapter(mReviewAdapter);
                ((TextView) rootView.findViewById(R.id.detail_title)).setText(mTitleStr);
                ((TextView) rootView.findViewById(R.id.detail_overview)).setText(mOverviewStr);
                ((TextView) rootView.findViewById(R.id.detail_date)).setText(mReleaseStr);
                ((TextView) rootView.findViewById(R.id.detail_rating)).setText(mRatingStr);
                Picasso.with(getContext()).load(mPosterStr).into((ImageView) rootView.findViewById(R.id.detail_poster));

                getRelatedVideos(mIdStr);
                getReviews(mIdStr);
            }
            return rootView;
        }

        private void getRelatedVideos(String id) {
            final Type listType = new TypeToken<ArrayList<RelatedVideo>>(){}.getType();
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(listType, new RelatedVideo.RelatedVideosDeserializer())
                    .create();
            MovieDBService service = ApiClient.getClient().create(MovieDBService.class);
            Call<JsonObject> call = service.getRelatedVideos(id);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        List<RelatedVideo> relatedVideos = gson.fromJson(response.body(), listType);
                        if (!relatedVideos.isEmpty()) {
                            mRelatedVideoAdapter.clear();
                            for (RelatedVideo video : relatedVideos) {
                                mRelatedVideoAdapter.add(video);
                            }
                        }
                    } else {
                        Log.d(LOG_TAG, "SOMETHING WENT WRONG");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d(LOG_TAG, "SOMETHING ELSE WENT WRONG");
                }
            });
        }

        private void getReviews(String id) {
            final Type listType = new TypeToken<ArrayList<Review>>(){}.getType();
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(listType, new Review.ReviewsDeserializer())
                    .create();
            MovieDBService service = ApiClient.getClient().create(MovieDBService.class);
            Call<JsonObject> call = service.getReviews(id);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        List<Review> reviews = gson.fromJson(response.body(), listType);
                        if (!reviews.isEmpty()) {
                            mReviewAdapter.clear();
                            for (Review review : reviews) {
                                mReviewAdapter.add(review);
                            }
                        }
                    } else {
                        Log.d(LOG_TAG, "SOMETHING WENT WRONG");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d(LOG_TAG, "SOMETHING ELSE WENT WRONG");
                }
            });
        }
    }
}
