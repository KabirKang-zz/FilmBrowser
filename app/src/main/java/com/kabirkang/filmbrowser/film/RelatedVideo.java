package com.kabirkang.filmbrowser.film;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kabirkang on 12/22/16.
 */

public class RelatedVideo {
    @SerializedName("id")
    @Expose
    private String mVideoId;

    @SerializedName("key")
    @Expose
    private String mKey;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("site")
    @Expose
    private String mSite;

    @SerializedName("size")
    @Expose
    private int mSize;

    @SerializedName("type")
    @Expose
    private String mType;

    // iso_639_1
    @SerializedName("iso_639_1")
    @Expose
    private String mLanguage;

    // iso_3166_1
    @SerializedName("iso_3166_1")
    @Expose
    private String mCountry;

    public String getmKey() {
        return mKey;
    }

    public String getmName() {
        return mName;
    }

    public RelatedVideo(String mVideoId, String mKey, String mName, String mSite, int mSize, String mType, String mLanguage, String mCountry) {
        this.mVideoId = mVideoId;
        this.mKey = mKey;
        this.mName = mName;
        this.mSite = mSite;
        this.mSize = mSize;
        this.mType = mType;
        this.mLanguage = mLanguage;
        this.mCountry = mCountry;
    }

    public static class RelatedVideosDeserializer implements JsonDeserializer<List<RelatedVideo>> {
        public List<RelatedVideo> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            JsonArray data = je.getAsJsonObject().getAsJsonArray("results");
            ArrayList<RelatedVideo> relatedVideosList = new ArrayList<RelatedVideo>();
            for (JsonElement e : data) {
                relatedVideosList.add((RelatedVideo) jdc.deserialize(e, RelatedVideo.class));
            }
            return relatedVideosList;
        }
    }
}
