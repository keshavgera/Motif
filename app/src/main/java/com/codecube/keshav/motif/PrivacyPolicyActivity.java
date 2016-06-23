package com.codecube.keshav.motif;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by bali on 25/5/16.
 */
public class PrivacyPolicyActivity extends AppCompatActivity {
    TextView tax_1;
    private PopupWindow pwindo;

    Toolbar toolbar;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        toolbar=(Toolbar) findViewById(R.id.toolbar_include);
        toolbarTitle=(TextView)findViewById(R.id.toolbar_title_include);

        SpannableString str = new SpannableString("TaxManager.in");
        str.setSpan(new UnderlineSpan(), 0, str.length(), Spanned.SPAN_PARAGRAPH);
        tax_1=(TextView) findViewById(R.id.tax_1);
        tax_1.setText("TaxManager.in " + getResources().getString(R.string.line1));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarTitle.setText("Privacy And Policy");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_abouts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                PrivacyPolicyActivity.this.finish();
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }


}
