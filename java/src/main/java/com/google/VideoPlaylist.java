package com.google;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to represent a Playlist
 */
class VideoPlaylist {

    String name;
    List<Video> playlistVidoes;


    public List<Video> getPlaylistVidoes() {
        return playlistVidoes;
    }

    public void setPlaylistVidoes(List<Video> playlistVidoes) {
        this.playlistVidoes = playlistVidoes;
    }

    public VideoPlaylist(List<Video> playlistVidoes) {
        this.playlistVidoes = playlistVidoes;
    }

    public VideoPlaylist(String name) {
        this.name = name;
        this.playlistVidoes = new ArrayList<>();
    }

    public boolean VideoAlreadyAdded(String videoId) {
        for (Video video : playlistVidoes) {
            if (video.getVideoId().equals(videoId)) {
                return true;
            }
        }
        return false;
    }

    public void addToPlaylist(Video video) {
        playlistVidoes.add(video);
    }

    public void removeFromPlaylist(Video video) {
        playlistVidoes.remove(video);
    }

    public void clear() {
        playlistVidoes.clear();
    }

    public String displayList(){
        String display = "";
        for(Video video: playlistVidoes)
        {
            display+=video+"\n";
        }
        return display.trim();
    }


}
