package fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.tech.NfcBarcode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codecube.keshav.motif.HomeActivity;
import com.codecube.keshav.motif.OffersDetailActivity;
import com.codecube.keshav.motif.R;
import com.codecube.keshav.motif.UploadRequirementActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MyPreference.LoginPreferences;
import adapters.UploadRequirementBrokerAdapter;
import models.OffersPojo;
import models.UploadRequirementBrokerPojo;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;
import utils.ImageLoader1;

public class DisplayFragment extends Fragment
{

    // TODO Permission

    String TAG="PolicyDetailsFragment";
    final int REQUEST_ID_MULTIPLE_PERMISSIONS=11;

    // TODO Permission


    View convertView;

    LinearLayout lny_guest_house, lny_apartments, lny_house, lny_pg, lny_villa;
    TextView house, apartments, pg, villa, guest_house;
    private RecyclerView      horizontal_recycler_view;
    Button button_my_request;
    Button button_my_offers;
    String typeOfProperty;
    private HorizontalAdapter horizontalAdapter;
    private Fragment mFragment = null;
    ArrayList<OffersPojo> offersPojoArrayList = new ArrayList<OffersPojo>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        convertView = inflater.inflate(R.layout.fragment_display, container, false);

        // TODO Permission
        checkAndRequestPermissions();               // TODO Permission

        horizontal_recycler_view= (RecyclerView)convertView. findViewById(R.id.horizontal_recycler_view);
//        horizontalList=new ArrayList<OffersPojo>();

        new MotifOffersAsyncTask().execute();
//        horizontalList.add("A new currency without any usage limits.");
//        horizontalList.add("Enjoy fun filled family activities.");
//        horizontalList.add("Now, gift cash to your friends.");
//        horizontalList.add("Get 20% off.");
//        horizontalList.add("Invite your friends, and get 25% off");
//        horizontalAdapter=new HorizontalAdapter(horizontalList);

//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
//        horizontal_recycler_view.setAdapter(horizontalAdapter);

        if(!CommonMethod.isOnline(getActivity()))
        {
            CommonMethod.showAlert("Intenet Connectivity Failure",getActivity());
        }

        lny_guest_house = (LinearLayout) convertView.findViewById(R.id.lny_guest_house);
        lny_apartments = (LinearLayout) convertView.findViewById(R.id.lny_apartments);
        lny_house = (LinearLayout) convertView.findViewById(R.id.lny_house);
        lny_pg = (LinearLayout) convertView.findViewById(R.id.lny_pg);
        lny_villa = (LinearLayout) convertView.findViewById(R.id.lny_villa);

        house = (TextView) convertView.findViewById(R.id.house);
        apartments = (TextView) convertView.findViewById(R.id.apartments);
        pg = (TextView) convertView.findViewById(R.id.pg);
        villa = (TextView) convertView.findViewById(R.id.villa);
        guest_house = (TextView) convertView.findViewById(R.id.guest_house);
        button_my_request = (Button) convertView.findViewById(R.id.button_my_request);
        button_my_offers = (Button) convertView.findViewById(R.id.button_my_offers);


        convertView.setFocusableInTouchMode(true);
        convertView.requestFocus();
        convertView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        new android.support.v7.app.AlertDialog.Builder(getActivity())
                                .setTitle("Are you sure you want to exit?")
                                .setMessage("")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                getActivity().finish();
                                            }
                                        }).setNegativeButton("No", null).show();
                        return true;
                    }
                    return true;
                }
                return false;
            }
        });

        button_my_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_my_request.setBackgroundColor(getResources().getColor(R.color.button_select));

                mFragment = new MyRequestsFragment();

                if(mFragment!=null) {
                    HomeActivity.toolbartitle.setText("My Requests");
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.containerView, mFragment).commit();
                }
            }
        });

        button_my_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_my_offers.setBackgroundColor(getResources().getColor(R.color.button_select));
                mFragment = new NewsFragment();

                if(mFragment!=null) {
                    HomeActivity.toolbartitle.setText("Promotion And Offers");
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.containerView, mFragment).commit();
                }

            }
        });

        lny_guest_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = guest_house.getText().toString();
//                lny_guest_house.setBackgroundColor(getResources().getColor(R.color.button_select));
                Log.e("typeOfProperty", "apartments ->" + typeOfProperty);

                Intent i=new Intent(getActivity(), UploadRequirementActivity.class);
                i.putExtra("typeOfProperty",typeOfProperty);
                i.putExtra("first","0");
                i.putExtra("second","10000");
                startActivity(i);
//                getActivity().finish();

            }
        });
        lny_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = house.getText().toString();
//                lny_house.setBackgroundColor(getResources().getColor(R.color.button_select));
                Log.e("typeOfProperty", "apartments ->" + typeOfProperty);

                Intent i=new Intent(getActivity(), UploadRequirementActivity.class);
                i.putExtra("typeOfProperty",typeOfProperty);
                i.putExtra("first","0");
                i.putExtra("second","1000000");
                startActivity(i);
//                getActivity().finish();

            }
        });
        lny_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = pg.getText().toString();
//                lny_pg.setBackgroundColor(getResources().getColor(R.color.button_select));
                Log.e("typeOfProperty", "apartments ->" + typeOfProperty);

                Intent i=new Intent(getActivity(), UploadRequirementActivity.class);
                i.putExtra("typeOfProperty",typeOfProperty);
                i.putExtra("first","4000");
                i.putExtra("second","10000");
                startActivity(i);
//                getActivity().finish();

            }
        });
        lny_apartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = apartments.getText().toString();
//                lny_apartments.setBackgroundColor(getResources().getColor(R.color.button_select));
                Log.e("typeOfProperty", "apartments ->" + typeOfProperty);

                Intent i=new Intent(getActivity(), UploadRequirementActivity.class);
                i.putExtra("typeOfProperty",typeOfProperty);
                i.putExtra("first","0");
                i.putExtra("second","1000000");
                startActivity(i);
//                getActivity().finish();

            }
        });
        lny_villa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = villa.getText().toString();
//                lny_villa.setBackgroundColor(getResources().getColor(R.color.button_select));
                Log.e("typeOfProperty", "apartments ->" + typeOfProperty);

                Intent i=new Intent(getActivity(), UploadRequirementActivity.class);
                i.putExtra("typeOfProperty",typeOfProperty);
                i.putExtra("first","0");
                i.putExtra("second","1000000");
                startActivity(i);
//                getActivity().finish();

            }
        });

        return convertView;
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
    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<OffersPojo> horizontalList;
        private ImageLoader1 imgload;

        public class MyViewHolder extends RecyclerView.ViewHolder {
//            public TextView text_offer_id;
//            public TextView text_offer_desc;
//            public TextView text_offer_type;
            public TextView text_offer_name;
            public ImageView iv_offer_image;
            public RelativeLayout rly_recycler_main;

            public MyViewHolder(View view) {
                super(view);
//                text_offer_id = (TextView) view.findViewById(R.id.text_offer_id);
//                text_offer_desc = (TextView) view.findViewById(R.id.text_offer_desc);
//                text_offer_type = (TextView) view.findViewById(R.id.text_offer_type);

                text_offer_name = (TextView) view.findViewById(R.id.text_offer_name);

                rly_recycler_main = (RelativeLayout) view.findViewById(R.id.rly_recycler_main);
                iv_offer_image = (ImageView) view.findViewById(R.id.iv_offer_image);
            }
        }


//        public HorizontalAdapter(List<String> horizontalList) {
//            this.horizontalList = horizontalList;
//        }

        public HorizontalAdapter(List<OffersPojo> horizontalList) {
            this.horizontalList = horizontalList;

            imgload = new ImageLoader1(getActivity());

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_home_recycle, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
//            holder.text_offer_id.setText(horizontalList.get(position).getOfferId());
//            holder.text_offer_type.setText(horizontalList.get(position).getOfferType());
//            holder.text_offer_desc.setText(horizontalList.get(position).getOfferDesc());
            holder.text_offer_name.setText(horizontalList.get(position).getOfferName());

            Log.e("id "," is --  > " +horizontalList.get(position).getOfferId());
            Log.e("name "," is --  > " +horizontalList.get(position).getOfferName());
            Log.e("type "," is --  > " +horizontalList.get(position).getOfferType());
            Log.e("desc "," is --  > " +horizontalList.get(position).getOfferDesc());
            Log.e("image "," is --  > " +horizontalList.get(position).getOfferImage());
//            holder.iv_offer_image.setText(horizontalList.get(position).getOfferDesc());
            imgload.DisplayImage(getActivity(), horizontalList.get(position).getOfferImage(), holder.iv_offer_image);

            holder.rly_recycler_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(getActivity(), OffersDetailActivity.class);
                    i.putExtra("offersArrayList",horizontalList.get(position));
//                    i.putExtra("id",horizontalList.get(position).getOfferId());
//                    i.putExtra("name",horizontalList.get(position).getOfferName());
//                    i.putExtra("type",horizontalList.get(position).getOfferType());
//                    i.putExtra("desc",horizontalList.get(position).getOfferDesc());
//                    i.putExtra("image",horizontalList.get(position).getOfferImage());
                    startActivity(i);
//                    Toast.makeText(getActivity(),holder.text_offer_name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    public class MotifOffersAsyncTask extends AsyncTask<String, Void, String> {
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

            JSONObject object;

            if (response != null) {
                try {
                    object = new JSONObject(response);
                    String success = object.getString("responseCode");
                    String responseMessage = object.getString("responseMessage");

                    if (success.equals("200")) {

                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            JSONArray jsonarry = jObject.getJSONArray("data");
                            offersPojoArrayList.clear();
                            for (int i = 0; i < jsonarry.length(); i++) {
                                OffersPojo offersPojo = new OffersPojo();

                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);

                                offersPojo.setOfferId(jsonObject2.getString("offerId"));
                                offersPojo.setOfferName(jsonObject2.getString("offerName"));
                                offersPojo.setOfferDesc(jsonObject2.getString("offerDesc"));
                                offersPojo.setOfferType(jsonObject2.getString("offerType"));
                                offersPojo.setOfferImage(jsonObject2.getString("offerImage"));

                                offersPojoArrayList.add(offersPojo);
                            }


                            horizontalAdapter=new HorizontalAdapter(offersPojoArrayList);

                            LinearLayoutManager horizontalLayoutManagaer
                                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                            horizontal_recycler_view.setAdapter(horizontalAdapter);


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
            Log.d("offers Pojo","ArrayList -->  " + offersPojoArrayList);
        }


        private String callService() {
            String url = ConstantValues.Offers;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                client.addFormPart("mode", "3");  // TODO Get Mode

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


    //TODO PERMISSION
    private boolean checkAndRequestPermissions() {

        int readCall = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int locationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (readCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        Log.d(TAG, "Permission callback called-------");

        switch (requestCode)
        {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions

                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.SEND_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(getActivity(), "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    //TODO PERMISSION
}
