// MainActivity.java
package com.ssc.at;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView songListView;
    private MediaPlayer mediaPlayer;
    private ArrayList<String> songList;
    private HashMap<String, Integer> songMap;
    private boolean isPlaying = false;
    private String currentSong = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ListView
        songListView = findViewById(R.id.songListView);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();

        // Initialize song list and map
        setupSongList();

        // Set up the ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                songList
        );
        songListView.setAdapter(adapter);

        // Set item click listener for the ListView
        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSong = songList.get(position);
                playSong(selectedSong);
            }
        });
    }

    private void setupSongList() {
        songList = new ArrayList<>();
        songMap = new HashMap<>();

        // Add songs to the list and map them to resource IDs
        // In a real app, you would typically load these from storage or a database
        songList.add("Song 1");
        songList.add("Song 2");

        // Map song names to resource IDs
        // These would be your actual song resources in the raw folder
        songMap.put("Song 1", R.raw.song1);
        songMap.put("Song 2", R.raw.song2);
    }

    private void playSong(String songName) {
        try {
            // If a song is already playing, stop it
            if (isPlaying) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }

            // Get the resource ID for the selected song
            Integer songResId = songMap.get(songName);
            if (songResId != null) {
                // Set the data source from the raw resource
                mediaPlayer = MediaPlayer.create(this, songResId);

                // Start playing
                mediaPlayer.start();
                isPlaying = true;
                currentSong = songName;

                // Show toast message
                Toast.makeText(this, "Now playing: " + songName, Toast.LENGTH_SHORT).show();

                // Set a completion listener
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        isPlaying = false;
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error playing song: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when the activity is destroyed
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}