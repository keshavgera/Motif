package com.codecube.keshav.motif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import utils.TouchImageView;


/**
 * Created by bali on 19/3/16.
 */
public class MultiTouchActivity extends AppCompatActivity {
    String url;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        url = getIntent().getStringExtra("chatHistoryImage");
        position =  getIntent().getIntExtra("position", 0);

        TouchImageView img = new TouchImageView(this);
        img.setBackgroundColor(getResources().getColor(R.color.white));
        Picasso.with(MultiTouchActivity.this).load(url)
                .error(R.color.light_grey)
                .placeholder(R.color.light_grey)
                .into(img);
        img.setMaxZoom(4f);
        setContentView(img);
    }
}
