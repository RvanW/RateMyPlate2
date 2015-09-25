package com.vanw.robbert.ratemyplate2;

/**
 * Created by Robbert on 16-9-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<Plate> {
    Context context;
    int layoutResourceId;
    ArrayList<Plate> data = new ArrayList<Plate>();

    public CustomListViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<Plate> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            holder.ratingBar = (RatingBar) row.findViewById(R.id.ratingBar2);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        Plate item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.imageItem.setImageBitmap(item.getImage());
        holder.ratingBar.setRating(item.getRating());
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
        RatingBar ratingBar;
    }
}