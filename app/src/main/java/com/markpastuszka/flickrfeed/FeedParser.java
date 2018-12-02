package com.markpastuszka.flickrfeed;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markpastuszka on 02/12/2018.
 */

class FeedParser {

    static List<ImageWithMetadata> parseFlickrFeed(InputStream feedData) throws XmlPullParserException, IOException {
        String text = "";
        ImageWithMetadata image = new ImageWithMetadata();
        List<ImageWithMetadata> images = new ArrayList<>();

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(feedData, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("entry")) {
                            image = new ImageWithMetadata();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("entry")) {
                            image.description = "test";
                            images.add(image);
                        } else if (tagName.equalsIgnoreCase("title")) {
                            image.title = text;
                        } else if (tagName.equalsIgnoreCase("name")) {
                            image.author = text;
                        } else if (tagName.equalsIgnoreCase("link")) {
                            String rel = parser.getAttributeValue(null, "rel");
                            if (rel.equalsIgnoreCase("enclosure")) {
                                image.imageUrl = parser.getAttributeValue(null, "href");
                            } else {
                                image.flickrUrl = parser.getAttributeValue(null, "href");
                            }
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }
            return images;
        } finally {
            feedData.close();
        }
    }

}

