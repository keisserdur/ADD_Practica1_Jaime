package com.example.dam.practica1add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.dam.practica1add.datos.Persona;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Admin on 18/10/2015.
 */
public class Insert extends Activity {

    private List<Persona> lista;
    private Persona nueva;
    private Long lastId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Bundle b=getIntent().getExtras();
        lastId=b.getLong("id");

    }

    public void agregar (View v){

        EditText nombre= (EditText) findViewById(R.id.et_nombre);
        EditText telefono= (EditText) findViewById(R.id.et_telefono);

        //Creamos una lista auxiliar donde guardar los telefonos
        List<String> telef=new ArrayList<String>();
        telef.add(telefono.getText().toString());

        //gauardamos el Id del ultimo contacto de la lista

        //Creamos una persona nueva, asignandole una Id mayor a la del ultimo
        nueva=new Persona( lastId+1 ,nombre.getText().toString(),telef);

        //Añadimos al usiario a la lista

        Intent i=new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("persona",nueva);
        i.putExtras(bundle);

        setResult(Activity.RESULT_OK, i);
        volver();

    }

    public void cancelar (View v){
        setResult(Activity.RESULT_CANCELED);
        volver();
    }

    private void volver(){
        //Actualizamos la lista y finalizamos la actividad

        finish();
    }
}
