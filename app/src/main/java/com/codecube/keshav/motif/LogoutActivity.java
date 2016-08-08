
package com.codecube.keshav.motif;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import MyPreference.LoginPreferences;

public class LogoutActivity extends AppCompatActivity {


    Toolbar toolbar;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);


        toolbar=(Toolbar) findViewById(R.id.toolbar_logout);
        toolbarTitle=(TextView)findViewById(R.id.toolbar_title_logout);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Logout");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LogoutActivity.this);

        // set title
        alertDialogBuilder.setTitle("Logout!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are You Sure You Want to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setClientId("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setUserId("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setUserName("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setUserType("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setMobile("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setEmail("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setCreatedDate("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setUpdatedDate("");
                    LoginPreferences.getActiveInstance(LogoutActivity.this).setProfileImage("");
//                    LoginPreferences.getActiveInstance(LogoutActivity.this).setIsCompanyVerified("");

                    LoginPreferences.getActiveInstance(LogoutActivity.this).setIsLoggedIn(false);

Log.e("Logout ","clientId  is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getClientId());
Log.e("Logout","userId  is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getUserId());
Log.e("Logout","userType is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getUserType());
Log.e("Logout","username is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getUserName());
Log.e("Logout","mobile is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getMobile());
Log.e("Logout","email is ->" + LoginPreferences.getActiveInstance(LogoutActivity.this).getEmail());
Log.e("Logout","createdDate is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getCreatedDate());
Log.e("Logout","updatedDate is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getUpdatedDate());
Log.e("Logout","IsLoggedIn is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getIsLoggedIn());
Log.e("Logout","profileImage is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getProfileImage());
Log.e("Logout","isCompanyVerified is -> " + LoginPreferences.getActiveInstance(LogoutActivity.this).getIsCompanyVerified());

                        Intent i = new Intent(LogoutActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(LogoutActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_abouts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                LogoutActivity.this.finish();
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}



