package com.kabirkang.filmbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kabirkang.filmbrowser.film.RelatedVideo;
import com.kabirkang.filmbrowser.film.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kabirkang on 12/23/16.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {
    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();

    public ReviewAdapter(Activity context, List<Review> reviews) {super(context, 0, reviews);}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }

        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView content = (TextView) convertView.findViewById(R.id.content);

        author.setText(review.getmAuthor());
        content.setText(review.getmContent());
        return convertView;
    }

}
