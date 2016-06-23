package com.codecube.keshav.motif;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;

import MyPreference.LoginPreferences;
import adapters.ChatAdapter;
import adapters.MessageChatAdapter;
import adapters.UploadRequirementBrokerAdapter;
import constant.AppConstants;
import constant.AppUtils;
import models.ChatMessage;
import models.MessageChatPojo;
import models.UploadRequirementBrokerPojo;
import utils.ConstantValues;
import utils.HttpClient;

public class MessageActivity extends AppCompatActivity
{
    private ListView messagesContainer;

    private EditText chatText;
    TextView toolbartitle;
    public static Typeface my_font;
    private ImageView buttonSend;

    MessageChatAdapter messageChatAdapter;
    String message;
    private ArrayList<MessageChatPojo> messageChatPojoArrayList = new ArrayList<MessageChatPojo>();

    SwipeRefreshLayout swipe_container_message_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        my_font = Typeface.createFromAsset(MessageActivity.this.getAssets(),
                "fonts/my_font.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        setSupportActionBar(toolbar);
        toolbartitle = (TextView) findViewById(R.id.toolbar_title_include);
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);

        toolbartitle.setTypeface(my_font);

        new GetChatMessageAsyncTask().execute();


        chatText = (EditText) findViewById(R.id.msg);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartitle.setText("Messages");

        buttonSend = (ImageView) findViewById(R.id.send);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = chatText.getEditableText().toString().trim();
                if (message.equals("")) {
                    Toast.makeText(MessageActivity.this, "please write message", Toast.LENGTH_LONG).show();
                } else {
                    new MessageActivityAsyncTask().execute();
                    chatText.setText("");

                    // TODO Keshav CALL REFRESH PAGE Automatically

                    new GetChatMessageAsyncTask().execute();
                }
            }
        });


        swipe_container_message_activity = (SwipeRefreshLayout) findViewById(R.id.swipe_container_message_activity);
        // Setup refresh listener which triggers new data loading
        swipe_container_message_activity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetChatMessageAsyncTask().execute();
                swipe_container_message_activity.setRefreshing(false);

            }
        });


        messagesContainer.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (messagesContainer != null && messagesContainer.getChildCount() > 0) {
                    boolean firstItemVisible = messagesContainer.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = messagesContainer.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_container_message_activity.setEnabled(enable);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

//                MessageActivity.this.finish();
                Intent intent=new Intent(MessageActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();


                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {

        MessageActivity.super.onBackPressed();
        Intent intent=new Intent(MessageActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    public class MessageActivityAsyncTask extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(MessageActivity.this, "", "Please wait", true);
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

            if(response==null){
                Toast.makeText(MessageActivity.this, "Please CHeck Internet", Toast.LENGTH_SHORT).show();
            }else {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("responseCode");
                    String responseMessage = object.getString("responseMessage");
                    if (success.equals("200")) {
                        Toast.makeText(MessageActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
//                        Intent in = new Intent(MessageActivity.this, HomeActivity.class);
//                        startActivity(in);
                    } else if (success.equals("1")) {
                        Toast.makeText(MessageActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    } else if (success.equals("2")) {
                        Toast.makeText(MessageActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private String callService() {
            String url = ConstantValues.Message;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                client.addFormPart("mode", "0");        // insert
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(MessageActivity.this).getClientId());
                client.addFormPart("type", "corporateToadmin");
                client.addFormPart("messageFrom", LoginPreferences.getActiveInstance(MessageActivity.this).getUserId());
                client.addFormPart("messageText", message);

                client.finishMultipart();

                response = client.getResponse().toString();
                Log.d("response", response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }


    // TODO GET

    public class GetChatMessageAsyncTask extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(MessageActivity.this, "", "Please wait", true);
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
            Log.d("##########Response", "" + response);

            //responseCode
            JSONObject object;

            if (response != null) {
                try {
                    object = new JSONObject(response);
                    String success = object.getString("responseCode");
                    String responseMessage = object.getString("responseMessage");
                    String total;
                    long totalPrice = 0;
                    Log.d("object", "" + object);
                    if (success.equals("200")) {

                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            JSONArray jsonarry = jObject.getJSONArray("data");
                            messageChatPojoArrayList.clear();
                            for (int i = 0; i < jsonarry.length(); i++) {
                                MessageChatPojo messageChatPojo = new MessageChatPojo();

                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);

                                messageChatPojo.setMessageId(jsonObject2.getString("messageId"));
                                messageChatPojo.setType(jsonObject2.getString("type"));
                                messageChatPojo.setMessageText(jsonObject2.getString("messageText"));
                                messageChatPojo.setMessageTime(jsonObject2.getString("messageTime"));
                                messageChatPojo.setName(jsonObject2.getString("name"));

                                messageChatPojoArrayList.add(messageChatPojo);
                            }
                            messageChatAdapter = new MessageChatAdapter(messageChatPojoArrayList,MessageActivity.this);
                            messagesContainer.setAdapter(messageChatAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
            }

        }


        private String callService() {
            String url = ConstantValues.GetMessage;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);
                client.addFormPart("mode", "3");  // TODO Get Mode
                client.addFormPart("messageFrom", LoginPreferences.getActiveInstance(MessageActivity.this).getUserId());

                Log.e("UserId","is --> " +  LoginPreferences.getActiveInstance(MessageActivity.this).getUserId());
                Log.d("client", client.toString());
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
