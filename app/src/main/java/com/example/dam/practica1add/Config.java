package com.example.dam.practica1add;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

/**
 * Created by Admin on 01/11/2015.
 */
public class Config extends Activity {

    //private CheckBox sinc;
    private RadioGroup rg;
    private SharedPreferences.Editor ed;
    private SharedPreferences pc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

       // sinc= (CheckBox) findViewById(R.id.checkBox);
        rg= (RadioGroup) findViewById(R.id.formasinc);
        pc = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = pc.edit();


    }

    public void sinc(View V){
        Boolean aux=pc.getBoolean("sinc",false);
        if(!aux)

            ed.putBoolean("sinc", true);
        else
            ed.putBoolean("sinc", false);

    }

    public void aceptar(View v){
        if(R.id.radioAbsoluta==rg.getCheckedRadioButtonId()){
            ed.putBoolean("forma", true);
        }else{
            ed.putBoolean("forma", false);
        }
        ed.commit();

        finish();
    }
}
