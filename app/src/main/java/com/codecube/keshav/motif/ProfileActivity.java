package com.codecube.keshav.motif;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import utils.ConstantValues;
import utils.HttpClient;
import utils.ImageLoader1;

/**
 * Created by Dell on 23-05-2016.
 */
public class ProfileActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mToolbar;
    String name = "";
    String sex = "";
    String department = "";
    String designation = "";
    String address = "";
    String mobile = "";
    String joiningDate = "";
    String location = "";
    String reportingTo = "";
    String email = "";
    String flag = "";
    ImageView img_Detail, img_Main;
    String image;
    private ImageLoader1 imgload;
    private ImageLoader imgLoader;
    private ImageView editimage;
    Toolbar toolbar;
    TextView tv_name, tv_upper_desig, tv_sex, tv_lower_desig,
            tv_reportTo, tv_department, tv_joining, tv_location, tv_address, tv_mobile, tv_email;
    double imageName;
    protected static final int CAMERA_REQUEST = 0;
    int REQUEST_CAMERA = 0;
    int PICK_IMAGE = 1;
    byte[] mData;
    ImageView img_call, img_chat, img_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_phonebook_details);
        setContentView(R.layout.activity_profile);
        mToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(ProfileActivity.this));
        imgload = new ImageLoader1(ProfileActivity.this);

        editimage = (ImageView) findViewById(R.id.edit_image);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_upper_desig = (TextView) findViewById(R.id.tv_upper_desig);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_lower_desig = (TextView) findViewById(R.id.tv_lower_desig);
        tv_reportTo = (TextView) findViewById(R.id.tv_reportTo);
        tv_department = (TextView) findViewById(R.id.tv_department);
        tv_joining = (TextView) findViewById(R.id.tv_joining);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_email = (TextView) findViewById(R.id.tv_email);
        img_Detail = (ImageView) findViewById(R.id.img_Detail);
        img_Main = (ImageView) findViewById(R.id.img_Main);
        img_call = (ImageView) findViewById(R.id.img_call);
        img_email = (ImageView) findViewById(R.id.img_email);
        img_chat = (ImageView) findViewById(R.id.img_chat);

        flag = getIntent().getStringExtra("flag");
        if (flag != null && flag.equals("phone")) {
            name = getIntent().getStringExtra("name");
            sex = getIntent().getStringExtra("sex");
            department = getIntent().getStringExtra("department");
            designation = getIntent().getStringExtra("designation");
            address = getIntent().getStringExtra("address");
            mobile = getIntent().getStringExtra("mobile");
            joiningDate = getIntent().getStringExtra("joiningDate");
            location = getIntent().getStringExtra("location");
            reportingTo = getIntent().getStringExtra("reportingTo");
            mobile = getIntent().getStringExtra("mobile");
            email = getIntent().getStringExtra("email");
            image = getIntent().getStringExtra("image");
//            mToolbar.setTitle(name);
            mToolbar.setTitle("keshav ");
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //email = LoginPreferences.getActiveInstance(PhonebookDetails.this).getUserEmail();
            //image = LoginPreferences.getActiveInstance(PhonebookDetails.this).getProfileImage();

            editimage.setVisibility(View.GONE);

            /*
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(name);*/
        } else {
//            name = LoginPreferences.getActiveInstance(PhonebookDetails.this).getUserName();
//            sex = LoginPreferences.getActiveInstance(PhonebookDetails.this).getUserGender();
//            department = LoginPreferences.getActiveInstance(PhonebookDetails.this).getDepartment();
//            designation = LoginPreferences.getActiveInstance(PhonebookDetails.this).getUserDesignation();
//            address = LoginPreferences.getActiveInstance(PhonebookDetails.this).getAddress();
//            mobile = LoginPreferences.getActiveInstance(PhonebookDetails.this).getUserMobile();
//            joiningDate = LoginPreferences.getActiveInstance(PhonebookDetails.this).getJoiningDate();
//            location = LoginPreferences.getActiveInstance(PhonebookDetails.this).getLocation();
//            reportingTo = LoginPreferences.getActiveInstance(PhonebookDetails.this).getReportingTo();
//            // mobile = getIntent().getStringExtra("mobile");
//            email = LoginPreferences.getActiveInstance(PhonebookDetails.this).getUserEmail();
//            image = LoginPreferences.getActiveInstance(PhonebookDetails.this).getProfileImage();
//            Log.d("image", image);

              name = "Keshav Gera";
              sex = "Male";
              department = "IT";
              designation = "Developer";
              address = "H No 1274 Parvatiya Colony 30 Feet Road N I T Faridabad";
              mobile = "9654267338";
              joiningDate = "28 May 2015";
              location = "Faridabad";
              reportingTo = "Sanjiv Bajpai";
//            // mobile = getIntent().getStringExtra("mobile");
              email = "keshavgera@gmail.com";
//            image = LoginPreferences.getActiveInstance(PhonebookDetails.this).getProfileImage();


//            mToolbar.setTitle(name);
            mToolbar.setTitle("keshav Gera");

          /*  getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(name);*/
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar_transparent)));


        imgload.DisplayImage(ProfileActivity.this, image, img_Main);
        imgload.DisplayImage(ProfileActivity.this, image, img_Detail);
        tv_name.setText(name);
        tv_upper_desig.setText(designation);
        tv_sex.setText(sex);
        tv_lower_desig.setText(designation);
        tv_reportTo.setText(reportingTo);
        tv_department.setText(department);
        tv_joining.setText(joiningDate);
        tv_location.setText(location);
        tv_address.setText(address);
        tv_mobile.setText(mobile);
        tv_email.setText(email);
        editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(Intent.ACTION_SEND);               //TODO

                PackageManager pm = getPackageManager();
                chatIntent.setType("text/plain");
                if (mobile != null) {
                    chatIntent.putExtra("address", mobile);
                }
                // use in sms forwarding

                String text = " ";

                try {
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(ProfileActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }
                //waIntent.setPackage("com.whatsapp");              // TODO Using This show  Unfortunately Android system Has stoped
                chatIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(chatIntent, "Share with"));
            }
        });

        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                if(mobile != null){
                    intent.setData(Uri.parse("tel:" + mobile));
                }
                
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                }
                startActivity(intent);
            }
        });

    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_setting, menu);

        if (flag != null && flag.equals("phone")) {
            menu.findItem(R.id.settings).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar actions click

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

          /*  case R.id.settings:
                Intent in = new Intent(PhonebookDetails.this,SettingActivity.class);
                startActivity(in);
                break;*/

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Select From Gallery", "Cancel",

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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
            if (requestCode == PICK_IMAGE && resultCode == ProfileActivity.this.RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(ProfileActivity.this.getContentResolver(), uri);
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

        JSONObject jObject;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mDialog = ProgressDialog.show(ProfileActivity.this, "",
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
                        ProfileImage = object.getString("image");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("imageurl", "1: " + ProfileImage);
//todo comment
//                    LoginPreferences.getActiveInstance(ProfileActivity.this).setProfileImage(ProfileImage);

                    imgload.DisplayImage(ProfileActivity.this, ProfileImage, img_Detail);
                    imgload.DisplayImage(ProfileActivity.this, ProfileImage, img_Main);

                    Toast.makeText(ProfileActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    /*Intent in = new Intent(getActivity(), HomeActivity.class);
                    startActivity(in);*/
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private String callService() {
            String url = ConstantValues.IMAGEUPLOADProfile;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

//                client.addFormPart("loginKey", LoginPreferences.getActiveInstance(PhonebookDetails.this).getLoginKey());
//                client.addFormPart("ouId", LoginPreferences.getActiveInstance(PhonebookDetails.this).getOuId());
//                client.addFormPart("buId", LoginPreferences.getActiveInstance(PhonebookDetails.this).getBuId());
                client.addFormPart("loginKey", "CCD@anu_js8");
                client.addFormPart("ouId", "113");
                client.addFormPart("buId", "10");
                client.addFilePart("image", imageName + ".jpg", mData);
                Log.e("imageeeeeeeee", imageName + ".jpg" + ", " + mData);


                // Log.d("latitude, longitude", latitude + "," + longitude);

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
