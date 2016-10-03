package com.kabirkang.filmbrowser;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Kabir on 9/26/2016.
 */
public class Film implements Parcelable {
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

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mPosterPath);
        out.writeString(mTitle);
        out.writeString(mOverview);
        out.writeDouble(mVoteAverage);
        out.writeLong(mReleaseDate != null ? mReleaseDate.getTime() : -1);
    }

    private Film(Parcel in) {
        this.mPosterPath = in.readString();
        this.mTitle = in.readString();
        this.mOverview = in.readString();
        this.mVoteAverage = in.readDouble();
        long releaseAsLong = in.readLong();
        this.mReleaseDate = releaseAsLong == -1 ? null : new Date(releaseAsLong);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Film> CREATOR
            = new Parcelable.Creator<Film>() {

        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
