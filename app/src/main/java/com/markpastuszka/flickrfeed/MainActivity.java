package com.markpastuszka.flickrfeed;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FlickrFeed";
    private static final String flickrApi = "https://api.flickr.com/services/feeds/photos_public.gne";
    private List<ImageWithMetadata> imageList = Collections.emptyList();

    private RecyclerView feedView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    public void fetchBtnPressed(View view) {
        new FeedFetcher().execute();
    }

    private void setViews() {
        refreshLayout = findViewById(R.id.swipe_to_refresh);
        feedView = findViewById(R.id.feed_view);
        feedView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class FeedFetcher extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            refreshLayout.setRefreshing(true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL flickrUrl = new URL(flickrApi);
                InputStream inputStream = flickrUrl.openConnection().getInputStream();
                imageList = FeedParser.parseFlickrFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error while retrieving and parsing feed: ", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error while parsing XML: ", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            refreshLayout.setRefreshing(false);

            if (success) {
                feedView.setAdapter(new FeedListAdapter(imageList));
            } else {
                Toast.makeText(MainActivity.this, "There was an error in retrieving data from Flickr", Toast.LENGTH_LONG).show();
            }
        }
    }
}
