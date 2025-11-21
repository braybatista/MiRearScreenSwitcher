package com.tgwgroup.MiRearScreenSwitcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RearScreenMusicActivity extends Activity {

    private static final String TAG = "RearMusicActivity";

    private ImageView albumArtView;
    private ImageView backgroundAlbumArtView;
    private TextView titleView;
    private TextView artistView;
    private ImageView prevButton;
    private ImageView playPauseButton;
    private ImageView nextButton;
    private ImageView closeButton;
    private RelativeLayout rootLayout;

    private MediaController mediaController;
    
    private android.content.BroadcastReceiver closeReceiver = new android.content.BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("com.tgwgroup.MiRearScreenSwitcher.CLOSE_MUSIC_WIDGET".equals(intent.getAction())) {
                Log.d(TAG, "Received close broadcast - finishing activity");
                finish();
            }
        }
    };

    private final MediaController.Callback mediaCallback = new MediaController.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackState state) {
            updatePlaybackState(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadata metadata) {
            updateMetadata(metadata);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set orientation to landscape programmatically
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Make sure the screen is on and the activity is shown when locked.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        
        // Agregar flags críticas para lockscreen
        getWindow().addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        // Make activity fullscreen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );

        setContentView(R.layout.activity_rear_screen_music);

        // Find views
        albumArtView = findViewById(R.id.music_album_art);
        backgroundAlbumArtView = findViewById(R.id.background_album_art);
        titleView = findViewById(R.id.music_title);
        artistView = findViewById(R.id.music_artist);
        prevButton = findViewById(R.id.music_prev_button);
        playPauseButton = findViewById(R.id.music_play_pause_button);
        nextButton = findViewById(R.id.music_next_button);
        closeButton = findViewById(R.id.close_button);
        rootLayout = findViewById(R.id.root_layout);

        // Activate marquee for title
        titleView.setSelected(true);
        titleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        // Handle the intent that started the activity
        handleIntent(getIntent());

        // Set click listeners
        prevButton.setOnClickListener(v -> {
            if (mediaController != null) mediaController.getTransportControls().skipToPrevious();
        });

        playPauseButton.setOnClickListener(v -> {
            if (mediaController != null) {
                if (mediaController.getPlaybackState().getState() == PlaybackState.STATE_PLAYING) {
                    mediaController.getTransportControls().pause();
                } else {
                    mediaController.getTransportControls().play();
                }
            }
        });

        nextButton.setOnClickListener(v -> {
            if (mediaController != null) mediaController.getTransportControls().skipToNext();
        });

        closeButton.setOnClickListener(v -> finishAndRestore());
        
        // Registrar receptor para cerrar cuando se desactive el servicio
        android.content.IntentFilter closeFilter = new android.content.IntentFilter("com.tgwgroup.MiRearScreenSwitcher.CLOSE_MUSIC_WIDGET");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(closeReceiver, closeFilter, android.content.Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(closeReceiver, closeFilter);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // When a new intent arrives, re-handle it to update the media session
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // Unregister callback from the old controller
        if (mediaController != null) {
            mediaController.unregisterCallback(mediaCallback);
        }

        // Verificar si los datos vienen del caché (lanzado por shell command)
        boolean fromCache = intent.getBooleanExtra("fromCache", false);
        
        if (fromCache) {
            // Obtener datos del caché
            MusicNotificationCache cache = MusicNotificationCache.getInstance();
            MediaSession.Token token = cache.getToken();
            
            if (token != null) {
                mediaController = new MediaController(this, token);
                mediaController.registerCallback(mediaCallback);
                // Initial update
                updateMetadata(mediaController.getMetadata());
                updatePlaybackState(mediaController.getPlaybackState());
            } else {
                Log.e(TAG, "MediaSession.Token is null in cache. Using cached data.");
                String title = cache.getTitle();
                updateTitle("          " + title);
                artistView.setText(cache.getArtist());
                Bitmap albumArt = cache.getAlbumArt();
                if (albumArt != null) {
                    albumArtView.setImageBitmap(albumArt);
                    updateBackground(albumArt);
                } else {
                    albumArtView.setImageResource(android.R.color.darker_gray);
                }
                updatePlayPauseButton(cache.isPlaying());
            }
        } else {
            // Modo legacy: datos vienen directamente del intent
            MediaSession.Token token = intent.getParcelableExtra("token");

            // Create a new MediaController from the new token
            if (token != null) {
                mediaController = new MediaController(this, token);
                mediaController.registerCallback(mediaCallback);
                // Initial update
                updateMetadata(mediaController.getMetadata());
                updatePlaybackState(mediaController.getPlaybackState());
            } else {
                Log.e(TAG, "MediaSession.Token is null. Media controls will not work.");
                // Populate with placeholder data if token is null
                String title = intent.getStringExtra("title");
                updateTitle("          " + title);
                artistView.setText(intent.getStringExtra("artist"));
                Bitmap albumArt = intent.getParcelableExtra("albumArt");
                if (albumArt != null) {
                    albumArtView.setImageBitmap(albumArt);
                    updateBackground(albumArt);
                } else {
                    albumArtView.setImageResource(android.R.color.darker_gray);
                }
                updatePlayPauseButton(intent.getBooleanExtra("isPlaying", false));
            }
        }
    }


    private void updateMetadata(MediaMetadata metadata) {
        if (metadata == null) return;
        String title = metadata.getString(MediaMetadata.METADATA_KEY_TITLE);
        updateTitle("          " + title); // Add 10 spaces
        artistView.setText(metadata.getString(MediaMetadata.METADATA_KEY_ARTIST));
        Bitmap albumArt = metadata.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART);
        if (albumArt != null) {
            albumArtView.setImageBitmap(albumArt);
            updateBackground(albumArt);
        } else {
            albumArtView.setImageResource(android.R.color.darker_gray);
            backgroundAlbumArtView.setImageDrawable(null);
        }
    }

    private void updateTitle(String newTitle) {
        if (newTitle != null && !newTitle.equals(titleView.getText().toString())) {
            titleView.setText(newTitle);
        }
    }

    private void updatePlaybackState(PlaybackState state) {
        if (state == null) return;
        updatePlayPauseButton(state.getState() == PlaybackState.STATE_PLAYING);
    }

    private void updatePlayPauseButton(boolean isPlaying) {
        if (isPlaying) {
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    private void updateBackground(Bitmap bitmap) {
        backgroundAlbumArtView.setImageBitmap(bitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            backgroundAlbumArtView.setRenderEffect(RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.MIRROR));
        }
    }

    private void finishAndRestore() {
        Intent intent = new Intent("com.tgwgroup.MiRearScreenSwitcher.RESTORE_REAR_STATE");
        sendBroadcast(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(closeReceiver);
        } catch (Exception e) {
            Log.w(TAG, "Failed to unregister receiver", e);
        }
        if (mediaController != null) {
            mediaController.unregisterCallback(mediaCallback);
        }
    }
}
