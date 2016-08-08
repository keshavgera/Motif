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

import MyPreference.LoginPreferences;
import adapters.NewsAdapter;
import models.NewsPojo;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;

public class NewsFragment extends Fragment
{
    ListView lv;
    ArrayList<NewsPojo> newsPojoArrayList = new ArrayList<NewsPojo>();
    SwipeRefreshLayout swipe_container_news;
    public NewsAdapter newsAdapter;
    ImageView img_noDataFoundNews;

    public NewsFragment() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_companynews, container, false);
        setHasOptionsMenu(true);

        lv = (ListView) rootView.findViewById(R.id.newslist);
        img_noDataFoundNews = (ImageView) rootView.findViewById(R.id.img_noDataFoundNews);

        swipe_container_news = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_news);

        //     Todo Setup refresh listener which triggers new data loading
        swipe_container_news.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetNewsList().execute();
                swipe_container_news.setRefreshing(false);
            }
        });

        if(!CommonMethod.isOnline(getActivity()))
        {
            CommonMethod.showAlert("Intenet Connectivity Failure",getActivity());
        }else{
            new GetNewsList().execute();
        }

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (lv != null && lv.getChildCount() > 0) {
                    boolean firstItemVisible = lv.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = lv.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipe_container_news.setEnabled(enable);
            }
        });


        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
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


        return rootView;
    }

    public class GetNewsList extends AsyncTask<String, Void, String>
    {
        private Dialog mDialog;
        private String response;
        private String team_member_id;

        public GetNewsList() {
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

            JSONObject object;
            if (response == null) {
                Toast.makeText(getActivity(), "No Internet Access", Toast.LENGTH_SHORT).show();
            }

            if (response != null) {
                try {
                    object = new JSONObject(response);
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
                            newsPojoArrayList.clear();


                            for (int i = 0; i < jsonarry.length(); i++) {
                                NewsPojo newsPojo = new NewsPojo();

                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);
                                newsPojo.setNewsId(jsonObject2.getString("newsId"));
                                newsPojo.setNewsHead(jsonObject2.getString("newsHead"));
                                newsPojo.setNewsDesc(jsonObject2.getString("newsDesc"));
                                newsPojo.setNewsTo(jsonObject2.getString("newsTo"));
                                newsPojo.setNewsDate(jsonObject2.getString("newsDate"));
                                newsPojo.setNewsFrom(jsonObject2.getString("newsFrom"));
                                newsPojo.setNewsImgage(jsonObject2.getString("newsImg"));

                                newsPojoArrayList.add(newsPojo);
                            }

                            newsAdapter = new NewsAdapter(getActivity(),newsPojoArrayList);

                            lv.setAdapter(newsAdapter);
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
            Log.d("News Data", "1: " + newsPojoArrayList);
            if (newsPojoArrayList.isEmpty()) {
                img_noDataFoundNews.setVisibility(View.VISIBLE);
            } else {
                img_noDataFoundNews.setVisibility(View.GONE);
            }
        }


        private String callService()
        {

            String url = ConstantValues.News;

            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();
                Log.e("after connection", "" + url);

                client.addFormPart("mode", "3");
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(getActivity()).getClientId());
                client.addFormPart("newsTo", "all");

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
