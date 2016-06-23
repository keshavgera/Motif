package fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import MyPrefrences.LoginPreferences;
import adapters.NewsListAdapter;
import models.NewsDataList;
import utils.ConstantValues;
import utils.HttpClient;

public class CompanyNews extends Fragment implements NewsListAdapter.FbShare {

    ListView lv;

    ArrayList<NewsDataList> arrayListNewsListData = new ArrayList<NewsDataList>();
    SwipeRefreshLayout swipe_container_news;
    public NewsListAdapter newsListAdapter;
    ImageView img_noDataFoundNews;

    public CompanyNews() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_companynews, container,
                false);

        setHasOptionsMenu(true);


        lv = (ListView) rootView.findViewById(R.id.newslist);
        img_noDataFoundNews = (ImageView) rootView.findViewById(R.id.img_noDataFoundNews);

        swipe_container_news = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_news);
        // Setup refresh listener which triggers new data loading
        swipe_container_news.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetNewsList().execute();
                swipe_container_news.setRefreshing(false);

            }
        });

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

        new GetNewsList().execute();

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

            //responseCode
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
                            JSONArray jsonarry = jObject.getJSONArray("newsList");
                            arrayListNewsListData.clear();


                            for (int i = 0; i < jsonarry.length(); i++) {
                                NewsDataList newsDataList = new NewsDataList();

                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);
                                newsDataList.setNewsimage(jsonObject2.getString("newsImg"));
                                newsDataList.setNewdesc(jsonObject2.getString("newsDesc"));
                                newsDataList.setNewsdate(jsonObject2.getString("createdDate"));
                                newsDataList.setNewshead(jsonObject2.getString("newsHead"));


                                arrayListNewsListData.add(newsDataList);
                            }

                           newsListAdapter = new NewsListAdapter(getActivity(),arrayListNewsListData, CompanyNews.this);


                            lv.setAdapter(newsListAdapter);



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
            Log.d("News Data", "1: " + arrayListNewsListData);
            if (arrayListNewsListData.isEmpty()) {
                img_noDataFoundNews.setVisibility(View.VISIBLE);
            } else {
                img_noDataFoundNews.setVisibility(View.GONE);
            }
        }


        private String callService() {
            String url = ConstantValues.GETNEWS;

            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();
                Log.e("after connection", "" + url);

//                client.addFormPart("loginKey", LoginPreferences.getActiveInstance(getActivity()).getLoginKey());
//                client.addFormPart("ouId", LoginPreferences.getActiveInstance(getActivity()).getOuId());
//                client.addFormPart("buId", LoginPreferences.getActiveInstance(getActivity()).getBuId());


                client.addFormPart("loginKey", "CCD@anu_js8");
                client.addFormPart("ouId", "113");
                client.addFormPart("buId", "10");

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

        public void postImageonWall () {
       /* String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/camera/");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "shareSeema.jpg";
        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();
        try {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) NewsListAdapter.newsImage.getDrawable();
            Bitmap bm = bitmapDrawable.getBitmap();
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri uri = Uri.parse("file:///" + root + "/camera/" + fname);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList) {
            if ((app.activityInfo.name).contains("facebook")) {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                startActivity(shareIntent);
                break;
            }
        }


    }*/
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/share/");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "shareSeema.jpg";
            File file = new File(myDir, fname);

            if (file.exists())
                file.delete();
            try {
                //   BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                // Bitmap bitmap = bitmapDrawable.getBitmap();
                Bitmap bm = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.photo_bg);
                FileOutputStream out = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Uri uri = Uri.parse("file:///" + root + "/share/" + fname);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpeg");
            // shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) v.getTag(R.string.app_name));
            //shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, (String) v.getTag(R.drawable.ic_launcher));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

            PackageManager pm = getActivity().getPackageManager();
            List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
            for (final ResolveInfo app : activityList) {
                if ((app.activityInfo.name).contains("facebook")) {
                    final ActivityInfo activity = app.activityInfo;
                    final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                    shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    shareIntent.setComponent(name);
                    startActivity(shareIntent);
                    break;
                }
            }
        }
}
