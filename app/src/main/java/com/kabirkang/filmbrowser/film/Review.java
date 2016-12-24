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

public class Review {

    @SerializedName("id")
    @Expose
    private String mReviewId;

    @SerializedName("author")
    @Expose
    private String mAuthor;

    @SerializedName("content")
    @Expose
    private String mContent;

    @SerializedName("url")
    @Expose
    private String mUrl;

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public Review(String mReviewId, String mAuthor, String mContent, String mUrl) {
        this.mReviewId = mReviewId;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
        this.mUrl = mUrl;
    }

    public static class ReviewsDeserializer implements JsonDeserializer<List<Review>> {
        public List<Review> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            JsonArray data = je.getAsJsonObject().getAsJsonArray("results");
            ArrayList<Review> reviewsList = new ArrayList<Review>();
            for (JsonElement e : data) {
                reviewsList.add((Review) jdc.deserialize(e, Review.class));
            }
            return reviewsList;
        }
    }
}
