package com.slack.joiple.noti_2048;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener{
    public Button startButton;
    public TextView statusIndicator;
    public NotiBackService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        //Binding
        startButton =findViewById(R.id.starter);

        //EventBinding
        startButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.starter:
                startService(new Intent(getApplicationContext(),NotiBackService.class));
                break;
            default:
                Log.w("EVENT","Unbinded Event");
                break;
        }
    }
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("NotiBackService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
