package com.example.dam.practica1add.datos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2dam on 14/10/2015.
 */
public class Persona implements Serializable,Comparable<Persona>, Parcelable {
    private long Id;
    private String Nombre;
    private List<String> telf;

    public Persona() {
        this(0,"",new ArrayList<String>());
    }

    public Persona(long id,String nombre){
        this.Id=id;
        this.Nombre=nombre;
    }

    public Persona(long id, String nombre, List<String> telf) {
        this.Id = id;
        this.Nombre = nombre;
        this.telf = telf;
    }

    protected Persona(Parcel in) {
        Id = in.readLong();
        Nombre = in.readString();
        telf = in.createStringArrayList();
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public List<String> getTelf() {
        return telf;
    }

    public void setTelf(List<String> telf) {
        this.telf = telf;
    }

    /*Metodos delegados*/
    public String getTelefono(int position) {
        return telf.get(position);
    }

    public String getPrimerTelefono() {
        return telf.get(0);
    }

    public String getAllTelefonos(){
        String result="";
        for(String tel: telf){
            result+=tel+"\n";
        }
        return result;
    }

    public void setTelefono(List<String> telefono) {
        telf=telefono;
    }

    public int size() {
        return telf.size();
    }

    public boolean isEmpty() {
        return telf.isEmpty();
    }

    public boolean addTelefono(String object) {
        return telf.add(object);
    }

    /**/
    @Override
    public String toString() {
        return "persona{" +
                "telf=" + telf +
                ", nombre='" + Nombre + '\'' +
                ", id=" + Id +
                "}\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persona persona = (Persona) o;

        return Id == persona.Id;

    }

    @Override
    public int hashCode() {
        return (int) (Id ^ (Id >>> 32));
    }

    @Override
    public int compareTo(Persona persona) {
        int r =this.Nombre.compareToIgnoreCase(persona.Nombre);
        if(r==0){
            r= (int) (this.Id -persona.Id);
        }
        return r;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Nombre);
        dest.writeStringList(telf);
    }
}
