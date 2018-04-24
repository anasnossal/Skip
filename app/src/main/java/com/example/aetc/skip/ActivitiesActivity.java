package com.example.aetc.skip;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivitiesActivity extends AppCompatActivity {
    private Button playButton;
    private SeekBar positionBar;
    private TextView elapsedTimeLabel;
    private TextView remainingTimeLabel;
    private MediaPlayer mp;
    private int totalTime;
    private int currentPosition = 0;
    private String elapsedTime;
    private ImageView journal;
    private ImageView activities;
    private ImageView rewards;
    private ImageView news;
    private ImageView inbox;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        playButton = (Button) findViewById(R.id.play_button);
        positionBar = (SeekBar) findViewById(R.id.progress_seek_bar);
        elapsedTimeLabel = (TextView) findViewById(R.id.time_start);
        remainingTimeLabel = (TextView) findViewById(R.id.time_finish);

// Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("lesson date");
        myRefTwo = database.getReference("lesson time");



// Media Player
        MediaStore.Audio.Media Player;
        mp = MediaPlayer.create(this, R.raw.fonarik);
        mp.setLooping(false);
        mp.seekTo(0);
        totalTime = mp.getDuration();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date today = Calendar.getInstance().getTime();
                String reportDate = df.format(today);
                myRef.setValue(reportDate);
                Toast.makeText(ActivitiesActivity.this, "Congrats! You Received a Reward!!!", Toast.LENGTH_SHORT).show();
                myRefTwo.setValue(elapsedTime);


            }
        });

//        Position Bar

        positionBar.setMax(totalTime);
        positionBar.setProgress(currentPosition);
        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mp.seekTo(progress);
                    positionBar.setProgress(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


// Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();


        journal = (ImageView) findViewById(R.id.journal);
        activities = (ImageView) findViewById(R.id.activities);
        rewards = (ImageView) findViewById(R.id.rewards);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            currentPosition = msg.what;
//             Update positionBar.
            positionBar.setProgress(currentPosition);

//            Update Labels
            elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);


            String remainingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);

        }
    };


    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;
        return timeLabel;


    }

    public void playBtnClick(View view) {

        if (!mp.isPlaying()) {
//        Stopping
            mp.start();
            playButton.setBackgroundResource(R.drawable.ic_pause);


        } else {
//        Playing
            mp.pause();
            playButton.setBackgroundResource(R.drawable.ic_play_circle_outline_48px);

        }
    }

    //       Journal intent

    public void journalIntent(View view) {

        Intent journalInt = new Intent(this, JournalActivity.class);
        startActivity(journalInt);

    }

    //      Activities intent
    public void activitiesIntent(View view) {

        Intent activitiesInt = new Intent(this, ActivitiesActivity.class);
        startActivity(activitiesInt);

    }

//       Rewards intent

    public void rewardsIntent(View view) {
        Intent rewardsInt = new Intent(this, RewardsActivity.class);
        startActivity(rewardsInt);
    }

    //       News intent

    public void newsIntent(View view) {
        Intent newsInt = new Intent(this, NewsActivity.class);
        startActivity(newsInt);
    }

    //       Inbox intent

    public void inboxIntent(View view) {
        Intent inboxInt = new Intent(this, InboxActivity.class);
        if (inboxInt.resolveActivity(getPackageManager()) != null) {
            inboxInt.setData(Uri.parse("mailto:"));
            startActivity(inboxInt);
        }
    }
}
