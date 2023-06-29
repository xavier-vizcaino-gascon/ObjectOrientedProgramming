package edu.uoc.pac3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Album {

    private String title;
    private String artist;
    private Track[] tracks;
    private static Logger logger = (Logger) LogManager.getLogger(Album.class);

    public Album(String title, String artist, Track[] tracks) {
        logger.info("Creating new album");
        setTitle(title);
        setArtist(artist);
        setTracks(tracks);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        logger.debug("Title: {}",title);
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        logger.debug("Artist: {}",artist);
        this.artist = artist;
    }

    public Track[] getTracks() {
        return tracks;
    }

    public void setTracks(Track[] tracks) {
        logger.debug("#tracks: {}",tracks.length);
        this.tracks = tracks;
    }
}
