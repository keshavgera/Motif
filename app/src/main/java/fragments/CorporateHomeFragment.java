package fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codecube.keshav.motif.CorporateHomeFragmentDetailActivity;
import com.codecube.keshav.motif.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import MyPreference.LoginPreferences;
import adapters.PostInventoryCorporateAdapter;
import adapters.UploadRequirementBrokerAdapter;
import models.PostInventoryCorporatePojo;
import models.UploadRequirementBrokerPojo;
import utils.ConstantValues;
import utils.HttpClient;

public class CorporateHomeFragment extends Fragment {

    View convertView;

    ListView list_view_corporate;
    ImageView img_no_data_corporate_list;


    ArrayList<PostInventoryCorporatePojo> postInventoryCorporatePojoArrayList = new ArrayList<PostInventoryCorporatePojo>();



    PostInventoryCorporateAdapter postInventoryCorporateAdapter;

    SwipeRefreshLayout swipe_container_corporate_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        convertView = inflater.inflate(R.layout.fragment_corporate_home, container, false);


        list_view_corporate = (ListView) convertView.findViewById(R.id.list_view_corporate);
        img_no_data_corporate_list = (ImageView) convertView.findViewById(R.id.img_no_data_corporate_list);

        swipe_container_corporate_list = (SwipeRefreshLayout) convertView.findViewById(R.id.swipe_container_corporate_list);
        // Setup refresh listener which triggers new data loading
        swipe_container_corporate_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UploadRequirementBrokerAsyncTask().execute();
                swipe_container_corporate_list.setRefreshing(false);

            }
        });

        list_view_corporate.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (list_view_corporate != null && list_view_corporate.getChildCount() > 0) {
                    boolean firstItemVisible = list_view_corporate.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = list_view_corporate.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_container_corporate_list.setEnabled(enable);
            }
        });


        list_view_corporate.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getActivity(), CorporateHomeFragmentDetailActivity.class);
                i.putExtra("postInventoryCorporateDetails", postInventoryCorporatePojoArrayList.get(position));
//                i.putExtra("collectionMemberName", teamCollectionMemberName);
//                i.putExtra("comeTeamcollection",true);
                startActivity(i);
            }
        });


        new UploadRequirementBrokerAsyncTask().execute();

        convertView.setFocusableInTouchMode(true);
        convertView.requestFocus();
        convertView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener


                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        getActivity().finish();

                        return true;
                    }


                    return true;
                }
                return false;
            }
        });

//        HomeActivity.toolbar.setTitle("Item Wise Sales");
        return convertView;
    }

    public class UploadRequirementBrokerAsyncTask extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(getActivity(), "", "Please wait", true);
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
                            postInventoryCorporatePojoArrayList.clear();
                            for (int i = 0; i < jsonarry.length(); i++) {
                                PostInventoryCorporatePojo postInventoryCorporatePojo = new PostInventoryCorporatePojo();

                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);

                                postInventoryCorporatePojo.setName(jsonObject2.getString("name"));
                                postInventoryCorporatePojo.setMobileNo(jsonObject2.getString("mobileNo"));
                                postInventoryCorporatePojo.setTypeOfProperty(jsonObject2.getString("typeOfProperty"));
                                postInventoryCorporatePojo.setFurnishing(jsonObject2.getString("furnishing"));
                                postInventoryCorporatePojo.setNoOfBedRooms(jsonObject2.getString("rooms"));
                                postInventoryCorporatePojo.setCity(jsonObject2.getString("city"));
                                postInventoryCorporatePojo.setFloor(jsonObject2.getString("floor"));
                                postInventoryCorporatePojo.setLocation(jsonObject2.getString("location"));
                                postInventoryCorporatePojo.setSpecification(jsonObject2.getString("specification"));
                                postInventoryCorporatePojo.setExpRent(jsonObject2.getString("expRent"));

                                postInventoryCorporatePojoArrayList.add(postInventoryCorporatePojo);
                            }
                            postInventoryCorporateAdapter = new PostInventoryCorporateAdapter(getActivity(), postInventoryCorporatePojoArrayList);
                            list_view_corporate.setAdapter(postInventoryCorporateAdapter);

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
            Log.d("Item Wise Sale Data", "1: " + postInventoryCorporatePojoArrayList);
            if (postInventoryCorporatePojoArrayList.isEmpty()) {
                img_no_data_corporate_list.setVisibility(View.VISIBLE);
            } else {
                img_no_data_corporate_list.setVisibility(View.GONE);
            }
        }


        private String callService() {
            String url = ConstantValues.GetPostInventory;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);
                client.addFormPart("mode", "3");  // TODO Get Mode
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(getActivity()).getClientId());
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



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        menu.clear();
        inflater.inflate(R.menu.menu_direct, menu);

        menu.findItem(R.id.plus_udated).setVisible(false);
        menu.findItem(R.id.reload_refresh).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                getActivity().finish();
                break;

            default:
                return true;
        }
        return false;
    }

}
