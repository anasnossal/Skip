package com.example.aetc.skip;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JournalActivity extends AppCompatActivity {

    private ImageView journal;
    private ImageView activities;
    private ImageView rewards;
    private ImageView news;
    private ImageView inbox;
    private Button startButtonOne;
    private CheckBox checkBox;
    private TextView date;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRefTwo;
//    private String loadedDataTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        journal = (ImageView) findViewById(R.id.journal);
        activities = (ImageView) findViewById(R.id.activities);
        rewards = (ImageView) findViewById(R.id.rewards);
        startButtonOne = findViewById(R.id.start_button);
        date = findViewById(R.id.date);
        checkBox = findViewById(R.id.checkbox);

//          Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(date.getText().toString());
        myRefTwo = database.getReference("lesson time");


// Update Date

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.

                String loadedData = dataSnapshot.getValue(String.class);
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date today = Calendar.getInstance().getTime();
                loadedData = df.format(today);
                date.setText(loadedData);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(JournalActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });


        //        Update CheckBox


        myRefTwo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                String loadedDataTwo = dataSnapshot.getValue(String.class);
                if (loadedDataTwo != null && (loadedDataTwo.equals("0:23") || loadedDataTwo.equals("0:22"))) {
                    boolean checked = true;
                    checkBox.setChecked(checked);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(JournalActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void trackOne(View view)

    {
        Intent lessonOne = new Intent(this, ActivitiesActivity.class);

        startActivity(lessonOne);
    }
}
