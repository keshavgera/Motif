package com.codecube.keshav.motif;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyPreference.LoginPreferences;
import utils.ConstantValues;
import utils.CustomHttpClient;
import utils.HttpClient;
import utils.ImageLoader1;

public class ProfileUpdateActivity extends AppCompatActivity {
    EditText firstName, et_company_addres, mobile, email, et_company_name, et_specialized_area;

    String firstN;
    String mobileNo;
    String emailId;
    String companyName;
    String companyAddress;
    String conCanate;
    String specializedArea;

    // TODO CAMERA IMAGE

    double imageName;
    protected static final int CAMERA_REQUEST = 0;
    int REQUEST_CAMERA = 0;
    int PICK_IMAGE = 1;
    byte[] mData;

    String profileImageUrl;

    private ImageLoader1 imgload;
    private ImageLoader imgLoader;

    // TODO CAMERA IMAGE

    ImageView profile_image;
    Toolbar toolbar;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        firstName = (EditText) findViewById(R.id.et_name_profile_update);
        mobile = (EditText) findViewById(R.id.et_mobile_profile_update);
        email = (EditText) findViewById(R.id.et_email_profile_update);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_company_addres = (EditText) findViewById(R.id.et_company_addres);
        et_specialized_area = (EditText) findViewById(R.id.et_specialized_area);

        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(ProfileUpdateActivity.this));
        imgload = new ImageLoader1(ProfileUpdateActivity.this);


        profile_image = (ImageView) findViewById(R.id.update_profile_image);

        profileImageUrl = LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getProfileImage();

        Log.e("profileImageUrl", "is Profile Update  " + profileImageUrl);

        if (profileImageUrl != null) {

            imgload.DisplayImage(ProfileUpdateActivity.this, profileImageUrl, profile_image);

        }
        profile_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectImage();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        firstName.setText(LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserName());
        mobile.setText(LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getMobile());
        email.setText(LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getEmail());

        toolbarTitle.setText("Update Profile");


        if (LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserType().equals("Employee"))
            et_specialized_area.setVisibility(View.GONE);
        {
        }
        if (LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserType().equals("Broker")) {
            et_company_name.setVisibility(View.GONE);
            et_company_addres.setVisibility(View.GONE);
        }


    }

    private boolean checkValidation() {

        firstN = firstName.getText().toString();
        mobileNo = mobile.getText().toString();
        emailId = email.getText().toString();
        companyName = et_company_name.getText().toString();
        companyAddress = et_company_addres.getText().toString();
        specializedArea = et_specialized_area.getText().toString();

        conCanate = companyName + " Concatenate " + companyAddress;

        Log.e("keshav", "firstN  -> " + firstN);
        Log.e("keshav", "mobileNo ->" + mobileNo);
        Log.e("keshav", "emailId ->" + emailId);
        Log.e("keshav", "companyName  -> " + companyName);
        Log.e("keshav", "companyAddress ->" + companyAddress);
        Log.e("keshav", "conCanate ->" + conCanate);
        Log.e("keshav", "specializedArea ->" + specializedArea);

        if (firstName.getText().toString().trim().length() == 0) {
            firstName.setError("Invalid Name");
            return false;
        } else if (mobile.getText().toString().trim().length() == 0 || mobile.getText().toString().trim().length() <= 9) {
            mobile.setError("Invalid mobile number");
            return false;
        } else if (email.getText().toString().trim().length() == 0) {
            email.setError("email cannot be Left blank");
            return false;
        } else if (LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserType().equals("Employee")) {
            if (et_company_name.getText().toString().trim().length() == 0) {
                et_company_name.setError("company name cannot be Left blank");
                return false;
            } else if (et_company_addres.getText().toString().trim().length() == 0) {
                et_company_addres.setError("company address cannot be Left blank");
                return false;
            }
        } else if (LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserType().equals("Broker")) {
            if (et_specialized_area.getText().toString().trim().length() == 0) {
                et_specialized_area.setError("specialized area cannot be Left blank");
                return false;
            }
        } else if (!isValidEmail(emailId)) {
            email.setError("Invalid email id");
            email.setTextColor(Color.BLACK);//just to highlight that this is an error
            email.setText(email.getText().toString());//changes the selected item text to this
            return false;
        } else if (isValidEmail(emailId)) {
            email.setTextColor(Color.BLACK);//just to highlight that this is an error
            email.setText(email.getText().toString());//changes the selected item text to this
        }

        return true;
    }

    // TODO validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public class UpdateProfileAsyncTask extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
        private String result;
        private String code = "";
        private String responseMessage = "";

        JSONObject jObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ProfileUpdateActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                postData.add(new BasicNameValuePair("mode", "1"));                  // insert Mode
                postData.add(new BasicNameValuePair("NuserId", LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserId()));
                postData.add(new BasicNameValuePair("userName", firstN));
                postData.add(new BasicNameValuePair("mobile", mobileNo));
                postData.add(new BasicNameValuePair("email", emailId));

                if (LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserType().equals("Employee")) {
                    postData.add(new BasicNameValuePair("clientId", conCanate));
                } else if (LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserType().equals("Broker")) {
                    postData.add(new BasicNameValuePair("specialisedArea", specializedArea));
                }


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

                    if (code.equals("200")) {
//                        ChangePassword.changePasswordFlag = true;         // Not in use
                        Intent intent = new Intent(ProfileUpdateActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();

                        LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).setUserName(firstN);
                        LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).setMobile(mobileNo);
                        LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).setEmail(emailId);
                        Toast.makeText(getApplicationContext(), "User Profile Updated Successfully.", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(ProfileUpdateActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.done:
                if (checkValidation()) {
                    new UpdateProfileAsyncTask().execute();
                }
                break;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileUpdateActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Select From Gallery", "Cancel",

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileUpdateActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[item].equals("Select From Gallery")) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
//
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                }


            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "calllll");


        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                Bitmap bitmap;
                bitmap = (Bitmap) data.getExtras().get("data");

                // viewImage.setImageBitmap(bitmap);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                mData = bytes.toByteArray();


                Log.e("bytes is", "" + mData);
                //profilepic.setImageBitmap(bitmap);


            }
            if (requestCode == PICK_IMAGE && resultCode == ProfileUpdateActivity.this.RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(ProfileUpdateActivity.this.getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // viewImage.setImageBitmap(bitmap);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                mData = bytes.toByteArray();


                Log.e("bytes is", "" + mData);
                //profilepic.setImageBitmap(bitmap);


            }

            imageName = System.currentTimeMillis();
            new ImageUpload().execute();
        }
    }

    public class ImageUpload extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;
        private String ProfileImage;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mDialog = ProgressDialog.show(ProfileUpdateActivity.this, "",
                    "Please wait", true);
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

            //responseCode
            JSONObject object;
            try {
                object = new JSONObject(response);
                String success = object.getString("responseCode");
                String responseMessage = object.getString("responseMessage");
                if (success.equals("200")) {
                    try {
                        ProfileImage = object.getString("profileImage");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("imageurl", "1: " + ProfileImage);

                    LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).setProfileImage(ProfileImage);
                    imgload.DisplayImage(ProfileUpdateActivity.this, ProfileImage, profile_image);

                    Toast.makeText(ProfileUpdateActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private String callService() {
            String url = ConstantValues.IMAGEUPLOADProfile;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                client.addFormPart("mode", "5");
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getClientId());
                client.addFormPart("NuserId", LoginPreferences.getActiveInstance(ProfileUpdateActivity.this).getUserId());
                client.addFilePart("profileImage", imageName + ".jpg", mData);

                Log.e("imageeeeeeeee", imageName + ".jpg" + ", " + mData);

                client.finishMultipart();

                response = client.getResponse().toString();
                Log.d("response", response);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return response;
        }
    }
}
