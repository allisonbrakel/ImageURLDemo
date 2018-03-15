package com.example.allison.demo2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Allison on 2018-03-09.
 */

public class CustomListView extends ArrayAdapter<String> {
    private ArrayList<String> title;
    private ArrayList<String> image;
    private Activity context;

    public CustomListView(Activity context, ArrayList<String> title, ArrayList<String> image) {
        super(context, R.layout.simplerow, title);

        this.title = title;
        this.context = context;
        this.image = image;
    }

    public void updateListView(ArrayList<String> title, ArrayList<String> image){
        this.title = title;
        this.image = image;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.simplerow, null, true);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        vh.txtTitle.setText(title.get(position));

        // Loads the image via url into the image view
        Picasso.get().load(image.get(position)).into(vh.image);

        return convertView;
    }

    class ViewHolder {
        TextView txtTitle;
        ImageView image;

        ViewHolder (View v){
            this.txtTitle = v.findViewById(R.id.lvTitle);
            this.image = v.findViewById(R.id.imageView);

        }
    }
}

