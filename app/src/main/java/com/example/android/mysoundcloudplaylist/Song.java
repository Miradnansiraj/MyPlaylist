package com.example.android.mysoundcloudplaylist;

public class Song{
    private String artistName;
    private String songName;
    private int songResID;

    public Song(String artistName, String songName, int songResID) {
        this.artistName = artistName;
        this.songName = songName;
        this.songResID = songResID;
    }

    //For Albums
    public Song(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongResID() {
        return songResID;
    }

    public void setSongResID(int songResID) {
        this.songResID = songResID;
    }

}
