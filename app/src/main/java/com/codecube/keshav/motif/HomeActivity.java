package com.codecube.keshav.motif;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import MyPreference.LoginPreferences;
import adapters.NavDrawerListAdapter;
import fragments.DisplayFragment;
import fragments.DownloadListFragment;
import fragments.MyRequestsFragment;
import fragments.NewsFragment;
import fragments.SettingFragment;
import models.NavDrawerItem;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;
import utils.ImageLoader1;

public class HomeActivity extends ActionBarActivity
{
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static TextView toolbartitle;

    // TODO CAMERA IMAGE

    double imageName;
    protected static final int CAMERA_REQUEST = 0;
    int REQUEST_CAMERA = 0;
    int PICK_IMAGE = 1;
    byte[] mData;

    private ImageLoader1 imgload;
    private ImageLoader imgLoader;

    // TODO CAMERA IMAGE

    private ResponseReceiver receiver;
    public static ImageView frow_logo;
    public static Typeface my_font;

    ////////////////////////////////////////////////////////
    private String[] mOptionMenu;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    private int pos;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerRelativeLayout;
    private ListView mDrawerList;
//    private ActionBarDrawerToggle mDrawerToggle;

    String profileImageUrl;
    private Fragment mFragment = null;

    TextView username;
    ImageView profile_image;
    boolean login;
    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgLoader = ImageLoader.getInstance();
        imgLoader.init(ImageLoaderConfiguration.createDefault(HomeActivity.this));
        imgload = new ImageLoader1(HomeActivity.this);


        if(!LoginPreferences.getActiveInstance(HomeActivity.this).getIsCompanyVerified()){
            CommonMethod.showAlert("Company Not Verified Yet ",HomeActivity.this);
        }

//        profileImageUrl=getIntent().getStringExtra("profileImageUrl");

        profileImageUrl=LoginPreferences.getActiveInstance(HomeActivity.this).getProfileImage();


        Log.e("profileImageUrl","is Home ac--> " +profileImageUrl);
        final LayoutInflater mInflater = (LayoutInflater) HomeActivity.this
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View convertView = mInflater.inflate(R.layout.header, null);

        username=(TextView) convertView.findViewById(R.id.username);
        profile_image=(ImageView) convertView.findViewById(R.id.profile_image);
//        imgload.DisplayImage(HomeActivity.this, LoginPreferences.getActiveInstance(HomeActivity.this).getProfileImage(), profile_image);
        imgload.DisplayImage(HomeActivity.this, profileImageUrl, profile_image);


        login=getIntent().getBooleanExtra("login",false);

        if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Employee")) {
            mOptionMenu = getResources().getStringArray(R.array.nav_drawer_items_corporate);
        }else if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Broker")) {
            mOptionMenu = getResources().getStringArray(R.array.nav_drawer_items_broker);
        }

        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.left_drawer);

        mDrawerList = (ListView) findViewById(R.id.list_view_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();


        profile_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectImage();
            }
        });

        if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Employee")) {
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[0], navMenuIcons.getResourceId(0, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[1], navMenuIcons.getResourceId(1, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[2], navMenuIcons.getResourceId(2, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[3], navMenuIcons.getResourceId(3, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[4], navMenuIcons.getResourceId(4, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[5], navMenuIcons.getResourceId(5, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[6], navMenuIcons.getResourceId(6, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[7], navMenuIcons.getResourceId(7, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[8], navMenuIcons.getResourceId(8, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[9], navMenuIcons.getResourceId(9, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[10], navMenuIcons.getResourceId(10, -1)));
        }else if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Broker")) {
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[0], navMenuIcons.getResourceId(0, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[1], navMenuIcons.getResourceId(1, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[2], navMenuIcons.getResourceId(2, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[3], navMenuIcons.getResourceId(3, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[4], navMenuIcons.getResourceId(4, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[5], navMenuIcons.getResourceId(5, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[6], navMenuIcons.getResourceId(6, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[7], navMenuIcons.getResourceId(7, -1)));
            navDrawerItems.add(new NavDrawerItem(mOptionMenu[8], navMenuIcons.getResourceId(8, -1)));
        }


        username.setText(LoginPreferences.getActiveInstance(HomeActivity.this).getUserName());

        mDrawerList.addHeaderView(convertView);

        // Recycle the typed array
        navMenuIcons.recycle();


        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view,
                                                                       int position, long id) {

                                                   pos = position - 1;


                    if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Employee"))
                    {
                        Log.e("HomeActivity is","Corporate pos >" +pos);
                        switch (pos) {

                            case 0:
                                toolbartitle.setText("Motif Leasing");
                                frow_logo.setVisibility(View.GONE);
                                //mFragment = new CorporateHomeFragment();
                                mFragment = new DisplayFragment();
                                break;
                            case 1:
                                toolbartitle.setText("My Requests");
                                frow_logo.setVisibility(View.GONE);

                                mFragment = new MyRequestsFragment();

//                                Intent myRequests = new Intent(HomeActivity.this, MyRequestActivity.class);
//                                startActivity(myRequests);
//                                finish();
                                break;

                            case 2:
                                toolbartitle.setText("Motif Support");
                                frow_logo.setVisibility(View.GONE);
                                Intent msg = new Intent(HomeActivity.this, MessageActivity.class);
                                startActivity(msg);
                                finish();
                                break;

                            case 3:
                                mFragment = new NewsFragment();
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Promotion And Offers");
                                break;

                            case 4:
//                                mFragment = new NotificationFragment();
                                frow_logo.setVisibility(View.GONE);
//                                toolbartitle.setText("Shifting Guide");
                                break;

                            case 5:
                                mFragment = new SettingFragment();
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Settings");
                                break;
                            case 6:
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Update Profile");
                                Intent updateProfile = new Intent(HomeActivity.this, ProfileUpdateActivity.class);
                                startActivity(updateProfile);
                                finish();
                                break;
                            case 7:
                                mFragment = new DownloadListFragment();
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Downloads");
                                break;
                            case 8:
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Rate Us");
                                Intent rate = new Intent(HomeActivity.this, RateThisAppActivity.class);
                                startActivity(rate);
                                finish();
                                break;
                            case 9:
                                frow_logo.setVisibility(View.GONE);
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I would like to share this app with you");
                                sendIntent.setType("text/plain");
                                startActivity(Intent.createChooser(sendIntent,""));
                              //  finish();
                                break;
                            case 10:
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Logout");
                                Intent logout = new Intent(HomeActivity.this, LogoutActivity.class);
                                startActivity(logout);
                                finish();
                                break;
                        }
                    }else if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Broker")) {
                        Log.e("HomeActivity is", "broker pos >" + pos);

                        switch (pos) {
//                            case 0:
//                                toolbartitle.setText("Motif Leasing");
//                                frow_logo.setVisibility(View.GONE);
//                                mFragment = new DisplayFragment();
//                                break;

                            case 0:
                                toolbartitle.setText("Received Requests");
                                frow_logo.setVisibility(View.GONE);
                                mFragment = new MyRequestsFragment();
                                break;
//                            case 2:
//                                toolbartitle.setText("Post Inventory");
//                                frow_logo.setVisibility(View.GONE);
//                                Intent intent = new Intent(HomeActivity.this, PostInventoryActivity.class);
//                                startActivity(intent);
//                                finish();
//                                break;

                            case 1:
                                toolbartitle.setText("Motif Support");
                                frow_logo.setVisibility(View.GONE);
                                Intent msg = new Intent(HomeActivity.this, MessageActivity.class);
                                startActivity(msg);
                                finish();

                                break;

                            case 2:

                                mFragment = new NewsFragment();
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Promotion And Offers");
                                break;

                            case 3:
                            frow_logo.setVisibility(View.GONE);
                            toolbartitle.setText("Update Profile");
                            Intent updateProfile = new Intent(HomeActivity.this, ProfileUpdateActivity.class);
                            startActivity(updateProfile);
                            finish();
                            break;
                            case 4:
                                mFragment = new SettingFragment();
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Settings");
                                break;
                            case 5:
                                mFragment = new DownloadListFragment();
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Downloads");
                                break;

                            case 6:
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Rate Us");
                                Intent rate = new Intent(HomeActivity.this, RateThisAppActivity.class);
                                startActivity(rate);
                                finish();
                                break;
                            case 7:
                                frow_logo.setVisibility(View.GONE);
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I would like to share this app with you");
                                sendIntent.setType("text/plain");
                                startActivity(Intent.createChooser(sendIntent,""));
                             //   finish();

                                break;
                            case 8:
                                frow_logo.setVisibility(View.GONE);
                                toolbartitle.setText("Logout");
                                Intent logout = new Intent(HomeActivity.this, LogoutActivity.class);
                                startActivity(logout);
                                finish();
                                break;
                        }
                    }

                                                   if(mFragment!=null) {
                                                       FragmentManager fragmentManager = getSupportFragmentManager();
                                                       fragmentManager.beginTransaction().replace(R.id.containerView, mFragment).commit();
                                                   }
                                                   mDrawerList.setItemChecked(position, true);
                                                   mDrawerLayout.closeDrawer(mDrawerRelativeLayout);
                                               }
                                           });


        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbartitle=(TextView)findViewById(R.id.toolbar_title);
        frow_logo=(ImageView)findViewById(R.id.hederimage);

        if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Employee"))
        {
            toolbartitle.setText("Motif Leasing");
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView,new DisplayFragment()).commit();
        }else if(LoginPreferences.getActiveInstance(HomeActivity.this).getUserType().equals("Broker")) {

            toolbartitle.setText("Received Requests");
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView,new MyRequestsFragment()).commit();
        }



        my_font = Typeface.createFromAsset(HomeActivity.this.getAssets(), "fonts/my_font.ttf");

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


    //TODO KEShav using this show drawer toggle

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                    R.string.app_name);

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            mDrawerToggle.syncState();

        }



    public class ResponseReceiver extends BroadcastReceiver {

        public static final String ACTION_RESP =
                "com.example.rubi.fashionapp.intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
        adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onBackPressed() {
        Log.d("Home Activity", "onBackPressed Called");

        new android.support.v7.app.AlertDialog.Builder(HomeActivity.this)
                .setTitle("Are you sure you want to exit?")
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                HomeActivity.this.finish();
                            }
                        }).setNegativeButton("No", null).show();
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Select From Gallery", "Cancel",

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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
            if (requestCode == PICK_IMAGE && resultCode == HomeActivity.this.RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(HomeActivity.this.getContentResolver(), uri);
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

            mDialog = ProgressDialog.show(HomeActivity.this, "",
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
                   LoginPreferences.getActiveInstance(HomeActivity.this).setProfileImage(ProfileImage);
//                    imgload.DisplayImage(HomeActivity.this, ProfileImage, profile_image);   // img_Detail

                    imgload.DisplayImage(HomeActivity.this, ProfileImage, profile_image);

                    Toast.makeText(HomeActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
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
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                client.addFormPart("mode","5");
                client.addFormPart("clientId",LoginPreferences.getActiveInstance(HomeActivity.this).getClientId());
                client.addFormPart("NuserId",LoginPreferences.getActiveInstance(HomeActivity.this).getUserId());
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