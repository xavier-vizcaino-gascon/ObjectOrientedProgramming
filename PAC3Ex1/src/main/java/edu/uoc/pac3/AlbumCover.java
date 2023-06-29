package edu.uoc.pac3;

/**
 * This class represents de album cover which is a component of the album
 * @author Xavier Vizcaino
 * @version 1.0
 */
public class AlbumCover {
    // Attributes
    /**
     * Album cover name
     */
    private String name;
    /**
     * Album cover artist
     */
    private String artist;
    /**
     * Album cover width
     */
    private int width;
    /**
     * Album cover height
     */
    private int height;
    /**
     * Constant for album cover maximum resolution allowed
     */
    private static final int MAX_RESOLUTION=1166400;
    /**
     * Constant for name error text
     */
    public static final String ERR_UNDEFINED_NAME="[ERROR] The name cannot be an empty value";
    /**
     * Constant for artist error text
     */
    public static final String ERR_UNKNOWN_ARTIST="[ERROR] The artist cannot be an empty value";
    /**
     * Constant for minimum resolution error text
     */
    public static final String ERR_MIN_RESOLUTION="[ERROR] Width and height must be positive numbers";
    /**
     * Constant for maximum resolution error text
     */
    public static final String ERR_MAX_RESOLUTION="[ERROR] The image resolution (i.e., width * height) cannot be greater than MAX_RESOLUTION";

    // Constructors
    /**
     * Class constructor
     * Exceptions are catch and printed
     * @param name album's cover name
     * @param artist album's cover artist
     * @param width album's cover width
     * @param height album's cover height
     */
    public AlbumCover(String name, String artist, int width, int height){
        try{
            setName(name);
            setArtist(artist);
            setWidth(width);
            setHeight(height);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    // Methods
    /**
     * Album cover name getter
     * @return album's cover name
     */
    public String getName() { return name; }

    /**
     * Album cover name setter
     * @param name album's cover name
     * @throws Exception if name is null or empty string
     */
    public void setName(String name) throws Exception{
        if (name == null | name == "") { throw new Exception(ERR_UNDEFINED_NAME); }
        this.name = name;
    }

    /**
     * Album cover artist getter
     * @return album's cover artist
     */
    public String getArtist() { return artist; }

    /**
     * Album cover artist setter
     * @param artist album's cover artist
     * @throws Exception if artist is null or empty string
     */
    public void setArtist(String artist) throws Exception {
        if (artist == null | artist == "") { throw new Exception(ERR_UNKNOWN_ARTIST); }
        this.artist = artist;
    }

    /**
     * Album cover width getter
     * @return album's cover width
     */
    public int getWidth() { return width; }

    /**
     * Album cover width setter
     * @param width album's cover width
     * @throws Exception if width is negative or equal to zero, or actual height multiplied by
     * provided width gives bigger resolution than allowed by maximum resolution constant
     */
    public void setWidth(int width) throws Exception {
        if (width<=0) { throw new Exception(ERR_MIN_RESOLUTION); }
        if (width*getHeight() > MAX_RESOLUTION) { throw new Exception(ERR_MAX_RESOLUTION); }
        this.width = width;
    }

    /**
     * Album cover height getter
     * @return album's cover height
     */
    public int getHeight() { return height; }

    /**
     * Album cover height setter
     * @param height album's cover height
     * @throws Exception if height is negative or equal to zero, or actual width multiplied by
     * provided height gives bigger resolution than allowed by maximum resolution constant
     */
    public void setHeight(int height) throws Exception {
        if (height<=0) { throw new Exception(ERR_MIN_RESOLUTION); }
        if (getWidth()*height > MAX_RESOLUTION) { throw new Exception(ERR_MAX_RESOLUTION); }
        this.height = height;
    }

    /**
     * Album cover aspect ratio getter
     * This method iterates to find "maximum common divisor"
     * When found width and height are scaled with mcd
     * @return album's cover aspect ratio
     */
    public String getAspectRatio(){
        int mcd=1;
        int w = getWidth();
        int h=getHeight();

        for (int i=1; i<=w && i<=h; i++){
            if (w%i == 0 && h%i == 0) {mcd=i;}
        }
        return w / mcd +":"+ h / mcd;
    }
}