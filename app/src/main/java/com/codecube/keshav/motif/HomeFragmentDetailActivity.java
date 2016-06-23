package com.codecube.keshav.motif;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import models.PostInventoryCorporatePojo;
import models.UploadRequirementBrokerPojo;
import utils.CommonMethod;

public class HomeFragmentDetailActivity extends AppCompatActivity
{
    Toolbar toolbar;
    TextView toolbarTitle;

    UploadRequirementBrokerPojo uploadRequirementBrokerPojo;

    TextView tv_date_shifting;
    TextView tv_emp_location_detail;
    TextView tv_corporate_city_detail;
    TextView tv_emp_Property_type;
    TextView tv_emp_no_of_bedroom_details;
    TextView tv_emp_furnished_details;
    TextView tv_emp_specific_details;
    TextView tv_budget_from_details;
    TextView tv_budget_to_details;
    TextView tv_emp_contact_details;
    TextView tv_emp_name;
    ImageView iv_call_corporate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment_details);

        tv_date_shifting = (TextView) findViewById(R.id.tv_date_shifting);
        tv_emp_location_detail = (TextView) findViewById(R.id.tv_emp_location_detail);
        tv_corporate_city_detail = (TextView) findViewById(R.id.tv_emp_city_detail);
        tv_emp_Property_type = (TextView) findViewById(R.id.tv_emp_Property_type);
        tv_emp_no_of_bedroom_details = (TextView) findViewById(R.id.tv_emp_no_of_bedroom_details);
        tv_emp_furnished_details = (TextView) findViewById(R.id.tv_emp_furnished_details);
        tv_emp_specific_details = (TextView) findViewById(R.id.tv_emp_specific_details);
        tv_budget_from_details = (TextView) findViewById(R.id.tv_budget_from_details);
        tv_budget_to_details = (TextView) findViewById(R.id.tv_budget_to_details);
        tv_emp_contact_details = (TextView) findViewById(R.id.tv_emp_contact_details);
        tv_emp_name = (TextView) findViewById(R.id.tv_emp_name);



        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);
        iv_call_corporate = (ImageView) findViewById(R.id.iv_call_corporate);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Detail Page");
        uploadRequirementBrokerPojo=(UploadRequirementBrokerPojo) this.getIntent().getSerializableExtra("uploadRequirementBrokerDetails");

        if(uploadRequirementBrokerPojo.getDateOfShifting()!=null) {
            tv_date_shifting.setText(CommonMethod.SimpleDateFormat(uploadRequirementBrokerPojo.getDateOfShifting()));
        }
        tv_emp_location_detail.setText(uploadRequirementBrokerPojo.getPreferredLocation());
        tv_corporate_city_detail.setText(uploadRequirementBrokerPojo.getCity());
        tv_emp_Property_type.setText(uploadRequirementBrokerPojo.getTypeOfProperty());
        tv_emp_no_of_bedroom_details.setText(uploadRequirementBrokerPojo.getRooms());
        tv_emp_furnished_details.setText(uploadRequirementBrokerPojo.getFurnishing());
        tv_budget_from_details.setText(uploadRequirementBrokerPojo.getBudgetFrom());
        tv_budget_to_details.setText(uploadRequirementBrokerPojo.getBudgetTo());
        tv_emp_specific_details.setText(uploadRequirementBrokerPojo.getSpecification());
        tv_emp_contact_details.setText(uploadRequirementBrokerPojo.getMobileNo());
        tv_emp_name.setText(uploadRequirementBrokerPojo.getName());




        final String mobile = uploadRequirementBrokerPojo.getMobileNo();


        iv_call_corporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +mobile));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search, menu);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.done).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {

            case android.R.id.home:
                HomeFragmentDetailActivity.this.finish();
                break;

            default:
                break;
        }

        return false;
    }

}