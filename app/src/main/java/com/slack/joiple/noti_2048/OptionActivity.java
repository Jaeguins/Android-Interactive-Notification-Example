package com.slack.joiple.noti_2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class OptionActivity extends AppCompatActivity {
    public Button StartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        StartButton=findViewById(R.id.starter);
    }
}
