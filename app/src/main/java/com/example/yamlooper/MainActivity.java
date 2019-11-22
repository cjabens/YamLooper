package com.example.yamlooper;

import androidx.appcompat.app.AppCompatActivity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.media.AudioManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //declare vars here
    private SoundPool soundPool;
    private int soundID;
    boolean plays = false, loaded = false;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;
    int counter;
    MediaPlayer loopPlayer = new MediaPlayer();

    //oncreate is called when app is first opened/created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the layout of activity
        setContentView(R.layout.activity_main);

        //AudioManager settings for volume control
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;



        //hardware button settings for controlling volume
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //counter to keep track of stream id
        counter = 0;

        //load sounds to be looped
        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleID, int status){
                loaded = true;
            }
        });
        soundID = soundPool.load(this, R.raw.aminor, 1);
    }

    public void playSound(View v) {
        if (loaded && !plays){
            soundPool.play(soundID, volume, volume, 1, 0, 1f);
            counter++;
            Toast.makeText(this, "Played Sound", Toast.LENGTH_SHORT).show();
            plays = true;
        }
    }

    public void playLoop(View v){
        if (loaded && !plays){
            //sound will keep playing if loop par = -1
            soundPool.play(soundID, volume, volume, 1, -1, 1f);
            counter++;
            Toast.makeText(this, "Plays loop", Toast.LENGTH_SHORT).show();
            plays = true;
        }
    }

    //This doesn't stop the loop playback right now
    public void pauseSound(View v){
        if(plays){
            soundPool.pause(soundID);
            soundID = soundPool.load(this, R.raw.aminor, counter);
            Toast.makeText(this, "Pause Sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
    }

    public void stopSound(View v){
        if (plays){
            soundPool.stop(soundID);
            soundID = soundPool.load(this, R.raw.aminor, counter);
            Toast.makeText(this, "Stop sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
    }

}
