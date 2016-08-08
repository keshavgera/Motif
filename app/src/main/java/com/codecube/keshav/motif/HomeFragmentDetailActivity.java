package com.codecube.keshav.motif;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MyPreference.LoginPreferences;
import models.UploadRequirementBrokerPojo;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;
import utils.MultiSelectionSpinner;

public class HomeFragmentDetailActivity extends AppCompatActivity {
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
    TextView no_of_beds_ya_rooms;
    TextView tv_emp_name;
    TextView tv_office_address_details;
    TextView tv_visit_time_details;
    Button yes_i_have;
    RelativeLayout rly_yes_i_have;
    LinearLayout lly_visit_time;

    Button btnClosePopup;

    StringBuilder stringBuilderValue =new StringBuilder();


    Spinner spinner_prefered__visit_time;

    ArrayList<String> prefered_visit_time_arrayList = new ArrayList<>();

    String[] VisitTime = {"Select a Time", "9 AM - 11 AM", "11 AM - 1 Pm", "1 PM - 3 Pm", "3 PM - 5 Pm", "5 PM - 7 Pm", "7 PM - 9 Pm",};

    String selectTime;

    LinearLayout lly_city;
    LinearLayout lly_Specific_requirement;
    LinearLayout lly_office_address;
    LinearLayout lly_contact_no;
    LinearLayout lly_employee_name;
    TextView selectedRequests;


    String reqId;
    String selectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        no_of_beds_ya_rooms = (TextView) findViewById(R.id.no_of_beds_ya_rooms);
        tv_emp_name = (TextView) findViewById(R.id.tv_emp_name);
        tv_office_address_details = (TextView) findViewById(R.id.tv_office_address_details);
        tv_visit_time_details = (TextView) findViewById(R.id.tv_visit_time_details);
        lly_visit_time = (LinearLayout) findViewById(R.id.lly_visit_time);

        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);
        yes_i_have = (Button) findViewById(R.id.yes_i_have);
        rly_yes_i_have = (RelativeLayout) findViewById(R.id.rly_yes_i_have);

        lly_city = (LinearLayout) findViewById(R.id.lly_city);
        lly_Specific_requirement = (LinearLayout) findViewById(R.id.lly_Specific_requirement);
        lly_office_address = (LinearLayout) findViewById(R.id.lly_office_address);
        lly_contact_no = (LinearLayout) findViewById(R.id.lly_contact_no);
        lly_employee_name = (LinearLayout) findViewById(R.id.lly_employee_name);
        selectedRequests = (TextView) findViewById(R.id.tv);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Detail Page");
        uploadRequirementBrokerPojo = (UploadRequirementBrokerPojo) this.getIntent().getSerializableExtra("uploadRequirementBrokerDetails");


        if (uploadRequirementBrokerPojo.getDateOfShifting() != null) {
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
        tv_office_address_details.setText(uploadRequirementBrokerPojo.getOfficeAddress());
        tv_visit_time_details.setText(uploadRequirementBrokerPojo.getVisitTime());


        if (uploadRequirementBrokerPojo.getTypeOfProperty().equals("PG")
                || uploadRequirementBrokerPojo.getTypeOfProperty().equals("Guest House")) {
            no_of_beds_ya_rooms.setText("No Of Beds");
        }
        if (uploadRequirementBrokerPojo.getTypeOfProperty().equals("Builder Floor")
                || uploadRequirementBrokerPojo.getTypeOfProperty().equals("Apartments")
                || uploadRequirementBrokerPojo.getTypeOfProperty().equals("Villa")) {
            no_of_beds_ya_rooms.setText("No Of BedRooms");
        }

        if (uploadRequirementBrokerPojo.getTypeOfProperty().equals("PG")
                || uploadRequirementBrokerPojo.getTypeOfProperty().equals("Builder Floor")
                || uploadRequirementBrokerPojo.getTypeOfProperty().equals("Apartments")
                || uploadRequirementBrokerPojo.getTypeOfProperty().equals("Villa")) {

            lly_visit_time.setVisibility(View.GONE);
        }


        reqId = uploadRequirementBrokerPojo.getRequestId();

        Log.e("keshav", "reqId is > " + reqId);


        if (LoginPreferences.getActiveInstance(HomeFragmentDetailActivity.this).getUserType().equals("Broker")) {
            rly_yes_i_have.setVisibility(View.VISIBLE);

            lly_city.setVisibility(View.GONE);
            lly_Specific_requirement.setVisibility(View.GONE);
            lly_office_address.setVisibility(View.GONE);
            lly_contact_no.setVisibility(View.GONE);
            lly_employee_name.setVisibility(View.GONE);

        }

        yes_i_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent yes = new Intent(HomeFragmentDetailActivity.this, YesIHaveActivity.class);
//                yes.putExtra("reqId","reqId");
//                startActivity(yes);

//                http://android--code.blogspot.in/2015/08/android-alertdialog-multichoice.html

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeFragmentDetailActivity.this);

                // String array for alert dialog multi choice items
                String[] selectedRequest = new String[]{
                        "Date of Shifting",
                        "Location",
                        "Property Type",
                        "Beds",
                        "Furnished",
                        "Budget"
                };

                // Boolean array for initial selected items
                final boolean[] selectedRequestClickOrNot = new boolean[]{
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                };

                // Convert the color array to list
                final List<String> selectedRequestList = Arrays.asList(selectedRequest);


                builder.setMultiChoiceItems(selectedRequest, selectedRequestClickOrNot, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        // Update the current focused item's checked status
                        selectedRequestClickOrNot[which] = isChecked;

                        // Get the current focused item
                        String currentItem = selectedRequestList.get(which);

                        // Notify the current action
//                        Toast.makeText(getApplicationContext(),currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                // Set a title for alert dialog
                builder.setTitle("Requests");

                // Set the positive/yes button click listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < selectedRequestClickOrNot.length; i++) {
                            boolean checked = selectedRequestClickOrNot[i];
                            if (checked) {

                                if(i<selectedRequestClickOrNot.length-1)
                                stringBuilderValue.append("" + selectedRequestList.get(i) + ",");
                                else
                                stringBuilderValue.append(""+selectedRequestList.get(i));
                            }
                        }

                        Log.e("stringBuilderValue", "is " + stringBuilderValue);
                        selectedRequests.setText("" + stringBuilderValue);

                        new YesIHaveAsync(stringBuilderValue.toString()).execute();
                    }
                });


                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();

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
                HomeFragmentDetailActivity.this.finish();
                break;

            default:
                break;
        }

        return false;
    }

    public class YesIHaveAsync extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;
        String YesIHaveTime;


        public YesIHaveAsync() {

        }

        public YesIHaveAsync(String YesIHaveTime) {
            this.YesIHaveTime = YesIHaveTime;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(HomeFragmentDetailActivity.this, "", "Please wait", true);
            mDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            response = callService();
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            // TODO Auto-generated method stub
            super.onPostExecute(response);
            if (mDialog != null) {
                mDialog.dismiss();
            }
            Log.d("#####Response", "" + response);

            JSONObject object;

            try {
                object = new JSONObject(response);
                String success = object.getString("responseCode");
                String responseMessage = object.getString("responseMessage");
                if (success.equals("200")) {
                    Toast.makeText(HomeFragmentDetailActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    finish();
                } else if (success.equals("1")) {
                    Toast.makeText(HomeFragmentDetailActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                } else if (success.equals("2")) {
                    Toast.makeText(HomeFragmentDetailActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private String callService() {
            String url = ConstantValues.YesIHave;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                Log.e("userId", "is " + LoginPreferences.getActiveInstance(HomeFragmentDetailActivity.this).getUserId());

                client.addFormPart("p_mode", "1");
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(HomeFragmentDetailActivity.this).getClientId());
                client.addFormPart("reqId", reqId);
                client.addFormPart("userId", LoginPreferences.getActiveInstance(HomeFragmentDetailActivity.this).getUserId());
                client.addFormPart("response", "Y");
                client.addFormPart("responseData", "entered ");
//                client.addFormPart("responseData", YesIHaveTime);

                Log.e("YesIHaveTime","is" +YesIHaveTime);

                client.finishMultipart();

                response = client.getResponse().toString();
                Log.d("response", response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

}