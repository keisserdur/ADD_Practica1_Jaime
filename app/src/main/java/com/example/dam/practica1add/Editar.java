package com.example.dam.practica1add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.dam.practica1add.datos.Persona;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 18/10/2015.
 */
public class Editar extends Activity{

    private int pos;
    private EditText nombre,telf1,telf2,telf3;
    private Persona modificar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        //Sacamos de la intencion que lanzo la actividad los extras en este caso la posicion desde donde se lanzo
        Intent i=getIntent();
        Bundle b=i.getExtras();
        pos=b.getInt("pos");
        modificar=b.getParcelable("persona");
        ini();
    }
    public void ini(){

        nombre=(EditText)findViewById(R.id.et_nombre);
        telf1=(EditText)findViewById(R.id.et_telf1);
        telf2=(EditText)findViewById(R.id.et_telf12);
        telf3=(EditText)findViewById(R.id.et_telf13);

        //Asignamos el nombre al EditText para ver a quien modificamos
        nombre.setText(modificar.getNombre());

        //Comprobamos la longitud de listado de telefonos del elemento a modificar,
        //segun la cantidad de numeros los mostramos en pantalla
        if(modificar.getTelf().size()>=1){
            telf1.setText(modificar.getTelefono(0));
            if(modificar.getTelf().size()>=2){
                telf2.setText(modificar.getTelefono(1));
                if(modificar.getTelf().size()>=3){
                    telf3.setText(modificar.getTelefono(2));
                }
            }
        }

    }

    public void Editar(View v){
        //Asignamos los elementos de los EditText a la lista directamente

        modificar.setNombre(nombre.getText().toString());

        //Creamos un List auxiliar para poder modificar la lista de telefonos
        List<String> telf=new ArrayList<String>();
        //Comprobamos si esta vacio o es nulo, y en caso de no serlo, agregamos un numero de telefono a la lista auxiliar
        if(telf1.getText().toString()!=null){
            telf.add(telf1.getText().toString());
            if(telf2.getText().toString()!=null && !telf2.getText().toString().equals("")){
                telf.add(telf2.getText().toString());
                if(telf3.getText().toString()!=null && !telf3.getText().toString().equals("")){
                    telf.add(telf3.getText().toString());
                }
            }
        }
        modificar.setTelefono(telf);

        Intent i=new Intent();
        Bundle b=new Bundle();
        b.putParcelable("persona",modificar);
        b.putInt("pos",pos);
        i.putExtras(b);

        setResult(Activity.RESULT_OK, i);
        volver();
    }

    public void cancelar(View v){
        setResult(Activity.RESULT_CANCELED);
        volver();
    }
    private void volver(){
        //Actualiza la lista y finaliza la actividad
        this.finish();
    }
}
