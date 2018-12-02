package com.markpastuszka.flickrfeed;

public class ImageWithMetadata {

    public String imageUrl;
    public String title;
    public String author;
    public String description;
    public String flickrUrl;

    public ImageWithMetadata(String imageUrl, String title, String author, String description, String flickrUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.description = description;
        this.flickrUrl = flickrUrl;
    }
}
