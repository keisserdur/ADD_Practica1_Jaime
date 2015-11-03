package com.example.dam.practica1add.datos;

import java.util.Comparator;

/**
 * Created by Admin on 18/10/2015.
 */
public class OrdenInverso implements Comparator<Persona> {
    @Override
    public int compare(Persona lhs, Persona rhs) {
        //Ordena los valores que recibe de forma inversa a lo habitual
        int r =lhs.getNombre().compareToIgnoreCase(rhs.getNombre());
        if(r==0){
            r= (int) (lhs.getId() -lhs.getId());
        }else if(r<0){
            r=1;
        }else{
            r=-1;
        }

        return r;
    }
}
