package com.kabirkang.filmbrowser;

import java.util.Date;

/**
 * Created by Kabir on 9/26/2016.
 */
public class Film {
    String mPosterPath;
    String mTitle;
    String mOverview;
    float mVoteAverage;
    Date mReleaseDate;

    public Film(String posterPath, String title, String overview, float voteAverage, Date releaseDate) {
        this.mPosterPath = posterPath;
        this.mTitle = title;
        this.mOverview = overview;
        this.mVoteAverage = voteAverage;
        this.mReleaseDate = releaseDate;
    }
}
