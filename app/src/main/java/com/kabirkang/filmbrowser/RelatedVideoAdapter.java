package com.kabirkang.filmbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kabirkang.filmbrowser.film.RelatedVideo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by kabirkang on 12/23/16.
 */

public class RelatedVideoAdapter extends ArrayAdapter<RelatedVideo> {
    private static final String LOG_TAG = RelatedVideoAdapter.class.getSimpleName();

    public RelatedVideoAdapter(Activity context, List<RelatedVideo> relatedVideos) {super(context, 0, relatedVideos);}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelatedVideo video = getItem(position);
        String thumbnailUrl = "http://img.youtube.com/vi/" + video.getmKey() +"/0.jpg";

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.related_video_item, parent, false);
        }

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.video_thumbnail);
        TextView videoName = (TextView) convertView.findViewById(R.id.video_name);

        Picasso.with(getContext()).load(thumbnailUrl).into(thumbnail);
        videoName.setText(video.getmName());
        return convertView;
    }

}
