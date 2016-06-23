package com.codecube.keshav.motif;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import adapters.MultipleMessageAdapter;
import constant.AppConstants;
import constant.MyApplication;
import models.MessageuserId;
import models.MultipleMessagePojo;
import utils.HttpClient;

/**
 * Created by Vartulz on 4/15/2016.
 */

public class MultipleMessage extends AppCompatActivity
{
    int REQUEST_CAMERA = 0, SELECT_FILE = 3;
    ListView listView;
    MultipleMessageAdapter adapter;
    LinearLayout ll_msgto;
    String name;
    String message;
    Bitmap imagepic;
    byte[] imageArray;
    private EditText chatText;
    StringBuilder userId = new StringBuilder();

    ArrayList<MultipleMessagePojo> multipleBeanses = new ArrayList<MultipleMessagePojo>();
    ArrayList<MessageuserId> messageuserIds = new ArrayList<MessageuserId>();
    private ImageView buttonSend;
    ImageView iv_camera, imVCature_pic;
    CheckBox chek_value;
    String prifix=",";

    SwipeRefreshLayout swipe_container_message_multiple;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        validateMultipleMessage();
        listView = (ListView) findViewById(R.id.listviewmultiple);
        ll_msgto = (LinearLayout) findViewById(R.id.ll_msgto);
        buttonSend = (ImageView) findViewById(R.id.send);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        imVCature_pic = (ImageView) findViewById(R.id.imVCature_pic);
        chatText = (EditText) findViewById(R.id.msg);


        swipe_container_message_multiple = (SwipeRefreshLayout) findViewById(R.id.swipe_container_message_multiple);
        // Setup refresh listener which triggers new data loading
        swipe_container_message_multiple.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                validateMultipleMessage();
                swipe_container_message_multiple.setRefreshing(false);

            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_container_message_multiple.setEnabled(enable);
            }
        });

/*

        MultiSelectEditText editText = (MultiSelectEditText) findViewById(R.id.auto_text_complete);

        //Add some sample items
        List<SampleItem> sampleItems = Arrays.asList(
                new SampleItem("Aaron LastName"),
                new SampleItem("Cameron Chimes"),
                new SampleItem("Tim Gibbons"),
                new SampleItem("Gary Styles"),
                new SampleItem("Bart Thompson"),
                new SampleItem("Abagail B.D.E.")
        );

        editText.addAllItems(sampleItems);

        //Get the ListView associated with the MultiSelectEditText
        ListView list = editText.getListView();

        //Add it to a FrameLayout somewhere in the activity
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        list.setLayoutParams(params);

        FrameLayout frame = (FrameLayout) findViewById(R.id.auto_list_container);
        frame.addView(list);

        //Set a listener on bubble clicks
        editText.setBubbleClickListener(new MultiSelectEditText.BubbleClickListener<SampleItem>() {

            @Override
            public void onClick(SampleItem item) {
                Log.d("multimessage", "Item: " + item.getReadableName());
            }
        });

*/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                chek_value =(CheckBox)view.findViewById(R.id.chek_value_msg);

                chek_value.setVisibility(View.VISIBLE);

                if(chek_value.isChecked()){
                    //  holder.rl_bottom.setVisibility(View.VISIBLE);
                    chek_value.setChecked(false);
                }
                else{
                   chek_value.setChecked(true);
                }

                name = multipleBeanses.get(position).getUserName();
                Log.d("MultipleMessage", "nameee" + name);

                userId.append(multipleBeanses.get(position).getUserId());
                userId.append(prifix);

                final TextView rowTextView = new TextView(MultipleMessage.this);
                rowTextView.setText(name);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5,0,0,0);
                rowTextView.setLayoutParams(layoutParams);
                rowTextView.setBackgroundResource(R.drawable.box1);
                rowTextView.setTextSize(15);
                rowTextView.setPadding(5, 5, 5, 5);
                rowTextView.setVerticalScrollBarEnabled(true);

                ll_msgto.addView(rowTextView);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                message = chatText.getEditableText().toString().trim();
                if (userId != null && !message.equals("")) {

                    new ServiceAsnc(imageArray).execute();
                    chatText.setText("");
                    imVCature_pic.setImageBitmap(null);
                } else {
                    Toast.makeText(MultipleMessage.this, "please write message", Toast.LENGTH_LONG).show();
                }
            }
        });

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                MultipleMessage.this.finish();

                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return false;
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MultipleMessage.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            /*create instance of File with name img.jpg*/
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");
            /*put uri as extra in intent object*/
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            /*start activity for result pass intent as argument and request code */
                    startActivityForResult(intent, 1);
                } else if (items[item].equals("Choose from Library")) {
                    imVCature_pic=(ImageView)findViewById(R.id.imVCature_pic);
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    //  File file = new File(Intent.createChooser(intent, "Select File") + File.separator + "img.jpg");

                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if request code is same we pass as argument in startActivityForResult
        if (requestCode == 1) {
            //create instance of File with same name we created before to get image from storage
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");
            //Crop the captured image using an other intent
            try {
            /*the user's device may not support cropping*/
                cropCapturedImage(Uri.fromFile(file));
            } catch (ActivityNotFoundException aNFE) {
                //display an error message if user device doesn't support
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(MultipleMessage.this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if (requestCode == 3) {
            //create instance of File with same name we created before to get image from storage
            Uri selectedImageUri = null;
            if (data != null)                  // Todo Without using .... select galary and then back as well as  show gallery icon click in layout
                selectedImageUri = data.getData();
            //Crop the captured image using an other intent
            try {
            /*the user's device may not support cropping*/
                cropCapturedImage(selectedImageUri);
            } catch (ActivityNotFoundException aNFE) {
                //display an error message if user device doesn't support
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(MultipleMessage.this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }


        if (requestCode == 2) {
            //Create an instance of bundle and get the returned data
            Log.d("click ", "after capture image");
            Log.e("click ", "after capture image");
            Bundle extras = null;
            Log.d("click ", "extras capture image" + extras);
            if (data != null)
                extras = data.getExtras();
            //get the cropped bitmap from extras


            Log.e("click ", "extras capture image" + extras);

            imagepic = null;
            if (extras != null) {
                imagepic = extras.getParcelable("data");
            }
            //set image bitmap to image view
            if (imagepic != null) {

                imVCature_pic.setImageBitmap(imagepic);

            }

        }
    }
    public void cropCapturedImage(Uri picUri) {
        //call the standard crop action intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }

   /* public class ServiceAsnc extends AsyncTask<String, Void, String> {

        private String response;
        byte[] imageArray;
        ProgressDialog mDialog;

        public ServiceAsnc(byte[] imageArray,String messagesend) {

            this.imageArray = imageArray;
        }

        @Override

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

          *//*  mDialog = ProgressDialog.show(MessageActivity.this, "", "Please wait", true);
            mDialog.setCancelable(false);*//*
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

            if (response == null) {
                Toast.makeText(MultipleMessage.this, "There is some problem with the server. Please try again after sometime.",
                        Toast.LENGTH_LONG).show();
            } else {

                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray posts = result.getJSONArray("message");
                    if(posts!=null)
                    {
                        for (int i=0;i<posts.length();i++)
                        {
                            JSONObject object=posts.getJSONObject(i);
                            JSONArray post = object.optJSONArray("details");
                            for (int j=0;j<post.length();j++)
                            {
                                JSONObject obj=posts.getJSONObject(j);
                                MessageuserId Item=new MessageuserId();
                                Item.setMessageId(obj.getString("messageId"));
                                Item.setUserIdFrom(obj.getString("userIdFrom"));
                                Item.setUserIdTo(obj.getString("userIdTo"));
                                Item.setMessage(obj.getString("message"));
                                Item.setImage(obj.getString("image"));
                                Item.setStatus(obj.getString("status"));
                                Item.setCreatedOn(obj.getString("createdOn"));
                                messageuserIds.add(Item);
                            }
                        }
                    }

                    if(result.getString("responseCode").equals("200"))
                    {
                        Log.e("responseCode",""+result.getString("responseCode"));
                        Intent intent = new Intent(MultipleMessage.this, MessageActivity.class);
                        intent.putExtra("userId",userId.toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        MyApplication.multimessage=true;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.e("ResponceMultiplemessage", "" + response);

        }

        private String callService() {
            // TODO Auto-generated method stub
            String url = AppConstants.MESSAGE_URL;

            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            String Frow = "message" + ts;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            HttpClient client = new HttpClient(url);
            try {
                String SignedUserId=MyPreferences.getActiveInstance(MultipleMessage.this).getuserId();
                client.connectForMultipart();
                client.addFormPart(AppConstants.SIGNED_USERID, SignedUserId);
                client.addFormPart(AppConstants.USER_ID, userId.toString());
                if(messagesend!=null)
                {
                    client.addFormPart(AppConstants.MESSAGE, messagesend);
                }
                if (imageArray != null) {
                    client.addFilePart("image", Frow + ".png", imageArray);
                }
                client.finishMultipart();
                response = client.getResponse().toString();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

    }*/


    public class ServiceAsnc extends AsyncTask<String, Void, String> {

        private String response;
        byte[] imageArray;
        ProgressDialog mDialog;

        public ServiceAsnc(byte[] imageArray) {

            this.imageArray = imageArray;
        }

        @Override

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

          /*  mDialog = ProgressDialog.show(MessageActivity.this, "", "Please wait", true);
            mDialog.setCancelable(false);*/
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

            if (response == null) {
                Toast.makeText(MultipleMessage.this, "There is some problem with the server. Please try again after sometime.",
                        Toast.LENGTH_LONG).show();
            } else {

                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray posts = result.getJSONArray("message");
                    if(posts!=null)
                    {
                        for (int i=0;i<posts.length();i++)
                        {
                            JSONObject object=posts.getJSONObject(i);
                            JSONArray post = object.optJSONArray("details");
                            for (int j=0;j<post.length();j++)
                            {
                                JSONObject obj=posts.getJSONObject(j);
                                MessageuserId Item=new MessageuserId();
                                Item.setMessageId(obj.getString("messageId"));
                                Item.setUserIdFrom(obj.getString("userIdFrom"));
                                Item.setUserIdTo(obj.getString("userIdTo"));
                                Item.setMessage(obj.getString("message"));
                                Item.setImage(obj.getString("image"));
                                Item.setStatus(obj.getString("status"));
                                Item.setCreatedOn(obj.getString("createdOn"));
                                messageuserIds.add(Item);
                            }
                        }
                    }

                    if(result.getString("responseCode").equals("200"))
                    {
                        Log.e("responseCode", "" + result.getString("responseCode"));
                        Intent intent = new Intent(MultipleMessage.this, MessageActivity.class);
                        intent.putExtra("userId",userId.toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        MyApplication.multimessage=true;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d("##########Responsemulti", "" + response);

            try {
                String success, msg, error_msg;
                if ((response != null) && !response.equals("")) {
                    Log.e("*******************", response);
                    JSONObject object = new JSONObject(response);
                    success = object.getString("responseCode");

                    if (!success.equals("200")) {
                        error_msg = object.getString("error_msg");
                        Toast.makeText(MultipleMessage.this, error_msg,
                                Toast.LENGTH_SHORT).show();

                    }
                    {

                        String builder=userId.toString();
                        Intent intent = new Intent(MultipleMessage.this, MessageActivity.class);
                        intent.putExtra("multiuserid",builder);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        MyApplication.multimessage=true;
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private String callService() {
            // TODO Auto-generated method stub
            String url = AppConstants.MESSAGE_URL;

            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            String Frow = "multimessage" + ts;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            HttpClient client = new HttpClient(url);
            try {
//                String SignedUserId=MyPreferences.getActiveInstance(MultipleMessage.this).getuserId();
                String SignedUserId="3";
                client.connectForMultipart();
//                client.addFormPart(AppConstants.SIGNED_USERID, SignedUserId);
//                client.addFormPart(AppConstants.USER_ID, userId.toString());
                client.addFormPart(AppConstants.SIGNED_USERID, SignedUserId);
                client.addFormPart(AppConstants.USER_ID, "21");
                Log.e("userIdmultiple", "" + userId.toString());
                Log.e("multipleuserId",""+ userId.toString());
                if(message!=null)
                {
                    client.addFormPart(AppConstants.MESSAGE, message);
                }
                if (imageArray != null) {
                    client.addFilePart("image", Frow + ".png", imageArray);
                }
                client.finishMultipart();
                response = client.getResponse().toString();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

    }

    protected void validateMultipleMessage()
    {
//        String signedUserId = MyPreferences.getActiveInstance(MultipleMessage.this).getuserId();
//        String userid = MyPreferences.getActiveInstance(MultipleMessage.this).getuserId();
        String signedUserId = "1";
        String userid = "2";
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        RequestParams params = new RequestParams();
        params.add(AppConstants.SIGNED_USERID, signedUserId);
        params.add(AppConstants.USER_ID, userid);
        client.post(AppConstants.GET_FOLLOWING, params,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        super.onStart();

                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers,
                                          JSONObject responseCode) {
                        super.onSuccess(statusCode, (cz.msebera.android.httpclient.Header[]) headers, responseCode);
                        Log.e("", "Hash -> " + responseCode.toString());
                        Log.e("", "On Success of NoTs");
                        parsemultimessage(responseCode.toString());
                        try {

                            JSONObject jsonObject = new JSONObject(responseCode.toString());
                            Log.e("UserInfo", jsonObject.toString(2));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (multipleBeanses != null) {
                            adapter = new MultipleMessageAdapter(MultipleMessage.this, multipleBeanses);
                            listView.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }

                });

    }

    private void parsemultimessage(String result) {
        try {
            if (result != null) {

                JSONObject response = new JSONObject(result);
                if (response != null) {
                    JSONArray posts = response.optJSONArray("following");
                    if (posts != null) {
                        multipleBeanses.clear();
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject post = posts.optJSONObject(i);

                            MultipleMessagePojo item = new MultipleMessagePojo();
                            item.setUserName(post.getString("userName"));
                            item.setUserId(post.getString("userId"));
                            item.setEmail(post.getString("email"));
                            item.setProfileImage("http://52.11.140.186/frow/uploads/" + post.getString("profileImage"));
                            item.setDesignation(post.getString("designation"));
                            item.setFollowed(post.getString("followed"));
                            multipleBeanses.add(item);
                        }

                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
