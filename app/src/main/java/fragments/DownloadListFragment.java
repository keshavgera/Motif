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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.codecube.keshav.motif.HomeActivity;
import com.codecube.keshav.motif.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import MyPrefrences.LoginPreferences;
//import adapters.DownloadListAdapter;
//import models.DownloadData;
import MyPreference.LoginPreferences;
import adapters.DownloadListAdapter;
import models.DownloadPojo;
import utils.ConstantValues;
import utils.HttpClient;

public class DownloadListFragment extends Fragment{

    ListView lv_download;
    ImageView img_noDataDownload;
    SwipeRefreshLayout swipe_container_download;
    List<DownloadPojo> downloadPojoArrayList = new ArrayList<>();
    DownloadListAdapter downloadListAdapter;

    public DownloadListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_download_list, container, false);
        setHasOptionsMenu(true);

        lv_download = (ListView) rootView.findViewById(R.id.lv_download);
        img_noDataDownload = (ImageView) rootView.findViewById(R.id.img_noDataDownload);
        swipe_container_download = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_download);

        swipe_container_download.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetDownloadList().execute();
                swipe_container_download.setRefreshing(false);

            }
        });

        lv_download.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (lv_download != null && lv_download.getChildCount() > 0) {
                    boolean firstItemVisible = lv_download.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = lv_download.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_container_download.setEnabled(enable);
            }
        });

        new GetDownloadList().execute();

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener


                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        return true;
                    }
                    return true;
                }
                return false;
            }
        });

        return rootView;

    }

    public class GetDownloadList extends AsyncTask<String, Void, String>
    {
        private Dialog mDialog;
        private String response;

        public GetDownloadList() {
        }

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

            if (response == null) {
                Toast.makeText(getActivity(), "No Internet Access", Toast.LENGTH_SHORT).show();
            }

            if (response != null) {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("responseCode");
                    String responseMessage = object.getString("responseMessage");
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
                            downloadPojoArrayList.clear();


                            for (int i = 0; i < jsonarry.length(); i++) {
                                DownloadPojo downloadPojo = new DownloadPojo();
                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);
                                downloadPojo.setId(jsonObject2.getString("fileId"));
                                downloadPojo.setFileName(jsonObject2.getString("fileName"));
                                downloadPojo.setDownloadUrl(jsonObject2.getString("path"));
                                downloadPojo.setFileType(jsonObject2.getString("fileType"));

                              /*  if(downloadTaskId != null && !downloadTaskId.equals("")){
                                    Intent in = new Intent(getActivity(),DownloadFiles.class);
                                    in.putExtra("filename", jsonObject2.getString("fileName"));
                                    in.putExtra("url", jsonObject2.getString("path"));
                                    startActivity(in);
                                }else{*/
                                downloadPojoArrayList.add(downloadPojo);
                               // }

                            }
                       /*     if(downloadTaskId != null && !downloadTaskId.equals("")) {
                            }else{*/
                                downloadListAdapter = new DownloadListAdapter(getActivity(), downloadPojoArrayList);
                                lv_download.setAdapter(downloadListAdapter);
                         //   }


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
            Log.d("Download Data", "1: " + downloadPojoArrayList);
          /*  if(downloadTaskId != null && !downloadTaskId.equals("")) {
            }else{*/
                if (downloadPojoArrayList.isEmpty()) {
                    img_noDataDownload.setVisibility(View.VISIBLE);
                } else {
                    img_noDataDownload.setVisibility(View.GONE);
                }
          //  }

        }


        private String callService() {
            String url = ConstantValues.DOWNLOADLIST;

            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                client.addFormPart("p_mode", "3");
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(getActivity()).getClientId());
                client.addFormPart("fileId", "");
                client.finishMultipart();

                response = client.getResponse().toString();
                Log.d("response", "1" + response);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }
    }
    
}
