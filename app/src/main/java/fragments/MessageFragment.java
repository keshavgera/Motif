package fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codecube.keshav.motif.HomeActivity;
import com.codecube.keshav.motif.MessageActivity;
import com.codecube.keshav.motif.MultipleMessage;
import com.codecube.keshav.motif.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.MessageAdapter;
import constant.AppConstants;
import models.MessagePojo;
import utils.CommonMethod;

/**
 * Created by bali on 13/4/16.
 */

public class MessageFragment extends Fragment
{
    View view;
    ListView listView;
    MessageAdapter adapter;
    ArrayList<MessagePojo> messagePojoArrayList = new ArrayList<MessagePojo>();

    SwipeRefreshLayout swipe_container_message_fragment;

    private Dialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_message, container, false);
        listView = (ListView) view.findViewById(R.id.listviewdirect);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                Intent intent = new Intent(getActivity(), MessageActivity.class);
                String userId = messagePojoArrayList.get(position).getUserIdTo();
                String userName = messagePojoArrayList.get(position).getUserName();
                Log.d("dirctactivity", "userName" + userName);
                Log.d("DirectActivity", "userid" + messagePojoArrayList.get(position).getUserIdTo());
                intent.putExtra("userId", userId);
                intent.putExtra("userName", userName);
                startActivity(intent);

            }

        });

        swipe_container_message_fragment = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_message_fragment);
        // Setup refresh listener which triggers new data loading
        swipe_container_message_fragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                validateGetTemplate();
                swipe_container_message_fragment.setRefreshing(false);

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
                swipe_container_message_fragment.setEnabled(enable);
            }
        });

        if (CommonMethod.isNetworkAvailable(getActivity())) {
            validateGetTemplate();
        } else {

            CommonMethod.showAlert("No NetworkAvailable", getActivity());
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        Intent intent=new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                        return true;
                    }

                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        menu.clear();
        inflater.inflate(R.menu.menu_direct, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.reload_refresh:
                validateGetTemplate();
                break;
            case R.id.plus_udated:
                Intent i = new Intent(getActivity(), MultipleMessage.class);
                startActivity(i);
                return true;
            default:
                return true;
        }

        return false;
    }


    protected void validateGetTemplate()
    {
//        String signedUserId = MyPreferences.getActiveInstance(getActivity()).getuserId();
        String signedUserId = "2";
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        RequestParams params = new RequestParams();
        params.add(AppConstants.SIGNED_USERID, signedUserId);
        client.post(AppConstants.DIRECT_LIST, params,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mDialog = ProgressDialog.show(getActivity(), "", "Please wait", true);
                        mDialog.setCancelable(false);
                    }


                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers,
                                          JSONObject responseCode) {
                        //super.onSuccess(statusCode, headers, responseCode);

                        if (mDialog != null) {
                            mDialog.dismiss();
                        }

                        Log.e("", "Hash -> " + responseCode.toString());
                        Log.e("", "On Success of NoTs");
                        Log.e("currentFragment", "" + responseCode.toString());
                        parseResult(responseCode.toString());
                        if (messagePojoArrayList!=null) {
                            adapter = new MessageAdapter(getActivity(), messagePojoArrayList);
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

    private void parseResult(String result) {
        try {
            if (result != null) {

                JSONObject response = new JSONObject(result);
                if (response != null) {
                    JSONArray posts = response.getJSONArray("latestMessage");
                    if (posts != null) {
                        messagePojoArrayList.clear();
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject post = posts.getJSONObject(i);
                            MessagePojo messagePojo = new MessagePojo();
                            if (post != null) {
                                messagePojo.setMessage(post.getString("messageId"));
                                messagePojo.setUserIdFrom(post.getString("userIdFrom"));
                                messagePojo.setUserIdTo(post.getString("userIdTo"));
                                messagePojo.setMessage(post.getString("message"));
                                messagePojo.setStatus(post.getString("status"));
                                messagePojo.setCreatedOn(post.getString("createdOn"));
                                messagePojo.setProfileImage("http://52.11.140.186/frow/uploads/" + post.getString("profileImage"));
                                messagePojo.setUserName(post.getString("userName"));
                                messagePojoArrayList.add(messagePojo);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
