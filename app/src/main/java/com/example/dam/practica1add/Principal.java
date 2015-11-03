package com.example.dam.practica1add;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dam.practica1add.archivoXML.GestorXML;
import com.example.dam.practica1add.datos.OrdenInverso;
import com.example.dam.practica1add.datos.OrigenDatos;
import com.example.dam.practica1add.datos.Persona;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Principal extends AppCompatActivity {

    private final int CONFIG = 1,INSERTAR=2,EDITAR=2;
    private TextView tv;
    private List<Persona> lista,lista2;
    private GestorXML gestor;
    private ListView lv;
    private AdapterClass adt;
    private int num=0;
    private SharedPreferences.Editor ed;
    private SharedPreferences pc;
    private Date fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        try {
            ini();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    /********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menu
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Comprobamos cual se a pulsado y se ejecuta su codigo
        if (id == R.id.ordenarAZ) {
            //ordenamos la lista de forma ascendente
            Collections.sort(lista);
            adt.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.ordenarZA) {
            //ordenamos la lista de forma descendente
            Collections.sort(lista, new OrdenInverso());
            adt.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.menu_insertar) {
            //iniciamos una actividad nueva para la insercion de un nuevo contacto a la lista
            Intent i = new Intent(this, Insert.class);
            Bundle b=new Bundle();
            b.putLong("id",lista.get(lista.size()-1).getId());
            i.putExtras(b);
            startActivityForResult(i, INSERTAR);
            adt.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontextual, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();

        //DEVUELVE LA VISTA DONDE HE ECHO CLICK (ITEM)
        AdapterView.AdapterContextMenuInfo VistaInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int pos = VistaInfo.position;

        if (id == R.id.menu_borrar) {
            //Borramos el contacto de la posicion pos
            lista.remove(pos);
            adt.notifyDataSetChanged();

            return true;
        } else if (id == R.id.menu_editar) {
            //Lanzamos una nueva actividad y le pasamos la posicion del elemento a editar
            Intent i = new Intent(this, Editar.class);
            Bundle b=new Bundle();
            b.putInt("pos", pos);
            b.putParcelable("persona", lista.get(pos));
            i.putExtras(b);
            startActivityForResult(i, EDITAR);
            adt.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    /********************************************************************************************/

    public void ini() throws IOException, XmlPullParserException {
        fecha=new Date();
        lv = (ListView) findViewById(R.id.lv);
        lista2=new ArrayList<Persona>();

        //Registramos el menucontextual al ListView
        registerForContextMenu(lv);

        gestor=new GestorXML(this);

        pc = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed = pc.edit();


        //Saca los datos la primera vez
        if(pc.getBoolean("first",true)) {
            obtenerDatos();

            gestor.sincronizar(lista, "total.xml");

            ed.putString("fecha","("+fecha.getHours()+":"+fecha.getMinutes()+")"+fecha.getDay()+"/"+fecha.getMonth()+"/"+fecha.getYear()+1900);
            ed.putBoolean("first",false);
            ed.commit();
        }else{
            lista=gestor.recuperar("total.xml");
        }

        //Autosincroniza
        if(pc.getBoolean("sinc",false)){
            gestor.sincronizar(lista, "total.xml");
            ed.putString("fecha","("+fecha.getHours()+":"+fecha.getMinutes()+")"+fecha.getDay()+"/"+fecha.getMonth()+"/"+fecha.getYear()+1900);
            ed.commit();
        }


        //Creamos un adaptador y se lo asignamos al ListView
        adt = new AdapterClass(this, R.layout.elemento_lista, lista);
        lv.setAdapter(adt);
    }
    public void obtenerDatos(){
        lista=new ArrayList<Persona>();
        for (Persona p : OrigenDatos.getListaContactos(this)) {
            //Creamos personas que seran agregadas a la lista
            Persona per = new Persona(p.getId(), p.getNombre(), OrigenDatos.getListaTelefonos(this, p.getId()));
            lista.add(per);
        }
    }

    /********************************************************************************************/


    public void sincronizar(View v) throws IOException, XmlPullParserException {

        Log.v("SAP",lista.isEmpty()+"");
        if(pc.getBoolean("forma",true) && !lista.isEmpty()){
            gestor.sincronizar(lista,"archivo.xml");
            lista2.clear();
            num=0;

            ed.putString("fecha", "(" + fecha.getHours() + ":" + fecha.getMinutes() + ")" + fecha.getDay() + "/" + fecha.getMonth() + "/" + fecha.getYear()+1900);
            ed.commit();
        }else{
            if(lista2!=null) {
                gestor.sincronizar(lista2,"parcial"+num+".xml");
                num++;
                lista2.clear();
            }
        }
    }

    public void about(View v){
        Intent i=new Intent(this, About.class);
        startActivity(i);
    }

    public void escribir(View v) throws IOException, XmlPullParserException {

    }

    public void configurar(View v){
        Intent i=new Intent(this, Config.class);
        startActivityForResult(i, CONFIG);
    }

    /********************************************************************************************/

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK && requestCode == INSERTAR){
            Bundle b=data.getExtras();
            Persona p=b.getParcelable("persona");
            lista.add(p);
            lista2.add(p);
            Collections.sort(lista);
            adt.notifyDataSetChanged();
        }
        if(resultCode == Activity.RESULT_OK && requestCode == EDITAR){
            Bundle b=data.getExtras();
            int pos=b.getInt("pos");
            Persona p=b.getParcelable("persona");
            lista.get(pos).setNombre(p.getNombre());
            lista.get(pos).setTelefono(p.getTelf());
            lista2.add(lista.get(pos));
            Collections.sort(lista);
            adt.notifyDataSetChanged();
        }
    }
}

