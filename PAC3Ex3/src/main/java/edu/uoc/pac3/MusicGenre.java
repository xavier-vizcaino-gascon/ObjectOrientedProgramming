package edu.uoc.pac3;

import java.util.Arrays;

/**
 * This enum represents the Music Genres
 */
public enum MusicGenre{
    ALTERNATIVE("Alternative Rock",'A',125),
    BLUES("Blues",'B',80),
    CLASSICAL("Classical Music",'C',60),
    COUNTRY("Country Music",'Y',120),
    DISCO("Disco",'D',130),
    JAZZ("Jazz",'J',100),
    METAL("Metal",'M',140),
    POP("Pop Music",'P',110),
    ROCK_N_ROLL("Rock & Roll",'R',150),
    R_N_B("R & B",'N',90),
    OTHER("Other",'O',105);
    /**
     * Description attribute for each enum
     */
    private final String description;
    /**
     * Code attribute for each enum
     */
    private final char code;
    /**
     * Tempo attribute for each enum
     */
    private final int tempo;

    // Constructor

    /**
     * Enum constructor
     * @param description genre description
     * @param code genre code
     * @param avgTempo genre average tempo
     */
    private MusicGenre(String description, char code, int avgTempo){
        this.description=description;
        this.code=code;
        this.tempo=avgTempo;
    }

    /**
     * Description getter
     * @return genre description
     */
    public String getDescription(){ return description; }

    /**
     * Code getter
     * @return genre code
     */
    public char getCode() { return code; }

    /**
     * Tempo getter
     * @return genre tempo
     */
    public int getTempo() { return tempo; }

    /**
     * This method gets the music genre receiving its code
     * @param code genre code
     * @return genre enum object
     */
    public static MusicGenre getMusicGenre (char code){
        for (var genre:values()){
            if (genre.code==code){
                return genre;
            }
        }
        return null;
    }

    /**
     * This method sorts the genres by its code
     * @return array of genre enum objects sorted by its code
     */
    public static MusicGenre[] getGenresSortedByCode(){
        MusicGenre[] orderMG = new MusicGenre[values().length];
        char[] codes = new char[values().length];
        int i=0;

        // Convert codes into array
        for (var genre:values()){
            codes[i]= genre.code;
            i++;
        }

        // Sorting algorithm
        for (i=0; i<codes.length-1; i++){
            if(Character.compare(codes[i+1], codes[i])<0){
                char c1 = codes[i];
                char c2 = codes[i+1];
                //Swap & reset
                codes[i]=c2;
                codes[i+1]=c1;
                i=0;
            }
        }

        // Generate output based on sorted array
        i=0;
        for (var code:codes){
            orderMG[i]= getMusicGenre(code);
            i++;
        }
        return orderMG;
    }

    /**
     * This method gets the genre with next higher tempo
     * @return genre enum object with next higher tempo
     */
    public MusicGenre getGenreWithNextHigherTempo() {
        int[] tempos = new int[values().length];
        int i = 0;
        MusicGenre out = null;

        // Convert tempos into array & sort
        for (var genre : values()) {
            tempos[i] = genre.tempo;
            i++;
        }
        Arrays.sort(tempos);

        // Finding next tempo value
        int tempo = getTempo();
        boolean found = false;
        i = 0;

        while (!found) {
            if (tempo != tempos[i]) {
                i++;
            } else {
                found = true;
                tempo = (i < tempos.length-1) ? tempos[i + 1] : tempos[i]; //tempo stores newTempo
            }
        }

        // Getting enum object
        for (var genre : values()) {
            if (genre.tempo == tempo) {
                out = genre;
            }
        }
        return out;
    }
}
