package com.example.allison.demo2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView ;
    private ArrayList<ListItem> item = new ArrayList<>();
    CustomListView customListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTask();
    }


    private void startTask(){
        String [] urls = {"https://rest.bandsintown.com/artists/neck%20deep?app_id=allison%27",
                "https://rest.bandsintown.com/artists/waterparks?app_id=allison%27",
                "https://rest.bandsintown.com/artists/state%20champs?app_id=allison%27",
                "https://rest.bandsintown.com/artists/grayscale?app_id=allison%27"};

        for (int i = 0; i < urls.length; i++){
            OkHttpHandler okHttpHandler= new OkHttpHandler();
            okHttpHandler.execute(urls[i]);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public class OkHttpHandler extends AsyncTask {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(Object[] params) {
            mainListView = (ListView) findViewById( R.id.mainListView );
            // Building the request before sending
            Request.Builder builder = new Request.Builder();
            // Sets the api url
            builder.url(params[0].toString());
            Request request = builder.build();

            try {
                // Set the response by executing the request
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            parseResponse(o.toString());
        }
    }

    private void parseResponse(String response) {
        try{
            JSONObject json = new JSONObject(response);

            ListItem i = new ListItem();
            i.setTitle(json.getString("name"));
            i.setImage(json.getString("image_url"));

            item.add(i);

            ArrayList<String> titleList = new ArrayList<String>();
            ArrayList<String> imageList = new ArrayList<String>();

            for(ListItem li: item){
                titleList.add(li.getTitle());
                imageList.add(li.getImage());
            }

            customListView = new CustomListView(MainActivity.this, titleList, imageList);
            mainListView.setAdapter( customListView );
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class ListItem {
        private String title;
        private String image;

        public void setTitle(String title){
            this.title = title;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

    }
}


