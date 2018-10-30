package com.drizzidevs.tweeterclone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave;
    EditText edtName, edtKickSpeed, edtKickPower, edtKicksPerMinute, edtPunchPower;
    TextView txtGetData;
    Button btnGetAllData;
    String allKickBoxers;
    Button btnTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(MainActivity.this);

        edtName = findViewById(R.id.edtName);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        edtKicksPerMinute = findViewById(R.id.edtKicksPerMinute);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnTransition = findViewById(R.id.btnNextActivity);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBalls");
                parseQuery.getInBackground("EzAWghp3BR", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if (object != null && e == null) {

                            txtGetData.setText(String.format("%s - Punch Power: %s", object.get("name"), object.get("punch_power")));
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBalls");

                queryAll.whereGreaterThan("punch_power", 100);
                queryAll.whereGreaterThanOrEqualTo("punch_power", 3000);
                queryAll.setLimit(1);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {
                            if (objects.size() > 0) {

                                for (ParseObject kickBoxer : objects) {
                                    allKickBoxers = String.format("%s%s\n", allKickBoxers, kickBoxer.get("name"));
                                }

                                FancyToast.makeText(MainActivity.this, allKickBoxers,FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onClick(View v) {

        try {
            final ParseObject kickBallsBoxer = new ParseObject("KickBalls");
            kickBallsBoxer.put("name", edtName.getText().toString());
            kickBallsBoxer.put("kick_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBallsBoxer.put("kick_power", Integer.parseInt(edtKickPower.getText().toString()));
            kickBallsBoxer.put("punch_power", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBallsBoxer.put("kicks_per_minute", Integer.parseInt(edtKicksPerMinute.getText().toString()));
            kickBallsBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(MainActivity.this, kickBallsBoxer.get("name") + " is saved to server", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }
}



