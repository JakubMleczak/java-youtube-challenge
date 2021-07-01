package com.google;

import java.util.*;
import java.util.regex.Pattern;

public class VideoPlayer {

    private final VideoLibrary videoLibrary;
    private String currentlyPlaying;
    private HashMap<String, VideoPlaylist> playlists;
    private String currentlyPaused;

    public HashMap<String, VideoPlaylist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(HashMap<String, VideoPlaylist> playlists) {
        this.playlists = playlists;
    }

    public String getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPaused(String currentlyPaused) {
        this.currentlyPaused = currentlyPaused;
    }

    public void setCurrentlyPlaying(String currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public VideoPlayer() {
        this.currentlyPaused = "";
        this.videoLibrary = new VideoLibrary();
        this.currentlyPlaying = "";
        this.playlists = new HashMap<>();
    }

    public void numberOfVideos() {
        System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
    }

    public void showAllVideos() {
        System.out.println("Here's a list of all available videos:");
        List<Video> videoList = videoLibrary.getVideos();
        Collections.sort(videoList);
        for (Video video : videoList) {
            System.out.println(video);
        }
    }

    public void playVideo(String videoId) {
        if (videoLibrary.getVideo(videoId) == null) {
            System.out.println("Cannot play video: Video does not exist");
        } else if (!videoLibrary.getVideo(videoId).getFlag().equals("")) {
            System.out.println("Cannot play video: Video is currently flagged (reason: " + videoLibrary.getVideo(videoId).getFlag() + ")");
        } else {
            if (!currentlyPlaying.equals("")) {
                System.out.println("Stopping video: " + videoLibrary.getVideo(currentlyPlaying));
            }
            setCurrentlyPlaying(videoId);
            setCurrentlyPaused("");
            System.out.println("Playing video: " + videoLibrary.getVideo(currentlyPlaying));
        }

    }

    public void stopVideo() {
        if (!currentlyPlaying.equals("")) {
            System.out.println("Stopping video: " + videoLibrary.getVideo(currentlyPlaying));
            setCurrentlyPlaying("");
        } else {
            System.out.println("Cannot stop video: No video is currently playing");
        }
    }

    public void playRandomVideo() {
        List<Video> videoList = videoLibrary.getVideos();
        boolean available = false;
        for (Video video : videoList) {
            if (video.getFlag().equals("")) {
                available = true;
                break;
            }
        }
        if (available) {
            int randomId = -1;
            do {
                randomId = (int) Math.floor(Math.random() * (videoList.size()));

            } while (!videoList.get(randomId).getFlag().equals(""));
            playVideo(videoList.get(randomId).getVideoId());
        } else System.out.println("No videos available");

    }

    public void pauseVideo() {
        if (currentlyPlaying.equals("")) {
            System.out.println("Cannot pause video: No video is currently playing");
        } else {
            if (currentlyPaused.equals("")) {
                System.out.println("Pausing video: " + videoLibrary.getVideo(currentlyPlaying));
                setCurrentlyPaused(videoLibrary.getVideo(currentlyPlaying).getVideoId());
            } else {
                System.out.println("Video already paused: " + videoLibrary.getVideo(currentlyPlaying).getTitle());
            }
        }
    }


    public void continueVideo() {
        if (currentlyPlaying.equals("")) {
            System.out.println("Cannot continue video: No video is currently playing");
        } else if (currentlyPaused.equals("")) {
            System.out.println("Cannot continue video: Video is not paused");

        } else {
            System.out.println("Continuing video: " + videoLibrary.getVideo(currentlyPaused));
            setCurrentlyPaused("");
        }
    }

    public void showPlaying() {
        if (!currentlyPlaying.equals("")) {

            if (currentlyPaused.equals(currentlyPlaying)) {
                System.out.println("Currently playing: " + videoLibrary.getVideo(currentlyPlaying) + " - PAUSED");
            } else {
                System.out.println("Currently playing: " + videoLibrary.getVideo(currentlyPlaying));
            }

        } else {
            System.out.println("No video is currently playing");
        }
    }

    public void createPlaylist(String playlistName) {
        if (playlists.containsKey(playlistName.toLowerCase())) {
            System.out.println("Cannot create playlist: A playlist with the same name already exists");
        } else {
            playlists.put(playlistName.toLowerCase(), new VideoPlaylist(playlistName));
            System.out.println("Successfully created new playlist: " + playlistName);
        }
    }

    public void addVideoToPlaylist(String playlistName, String videoId) {
        if (!playlists.containsKey(playlistName.toLowerCase())) {
            System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        } else if (videoLibrary.getVideo(videoId) == null) {
            System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
        } else if (!videoLibrary.getVideo(videoId).getFlag().equals("")) {
            System.out.println("Cannot add video to " + playlistName + ": Video is currently flagged (reason: " + videoLibrary.getVideo(videoId).getFlag() + ")");
        } else if (playlists.get(playlistName.toLowerCase()).VideoAlreadyAdded(videoId)) {
            System.out.println("Cannot add video to " + playlistName + ": Video already added");
        } else {
            playlists.get(playlistName.toLowerCase()).addToPlaylist(videoLibrary.getVideo(videoId));
            System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
        }
    }

    public void showAllPlaylists() {
        if (playlists.size() == 0) {
            System.out.println("No playlists exist yet");
        } else {
            TreeMap<String, VideoPlaylist> sorted = new TreeMap<>(playlists);
            System.out.println("Showing all playlists:");
            for (Map.Entry<String, VideoPlaylist> entry : sorted.entrySet()) {
                System.out.println(entry.getKey());
            }
        }


    }

    public void showPlaylist(String playlistName) {
        if (!playlists.containsKey(playlistName.toLowerCase())) {
            System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
        } else {
            System.out.println("Showing playlist: " + playlistName);
            if (playlists.get(playlistName.toLowerCase()).playlistVidoes.size() == 0) {
                System.out.println("No videos here yet");
            } else {
                System.out.println(playlists.get(playlistName.toLowerCase()).displayList());
            }

        }
    }

    public void removeFromPlaylist(String playlistName, String videoId) {
        if (!playlists.containsKey(playlistName.toLowerCase())) {
            System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
        } else if (videoLibrary.getVideo(videoId) == null) {
            System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
        } else if (!playlists.get(playlistName.toLowerCase()).VideoAlreadyAdded(videoId)) {
            System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
        } else {
            playlists.get(playlistName.toLowerCase()).removeFromPlaylist(videoLibrary.getVideo(videoId));
            System.out.println("Removed video from " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
        }


    }

    public void clearPlaylist(String playlistName) {
        if (!playlists.containsKey(playlistName.toLowerCase())) {
            System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
        } else {
            playlists.get(playlistName.toLowerCase()).clear();
            System.out.println("Successfully removed all videos from " + playlistName);
        }

    }

    public void deletePlaylist(String playlistName) {
        if (!playlists.containsKey(playlistName.toLowerCase())) {
            System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
        } else {
            playlists.remove(playlistName.toLowerCase());
            System.out.println("Deleted playlist: " + playlistName);
        }
    }

    public static boolean isNumeric(String string) {
        // Checks if the provided string
        // is a numeric by applying a regular
        // expression on it.
        String regex = "[0-9]+[\\.]?[0-9]*";
        return Pattern.matches(regex, string);
    }

    public void searchVideos(String searchTerm) {

        List<Video> movieList = videoLibrary.getVideos();
        List<Video> searchList = new ArrayList<>();
        for (Video video : movieList) {
            if (video.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                if (video.getFlag().equals("")) {
                    searchList.add(video);
                }
            }
        }
        if (searchList.isEmpty()) {
            System.out.println("No search results for " + searchTerm);
        } else {
            System.out.println("Here are the results for " + searchTerm + ":");
            Collections.sort(searchList);
            int iterator = 1;
            for (Video video : searchList) {
                System.out.print(iterator + ") ");
                System.out.println(video);
                iterator++;
            }
            System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
            System.out.println("If your answer is not a valid number, we will assume it's a no.");
            var scanner = new Scanner(System.in);
            var input = scanner.nextLine();
            if (isNumeric(input)) {
                if (Integer.parseInt(input) - 1 < searchList.size()) {
                    playVideo(searchList.get(Integer.parseInt(input) - 1).getVideoId());
                }

            }


        }

    }

    public void searchVideosWithTag(String videoTag) {
        List<Video> movieList = videoLibrary.getVideos();
        List<Video> searchList = new ArrayList<>();
        for (Video video : movieList) {
            for (String tag : video.getTags())
                if (tag.equals(videoTag)) {
                    if (video.getFlag().equals("")) {
                        searchList.add(video);
                        break;
                    }
                }
        }
        if (searchList.isEmpty()) {
            System.out.println("No search results for " + videoTag);
        } else {
            System.out.println("Here are the results for " + videoTag + ":");
            Collections.sort(searchList);
            int iterator = 1;
            for (Video video : searchList) {
                System.out.print(iterator + ") ");
                System.out.println(video);
                iterator++;
            }
            System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
            System.out.println("If your answer is not a valid number, we will assume it's a no.");
            var scanner = new Scanner(System.in);
            var input = scanner.nextLine();
            if (isNumeric(input)) {
                if (Integer.parseInt(input) - 1 < searchList.size()) {
                    playVideo(searchList.get(Integer.parseInt(input) - 1).getVideoId());
                }

            }


        }
    }

    public void flagVideo(String videoId) {
        if (videoLibrary.getVideo(videoId) != null) {
            if (!videoLibrary.getVideo(videoId).getFlag().equals("")) {
                System.out.println("Cannot flag video: Video is already flagged");
            } else {
                videoLibrary.getVideo(videoId).setFlag("Not supplied");

                if (videoLibrary.getVideo(videoId).getVideoId().equals(currentlyPlaying)) {
                    stopVideo();
                }

                System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle()
                        + " (reason: " + videoLibrary.getVideo(videoId).getFlag() + ")");

            }

        } else System.out.println("Cannot flag video: Video does not exist");

    }

    public void flagVideo(String videoId, String reason) {
        if (videoLibrary.getVideo(videoId) != null) {
            if (!videoLibrary.getVideo(videoId).getFlag().equals("")) {
                System.out.println("Cannot flag video: Video is already flagged");
            } else {
                videoLibrary.getVideo(videoId).setFlag(reason);


                if (videoLibrary.getVideo(videoId).getVideoId().equals(currentlyPlaying)) {
                    stopVideo();
                }
                System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle()
                        + " (reason: " + videoLibrary.getVideo(videoId).getFlag() + ")");
            }

        } else System.out.println("Cannot flag video: Video does not exist");
    }

    public void allowVideo(String videoId) {

        if (videoLibrary.getVideo(videoId) != null) {
            if (videoLibrary.getVideo(videoId).getFlag().equals("")) {
                System.out.println("Cannot remove flag from video: Video is not flagged");
            } else {
                videoLibrary.getVideo(videoId).setFlag("");
                System.out.println("Successfully removed flag from video: " + videoLibrary.getVideo(videoId).getTitle());
            }

        } else System.out.println("Cannot remove flag from video: Video does not exist");
    }
}