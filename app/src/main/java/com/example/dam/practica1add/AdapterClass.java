package com.example.dam.practica1add;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dam.practica1add.datos.Persona;

import java.io.Serializable;
import java.util.List;

public class AdapterClass extends ArrayAdapter<String>{
    private Context cont;
    private int res;
    private LayoutInflater inf;
    private List<Persona> objs;

    private TextView tv2,tv3;
    private ImageView btm,btp;
    private ViewHolder gv;


    public AdapterClass(Context context, int resource, List objects) {
        super(context, resource, objects);
        cont =context;//actividad
        res=resource;//layout
        objs=objects;//lista de valores
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //No usaremos una clase ViewHolder, porque al actualizar la lista, la vista sigue creada pero su actualizacion no se lleva a cabo
        //if (convertView==null) {
            gv = new ViewHolder();
            convertView = inf.inflate(res, null);

        if(position%2!=0){
            convertView.setBackgroundColor(Color.argb(9,99,00,99));
        }

            TextView tv = (TextView) convertView.findViewById(R.id.tv1);
            gv.t1=tv;

            tv2 = (TextView) convertView.findViewById(R.id.tv2);
            gv.t2=tv2;

            tv3 = (TextView) convertView.findViewById(R.id.tv3);
            gv.t3=tv3;

            btp=(ImageView) convertView.findViewById(R.id.bt_plus);
            gv.btp=btp;

            btm=(ImageView) convertView.findViewById(R.id.bt_menos);
            gv.btm=btm;

            convertView.setTag(gv);

        gv.t1.setText(objs.get(position).getNombre());

        //Tenemos dos TextView uno con todos los telefonos y otr con uno solo
        gv.t2.setText(objs.get(position).getPrimerTelefono());
        gv.t3.setText(objs.get(position).getAllTelefonos());

        //Creamos un oyente, donde se encargara de ir alternando los TetView e imagenes que mostrar
        addlistener(gv.btp, gv.btm, gv.t2, gv.t3);

        //Metodo que se encarga de verificar si tiene mas de un contacto para saber si mostrar la imagen de + o no
        tieneMasDeUno(gv.btp, gv.btm,position);

        return convertView;
    }

    private void addlistener(final ImageView btp,final ImageView btm,final TextView t2,final TextView t3){
        btp.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //Si el boton + esta visible, lo cambia al boton -, y pasa de mostrar uno a todos los numeros
                   if (btp.getVisibility() == View.VISIBLE) {
                       btp.setVisibility(View.GONE);
                       btm.setVisibility(View.VISIBLE);
                       t3.setVisibility(View.VISIBLE);
                       t2.setVisibility(View.GONE);
                   }
               }

           }


        );
        btm.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //Si el boton - esta visible, lo cambia por el boton +, y pasa de mostrar todos los contactos a uno solo
                   if (btm.getVisibility() == View.VISIBLE) {
                       btm.setVisibility(View.GONE);
                       btp.setVisibility(View.VISIBLE);
                       t2.setVisibility(View.VISIBLE);
                       t3.setVisibility(View.GONE);
                   }
               }

           }
        );
                 notifyDataSetChanged();
    }


    public void tieneMasDeUno(ImageView btp, ImageView btm,int position){
        //Conprueba los telefonos del contacto, caso de ser uno, oculta las imagenes de + y -
        if(objs.get(position).getTelf().size()==1){
            btm.setVisibility(View.GONE);
            btp.setVisibility(View.GONE);
        }
    }

    static class ViewHolder {
        public TextView t1;
        public TextView t2;
        public TextView t3;
        public ImageView btp,btm;
    }
}
