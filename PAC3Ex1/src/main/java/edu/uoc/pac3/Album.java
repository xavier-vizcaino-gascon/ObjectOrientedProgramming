package edu.uoc.pac3;

import java.time.LocalDate;
import java.lang.String;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

/**
 * This class represents an album
 * @author Xavier Vizcaino
 * @version 1.0
 */
public class Album {
    // Attributes
    /**
     * Album identifier (UUID)
     */
    private UUID id;
    /**
     * Album title
     */
    private String title;
    /**
     * Album artist
     */
    private String artist;
    /**
     * Album genre
     */
    private String genre;
    /**
     * Album release date
     */
    private LocalDate releaseDate;
    /**
     * Album boolean variable informing about online availability
     */
    private boolean availableOnline;
    /**
     * Album price
     */
    private double price;
    /**
     * Constant for minimum price allowed
     */
    private static final double MIN_PRICE=4.99;
    /**
     * Constant for maximum price allowed
     */
    private static final double MAX_PRICE=29.99;
    /**
     * Constant for genre error text
     */
    public static final String ERR_GENRE="[ERROR] The genre is not a valid value";
    /**
     * Constant for min price error text
     */
    public static final String ERR_MIN_PRICE="[ERROR] The album price must be greater than MIN_PRICE";
    /**
     * Constant for max price error text
     */
    public static final String ERR_MAX_PRICE="[ERROR] The album price must be less than MAX_PRICE (or 80% of MAX_PRICE if the album is available online)";
    /**
     * Album cover reference to AlbumCover object
     */
    private AlbumCover albumCover;

    // Constructors
    /**
     * Class constructor with 3 parameters.
     * Non-feed parameters are initialized to default values
     * Exceptions are catch and printed
     * @param title album's title
     * @param artist album's artist
     * @param genre album's genre
     */
    public Album(String title, String artist, String genre){
        try{
            setTitle(title);
            setArtist(artist);
            setGenre(genre);
            setId();
            setReleaseDate(null);
            setAvailableOnline(false);
            setPrice(14.99);
            this.albumCover=null;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Class constructor with 6 parameters.
     * Non-feed parameters are initialized to default values
     * Exceptions are catch and printed
     * @param title album's title
     * @param artist album's artist
     * @param genre album's genre
     * @param releaseDate album's release date
     * @param availableOnline album's online availability
     * @param price album's price
     */
    public Album(String title, String artist, String genre, LocalDate releaseDate, boolean availableOnline, double price){
        try{
            setTitle(title);
            setArtist(artist);
            setGenre(genre);
            setId();
            setReleaseDate(releaseDate);
            setAvailableOnline(availableOnline);
            setPrice(price);
            this.albumCover=null;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Class constructor with 10 parameters.
     * Exceptions are catch and printed
     * @param title album's title
     * @param artist album's artist
     * @param genre album's genre
     * @param releaseDate album's release date
     * @param availableOnline album's online availability
     * @param price album's price
     * @param coverName album's cover name
     * @param coverArtist album's cover artist
     * @param coverWidth album's cover width
     * @param coverHeight album's cover height
     */
    public Album(String title, String artist, String genre, LocalDate releaseDate, boolean availableOnline, double price, String coverName, String coverArtist, int coverWidth, int coverHeight){
        try{
            setTitle(title);
            setArtist(artist);
            setGenre(genre);
            setId();
            setReleaseDate(releaseDate);
            setAvailableOnline(availableOnline);
            setPrice(price);
            setAlbumCover(coverName, coverArtist, coverWidth, coverHeight);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Methods
    /**
     * Album id getter
     * @return album's id
     */
    public UUID getId() { return id; }

    /**
     * Album id setter
     * Method sets random UUID as album id
     */
    private void setId () { this.id=UUID.randomUUID(); }

    /**
     * Album title getter
     * @return album's title
     */
    public String getTitle() { return title; }

    /**
     * Album title setter
     * @param title album's title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Album artist getter
     * @return album's artist
     */
    public String getArtist() { return artist; }

    /**
     * Album artist setter
     * This method generates Pascal Case versions of artist name.
     * When artist name is multi-word, this method adds "-" as separator between words
     * @param artist album's artist
     */
    public void setArtist(String artist) {

        String clean_artist = artist.strip().replaceAll(" +", " "); //string preparation with RegEx
        String[] words = clean_artist.split(" ");
        String Pascal ="";

        //Main loop "Pascalize-ing" input words
        for (String word : words) {
            String temp = word.substring(0, 1).toUpperCase() +
                    word.substring(1).toLowerCase();
            Pascal = String.join("-", Pascal, temp);
            }

        //Remove first "-" from Pascal var initialization "" & standard joint procedure
        this.artist = Pascal.substring(1);
    }

    /**
     * Album genre getter
     * @return Album's genre
     */
    public String getGenre() { return genre; }

    /**
     * Album genre setter
     * This method checks whether the param is single or multi-worded.
     * For single-worded, this method checks if any of genre options is contained in the parameter
     * @param genre album's genre
     * @throws Exception when: 1. The input param is multi-worded and 2. None of the options is found as part of the input param.
     */
    public void setGenre(String genre) throws Exception {
        String [] options = {"CLASSIC", "JAZZ", "ROCK&ROLL", "POP", "DISCO"};
        String clean_genre=genre.strip().toUpperCase();

        String [] words = clean_genre.split(" ");

        // Case +1 word
        if (words.length>1) {throw new Exception(ERR_GENRE);}

        // Loop initialization
        boolean found = false;
        int i = 0;

        // Main loop through options checking if option is contained in genre
        while (i < options.length & !found) {
            if (clean_genre.contains(options[i])) {
                found = true;
            }
            i++;
        }

        // Exception raise
        if (!found) {
            throw new Exception(ERR_GENRE);
        }
        this.genre = clean_genre;
    }

    /**
     * Album release date getter
     * @return album's release date
     */
    public LocalDate getReleaseDate() { return releaseDate; }

    /**
     * Album release date setter
     * @param releaseDate album's release date
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Album release date getter with specific human friendly format
     * This method provides a String with the release date in a format such as "MMMM d, yyyy" in English
     * Moreover, this method checks the year's difference between release and now and adds this information as additional output
     * @return album's detailed release date
     */
    public String getFormattedReleaseDate(){

        String pre_text = "Released on ";
        String post_text;

        Locale locale = Locale.forLanguageTag("en-US");
        LocalDate rDate = getReleaseDate();
        LocalDate nDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy").withLocale(locale);
        String main_text = rDate.format(formatter);

        int yDiff = rDate.getYear()-nDate.getYear();

        if (yDiff==0) {
            post_text=" (this year)";
            return pre_text+main_text+post_text;
        }
        if (yDiff==1) {
            post_text=" (next year)";
            return pre_text+main_text+post_text;
        }
        if (yDiff==-1) {
            post_text=" (last year)";
            return pre_text+main_text+post_text;
        }
        if (yDiff > 0) {
            post_text=" (in " + yDiff + " years)";
        }
        else {
            post_text=" (" + -yDiff + " years ago)";
        }
        return pre_text+main_text+post_text;
    }

    /**
     * Album online availability getter
     * @return album's online availability
     */
    public boolean isAvailableOnline() { return availableOnline; }

    /**
     * Album online availability setter
     * This method checks if the price is more than 80% of the maximum allowed when changing from not available to available online
     * If so, the price is updated to 80% of the maximum allowed, otherwise the price provided as param is set.
     * @param availableOnline album's online availability
     * @throws Exception from setPrice may be propagated through this method
     */
    public void setAvailableOnline(boolean availableOnline) throws Exception {
        if (!isAvailableOnline() & availableOnline & price>0.8*MAX_PRICE){
            setPrice(0.8*MAX_PRICE);
        }
        this.availableOnline = availableOnline;
    }

    /**
     * Album price getter
     * @return album's price
     */
    public double getPrice() { return price; }

    /**
     * Album price setter
     * @param price album's price
     * @throws Exception when: 1. price is less than the minimum allowed,
     * 2. the album is not available online and the price is more than the maximum price allowed or
     * 3. the album is available online and the price is more than 80% the maximum price allowed
     */
    public void setPrice(double price) throws Exception {
        //Exceptions
        if (price<MIN_PRICE) { throw new Exception(ERR_MIN_PRICE);}
        if (!isAvailableOnline() & price>MAX_PRICE) {throw new Exception(ERR_MAX_PRICE);}
        if (isAvailableOnline() & price>MAX_PRICE*0.8) {throw new Exception(ERR_MAX_PRICE);}
        //Main
        this.price = price;
    }

    /**
     * AlbumCover object getter
     * @return album's albumCover
     */
    public AlbumCover getAlbumCover() {return albumCover; }

    /**
     * AlbumCover setter
     * This method checks typical error conditions, if any is achieved "null" is set into albumCover reference,
     * otherwise parameters are set
     * @param name album's cover name
     * @param artist album's cover artist
     * @param width album's cover width
     * @param height album's cover height
     */
    public void setAlbumCover(String name, String artist, int width, int height) {
        if (name == null | name == "" | artist == null | artist == "" | width<=0 | height<=0) { albumCover=null;}
        else {
            albumCover = new AlbumCover(name, artist, width, height);
        }
    }
}
