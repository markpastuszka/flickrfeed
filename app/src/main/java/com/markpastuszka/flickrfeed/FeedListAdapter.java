package com.markpastuszka.flickrfeed;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class FeedListAdapter
        extends RecyclerView.Adapter<FeedListAdapter.FeedListViewHolder> {

    private static final String TAG = "FlickrFeed:Adapter";
    private List<ImageWithMetadata> images;

    static class FeedListViewHolder extends RecyclerView.ViewHolder {
        private View feedView;

        FeedListViewHolder(View v) {
            super(v);
            feedView = v;
        }
    }

    FeedListAdapter(List<ImageWithMetadata> images) {
        this.images = images;
    }

    @Override
    public FeedListViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_adapter, parent, false);
        return new FeedListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedListViewHolder holder, int position) {
        final ImageWithMetadata image = images.get(position);

        ImageView imgView = holder.feedView.findViewById(R.id.image_view);
        try {
            Glide.with(holder.feedView.getContext()).load(image.imageUrl).into(imgView);
        } catch (Exception e) {
            Log.e(TAG, "Error loading image: ", e);
        }

        ((TextView)holder.feedView.findViewById(R.id.title_label)).setText(image.title);
        ((TextView)holder.feedView.findViewById(R.id.author_label)).setText(image.author);
        ((TextView)holder.feedView.findViewById(R.id.description_label)).setText(image.description);
        ((TextView)holder.feedView.findViewById(R.id.link_label)).setText(image.flickrUrl);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
