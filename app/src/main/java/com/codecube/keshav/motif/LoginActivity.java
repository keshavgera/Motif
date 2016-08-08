package com.codecube.keshav.motif;

import android.*;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MyPreference.LoginPreferences;
import utils.ConstantValues;
import utils.CustomHttpClient;

public class LoginActivity extends AppCompatActivity {


    EditText edtEmail, edtPass;

    Button loginSub;

    String username;
    String password;
    TextView dont_have_an_account;
    CheckBox check_box_password;

    // TODO Permission

    String TAG="PolicyDetailsFragment";
    final int REQUEST_ID_MULTIPLE_PERMISSIONS=11;

    // TODO Permission

    //TODO https://developers.google.com/cloud-messaging/android/client#sample-register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // TODO Permission
        checkAndRequestPermissions();               // TODO Permission


        Intent startRegistrationIntentService = new Intent(LoginActivity.this, RegistrationIntentService.class);
        //msgIntent.putExtra("userId", MyPreferences.getActiveInstance(HomeActivity.this).getuserId());
        startService(startRegistrationIntentService);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        loginSub = (Button) findViewById(R.id.loginSub);
        dont_have_an_account = (TextView) findViewById(R.id.dont_have_an_account);
        check_box_password = (CheckBox) findViewById(R.id.check_box_password);

        check_box_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (isChecked) {
                    // hide password
                    Log.e("check_box_password","if ");
                    edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // show password
                    Log.e("check_box_password","else ");
                    edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

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

        dont_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(i);
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
        private String profileImageUrl;
        private boolean isCompanyVerified;

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
                        clientId = jObject.getString("clientId");
                        userId = jObject.getString("userId");
                        userType = jObject.getString("UserType");
                        userName = jObject.getString("userName");
                        mobile = jObject.getString("mobile");
                        email = jObject.getString("email");
                        createdDate = jObject.getString("createdDate");
                        updatedDate = jObject.getString("updatedDate");
                        profileImageUrl = jObject.getString("profileImageUrl");
                        isCompanyVerified = jObject.getBoolean("companyStatus");

                        LoginPreferences.getActiveInstance(LoginActivity.this).setClientId(clientId);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUserId(userId);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUserType(userType);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUserName(userName);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setMobile(mobile);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setEmail(email);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setCreatedDate(createdDate);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setUpdatedDate(updatedDate);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setProfileImage(profileImageUrl);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setIsLoggedIn(true);
                        LoginPreferences.getActiveInstance(LoginActivity.this).setIsCompanyVerified(isCompanyVerified);

Log.e("Login ","clientId  is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getClientId());
Log.e("Login","userId  is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUserId());
Log.e("Login","userType is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUserType());
Log.e("Login","username is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUserName());
Log.e("Login","mobile is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getMobile());
Log.e("Login","email is ->" + LoginPreferences.getActiveInstance(LoginActivity.this).getEmail());
Log.e("Login","createdDate is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getCreatedDate());
Log.e("Login","updatedDate is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getUpdatedDate());
Log.e("Login","IsLoggedIn is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getIsLoggedIn());
Log.e("Login","profileImage is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getProfileImage());
Log.e("Login","isCompanyVerified is -> " + LoginPreferences.getActiveInstance(LoginActivity.this).getIsCompanyVerified());


                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                        profileImageUrl = jObject.getString("profileImageUrl");
                        Log.e("profileImageUrl","is Login--> " +profileImageUrl);
//                        i.putExtra("profileImageUrl",profileImageUrl);

                        startActivity(i);
                        finish();

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

    //TODO PERMISSION
    private boolean checkAndRequestPermissions() {

        int readCall = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int locationPermission = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (readCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(LoginActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        Log.d(TAG, "Permission callback called-------");

        switch (requestCode)
        {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions

                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.SEND_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(LoginActivity.this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    //TODO PERMISSION

}
