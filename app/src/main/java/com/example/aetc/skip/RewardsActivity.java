package com.example.aetc.skip;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardsActivity extends AppCompatActivity {

    private ImageView journal;
    private ImageView activities;
    private ImageView rewards;
    private ImageView news;
    private ImageView inbox;
    private ImageView heart;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ImageView starOne;
//    private String loadedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        journal = (ImageView) findViewById(R.id.journal);
        activities = (ImageView) findViewById(R.id.activities);
        rewards = (ImageView) findViewById(R.id.rewards);
        starOne = findViewById(R.id.star_1);


        //   Firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("lesson time");


        //  Update Image

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                String loadedData = dataSnapshot.getValue(String.class);
                if (loadedData != null && (loadedData.equals("0:23") || loadedData.equals("0:22"))) {
                    starOne.setImageResource(R.drawable.yellow_star);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(RewardsActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
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
}
