package com.tgwgroup.MiRearScreenSwitcher;

import android.graphics.Bitmap;
import android.media.session.MediaSession;

/**
 * Cache para almacenar temporalmente datos de notificaciones de música
 * que no pueden ser pasados a través de comandos shell
 */
public class MusicNotificationCache {
    private static MusicNotificationCache instance;
    
    private String title;
    private String artist;
    private Bitmap albumArt;
    private boolean isPlaying;
    private MediaSession.Token token;
    
    private MusicNotificationCache() {}
    
    public static synchronized MusicNotificationCache getInstance() {
        if (instance == null) {
            instance = new MusicNotificationCache();
        }
        return instance;
    }
    
    public synchronized void setData(String title, String artist, Bitmap albumArt, boolean isPlaying, MediaSession.Token token) {
        this.title = title;
        this.artist = artist;
        this.albumArt = albumArt;
        this.isPlaying = isPlaying;
        this.token = token;
    }
    
    public synchronized String getTitle() {
        return title;
    }
    
    public synchronized String getArtist() {
        return artist;
    }
    
    public synchronized Bitmap getAlbumArt() {
        return albumArt;
    }
    
    public synchronized boolean isPlaying() {
        return isPlaying;
    }
    
    public synchronized MediaSession.Token getToken() {
        return token;
    }
    
    public synchronized void clear() {
        this.title = null;
        this.artist = null;
        this.albumArt = null;
        this.isPlaying = false;
        this.token = null;
    }
}
