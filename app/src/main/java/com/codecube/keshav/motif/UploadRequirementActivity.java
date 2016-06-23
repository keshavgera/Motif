package com.codecube.keshav.motif;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import MyPreference.LoginPreferences;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;

public class UploadRequirementActivity extends AppCompatActivity
{
    HorizontalScrollView horizontal_type_of_property;
    EditText et_date_of_shifting;
    EditText et_preferred_location;
    EditText et_specification;

    TextView house, apartments, pg, villa, guest_house;
    Button row, fully, semi;
    Button room_one, room_two, room_three, room_four, room_five, room_five_plus;

    String typeOfProperty;
    String typeOfFurnishing;
    String roomNumber;

    Toolbar toolbar;
    TextView toolbarTitle;


    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);


    String serverSendDateOfShifting;
    String preferredLocation;
    int priceMinimum = 3000;
    int priceMaximum = 100000;

    String specification;
    String distanceFromOffice;
    String selectCity;

    private AutoCompleteTextView auto_preferred_location;

    Spinner spinner_distance_from_office;
    Spinner spinner_city;

    ArrayAdapter<String> cityAdapter;

    ArrayList<String> auto_preferred_locationArrayList = new ArrayList<>();
    ArrayList<String> cityArrayList = new ArrayList<>();
    ArrayList<String> auto_distance_from_officeArrayList = new ArrayList<>();


    LinearLayout lny_guest_house,lny_apartments,lny_house,lny_pg,lny_villa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_requirement);

        auto_distance_from_officeArrayList.add("Select a distance");
        auto_distance_from_officeArrayList.add("Within 1 Km");
        auto_distance_from_officeArrayList.add("Within 2 Km");
        auto_distance_from_officeArrayList.add("Within 5 Km");
        auto_distance_from_officeArrayList.add("Upto 10 km");



        horizontal_type_of_property = (HorizontalScrollView) findViewById(R.id.horizontal_type_of_property);

        et_date_of_shifting = (EditText) findViewById(R.id.et_date_of_shifting);

        auto_preferred_location = (AutoCompleteTextView) findViewById(R.id.auto_preferred_location);
        auto_preferred_location.setThreshold(0);


        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_distance_from_office = (Spinner) findViewById(R.id.spinner_distance_from_office);



        ArrayAdapter<String> adapterDistance = new ArrayAdapter<String>
                (UploadRequirementActivity.this, android.R.layout.simple_list_item_1, auto_distance_from_officeArrayList);
        spinner_distance_from_office.setAdapter(adapterDistance);



        et_specification = (EditText) findViewById(R.id.et_specification);
        house = (TextView) findViewById(R.id.house);
        apartments = (TextView) findViewById(R.id.apartments);
        pg = (TextView) findViewById(R.id.pg);
        villa = (TextView) findViewById(R.id.villa);
        guest_house = (TextView) findViewById(R.id.guest_house);

        row = (Button) findViewById(R.id.row);
        fully = (Button) findViewById(R.id.fully);
        semi = (Button) findViewById(R.id.semi);
        room_one = (Button) findViewById(R.id.room_one);
        room_two = (Button) findViewById(R.id.room_two);
        room_three = (Button) findViewById(R.id.room_three);
        room_four = (Button) findViewById(R.id.room_four);
        room_five = (Button) findViewById(R.id.room_five);
        room_five_plus = (Button) findViewById(R.id.room_five_plus);


        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Upload Requirement");



        lny_guest_house= (LinearLayout)findViewById(R.id.lny_guest_house);
        lny_apartments= (LinearLayout)findViewById(R.id.lny_apartments);
        lny_house= (LinearLayout)findViewById(R.id.lny_house);
        lny_pg= (LinearLayout)findViewById(R.id.lny_pg);
        lny_villa= (LinearLayout)findViewById(R.id.lny_villa);

        cityArrayList.clear();
        cityArrayList.add("Select a city");

        cityAdapter = new ArrayAdapter<String>(
                UploadRequirementActivity.this, android.R.layout.simple_spinner_item, cityArrayList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(cityAdapter);


        if (!cityArrayList.equals("Select a city")) {
            Log.e("keshav","fetch city list");
                new GetCityFetch().execute();
        }


        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //if (spinCountry.getSelectedItem()!=null) {
                selectCity = spinner_city.getSelectedItem().toString();

                Log.e("keshav", "selectCity is --> " + selectCity);

                if (!selectCity.equals("Select a city")) {

                    Log.e("keshav", "first if--> ");

                    new GetLocalityFetch().execute();

                    auto_preferred_locationArrayList.clear();
                    auto_preferred_location.clearListSelection();
                    auto_preferred_location.setText("");
                }
                if (selectCity.equals("Select a city")) {

                    Log.e("keshav", "first second--> ");
                    auto_preferred_location.clearListSelection();
                    auto_preferred_location.setText("");
                    auto_preferred_locationArrayList.clear();

                    Log.e("keshav", "second list-> " +auto_preferred_locationArrayList);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (UploadRequirementActivity.this, android.R.layout.simple_list_item_1, auto_preferred_locationArrayList);
                    auto_preferred_location.setAdapter(adapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        final TextView min = (TextView) findViewById(R.id.minValue);
        final TextView max = (TextView) findViewById(R.id.maxValue);

        // create RangeSeekBar as Integer range between 20 and 75
        final RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(0, 100000, this);

        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                // handle changed range values
                Log.i("abc", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);

                min.setText(minValue.toString());
                max.setText(maxValue.toString());

                priceMinimum = minValue;
                priceMaximum = maxValue;

            }
        });

        // add RangeSeekBar to pre-defined layout
        ViewGroup layout = (ViewGroup) findViewById(R.id.layout);
        layout.addView(seekBar);

        et_date_of_shifting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });



        lny_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = house.getText().toString();
                lny_house.setBackgroundColor(getResources().getColor(R.color.button_select));
                lny_apartments.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_pg.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_villa.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_guest_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));

                Log.e("typeOfProperty", "house -> " + typeOfProperty);

            }
        });
        lny_apartments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = apartments.getText().toString();

                lny_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_apartments.setBackgroundColor(getResources().getColor(R.color.button_select));
                lny_pg.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_villa.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_guest_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));

                Log.e("typeOfProperty", "apartments ->" + typeOfProperty);
            }
        });
        lny_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = pg.getText().toString();
                lny_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_apartments.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_pg.setBackgroundColor(getResources().getColor(R.color.button_select));
                lny_villa.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_guest_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));

                Log.e("typeOfProperty", "is -> " + typeOfProperty);
            }
        });
        lny_villa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = villa.getText().toString();
                lny_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_apartments.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_pg.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_villa.setBackgroundColor(getResources().getColor(R.color.button_select));
                lny_guest_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                Log.e("typeOfProerty", "is ->" + typeOfProperty);
            }
        });

        lny_guest_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfProperty = guest_house.getText().toString();

                lny_house.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_apartments.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_pg.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_villa.setBackgroundColor(getResources().getColor(R.color.button_deselect));
                lny_guest_house.setBackgroundColor(getResources().getColor(R.color.button_select));
                Log.e("typeOfProerty", "is ->" + typeOfProperty);
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfFurnishing = row.getText().toString();
                fully.setBackgroundColor(getResources().getColor(R.color.deselect));
                row.setBackgroundColor(getResources().getColor(R.color.select));
                semi.setBackgroundColor(getResources().getColor(R.color.deselect));

                Log.e("typeOfFurnishing", "is ->" + typeOfFurnishing);
            }
        });
        fully.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfFurnishing = fully.getText().toString();
                Log.e("typeOfFurnishing", "is " + typeOfFurnishing);
                row.setBackgroundColor(getResources().getColor(R.color.deselect));
                fully.setBackgroundColor(getResources().getColor(R.color.select));
                semi.setBackgroundColor(getResources().getColor(R.color.deselect));
            }
        });
        semi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfFurnishing = semi.getText().toString();
                Log.e("typeOfFurnishing", "is " + typeOfFurnishing);
                row.setBackgroundColor(getResources().getColor(R.color.deselect));
                semi.setBackgroundColor(getResources().getColor(R.color.select));
                fully.setBackgroundColor(getResources().getColor(R.color.deselect));

            }
        });
        room_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_one.getText().toString();
                Log.e("roomNumber", "is " + roomNumber);
                room_one.setBackgroundColor(getResources().getColor(R.color.select));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));
            }
        });
        room_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_two.getText().toString();
                room_two.setBackgroundColor(getResources().getColor(R.color.select));
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("roomNumber", "is " + roomNumber);
            }
        });
        room_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_three.getText().toString();
                room_three.setBackgroundColor(getResources().getColor(R.color.select));
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("roomNumber", "is " + roomNumber);
            }
        });
        room_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_four.getText().toString();
                Log.e("roomNumber", "is " + roomNumber);
                room_four.setBackgroundColor(getResources().getColor(R.color.select));
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));
            }
        });
        room_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_five.getText().toString();
                Log.e("roomNumber", "is " + roomNumber);
                room_five.setBackgroundColor(getResources().getColor(R.color.select));
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));

            }
        });
        room_five_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_five_plus.getText().toString();
                Log.e("roomNumber", "is " + roomNumber);
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.select));
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));

            }
        });

    }

    // TODO Hide previous Date
    protected Dialog onCreateDialog(int id) {

        DatePickerDialog dialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        dialog.getDatePicker().setMinDate(new Date().getTime());
        return dialog;
    }


    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            et_date_of_shifting.setText((CommonMethod.SimpleDateFormat(selectedDay + "-" + (selectedMonth + 1)
                    + "-" + selectedYear)));
            serverSendDateOfShifting = ((+selectedYear) + "-" + (selectedMonth + 1) + "-" + (selectedDay));

            Log.e("DateOfShifting", "is --> " + serverSendDateOfShifting);

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intent=new Intent(UploadRequirementActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.done:
                if (checkValidation()) {
                    new UploadRequirementAsyncTask().execute();
                }
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {

        UploadRequirementActivity.super.onBackPressed();
        Intent intent = new Intent(UploadRequirementActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public class UploadRequirementAsyncTask extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(UploadRequirementActivity.this, "", "Please wait", true);
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


            try {
                if (response == null) {
                    Toast.makeText(UploadRequirementActivity.this, "Please Check Internet", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject object = new JSONObject(response);
                    Log.e("UploadReesponce", "" + object.toString(2));
                    String success = object.getString("responseCode");
                    String responseMessage = object.getString("responseMessage");
                    if (success.equals("200")) {
                        Toast.makeText(UploadRequirementActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(UploadRequirementActivity.this, HomeActivity.class);
                        startActivity(in);
                    } else if (success.equals("1")) {
                        Toast.makeText(UploadRequirementActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    } else if (success.equals("2")) {
                        Toast.makeText(UploadRequirementActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private String callService() {
            String url = ConstantValues.UploadRequirements;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                client.addFormPart("mode", "0");            // TODO INSERT Mode
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(UploadRequirementActivity.this).getClientId());
                client.addFormPart("userId", LoginPreferences.getActiveInstance(UploadRequirementActivity.this).getUserId());
                client.addFormPart("dateOfShifting", serverSendDateOfShifting);
                client.addFormPart("typeOfProperty", typeOfProperty);
                client.addFormPart("furnishing", typeOfFurnishing);
                client.addFormPart("rooms", roomNumber);
                client.addFormPart("preferredLocation", preferredLocation);
                client.addFormPart("budgetFrom", String.valueOf(priceMinimum));
                client.addFormPart("budgetTo", String.valueOf(priceMaximum));
                client.addFormPart("specification", specification);
                client.addFormPart("distanceFromOffice", distanceFromOffice);
//                client.addFormPart("specificRequirement", specifiRequirement);


                Log.e("upload", "userId  ->" + LoginPreferences.getActiveInstance(UploadRequirementActivity.this).getUserId());
                Log.e("upload", "clientId  ->" + LoginPreferences.getActiveInstance(UploadRequirementActivity.this).getClientId());
                client.finishMultipart();

                response = client.getResponse().toString();
                Log.d("response", response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    private boolean checkValidation() {
        preferredLocation = auto_preferred_location.getText().toString();
        specification = et_specification.getText().toString();
        selectCity = spinner_city.getSelectedItem().toString();
        distanceFromOffice = spinner_distance_from_office.getSelectedItem().toString();

        Log.e("DateOfShifting", "is " + serverSendDateOfShifting);
        Log.e("typeOfProperty", "is " + typeOfProperty);
        Log.e("typeOfFurnishing", "is " + typeOfFurnishing);
        Log.e("preferredLocation", "is " + preferredLocation);
        Log.e("roomNumber", "is " + roomNumber);
        Log.e("priceMinimum", "is " + priceMinimum);
        Log.e("priceMaximum", "is " + priceMaximum);
        Log.e("specification", "is " + specification);
        Log.e("distanceFromOffice", "is " + distanceFromOffice);
        Log.e("selectCity", "is " + selectCity);

        if (serverSendDateOfShifting == null) {
            CommonMethod.showAlert("Please select Date.", UploadRequirementActivity.this);
            return false;
        } else if (typeOfProperty == null) {
            CommonMethod.showAlert("Please select Property.", UploadRequirementActivity.this);
            return false;
        } else if (typeOfFurnishing == null) {
            CommonMethod.showAlert("Please select Furnishing.", UploadRequirementActivity.this);
            return false;
        } else if (roomNumber == null) {
            CommonMethod.showAlert("Please select Room.", UploadRequirementActivity.this);
            return false;
        } else if (selectCity == null || selectCity.equals("")||selectCity.equals("Select a city")) {
            CommonMethod.showAlert("Please select city", UploadRequirementActivity.this);
            return false;
        } else if (preferredLocation == null || preferredLocation.equals("")) {
            CommonMethod.showAlert("Please select Preferred Location.", UploadRequirementActivity.this);
            return false;
        } else if (!auto_preferred_locationArrayList.contains(preferredLocation)) {
            CommonMethod.showAlert("Please select valid location.", UploadRequirementActivity.this);
            return false;
        }
        /*else if (et_specification.getText().toString().equals("")) {
            CommonMethod.showAlert("Please select Specification.", UploadRequirementActivity.this);
            return false;
        }*/ else if (spinner_distance_from_office.getSelectedItem().toString().equals("Select a distance")) {
            CommonMethod.showAlert("Please select Distance From office.", UploadRequirementActivity.this);
            return false;
        }
        return true;
    }

    //  TODO City Fetch

    public class GetCityFetch extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(UploadRequirementActivity.this, "", "Loading...", true);
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
                    Log.d("object", "" + object);
                    if (success.equals("200")) {

                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            JSONArray jsonarry = jObject.getJSONArray("city");
                            cityArrayList.clear();
                            cityArrayList.add("Select a city");

                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);
                                cityArrayList.add(jsonObject2.getString("name"));
                            }
                            if (cityArrayList != null) {
                                cityAdapter= new ArrayAdapter<String>
                                        (UploadRequirementActivity.this, android.R.layout.simple_list_item_1, cityArrayList);
                                spinner_city.setAdapter(cityAdapter);
                            } else {
                                Toast.makeText(UploadRequirementActivity.this, "Don't have any City.", Toast.LENGTH_SHORT).show();
                            }


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
            String url = ConstantValues.City;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

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

    //  TODO locality_fetch

    public class GetLocalityFetch extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(UploadRequirementActivity.this, "", "Loading...", true);
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
                            auto_preferred_locationArrayList.clear();
                            for (int i = 0; i < jsonarry.length(); i++) {
                                JSONObject jsonObject2 = jsonarry.getJSONObject(i);
                                auto_preferred_locationArrayList.add(jsonObject2.getString("locality"));
                            }
                            if (auto_preferred_locationArrayList != null) {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (UploadRequirementActivity.this, android.R.layout.simple_list_item_1, auto_preferred_locationArrayList);
                                auto_preferred_location.setAdapter(adapter);
                            } else {
                                Toast.makeText(UploadRequirementActivity.this, "Don't have any location.", Toast.LENGTH_SHORT).show();
                            }


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
            String url = ConstantValues.LocalityFetch;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);
            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);
                client.addFormPart("mode", "3");            // TODO Get Mode
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(UploadRequirementActivity.this).getClientId());
                client.addFormPart("city", selectCity);

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
