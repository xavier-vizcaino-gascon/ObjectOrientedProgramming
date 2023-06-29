package edu.uoc.pac3;

/**
 * This class represents each Track that can be found on an album
 */
public class Track {
    // Attributes
    /**
     * Track name
     */
    private String name;
    /**
     * Track duration
     */
    private int duration;
    /**
     * Track composer
     */
    private String composer;
    /**
     * Constant for minimum duration error text
     */
    public static final String ERR_MIN_DURATION="[ERROR] Duration must be greater than 0";

    // Constructors
    /**
     * Track object constructor
     * @param name track's name
     * @param duration track's duration
     * @param composer track's composer
     */
    public Track(String name, int duration, String composer){
        try{
            setName(name);
            setDuration(duration);
            setComposer(composer);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Methods
    /**
     * Track name getter
     * @return Track's name
     */
    public String getName() { return name; }

    /**
     * Track name setter
     * @param name Track's name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Track duration getter
     * @return Track's duration
     */
    public int getDuration() { return duration; }

    /**
     * Track duration setter
     * @param duration Track's duration
     * @throws Exception when duration is negative or equal to 0
     */
    public void setDuration(int duration) throws Exception {
        if (duration<=0) {throw new Exception(ERR_MIN_DURATION);}
        this.duration = duration;
    }

    /**
     * Track composer getter
     * @return Track's composer
     */
    public String getComposer() { return composer; }

    /**
     * Track composer setter
     * @param composer Track's composer
     */
    public void setComposer(String composer) {
        this.composer = composer;
    }
}
