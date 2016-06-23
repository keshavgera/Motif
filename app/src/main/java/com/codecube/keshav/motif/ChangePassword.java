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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import MyPreference.LoginPreferences;
import utils.ConstantValues;
import utils.HttpClient;

public class ChangePassword extends AppCompatActivity
{
    private EditText edtCurrentPass;
    private EditText edtNewPass;
    private EditText edtConfirmPass;

    String currentPass;
    String newPass;
    String confirmPass;

    Toolbar toolbar;
    TextView toolbarTitle;

    public static boolean changePasswordFlag= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);


        edtCurrentPass = (EditText) findViewById(R.id.edtCurrentPass);
        edtNewPass = (EditText)findViewById(R.id.edtNewPass);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPass);

        toolbar=(Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle=(TextView)findViewById(R.id.toolbar_title_include);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Change Password");

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
                ChangePassword.this.finish();
                break;

            case R.id.done:
                changePassword();
                break;
            default:
                break;
        }

        return false;
    }



    public class ChangePasswordAsync extends AsyncTask<String, Void, String>
    {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(ChangePassword.this, "", "Please wait", true);
            mDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            response = callService();
            return response;
        }

        @Override
        protected void onPostExecute(String response)
        {
            // TODO Auto-generated method stub
            super.onPostExecute(response);
            if (mDialog != null) {
                mDialog.dismiss();
            }
            Log.d("#####Response", "" + response);

            JSONObject object;

            try
            {
                object = new JSONObject(response);
                String success = object.getString("responseCode");
                String responseMessage = object.getString("responseMessage");
                if (success.equals("200")) {
                    Toast.makeText(ChangePassword.this, responseMessage, Toast.LENGTH_SHORT).show();
//                    Intent in = new Intent(ChangePassword.this, HomeActivity.class);
//                    startActivity(in);
                    changePasswordFlag=true;
                    finish();
                } else if (success.equals("1")) {
                    Toast.makeText(ChangePassword.this, responseMessage, Toast.LENGTH_SHORT).show();
                } else if (success.equals("2")) {
                    Toast.makeText(ChangePassword.this, responseMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private String callService() {
            String url = ConstantValues.CHANGEPASSWORD;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                Log.e("userId","is "+ LoginPreferences.getActiveInstance(ChangePassword.this).getUserId());

                client.addFormPart("mode", "4");
                client.addFormPart("NuserId", LoginPreferences.getActiveInstance(ChangePassword.this).getUserId());
                client.addFormPart("oldPassword", currentPass);
                client.addFormPart("newPassword", newPass);

                client.finishMultipart();

                response = client.getResponse().toString();
                Log.d("response", response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public void changePassword() {
        currentPass = edtCurrentPass.getText().toString().trim();
        newPass = edtNewPass.getText().toString().trim();
        confirmPass = edtConfirmPass.getText().toString().trim();

        if (currentPass.equals("") || newPass.equals("") || confirmPass.equals("")) {
            Toast.makeText(ChangePassword.this, "Please Fill all the Details", Toast.LENGTH_SHORT).show();
        } else {
            if (newPass.equals(confirmPass)) {
                new ChangePasswordAsync().execute();
            } else {
                Toast.makeText(ChangePassword.this, "New Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
