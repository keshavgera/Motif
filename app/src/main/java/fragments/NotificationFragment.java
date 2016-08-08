package fragments;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.codecube.keshav.motif.HomeActivity;
import com.codecube.keshav.motif.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.NotificationAdapter;
import models.NotificationData;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.CustomHttpClient;

public class NotificationFragment extends Fragment {
	
	private ListView lv;
	ArrayList<NotificationData> notifyList = new ArrayList<NotificationData>();
	public NotificationAdapter notAdapter;

	ImageView img_noDataFoundNotification;

	SwipeRefreshLayout swipe_container_notification;

    public NotificationFragment()
	{

    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        setHasOptionsMenu(true);

        lv = (ListView) rootView.findViewById(R.id.list);
		img_noDataFoundNotification = (ImageView) rootView.findViewById(R.id.img_noDataFoundNotification);
		swipe_container_notification = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_notification);

		notifyList.clear();


		if(!CommonMethod.isOnline(getActivity()))
		{
			CommonMethod.showAlert("Intenet Connectivity Failure",getActivity());
		}else{
			new MyTask().execute();
		}

		swipe_container_notification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new MyTask().execute();
				swipe_container_notification.setRefreshing(false);

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
				swipe_container_notification.setEnabled(enable);
			}
		});
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String selected = parent.getItemAtPosition(position).toString();

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

	public class MyTask extends AsyncTask<String, Void, String> {
		ProgressDialog pDailog;
		private String code = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDailog = new ProgressDialog(getActivity());
			pDailog.setMessage("Loading");
			pDailog.setCancelable(true);
			pDailog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				ArrayList<NameValuePair> paramspair=new ArrayList<NameValuePair>();
				paramspair.add(new BasicNameValuePair("loginKey","CCD@anu_js8"));
				paramspair.add(new BasicNameValuePair("ouId","113"));
                paramspair.add(new BasicNameValuePair("buId","10"));
				result = CustomHttpClient.executeHttpPost(ConstantValues.GETNOTIFICATIONS,paramspair);
			}
			catch (Exception e) {
				result = "afASF";

			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Log.e("result","is notifi " +result);
			if (result == null) {
				Toast.makeText(
						getActivity(),
						"There is some problem with the server. Please try again after sometime.",
						Toast.LENGTH_LONG).show();

			} else {

				try {
					JSONObject jObject = new JSONObject(result);
					code = jObject.getString("responseCode");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (code.equals("200")) {

					JSONObject jObject = null;
					try {
						jObject = new JSONObject(result);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					try {
						JSONArray jsonarry = jObject.getJSONArray("DATA");
						notifyList.clear();
						for (int i = 0; i < jsonarry.length(); i++) {
							NotificationData details = new NotificationData();
							JSONObject jsonObject2 = jsonarry.getJSONObject(i);
							details.setNotAlert(jsonObject2.getString("pushMessage"));
							details.setNotDate(jsonObject2.getString("createdOn"));
							details.setNotification_title(jsonObject2.getString("title"));
							details.setImage(jsonObject2.getString("image"));

							notifyList.add(details);

						}

						notAdapter = new NotificationAdapter(
								getActivity(), notifyList);
                         lv.setAdapter(notAdapter);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getActivity(),
							"Please Check Your Internet Connection",
							Toast.LENGTH_LONG).show();
				}
				Log.d("Notification Data", "1: " + notifyList);
				if (notifyList.isEmpty()) {
					img_noDataFoundNotification.setVisibility(View.VISIBLE);
				} else {
					img_noDataFoundNotification.setVisibility(View.GONE);
				}
			}
			pDailog.dismiss();
		}
	}

}
