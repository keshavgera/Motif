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
import com.codecube.keshav.motif.HomeFragmentDetailActivity;
import com.codecube.keshav.motif.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import MyPreference.LoginPreferences;
import adapters.MessageAdapter;
import adapters.UploadRequirementBrokerAdapter;
import models.MessagePojo;
import models.UploadRequirementBrokerPojo;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;

public class HomeFragment extends Fragment {
    View convertView;

    ListView list_view_broker;
    ImageView img_no_data_broker_list;


    ArrayList<UploadRequirementBrokerPojo> uploadRequirementBrokerPojoArrayList = new ArrayList<UploadRequirementBrokerPojo>();
    UploadRequirementBrokerAdapter uploadRequirementBrokerAdapter;



    TextView tv_totalSale;
    RelativeLayout relTot;
    SwipeRefreshLayout swipe_container_broker_list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        convertView = inflater.inflate(R.layout.fragment_home, container, false);


        list_view_broker = (ListView) convertView.findViewById(R.id.list_view_broker);
        img_no_data_broker_list = (ImageView) convertView.findViewById(R.id.img_no_data_broker_list);

        swipe_container_broker_list = (SwipeRefreshLayout) convertView.findViewById(R.id.swipe_container_broker_list);
        // Setup refresh listener which triggers new data loading
        swipe_container_broker_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UploadRequirementBrokerAsyncTask().execute();
                swipe_container_broker_list.setRefreshing(false);

            }
        });

        list_view_broker.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (list_view_broker != null && list_view_broker.getChildCount() > 0) {
                    boolean firstItemVisible = list_view_broker.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = list_view_broker.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_container_broker_list.setEnabled(enable);
            }
        });

        new UploadRequirementBrokerAsyncTask().execute();


        list_view_broker.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getActivity(), HomeFragmentDetailActivity.class);
                i.putExtra("uploadRequirementBrokerDetails",uploadRequirementBrokerPojoArrayList.get(position));
//                i.putExtra("collectionMemberName", teamCollectionMemberName);
//                i.putExtra("comeTeamcollection",true);
                startActivity(i);
            }
        });



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
                            uploadRequirementBrokerPojoArrayList.clear();
                            for (int i = 0; i < jsonarry.length(); i++) {
                                UploadRequirementBrokerPojo uploadRequirementBrokerPojo = new UploadRequirementBrokerPojo();

                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);

                                uploadRequirementBrokerPojo.setName(jsonObject2.getString("name"));
                                uploadRequirementBrokerPojo.setMobileNo(jsonObject2.getString("mobileNo"));
                                uploadRequirementBrokerPojo.setDateOfShifting(jsonObject2.getString("dateOfShifting"));
                                uploadRequirementBrokerPojo.setTypeOfProperty(jsonObject2.getString("typeOfProperty"));
                                uploadRequirementBrokerPojo.setFurnishing(jsonObject2.getString("furnishing"));
                                uploadRequirementBrokerPojo.setRooms(jsonObject2.getString("rooms"));
                                uploadRequirementBrokerPojo.setBudgetFrom(jsonObject2.getString("budgetFrom"));
                                uploadRequirementBrokerPojo.setBudgetTo(jsonObject2.getString("budgetTo"));
                                uploadRequirementBrokerPojo.setPreferredLocation(jsonObject2.getString("preferredLocation"));
                                uploadRequirementBrokerPojo.setDistanceFromOffice(jsonObject2.getString("distanceFromOffice"));
                                uploadRequirementBrokerPojo.setSpecification(jsonObject2.getString("specification"));
                                uploadRequirementBrokerPojo.setCity(jsonObject2.getString("city"));

                                uploadRequirementBrokerPojoArrayList.add(uploadRequirementBrokerPojo);
                            }
                            uploadRequirementBrokerAdapter = new UploadRequirementBrokerAdapter(getActivity(), uploadRequirementBrokerPojoArrayList);
                            list_view_broker.setAdapter(uploadRequirementBrokerAdapter);

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
            Log.d("uploadRequirementBroker","PojoArrayList" + uploadRequirementBrokerPojoArrayList);
            if (uploadRequirementBrokerPojoArrayList.isEmpty()) {
                img_no_data_broker_list.setVisibility(View.VISIBLE);
            } else {
                img_no_data_broker_list.setVisibility(View.GONE);
            }
        }


        private String callService() {
            String url = ConstantValues.GetCorporateList;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);
                client.addFormPart("mode", "3");  // TODO Get Mode
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(getActivity()).getClientId());

                Log.e("clientId","is --> " +  LoginPreferences.getActiveInstance(getActivity()).getClientId());
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
