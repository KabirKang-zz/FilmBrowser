package com.kabirkang.filmbrowser;

import java.util.Date;

/**
 * Created by Kabir on 9/26/2016.
 */
public class Film {
    String mPosterPath;
    String mTitle;
    String mOverview;
    double mVoteAverage;
    Date mReleaseDate;

    public Film(String posterPath, String title, String overview, double voteAverage, Date releaseDate) {
        this.mPosterPath = posterPath;
        this.mTitle = title;
        this.mOverview = overview;
        this.mVoteAverage = voteAverage;
        this.mReleaseDate = releaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        mReleaseDate = releaseDate;
    }
}
