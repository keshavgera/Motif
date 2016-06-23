package com.codecube.keshav.motif;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import MyPreference.LoginPreferences;
import models.PostInventoryCorporatePojo;
import utils.CommonMethod;
import utils.ConstantValues;
import utils.HttpClient;

public class PostInventoryActivity extends AppCompatActivity
{

    HorizontalScrollView horizontal_type_of_property;
    EditText et_excepted_rent;
    EditText et_specification;
    /*EditText et_specific_criteria;*/

    TextView house, apartments, pg, villa, guest_house;
    Button row, fully, semi;
    Button ground_floor, floor_one, floor_two,floor_three ,floor_four,floor_five,floor_six,floor_seven,floor_eight,floor_nine;
    Button floor_ten, floor_eleven, floor_twelve,floor_thirten ,floor_fourten,floor_fiften,floor_sixten,floor_seventen;
    Button floor_eighteen, floor_ninteen, floor_twenty;

    Button room_one, room_two, room_three,room_four,room_five,room_five_plus;

    String typeOfProperty;
    String typeOfFurnishing;
    String FloorNumber;
    String roomNumber;

    Toolbar toolbar;
    TextView toolbarTitle;

    String exceptedRent;
    String specification;
    String specificCriteria;
    String preferredLocation;

    private AutoCompleteTextView auto_preferred_location;

    ArrayList<String> auto_preferred_locationArrayList = new ArrayList<>();

    LinearLayout lny_guest_house,lny_apartments,lny_house,lny_pg,lny_villa;

    Spinner spinner_city;
    ArrayList<String> cityArrayList = new ArrayList<>();
    String selectCity;
    ArrayAdapter<String> cityAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_inventory);

        et_excepted_rent = (EditText) findViewById(R.id.et_excepted_rent);
        et_specification = (EditText) findViewById(R.id.et_specification_inventory);
        /*et_specific_criteria = (EditText) findViewById(R.id.et_specific_criteria);*/
        spinner_city = (Spinner) findViewById(R.id.spinner_city_inventory);

        auto_preferred_location = (AutoCompleteTextView) findViewById(R.id.auto_preferred_location_inventory);
        auto_preferred_location.setThreshold(0);

        house = (TextView) findViewById(R.id.house_inventory);
        apartments = (TextView) findViewById(R.id.apartments_inventory);
        pg = (TextView) findViewById(R.id.pg_inventory);
        villa = (TextView) findViewById(R.id.villa_inventory);
        guest_house = (TextView) findViewById(R.id.guest_house_inventory);

        lny_guest_house= (LinearLayout)findViewById(R.id.lny_guest_house_inventory);
        lny_apartments= (LinearLayout)findViewById(R.id.lny_apartments_inventory);
        lny_house= (LinearLayout)findViewById(R.id.lny_house_inventory);
        lny_pg= (LinearLayout)findViewById(R.id.lny_pg_inventory);
        lny_villa= (LinearLayout)findViewById(R.id.lny_villa_inventory);


        row = (Button) findViewById(R.id.row_inventory);
        fully = (Button) findViewById(R.id.fully_inventory);
        semi = (Button) findViewById(R.id.semi_inventory);

        room_one = (Button) findViewById(R.id.room_one_inventory);
        room_two = (Button) findViewById(R.id.room_two_inventory);
        room_three = (Button) findViewById(R.id.room_three_inventory);
        room_four = (Button) findViewById(R.id.room_four_inventory);
        room_five = (Button) findViewById(R.id.room_five_inventory);
        room_five_plus = (Button) findViewById(R.id.room_five_plus_inventory);


        ground_floor = (Button) findViewById(R.id.ground_floor);
        floor_one = (Button) findViewById(R.id.floor_one);
        floor_two = (Button) findViewById(R.id.floor_two);
        floor_three = (Button) findViewById(R.id.floor_three);
        floor_four = (Button) findViewById(R.id.floor_four);
        floor_five = (Button) findViewById(R.id.floor_five);
        floor_six = (Button) findViewById(R.id.floor_six);
        floor_seven = (Button) findViewById(R.id.floor_seven);
        floor_eight = (Button) findViewById(R.id.floor_eight);
        floor_nine = (Button) findViewById(R.id.floor_nine);
        floor_ten = (Button) findViewById(R.id.floor_ten);
        floor_eleven = (Button) findViewById(R.id.floor_eleven);
        floor_twelve = (Button) findViewById(R.id.floor_twelve);
        floor_thirten = (Button) findViewById(R.id.floor_thirten);
        floor_fourten = (Button) findViewById(R.id.floor_fourten);
        floor_fiften = (Button) findViewById(R.id.floor_fiften);
        floor_sixten = (Button) findViewById(R.id.floor_sixten);
        floor_seventen = (Button) findViewById(R.id.floor_seventen);
        floor_eighteen = (Button) findViewById(R.id.floor_eighteen);
        floor_ninteen = (Button) findViewById(R.id.floor_ninteen);
        floor_twenty = (Button) findViewById(R.id.floor_twenty);


        toolbar = (Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_include);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Post Inventory");

//        new GetLocalityFetch().execute();

        cityArrayList.clear();
        cityArrayList.add("Select a city");

        cityAdapter = new ArrayAdapter<String>(
                PostInventoryActivity.this, android.R.layout.simple_spinner_item, cityArrayList);
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
                            (PostInventoryActivity.this, android.R.layout.simple_list_item_1, auto_preferred_locationArrayList);
                    auto_preferred_location.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

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
                row.setBackgroundColor(getResources().getColor(R.color.deselect));
                fully.setBackgroundColor(getResources().getColor(R.color.select));
                semi.setBackgroundColor(getResources().getColor(R.color.deselect));

                Log.e("typeOfFurnishing", "is " + typeOfFurnishing);
            }
        });
        semi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfFurnishing = semi.getText().toString();

                row.setBackgroundColor(getResources().getColor(R.color.deselect));
                semi.setBackgroundColor(getResources().getColor(R.color.select));
                fully.setBackgroundColor(getResources().getColor(R.color.deselect));

                Log.e("typeOfFurnishing", "is " + typeOfFurnishing);
            }
        });


        room_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_one.getText().toString();
                room_one.setBackgroundColor(getResources().getColor(R.color.select));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));

                Log.e("roomNumber", "is " + roomNumber);
            }
        });
        room_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_two.getText().toString();
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.select));
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
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.select));
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
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.select));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));

                Log.e("roomNumber", "is " + roomNumber);
            }
        });
        room_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_five.getText().toString();
                Log.e("roomNumber", "is " + roomNumber);
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.select));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.deselect));

            }
        });
        room_five_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber = room_five_plus.getText().toString();
                room_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                room_five_plus.setBackgroundColor(getResources().getColor(R.color.select));

                Log.e("roomNumber", "is " + roomNumber);
            }
        });



        ground_floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = ground_floor.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.select));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));


                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_one.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.select));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_two.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.select));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_three.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.select));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_four.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.select));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_five.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.select));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_six.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.select));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_seven.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.select));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_eight.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.select));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_eight.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.select));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_nine.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.select));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_ten.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.select));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_eleven.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.select));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_twelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_twelve.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.select));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_thirten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_thirten.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.select));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_fourten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_fourten.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.select));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_fiften.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_fiften.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.select));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_sixten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_sixten.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.select));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_seventen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_seventen.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.select));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_eighteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_eighteen.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.select));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_ninteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_ninteen.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.select));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.deselect));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });
        floor_twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloorNumber = floor_twenty.getText().toString();
                ground_floor.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_one.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_two.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_three.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_four.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_five.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_six.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eight.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_nine.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eleven.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twelve.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_thirten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fourten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_fiften.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_sixten.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_seventen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_eighteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_ninteen.setBackgroundColor(getResources().getColor(R.color.deselect));
                floor_twenty.setBackgroundColor(getResources().getColor(R.color.select));
                Log.e("FloorNumber", "is " + FloorNumber);
            }
        });


    }


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
                Intent intent=new Intent(PostInventoryActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.done:
                if (checkValidation()) {
                    new PostInventoryAsyncTask().execute();
                }
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed()
    {
        PostInventoryActivity.super.onBackPressed();
        Intent intent = new Intent(PostInventoryActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public class PostInventoryAsyncTask extends AsyncTask<String, Void, String> {
        private Dialog mDialog;
        private String response;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mDialog = ProgressDialog.show(PostInventoryActivity.this, "", "Please wait", true);
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

            if(response==null){
                Toast.makeText(PostInventoryActivity.this, "Please CHeck Internet", Toast.LENGTH_SHORT).show();
            }else {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("responseCode");
                    String responseMessage = object.getString("responseMessage");
                    if (success.equals("200")) {
                        Toast.makeText(PostInventoryActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(PostInventoryActivity.this, HomeActivity.class);
                        startActivity(in);
                    } else if (success.equals("1")) {
                        Toast.makeText(PostInventoryActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    } else if (success.equals("2")) {
                        Toast.makeText(PostInventoryActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private String callService() {
            String url = ConstantValues.PostInventory;
            HttpClient client = new HttpClient(url);
            Log.e("before connection", "" + url);

            try {
                client.connectForMultipart();

                Log.e("after connection", "" + url);

                client.addFormPart("mode", "0");        // insert
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(PostInventoryActivity.this).getClientId());
                client.addFormPart("userId", LoginPreferences.getActiveInstance(PostInventoryActivity.this).getUserId());
                client.addFormPart("typeOfProperty", typeOfProperty);
                client.addFormPart("furnishing", typeOfFurnishing);
                client.addFormPart("city", selectCity);
                client.addFormPart("location",preferredLocation);
                client.addFormPart("rooms", roomNumber);
                client.addFormPart("floor", FloorNumber);
                client.addFormPart("expRent",exceptedRent);
                client.addFormPart("specification", specification);
             //   client.addFormPart("specificRequirement", specificCriteria);

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
        preferredLocation=auto_preferred_location.getText().toString();
        exceptedRent = et_excepted_rent.getText().toString();
        specification = et_specification.getText().toString();
//        specificCriteria = et_specific_criteria.getText().toString();

        Log.e("typeOfProperty", "is " + typeOfProperty);
        Log.e("typeOfFurnishing", "is " + typeOfFurnishing);
        Log.e("selectCity", "is " + selectCity);
        Log.e("preferredLocation", "is " + preferredLocation);
        Log.e("roomNumber", "is " + roomNumber);
        Log.e("FloorNumber", "is " + FloorNumber);
        Log.e("exceptedRent", "is " + exceptedRent);
        Log.e("specification", "is " + specification);

        Log.e("list ","is "+ auto_preferred_locationArrayList);
        Log.e("list ","is "+ auto_preferred_locationArrayList.contains(preferredLocation));
        Log.e("list ","is "+ !auto_preferred_locationArrayList.contains(preferredLocation));


        if (typeOfProperty == null) {
            CommonMethod.showAlert("Please select Property.", PostInventoryActivity.this);
            return false;
        } else if (typeOfFurnishing == null) {
            CommonMethod.showAlert("Please select Furnishing.", PostInventoryActivity.this);
            return false;
        } else if (selectCity == null||selectCity.equals("")||selectCity.equals("Select a city")) {
            CommonMethod.showAlert("Please select City.", PostInventoryActivity.this);
            return false;
        } else if (preferredLocation == null||preferredLocation.equals("")) {
            CommonMethod.showAlert("Please select Location.", PostInventoryActivity.this);
            return false;
        } else if (!auto_preferred_locationArrayList.contains(preferredLocation)) {
            CommonMethod.showAlert("Please select valid location.", PostInventoryActivity.this);
            return false;
        } else if (roomNumber == null) {
            CommonMethod.showAlert("Please select Room.", PostInventoryActivity.this);
            return false;
        } else if (FloorNumber == null) {
            CommonMethod.showAlert("Please select Floor.", PostInventoryActivity.this);
            return false;
        } else if (et_excepted_rent.getText().toString().equals("")) {
            CommonMethod.showAlert("Please select Excepted Rent", PostInventoryActivity.this);
            return false;
        } /*else if (et_specification.getText().toString().equals("")) {
            CommonMethod.showAlert("Please select Specification.", PostInventoryActivity.this);
            return false;
        } else if (et_specific_criteria.getText().toString().equals("")) {
            CommonMethod.showAlert("Please select Specific Criteria.", PostInventoryActivity.this);
            return false;
        }*/
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
            mDialog = ProgressDialog.show(PostInventoryActivity.this, "", "Loading...", true);
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
                                        (PostInventoryActivity.this, android.R.layout.simple_list_item_1, cityArrayList);
                                spinner_city.setAdapter(cityAdapter);
                            } else {
                                Toast.makeText(PostInventoryActivity.this, "Don't have any City.", Toast.LENGTH_SHORT).show();
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
            mDialog = ProgressDialog.show(PostInventoryActivity.this, "", "Loading...", true);
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
                                        (PostInventoryActivity.this, android.R.layout.simple_list_item_1, auto_preferred_locationArrayList);
                                auto_preferred_location.setAdapter(adapter);
                            } else {
                                Toast.makeText(PostInventoryActivity.this, "Don't have any location.", Toast.LENGTH_SHORT).show();
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
                client.addFormPart("clientId", LoginPreferences.getActiveInstance(PostInventoryActivity.this).getClientId());
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