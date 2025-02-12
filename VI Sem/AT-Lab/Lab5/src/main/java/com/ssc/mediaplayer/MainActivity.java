package com.ssc.mediaplayer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3;
    MediaPlayer mediaPlayer;
    double startTime=0,finaltime=0;
    Handler handler=new Handler();
    int fwdtime=5000,bcktime=5000;
    SeekBar seekBar;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b1=findViewById(R.id.button5);
        b2=findViewById(R.id.button6);
        b3=findViewById(R.id.button7);
        seekBar=findViewById(R.id.seekBar2);
        mediaPlayer=MediaPlayer.create(this,R.raw.song);
        seekBar.setClickable(false);
        mediaPlayer.setOnPreparedListener(mp->{
            finaltime=mediaPlayer.getDuration();
            seekBar.setMax((int)finaltime);
        });
        mediaPlayer.setOnCompletionListener(mp -> {
            seekBar.setProgress(0);  // Reset seek bar when the song finishes
            b2.setText("Start");  // Reset button text to "Start/Stop"
        });

        b1.setOnClickListener(v->{
            int curr=mediaPlayer.getCurrentPosition();
            if(curr-fwdtime>=0){
                mediaPlayer.seekTo(curr-fwdtime);
            }
            else{
                mediaPlayer.seekTo(0);
            }
        });
        b2.setOnClickListener(v->{
            if(mediaPlayer.isPlaying()){
                b2.setText("Start");
                mediaPlayer.pause();
            }
            else {
                b2.setText("Pause");
                mediaPlayer.start();
                updateSeekBar();
            }
        });
        b3.setOnClickListener(v->{
            int curr=mediaPlayer.getCurrentPosition();
            if(curr+fwdtime<=finaltime){
                mediaPlayer.seekTo(curr+fwdtime);
            }
            else{
                mediaPlayer.seekTo((int) finaltime);
            }
        });


    }
    private void updateSeekBar() {
        handler.postDelayed(updateSeekBarRunnable, 100);  // Update seek bar every 100ms
    }

    private Runnable updateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();  // Get current position of the song
            seekBar.setProgress((int) startTime);  // Update seek bar progress
            handler.postDelayed(this, 100);  // Keep updating every 100ms
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();  // Pause the media player when the activity is paused
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();  // Release the media player resources
        }
    }
}
