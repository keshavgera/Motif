package com.codecube.keshav.motif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import models.OffersPojo;
import utils.CommonMethod;
import utils.ImageLoader1;

public class OffersDetailActivity extends AppCompatActivity
{
    Toolbar toolbar;
    TextView toolbarTitle;
    OffersPojo offersPojo;

    ImageView iv_offer_image_details;
    TextView text_offer_id_details;
    TextView text_offer_name_details;
    TextView text_offer_type_details;
    TextView text_offer_desc_details;
    private ImageLoader1 imgload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_details);

        imgload = new ImageLoader1(OffersDetailActivity.this);

        if (!CommonMethod.isOnline(OffersDetailActivity.this)) {
            CommonMethod.showAlert("Intenet Connectivity Failure", OffersDetailActivity.this);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);
        text_offer_id_details = (TextView) findViewById(R.id.text_offer_id_details);
        text_offer_name_details = (TextView) findViewById(R.id.text_offer_name_details);
        text_offer_type_details = (TextView) findViewById(R.id.text_offer_type_details);
        text_offer_desc_details = (TextView) findViewById(R.id.text_offer_desc_details);
        iv_offer_image_details = (ImageView) findViewById(R.id.iv_offer_image_details);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        offersPojo=(OffersPojo)getIntent().getSerializableExtra("offersArrayList");

        toolbarTitle.setText("Offer Details");


        // TODO Progress Bar progress Feasibility Meter


        text_offer_id_details.setText(offersPojo.getOfferId());
        text_offer_name_details.setText(offersPojo.getOfferName());
        text_offer_type_details.setText(offersPojo.getOfferType());
        text_offer_desc_details.setText(offersPojo.getOfferDesc());
        imgload.DisplayImage(OffersDetailActivity.this, offersPojo.getOfferImage(), iv_offer_image_details);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.done).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                OffersDetailActivity.super.onBackPressed();
                break;
            default:
                break;
        }
        return false;
    }


}
