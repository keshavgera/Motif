package com.codecube.keshav.motif;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import adapters.ChatAdapter;
import constant.AppConstants;
import constant.AppUtils;
import constant.MyApplication;
import models.ChatMessage;
import utils.HttpClient;

public class MessageActivity1 extends AppCompatActivity
{
    private ListView messagesContainer;
    private EditText chatText;
    TextView toolbartitle;
    public static Typeface my_font;
    private ImageView buttonSend;
    String userId,username,msg_user;
    String image;
    ChatAdapter adapter;
    String message;
    ImageView imVCature_pic;
    Bitmap imagepic;
    byte[] imageArray;
    String builder;
    private ArrayList<ChatMessage> chatHistory=new ArrayList<ChatMessage>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        my_font = Typeface.createFromAsset(MessageActivity1.this.getAssets(),
                "fonts/my_font.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbartitle=(TextView)findViewById(R.id.toolbar_title);
        toolbartitle.setTypeface(my_font);

        imVCature_pic=(ImageView)findViewById(R.id.imVCature_pic);

        chatText=(EditText)findViewById(R.id.msg);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userId=getIntent().getStringExtra("userId");
        username = getIntent().getStringExtra("userName");
        toolbartitle.setText(username);

        builder=getIntent().getStringExtra("multiuserid");

        msg_user = getIntent().getStringExtra("userIdMessage");
        buttonSend = (ImageView) findViewById(R.id.send);
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=chatText.getEditableText().toString().trim();
                if (userId!=null&& !message.equals(""))
                {
                    Log.e("userid ","is  >" + userId);
                    imageArray = sendImage(imagepic);
                    Log.e("imageArray ","is  >" + imageArray);
                    new ServiceAsnc(imageArray).execute();
                    chatText.setText("");
                    imVCature_pic.setImageBitmap(null);
                    // TODO Keshav CALL REFRESH PAGE Automatically
                    validateGetMessage();

                }else {
                    Toast.makeText(MessageActivity1.this, "please write message", Toast.LENGTH_LONG).show();
                }

            }
        });

        validateGetMessage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                MessageActivity1.this.finish();

                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    protected void validateGetMessage() {
//        String signedUserId = MyPreferences.getActiveInstance(MessageActivity.this).getuserId();
        String signedUserId = "2";
        Log.e("****************", "" + signedUserId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        RequestParams params = new RequestParams();
        params.add(AppConstants.SIGNED_USERID, signedUserId);
        if(MyApplication.multimessage==true)
        {
            params.add(AppConstants.USER_ID, builder);
            Log.e("messbuilder",""+builder);
            MyApplication.multimessage=false;
        }else{
            params.add(AppConstants.USER_ID, userId);
        }
        Log.d("GetMessage","userId"+userId);
        params.add("image",image);
        client.post(AppConstants.GetMESSAGE_URL, params,
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
                        Log.e("GetMessage", "" + responseCode.toString());
                        parseResultgetMessage(responseCode.toString());

                        try{

                            JSONObject object=new JSONObject(responseCode.toString());
                            Log.e("messageresponse",""+object.toString(2));
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        if(chatHistory!=null)
                        {
                            adapter = new ChatAdapter(MessageActivity1.this, chatHistory);

                            messagesContainer.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        AppUtils.stopWaitingDialog();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        AppUtils.stopWaitingDialog();
                    }

                });

    }


    private void parseResultgetMessage(String result) {

        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.getJSONArray("message");

            if (posts != null) {
                chatHistory.clear();
                for(int i=0;i<posts.length();i++) {
                    JSONObject post=posts.getJSONObject(i);
                    JSONArray data = post.getJSONArray("details");
                    for(int j=0;j<data.length();j++)
                    {
                        JSONObject messagedata=data.getJSONObject(j);
                        ChatMessage chatMessage = new ChatMessage();

//                        if(MyPreferences.getActiveInstance(MessageActivity.this).getuserId().equals(messagedata.getString("userIdTo"))) {

                            chatMessage.setMessage(messagedata.getString("message"));
                            chatMessage.setDate(messagedata.getString("createdOn"));
                            chatMessage.setImage("http://52.11.140.186/frow/uploads/" + messagedata.getString("image"));
                            Log.e("imagpauserid", "" + "http://52.11.140.186/frow/uploads/" + messagedata.getString("image"));
                            chatMessage.setMe(false);
//                        }else{
//
//                            chatMessage.setMessage(messagedata.getString("message"));
//                            chatMessage.setDate(messagedata.getString("createdOn"));
//                            chatMessage.setImage("http://52.11.140.186/frow/uploads/" + messagedata.getString("image"));
//                            Log.e("imagpathmulti",""+"http://52.11.140.186/frow/uploads/" + messagedata.getString("image"));
//                            chatMessage.setMe(true);
//                        }
                        chatHistory.add(chatMessage);
                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void cropCapturedImage(Uri picUri) {
        //call the standard crop action intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        //indicate image type and Uri of image
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
                Toast.makeText(MessageActivity1.this, "There is some problem with the server. Please try again after sometime.",
                        Toast.LENGTH_LONG).show();
            } else {

                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray posts = result.getJSONArray("message");

                    if (posts != null) {
                        chatHistory.clear();
                        for(int i=0;i<posts.length();i++) {
                            JSONObject post=posts.getJSONObject(i);
                            JSONArray data = post.getJSONArray("details");
                            for(int j=0;j<data.length();j++)
                            {
                                JSONObject messagedata=data.getJSONObject(j);
                                ChatMessage chatMessage = new ChatMessage();

//                                if(MyPreferences.getActiveInstance(MessageActivity.this).getuserId().equals(messagedata.getString("userIdTo"))) {

                                    chatMessage.setMessage(messagedata.getString("message"));
                                    Log.e("meksjkdsj", "" + messagedata.getString("message"));
                                    chatMessage.setDate(messagedata.getString("createdOn"));
                                    chatMessage.setImage("http://52.11.140.186/frow/uploads/" + messagedata.getString("image"));
                                    Log.e("urlkj", "http://52.11.140.186/frow/uploads/" + messagedata.getString("image"));
                                    chatMessage.setMe(false);
//                                }else{
//
//                                    chatMessage.setMessage(messagedata.getString("message"));
//                                    chatMessage.setDate(messagedata.getString("createdOn"));
//                                    chatMessage.setImage("http://52.11.140.186/frow/uploads/" +messagedata.getString("image"));
//                                    chatMessage.setMe(true);
//                                }
                                chatHistory.add(chatMessage);
                            }

                        }
                        if(chatHistory!=null)
                        {
                            adapter = new ChatAdapter(MessageActivity1.this, chatHistory);
                            messagesContainer.setAdapter(adapter);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d("##########Response", "" + response);

            try {
                String success, msg, error_msg;
                if ((response != null) && !response.equals("")) {
                    Log.e("*******************", response);
                    JSONObject object = new JSONObject(response);
                    success = object.getString("responseCode");

                    if (!success.equals("200")) {
                        error_msg = object.getString("error_msg");
                        Toast.makeText(MessageActivity1.this, error_msg,
                                Toast.LENGTH_SHORT).show();

                    }
                    {
                       /* Intent intent = new Intent(MessageActivity.this, HomeActivity.class);
                        startActivity(intent);*/
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
            String Frow = "Frwo" + ts;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            HttpClient client = new HttpClient(url);
            try {
//                String SignedUserId=MyPreferences.getActiveInstance(MessageActivity.this).getuserId();
                String SignedUserId="2";
                client.connectForMultipart();
                client.addFormPart(AppConstants.SIGNED_USERID, SignedUserId);
                client.addFormPart(AppConstants.USER_ID, userId);
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

    public byte[] sendImage(Bitmap bm) {
        if (bm != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            imageArray = bos.toByteArray();

        }
        else{
            // Toast.makeText(MessageActivity.this, "", Toast.LENGTH_SHORT).show();
            Log.d("Message Activity","No Image....");
        }
        return imageArray;
    }

}
