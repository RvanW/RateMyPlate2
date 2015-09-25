package com.vanw.robbert.ratemyplate2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Robbert on 16-9-2015.
 */

public class Plate implements Serializable {
    byte[] imageBytes;
    String title;
    float rating;

    static final AtomicLong NEXT_ID = new AtomicLong(0);
    final long id = NEXT_ID.getAndIncrement();


    public long getId() {
        return id;
    }

    public float getRating() {
        // get shared prefs, no context here so we have to get context first
        Context applicationContext = MainActivity.getContextOfApplication();
        SharedPreferences prefs = applicationContext.getSharedPreferences(
                applicationContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.rating = prefs.getFloat(getId()+"", 0);
        return rating;
    }

    public Plate(Context context, String title) {
        super();

        this.title = title;

        Bitmap image;
        if(title.toLowerCase().contains("burger")) {
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamburger);
        }
        else if(title.toLowerCase().contains("pizza")){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.pizza);
        }
        else {
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.emptyplate);
        }

        // compress the image on creation (because objects might be send through an intent)
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.imageBytes = stream.toByteArray();
    }
    public Bitmap getImage() {
        // Uncompress image
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }
    public void setImage(Bitmap image) {
        // compress the image
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        this.imageBytes = stream.toByteArray();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // for sorting
    public static Comparator<Plate> COMPARE_BY_TITLE = new Comparator<Plate>() {
        public int compare(Plate one, Plate other) {
            return one.title.compareTo(other.title);
        }
    };

    public static Comparator<Plate> COMPARE_BY_RATING = new Comparator<Plate>() {
        public int compare(Plate one, Plate other) {
            return one.getRating() < other.getRating() ? +1 : one.getRating() > other.getRating() ? -1 : 0;
        }
    };
}