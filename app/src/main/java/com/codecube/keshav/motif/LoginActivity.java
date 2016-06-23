package com.codecube.keshav.motif;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import MyPreference.LoginPreferences;
import utils.ConstantValues;
import utils.CustomHttpClient;

public class LoginActivity extends AppCompatActivity {


    EditText edtEmail, edtPass;

    Button loginSub;

    String username;
    String password;

    //TODO https://developers.google.com/cloud-messaging/android/client#sample-register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent startRegistrationIntentService = new Intent(LoginActivity.this, RegistrationIntentService.class);
        //msgIntent.putExtra("userId", MyPreferences.getActiveInstance(HomeActivity.this).getuserId());
        startService(startRegistrationIntentService);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        loginSub = (Button) findViewById(R.id.loginSub);


        loginSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtEmail.getText().toString();
                password = edtPass.getText().toString();

//                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(i);


                if (checkValidation()) {
                    new LoginAsyncTask().execute();
                }
            }
        });
    }

    private boolean checkValidation() {
        if (edtEmail.getText().toString().trim().length() == 0) {
            edtEmail.setError("Invalid Login ID");
            return false;
        } else if (edtPass.getText().toString().trim().length() == 0) {
            edtPass.setError("Invalid Password");
            return false;
        }
        return true;
    }

    public class LoginAsyncTask extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
        private String result;
        private String code = "";
        private String responseMessage = "";

        private String clientId;
        private String userId;
        private String userType;
        private String userName;
        private String mobile;
        private String email;
        private String createdDate;
        private String updatedDate;

        JSONObject jObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                postData.add(new BasicNameValuePair("userName", username));
                postData.add(new BasicNameValuePair("password", password));
                postData.add(new BasicNameValuePair("gcmId", LoginPreferences.getActiveInstance(LoginActivity.this).getGcmToken()));

                Log.e("Login ","GcmId is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getGcmToken());
                result = CustomHttpClient
                        .executeHttpPost(ConstantValues.LOGIN, postData);
                // System.out.print(result);
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
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();

                        clientId = jObject.getString("clientId");
                        userId = jObject.getString("userId");
                        userType = jObject.getString("UserType");
                        userName = jObject.getString("userName");
                        mobile = jObject.getString("mobile");
                        email = jObject.getString("email");
                        createdDate = jObject.getString("createdDate");
                        updatedDate = jObject.getString("updatedDate");


                        LoginPreferences.getActiveInstance(LoginActivity.this).setClientId(clientId);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUserId(userId);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUserType(userType);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUserName(userName);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setMobile(mobile);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setEmail(email);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setCreatedDate(createdDate);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUpdatedDate(updatedDate);


Log.e("Login ","clientId  is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getClientId());
Log.e("Login","userId  is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUserId());
Log.e("Login","userType is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUserType());
Log.e("Login","username is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUserName());
Log.e("Login","mobile is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getMobile());
Log.e("Login","email is ->" + LoginPreferences.getActiveInstance(LoginActivity.this).getEmail());
Log.e("Login","createdDate is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getCreatedDate());
Log.e("Login","updatedDate is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUpdatedDate());


                    } else if (code.equals("0")) {
                        Toast.makeText(getApplicationContext(),
                                responseMessage,
                                Toast.LENGTH_LONG).show();
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


}
