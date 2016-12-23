package com.kabirkang.filmbrowser.film;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private RelatedVideo[] mRelatedVideos;

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

    public RelatedVideo[] getmRelatedVideos() {
        return mRelatedVideos;
    }

    public void setmRelatedVideos(RelatedVideo[] mRelatedVideos) {
        this.mRelatedVideos = mRelatedVideos;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mId);
        out.writeString(mPosterPath);
        out.writeString(mTitle);
        out.writeString(mOverview);
        out.writeString(mVoteAverage);
        out.writeString(mReleaseDate);
    }

    private Film(Parcel in) {
        this.mId = in.readString();
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

    public static class FilmsDeserializer implements JsonDeserializer<List<Film>> {
        public List<Film> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            JsonArray data = je.getAsJsonObject().getAsJsonArray("results");
            ArrayList<Film> filmsList = new ArrayList<Film>();
            for (JsonElement e : data) {
                filmsList.add((Film)jdc.deserialize(e, Film.class));
            }
            return filmsList;
        }
    }
}
