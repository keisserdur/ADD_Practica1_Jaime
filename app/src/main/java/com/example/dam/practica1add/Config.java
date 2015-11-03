package com.example.dam.practica1add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

/**
 * Created by Admin on 01/11/2015.
 */
public class Config extends Activity {

    //private CheckBox sinc;
    private boolean sinc=false,forma;
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

       // sinc= (CheckBox) findViewById(R.id.checkBox);
        rg= (RadioGroup) findViewById(R.id.formasinc);


    }

    public void sinc(View V){
        sinc=!sinc;
    }

    public void aceptar(View v){
        if(R.id.radioAbsoluta==rg.getCheckedRadioButtonId()){
            forma=true;
        }else{
            forma=false;
        }

        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("sinc",sinc);
        bundle.putBoolean("absoluta",forma);

        i.putExtras(bundle);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
