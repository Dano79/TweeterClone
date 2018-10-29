package com.drizzidevs.tweeterclone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave;
    EditText edtName, edtKickSpeed, edtKickPower, edtKicksPerMinute, edtPunchPower;

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



