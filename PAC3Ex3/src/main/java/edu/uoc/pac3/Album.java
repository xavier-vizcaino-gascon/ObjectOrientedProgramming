package edu.uoc.pac3;

public class Album {

    private String title;
    private MusicGenre musicGenre;

    public Album(String title, MusicGenre musicGenre) {
        setTitle(title);
        setMusicGenre(musicGenre);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
    }
}
