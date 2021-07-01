package com.google;

import java.util.Collections;
import java.util.List;

/**
 * A class used to represent a video.
 */
class Video implements Comparable<Video> {

    private final String title;
    private final String videoId;
    private final List<String> tags;
    private String flag;

    Video(String title, String videoId, List<String> tags) {
        this.title = title;
        this.videoId = videoId;
        this.tags = Collections.unmodifiableList(tags);
        this.flag = "";
    }

    /**
     * Returns the title of the video.
     */
    String getTitle() {
        return title;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * Returns the video id of the video.
     */
    String getVideoId() {
        return videoId;
    }

    /**
     * Returns a readonly collection of the tags of the video.
     */
    List<String> getTags() {
        return tags;
    }

    private String tagsDisplay() {
        String tagsDis = "[";
        for (String tag : tags) {
            tagsDis += tag + " ";
        }
        tagsDis = tagsDis.trim();
        tagsDis += "]";
        return tagsDis;
    }

    @Override
    public String toString() {
        String flagDes="";
        if(!flag.equals("")){
            flagDes+=" - FLAGGED (reason: "+flag+")";
        }
        return title +
                " (" + videoId + ") " +
                tagsDisplay() +flagDes ;
    }

    @Override
    public int compareTo(Video o) {
        return title.compareTo(o.title);
    }

}
