package com.example.android.mysoundcloudplaylist;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.android.mysoundcloudplaylist.adapter.SongAdapter;

import java.util.ArrayList;

public class ArtistSongActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private ImageButton playPause, prev, next;
    private final ArrayList<Song> songs = new ArrayList<>();
    private final ArrayList<Song> artistsongs = new ArrayList<>();
    private int currentSongNumber = 0;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_artist_song);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get artist name
        Intent intent = getIntent();
        String Name = intent.getStringExtra("ARTISTNAME");
        Log.i("Name", Name);
        getSupportActionBar().setTitle(Name);
        // Enable the Up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        playPause = findViewById(R.id.play_pause_button_artist_song);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(playPause);
            }
        });
        prev = findViewById(R.id.previous_button_artist_song);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(prev);
            }
        });
        next = findViewById(R.id.skip_next_button_artist_song);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(next);
            }
        });
        songs.add(new Song("88rising", "Midsummer Madness", R.raw.midsummer_madness_88rising));
        songs.add(new Song("Aruna & Rameses B", "Ready to go", R.raw.arunaandramesesbreadytogo));
        songs.add(new Song("Alex Ferro Catas", "One Night", R.raw.alexferrocatasonenight));
        songs.add(new Song("Bad Computer", "Silhouette", R.raw.badcomputersilhouette));
        songs.add(new Song("Bazzi", "Beautiful", R.raw.bazzibeautiful));
        songs.add(new Song("Bazzi", "Mine", R.raw.bazzimine));
        songs.add(new Song("Bumkey", "When i saw you", R.raw.bumkeywhenisawyou));
        songs.add(new Song("Childish Gambino", "Feels Like Summer", R.raw.childishgambinofeelslikesummer));
        songs.add(new Song("Childish Gambino", "This is America", R.raw.childishgambinothisisamerica));
        songs.add(new Song("Childish Gambino", "Sober", R.raw.childishgambinosober));
        //create a list of songs for the artist selected
        for(int i = 0; i <10; i++)
        {
            if(Name.equals(songs.get(i).getArtistName()))
                artistsongs.add(songs.get(i));
        }
        mMediaPlayer = MediaPlayer.create(this, artistsongs.get(currentSongNumber).getSongResID());
        SongAdapter adapter = new SongAdapter(this, artistsongs);
        ListView listView = findViewById(R.id.song_list_artist_song);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                currentSongNumber = i;
                Song song = artistsongs.get(currentSongNumber);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(), song.getSongResID());

                    // Start the audio file
                    mMediaPlayer.start();
                    playPause.setImageResource(R.drawable.twotone_pause_circle_filled_black_48);

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    public void onButtonClick(View view)
    {
        switch (view.getId())
        {
            case R.id.play_pause_button_artist_song:
                if(mMediaPlayer.isPlaying())
                {
                    mMediaPlayer.pause();
                    playPause.setImageResource(R.drawable.twotone_play_circle_filled_black_48);
                }
                else
                {
                    mMediaPlayer.start();
                    playPause.setImageResource(R.drawable.twotone_pause_circle_filled_black_48);
                }
                break;
            case R.id.previous_button_artist_song:
                releaseMediaPlayer();
                if(currentSongNumber-1 <0)
                    currentSongNumber = artistsongs.size()-1;
                else
                    currentSongNumber--;
                mMediaPlayer = MediaPlayer.create(this, artistsongs.get(currentSongNumber).getSongResID());
                mMediaPlayer.start();
                break;
            case R.id.skip_next_button_artist_song:
                releaseMediaPlayer();
                if(currentSongNumber+1 >(artistsongs.size()-1))
                    currentSongNumber = 0;
                else
                    currentSongNumber++;
                mMediaPlayer = MediaPlayer.create(this, artistsongs.get(currentSongNumber).getSongResID());
                mMediaPlayer.start();
                break;
        }
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
