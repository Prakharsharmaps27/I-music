package com.prakharsharma.imusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class individualsongs extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updatseek.interrupt();
    }

    TextView textView;
    ConstraintLayout constraintLayout;
    ImageView forward,pause, backward;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    ArrayList<File> songs;
    String name;
    int position;
    Thread updatseek;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invidualsongs);

        textView = findViewById(R.id.textView2);
        forward = findViewById(R.id.forward);
        pause = findViewById(R.id.pause);
        backward = findViewById(R.id.backward);
        seekBar = findViewById(R.id.seekBar);

        constraintLayout = findViewById(R.id.Main_layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        songs =(ArrayList) bundle.getParcelableArrayList("song");
        name = intent.getStringExtra("current");
        textView.setText(name);
        textView.setSelected(true);
        position = intent.getIntExtra("position",0);
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this,uri);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());

        
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        updatseek = new Thread(){

            @Override
            public void run() {
                int currentposition = 0;

                try {
                    while(currentposition<mediaPlayer.getDuration()) {
                        currentposition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentposition);
                        sleep(800);

                    }



                }
                    catch(Exception e){

                    e.printStackTrace();

                    }

                }

            };
        updatseek.start();

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    pause.setImageResource(R.drawable.play);
                mediaPlayer.pause();


            }
                else{
                    pause.setImageResource(R.drawable.pause);
                    mediaPlayer.start();

                }
        }

        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                if(position!=0){
                    position = position-1;
                }else{
                    position = songs.size() -1;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());

                name = songs.get(position).getName().toString();
                textView.setText(name);
                textView.setSelected(true);


                pause.setImageResource(R.drawable.pause);

                updatseek = new Thread(){

                    @Override
                    public void run() {
                        int currentposition = 0;
                        try {
                            while(currentposition<mediaPlayer.getDuration()) {
                                currentposition = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(currentposition);
                                sleep(800);
                            }

                        }
                        catch(Exception e){

                            e.printStackTrace();

                        }

                    }

                };
                updatseek.start();


            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                if(position< songs.size()-1){
                    position = position+1;
                }else{
                    position = 0;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());

                name = songs.get(position).getName().toString();
                textView.setText(name);
                textView.setSelected(true);

                pause.setImageResource(R.drawable.pause);
                int currentpositions = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentpositions);

                updatseek = new Thread(){

                    @Override
                    public void run() {
                        int currentposition = 0;
                        try {
                            while(currentposition<mediaPlayer.getDuration()) {
                                currentposition = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(currentposition);
                                sleep(800);
                            }

                        }
                        catch(Exception e){

                            e.printStackTrace();

                        }

                    }

                };
                updatseek.start();


            }

    });

       

    }


    }




