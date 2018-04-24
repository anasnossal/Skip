package com.example.aetc.skip;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class InboxActivity extends AppCompatActivity {

    private ImageView journal;
    private ImageView activities;
    private ImageView rewards;
    private ImageView news;
    private ImageView inbox;

    EditText recipient;
    EditText subject;
    EditText message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        journal = (ImageView) findViewById(R.id.journal);
        activities = (ImageView) findViewById(R.id.activities);
        rewards = (ImageView) findViewById(R.id.rewards);

        recipient = (EditText) findViewById(R.id.recipient);
        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);

        send = (Button) findViewById(R.id.sendEmail);

        addListeneroOnButton();
    }


    // Send button intent

    public void addListeneroOnButton() {
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String to = recipient.getText().toString();
                String sub = subject.getText().toString();
                String msg = message.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT, sub);
                email.putExtra(Intent.EXTRA_TEXT, msg);

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email Client"));
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
