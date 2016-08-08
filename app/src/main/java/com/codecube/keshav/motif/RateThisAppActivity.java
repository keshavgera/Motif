package com.codecube.keshav.motif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RateThisAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_this_app);

        // Set custom criteria (optional)
        RateThisApp.init(new RateThisApp.Config(3, 5));

        // Set callback (optional)
        RateThisApp.setCallback(new RateThisApp.Callback() {
            @Override
            public void onYesClicked() {
                Toast.makeText(RateThisAppActivity.this, "Yes event", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoClicked() {
                Toast.makeText(RateThisAppActivity.this, "No event", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelClicked() {
                Toast.makeText(RateThisAppActivity.this, "Cancel event", Toast.LENGTH_SHORT).show();
            }
        });

        RateThisApp.showRateDialog(RateThisAppActivity.this, R.style.MyAlertDialogStyle2);


        /*
        // Set custom title and message
        RateThisApp.Config config = new RateThisApp.Config(3, 5);
        config.setTitle(R.string.hello_world);
        config.setMessage(R.string.hello_world);
        RateThisApp.init(config);
        */

       /* Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show rating dialog explicitly.
                RateThisApp.showRateDialog(RateThisAppActivity.this, R.style.MyAlertDialogStyle2);
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // Show a dialog if criteria is satisfied
        RateThisApp.showRateDialogIfNeeded(this);
    }

}
