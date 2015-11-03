package com.example.dam.practica1add;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

/**
 * Created by 2dam on 20/10/2015.
 */
public class About extends Activity {
    private SharedPreferences pc;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        pc = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        tv= (TextView) findViewById(R.id.textView3);
        tv.setText(pc.getString("fecha","No se a realizado una sincronizacion"));
    }
}
