package com.example.expressapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity_Nosotros extends AppCompatActivity {
    private TextView tv_direccion_email,tv_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);
        tv_direccion_email = (TextView) findViewById(R.id.textView_Email);
        tv_num = (TextView) findViewById(R.id.textView_Num);


        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(Intent.ACTION_DIAL);
                inten.setData(Uri.parse("tel:6675967545"));
                startActivity(inten);
            }
        });
    }
}