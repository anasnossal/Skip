package com.example.aetc.skip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView mSkipList;
    private DatabaseReference mDatabase;
    private ImageView journal;
    private ImageView activities;
    private ImageView rewards;
    private ImageView news;
    private ImageView inbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        mSkipList = (RecyclerView) findViewById(R.id.skip_list);
        mSkipList.setHasFixedSize(true);
        mSkipList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Skip");

        journal = (ImageView) findViewById(R.id.journal);
        activities = (ImageView) findViewById(R.id.activities);
        rewards = (ImageView) findViewById(R.id.rewards);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <Skip,SkipViewHolder> FBRA = new FirebaseRecyclerAdapter<Skip, SkipViewHolder>(
                Skip.class,
                R.layout.skip_row,
                SkipViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(SkipViewHolder viewHolder, Skip model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };
        mSkipList.setAdapter(FBRA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class SkipViewHolder extends RecyclerView.ViewHolder{

        public SkipViewHolder(View itemView){
            super(itemView);
            View mView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView) itemView.findViewById(R.id.textTitle);
            post_title.setText(title);
        }

        public void setDesc(String desc){
            TextView post_desc = (TextView) itemView.findViewById(R.id.textDesc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.addicon) {
            Intent intent = new Intent(NewsActivity.this, com.example.aetc.skip.PostActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
