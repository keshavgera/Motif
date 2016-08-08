package fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codecube.keshav.motif.ChangePassword;
import com.codecube.keshav.motif.HomeActivity;
import com.codecube.keshav.motif.PrivacyPolicyActivity;
import com.codecube.keshav.motif.ProfileUpdateActivity;
import com.codecube.keshav.motif.R;
import com.codecube.keshav.motif.TermAndConditionsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import MyPreference.LoginPreferences;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;

public class SettingFragment extends Fragment {

    View view;
    String switch_value, switch_type;

    /*RelativeLayout editProfile;*/
    RelativeLayout rly_change_password;
    RelativeLayout rly_profile_update;
    RelativeLayout rel_privacy_policy;
    RelativeLayout rel_term_and_conditions;

    SwitchCompat switch_school_alerts, switch_wealth_management, switch_real_estate, switch_travel;
    SwitchCompat switch_forex_rates, switch_breaking_news, switch_entertainment,switch_taxation_related_info,switch_sounds;


    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_layout, container, false);
        setHasOptionsMenu(true);

        rly_change_password = (RelativeLayout) view.findViewById(R.id.rly_change_password);
        rly_profile_update = (RelativeLayout) view.findViewById(R.id.rly_profile_update);
        /*editProfile = (RelativeLayout) view.findViewById(R.id.rl_editProfile);*/
        rel_privacy_policy = (RelativeLayout) view.findViewById(R.id.rel_privacy_policy);
        rel_term_and_conditions = (RelativeLayout) view.findViewById(R.id.rel_term_and_conditions);

        switch_school_alerts = (SwitchCompat) view.findViewById(R.id.switch_school_alerts);
        switch_wealth_management = (SwitchCompat) view.findViewById(R.id.switch_wealth_management);
        switch_real_estate = (SwitchCompat) view.findViewById(R.id.switch_real_estate);
        switch_travel = (SwitchCompat) view.findViewById(R.id.switch_travel);
        switch_forex_rates = (SwitchCompat) view.findViewById(R.id.switch_forex_rates);
        switch_breaking_news = (SwitchCompat) view.findViewById(R.id.switch_breaking_news);
        switch_entertainment = (SwitchCompat) view.findViewById(R.id.switch_entertainment);
        switch_taxation_related_info = (SwitchCompat) view.findViewById(R.id.switch_taxation_related_info);
        switch_sounds = (SwitchCompat) view.findViewById(R.id.switch_sounds);


        switch_school_alerts.setChecked(false);
        switch_wealth_management.setChecked(true);
        switch_real_estate.setChecked(false);
        switch_travel.setChecked(true);
        switch_forex_rates.setChecked(false);
        switch_breaking_news.setChecked(false);
        switch_entertainment.setChecked(false);
        switch_taxation_related_info.setChecked(false);
        switch_sounds.setChecked(false);


        if(!CommonMethod.isOnline(getActivity()))
        {
            CommonMethod.showAlert("Intenet Connectivity Failure",getActivity());
        }


        /*editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
                Log.e("ProfileActivity", "clicked");
            }
        });*/

        rly_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangePassword.class);
                startActivity(i);
            }
        });

        rly_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileUpdateActivity.class);
                startActivity(i);
            }
        });


        rel_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(in);
            }
        });

        rel_term_and_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), TermAndConditionsActivity.class);
                startActivity(in);
            }
        });

       /* switch_school_alerts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "school Alerts";
                    new ServiceAsnc().execute();


                } else {
                    switch_value = "N";
                    switch_type = "school Alerts";
                    new ServiceAsnc().execute();
                }

            }
        });*/

       /* switch_wealth_management.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "wealthManagement";
                new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "wealthManagement";
                new ServiceAsnc().execute();

                }

            }
        });*/
       /* switch_real_estate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "real Estate Advisory";
                    new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "real Estate Advisory";
                    new ServiceAsnc().execute();
                }

            }
        });*/
       /* switch_travel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "travelPackages";
                    new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "travelPackages";
                    new ServiceAsnc().execute();

                }

            }
        });*/

        /*switch_forex_rates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "forexRates";
            new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "forexRates";
            new ServiceAsnc().execute();

                }

            }
        });*/
        /*switch_breaking_news.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "breakingNews";
            new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "breakingNews";
            new ServiceAsnc().execute();

                }

            }
        });*/
        /*switch_entertainment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "entertainment";
            new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "entertainment";
            new ServiceAsnc().execute();

                }

            }
        });*/
        /*switch_taxation_related_info.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "taxationRelatedInfo";
            new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "taxationRelatedInfo";
            new ServiceAsnc().execute();

                }

            }
        });*/
        /*switch_sounds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switch_value = "Y";
                    switch_type = "sounds";
            new ServiceAsnc().execute();

                } else {
                    switch_value = "N";
                    switch_type = "sounds";
            new ServiceAsnc().execute();

                }

            }
        });*/


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
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


        return view;
    }

    public class ServiceAsnc extends AsyncTask<String, Void, String> {
        private String response;
        ProgressDialog mDialog;

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
            response = callService(switch_type, switch_value);
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
                Toast.makeText(getActivity(), "There is some problem with the server. Please try again after sometime.",
                        Toast.LENGTH_LONG).show();
            } else {
                try {
                    String success,responseMessage;

                        Log.e("response is >", response);
                        JSONObject object = new JSONObject(response);
                        success = object.getString("responseCode");
                    responseMessage = object.getString("responseMessage");

                        Toast.makeText(getActivity(), responseMessage,Toast.LENGTH_SHORT).show();

                        if (!success.equals("200")) {
                            responseMessage = object.getString("responseMessage");
                            Toast.makeText(getActivity(), responseMessage, Toast.LENGTH_SHORT).show();
                        }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private String callService(String type, String status)
        {
            // TODO Auto-generated method stub
            String url = ConstantValues.UserNotificationSetting;

            HttpClient client = new HttpClient(url);
            try {

                client.connectForMultipart();
                client.addFormPart("mode", "1");
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(getActivity()).getClientId());
                client.addFormPart("userId", LoginPreferences.getActiveInstance(getActivity()).getUserId());
                client.addFormPart("value", status);
                client.addFormPart("type", type);

                client.finishMultipart();

                response = client.getResponse().toString();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        if (ChangePassword.changePasswordFlag) {

            mFragmentManager = getActivity().getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new DisplayFragment()).commit();

            HomeActivity.toolbartitle.setText("Motif Support");

            ChangePassword.changePasswordFlag = false;
        }
    }
}

