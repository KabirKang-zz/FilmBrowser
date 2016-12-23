package com.kabirkang.filmbrowser;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kabirkang.filmbrowser.film.RelatedVideo;

import java.util.List;

/**
 * Created by kabirkang on 12/23/16.
 */

public class RelatedVideoAdapter extends ArrayAdapter<RelatedVideo> {
    private static final String LOG_TAG = RelatedVideoAdapter.class.getSimpleName();

    public RelatedVideoAdapter(Activity context, List<RelatedVideo> relatedVideos) {super(context, 0, relatedVideos);}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

}
