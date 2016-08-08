package com.codecube.keshav.motif;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import java.util.List;

import MyPreference.LoginPreferences;
import models.UploadRequirementBrokerPojo;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;
import utils.MultiSelectionSpinner;

public class YesIHaveActivity extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    Toolbar toolbar;
    TextView toolbarTitle;

    MultiSelectionSpinner multiSelectionSpinner;
    String[] AssignedToField = {"Date of Shifting", "Location", "Property Type", "Beds", "Furnished", "Budget"};

    String reqId;
    String selectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_i_have);


        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);

        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.multiple_field_available);

        reqId=getIntent().getStringExtra("reqId");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbarTitle.setText("Yes I Have ");


        multiSelectionSpinner.setItems(AssignedToField);
//                            multiSelectionSpinnerAssignedToProject.setSelection(new int[]{2});
        multiSelectionSpinner.setListener(YesIHaveActivity.this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                YesIHaveActivity.this.finish();
                break;
            case R.id.done:

                if(checkValidation()){
                  new YesIHaveAsync(selectedValue).execute();
                }


                break;

            default:
                break;
        }

        return false;
    }

    boolean checkValidation(){
        if (selectedValue == null || selectedValue != null && selectedValue.toString().equals("")) {
            CommonMethod.showAlert("Please select requests.", YesIHaveActivity.this);
            return false;
        }

        return true;
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
            mDialog = ProgressDialog.show(YesIHaveActivity.this, "", "Please wait", true);
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
                    Toast.makeText(YesIHaveActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    finish();
                } else if (success.equals("1")) {
                    Toast.makeText(YesIHaveActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                } else if (success.equals("2")) {
                    Toast.makeText(YesIHaveActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
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

                Log.e("userId", "is " + LoginPreferences.getActiveInstance(YesIHaveActivity.this).getUserId());

                client.addFormPart("p_mode", "1");
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(YesIHaveActivity.this).getClientId());
                client.addFormPart("reqId", reqId);
                client.addFormPart("userId", LoginPreferences.getActiveInstance(YesIHaveActivity.this).getUserId());
                client.addFormPart("response", "Y");

                client.finishMultipart();

                response = client.getResponse().toString();
                Log.d("response", response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }
    //TODO  Multiple Spinner
    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        Log.e("facility in multiple "," " +strings.toString());

        selectedValue = strings.toString().replace("[","").replace("]","");

        Log.e("yesI Have", "multiple is " + selectedValue);

    }


}