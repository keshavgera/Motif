package com.codecube.keshav.motif;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyPreference.LoginPreferences;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.CustomHttpClient;
import utils.HttpClient;

public class SignInActivity extends AppCompatActivity
{
    EditText firstName,et_password,mobile,email,nameCorporate;
    Spinner spinner_type;

    CheckBox checkBox;

    String firstN;
    String password;
    String mobileNo;
    String emailId;
    String nameOfCorporate;
    String selectClientType;

    ArrayList<String> clientTypeArrayList = new ArrayList<>();

    Toolbar toolbar;
    TextView toolbarTitle;

    //  TODO https://developers.google.com/cloud-messaging/android/client#sample-register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firstName= (EditText) findViewById(R.id.et_first_name_sign_up);
        et_password = (EditText) findViewById(R.id.et_password);
        mobile = (EditText) findViewById(R.id.et_mobile_sign_up);
        email = (EditText) findViewById(R.id.et_email_sign_up);
        nameCorporate = (EditText) findViewById(R.id.et_name_of_corporate);
        checkBox = (CheckBox) findViewById(R.id.check_box);
        spinner_type = (Spinner) findViewById(R.id.spinner_type);


        toolbar=(Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle=(TextView)findViewById(R.id.toolbar_title_include);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Sign Up");


        clientTypeArrayList.add("Select a client type");
        clientTypeArrayList.add("Employee");
        clientTypeArrayList.add("Broker");


        ArrayAdapter<String> adapterClientType = new ArrayAdapter<String>
                (SignInActivity.this, android.R.layout.simple_list_item_1, clientTypeArrayList);
        spinner_type.setAdapter(adapterClientType);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (isChecked) {
                    // hide password
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // show password
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    private boolean checkValidation() {

        firstN = firstName.getText().toString();
        password = et_password.getText().toString();
        mobileNo = mobile.getText().toString();
        emailId = email.getText().toString();
        nameOfCorporate = nameCorporate.getText().toString();

        selectClientType = spinner_type.getSelectedItem().toString();

        Log.e("keshav","firstN  -> " + firstN);
        Log.e("keshav","mobileNo ->" +mobileNo);
        Log.e("keshav","emailId ->" +emailId);
        Log.e("keshav","password  -> "+ password);
        Log.e("keshav","nameOfCorporate ->" +nameOfCorporate);
        Log.e("keshav","selectClientType > " +selectClientType);


        if (firstName.getText().toString().trim().length() == 0) {
            firstName.setError("Invalid Name");
            return false;
        } else if (mobile.getText().toString().trim().length() == 0 || mobile.getText().toString().trim().length() <= 9) {
            mobile.setError("Invalid mobile number");
            return false;
        } else if (email.getText().toString().trim().length() == 0 ) {
            email.setError("email cannot be Left blank");
            return false;
        } else if (et_password.getText().toString().trim().length() == 0 || password.toString() ==null || password.toString().equals("")) {
            Log.e("inpassword"," validation");
            et_password.setError("Invalid Password");
            return false;
        } else if (nameCorporate.getText().toString().trim().length() == 0) {
            nameCorporate.setError("corporate name cannot be left or blank");
            return false;
        } else if (selectClientType == null || selectClientType.equals("")||selectClientType.equals("Select a client type")) {

            TextView errorText = (TextView)spinner_type.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select a client type");//changes the selected item text to this

            return false;
        } else if (!isValidEmail(emailId)) {
            email.setError("Invalid email id");
            email.setTextColor(Color.RED);//just to highlight that this is an error
            email.setText(email.getText().toString());//changes the selected item text to this
            return false;
        } else if (isValidEmail(emailId)) {
            email.setTextColor(Color.BLACK);//just to highlight that this is an error
            email.setText(email.getText().toString());//changes the selected item text to this
//            return false;
        }

        return true;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



    public class SignUpAsyncTask extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
        private String result;
        private String code = "";
        private String responseMessage = "";

        JSONObject jObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                postData.add(new BasicNameValuePair("mode", "0"));                  // insert Mode
                postData.add(new BasicNameValuePair("userName", firstN));
                postData.add(new BasicNameValuePair("mobile", mobileNo));
                postData.add(new BasicNameValuePair("email", emailId));
                postData.add(new BasicNameValuePair("newPassword", password));
                postData.add(new BasicNameValuePair("clientId", nameOfCorporate));
                postData.add(new BasicNameValuePair("userType", selectClientType));

                result = CustomHttpClient
                        .executeHttpPost(ConstantValues.SignUp, postData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                Toast.makeText(getApplicationContext(),
                        "Please check your Internet.",
                        Toast.LENGTH_LONG).show();

            } else {

                try {
                    jObject = new JSONObject(result);
                    code = jObject.getString("responseCode");
                    responseMessage = jObject.getString("responseMessage");

                    if (code.equals("200"))
                    {
                        Intent i = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_LONG).show();
                    } else if (code.equals("0")) {
                        Toast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please check your internet connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            pDialog.dismiss();
        }
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
                SignInActivity.this.finish();
                break;

            case R.id.done:
                if (checkValidation()) {
                    new SignUpAsyncTask().execute();
                }
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        SignInActivity.this.finish();
    }
}
