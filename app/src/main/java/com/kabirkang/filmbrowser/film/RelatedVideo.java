package com.kabirkang.filmbrowser.film;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kabirkang on 12/22/16.
 */

public class RelatedVideo {
    private String mFilmId;
    private String mVideoId;
    private String mKey;
    private String mName;
    private String mSite;

    private int mSize;
    private String mType;

    // iso_639_1
    private String mLanguage;

    // iso_3166_1
    private String mCountry;

    public RelatedVideo(String mFilmId, String mVideoId, String mKey, String mName, String mSite, int mSize, String mType, String mLanguage, String mCountry) {
        this.mFilmId = mFilmId;
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
