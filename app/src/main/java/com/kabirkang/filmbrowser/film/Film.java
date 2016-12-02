package com.kabirkang.filmbrowser.film;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Kabir on 9/26/2016.
 */
public class Film implements Parcelable {
    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("poster_path")
    @Expose
    private String mPosterPath;

    @SerializedName("original_title")
    @Expose
    private String mTitle;

    @SerializedName("overview")
    @Expose
    private String mOverview;

    @SerializedName("vote_average")
    @Expose
    private String mVoteAverage;

    @SerializedName("release_date")
    @Expose
    private String mReleaseDate;

    public Film(String mId, String posterPath, String title, String overview, String voteAverage, String releaseDate) {
        this.mId = mId;
        this.mPosterPath = posterPath;
        this.mTitle = title;
        this.mOverview = overview;
        this.mVoteAverage = voteAverage;
        this.mReleaseDate = releaseDate;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
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

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mPosterPath);
        out.writeString(mTitle);
        out.writeString(mOverview);
        out.writeString(mVoteAverage);
        out.writeString(mReleaseDate);
    }

    private Film(Parcel in) {
        this.mPosterPath = in.readString();
        this.mTitle = in.readString();
        this.mOverview = in.readString();
        this.mVoteAverage = in.readString();
        this.mReleaseDate = in.readString();


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
