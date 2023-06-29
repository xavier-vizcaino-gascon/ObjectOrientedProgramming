package edu.uoc.pac3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Track {

    private String name;
    private String composer;
    private int duration;
    private static Logger logger = (Logger) LogManager.getLogger(Track.class);

    public Track(String name, String composer, int duration) {
        setName(name);
        setComposer(composer);
        setDuration(duration);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration<=0) {
            logger.error("Duration should not be a negative value!");
        }else{
            this.duration = duration; }
    }
}


