package com.codecube.keshav.motif;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import models.PostInventoryCorporatePojo;

public class CorporateHomeFragmentDetailActivity extends AppCompatActivity
{
    Toolbar toolbar;
    TextView toolbarTitle;

    PostInventoryCorporatePojo postInventoryCorporatePojo;

    TextView tv_corporate_location_detail;
    TextView tv_corporate_city_detail;
    TextView tv_property_type_broker;
    TextView floor_details;
    TextView tv_no_of_bedroom_details;
    TextView tv_furnished_details;
    TextView tv_specific_requirement_details;
    TextView tv_exc_rent_details;
    TextView tv_contact_no_details;
    TextView tv_broker_name;

    ImageView iv_call_broker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporate_home_fragment_details);

        tv_corporate_location_detail = (TextView) findViewById(R.id.tv_corporate_location_detail);
        tv_corporate_city_detail = (TextView) findViewById(R.id.tv_corporate_city_detail);
        tv_property_type_broker = (TextView) findViewById(R.id.tv_property_type_broker);
        floor_details = (TextView) findViewById(R.id.floor_details);
        tv_no_of_bedroom_details = (TextView) findViewById(R.id.tv_no_of_bedroom_details);
        tv_furnished_details = (TextView) findViewById(R.id.tv_furnished_details);
        tv_specific_requirement_details = (TextView) findViewById(R.id.tv_specific_requirement_details);
        tv_exc_rent_details = (TextView) findViewById(R.id.tv_exc_rent_details);
        tv_contact_no_details = (TextView) findViewById(R.id.tv_contact_no_details);
        tv_broker_name = (TextView) findViewById(R.id.tv_broker_name);
        iv_call_broker = (ImageView) findViewById(R.id.iv_call_broker);

        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Detail Page");

        postInventoryCorporatePojo=(PostInventoryCorporatePojo) this.getIntent().getSerializableExtra("postInventoryCorporateDetails");


        tv_corporate_location_detail.setText(postInventoryCorporatePojo.getLocation());
        tv_corporate_city_detail.setText(postInventoryCorporatePojo.getCity());
        tv_property_type_broker.setText(postInventoryCorporatePojo.getTypeOfProperty());
        floor_details.setText(postInventoryCorporatePojo.getFloor());
        tv_no_of_bedroom_details.setText(postInventoryCorporatePojo.getNoOfBedRooms());
        tv_furnished_details.setText(postInventoryCorporatePojo.getFurnishing());
        tv_specific_requirement_details.setText(postInventoryCorporatePojo.getSpecification());
        tv_exc_rent_details.setText(postInventoryCorporatePojo.getExpRent());
        tv_contact_no_details.setText(postInventoryCorporatePojo.getMobileNo());
        tv_broker_name.setText(postInventoryCorporatePojo.getName());


        final String mobile = postInventoryCorporatePojo.getMobileNo();


        iv_call_broker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +mobile));
                startActivity(intent);
            }
        });

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
                CorporateHomeFragmentDetailActivity.this.finish();
                break;

            default:
                break;
        }

        return false;
    }

}
