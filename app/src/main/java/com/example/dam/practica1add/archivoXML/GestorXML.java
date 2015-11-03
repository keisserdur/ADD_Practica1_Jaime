package com.example.dam.practica1add.archivoXML;

import android.content.Context;
import android.util.Xml;
import com.example.dam.practica1add.datos.Persona;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 01/11/2015.
 */
public class GestorXML {
    private Context c;

    public GestorXML(Context c){
        this.c=c;
    }

    public void sincronizar(List<Persona> lista,String nombre) throws IOException {

        FileOutputStream fosxml = new FileOutputStream(new File(c.getExternalFilesDir(null),nombre));
        XmlSerializer docxml = Xml.newSerializer();
        docxml.setOutput(fosxml, "UTF-8");
        docxml.startDocument(null, Boolean.valueOf(true));
        docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        docxml.startTag(null, "contactos");
        for (Persona s : lista) {
            docxml.startTag(null, "contacto");
            docxml.startTag(null, "nombre");
            docxml.attribute(null, "id", s.getId() + "\n");
            docxml.text(s.getNombre());
            docxml.endTag(null, "nombre");
            for(String telf: s.getTelf()){
                docxml.startTag(null, "telefono");
                docxml.text(telf);
                docxml.endTag(null, "telefono");
            }
            docxml.endTag(null, "contacto");
        }
        docxml.endDocument();
        docxml.flush();
        fosxml.close();
    }

    public List<Persona> sincronizar() throws IOException, XmlPullParserException {

        List<Persona> r=new ArrayList<Persona>();
        List<String> telf=new ArrayList<String>();
        StringBuilder aux=new StringBuilder();

        XmlPullParser lectorxml = Xml.newPullParser();
        lectorxml.setInput(new FileInputStream(new File(c.getExternalFilesDir(null), "total.xml")), "utf-8");
        int evento = lectorxml.getEventType();
        while (evento != XmlPullParser.END_DOCUMENT){
            if(evento == XmlPullParser.START_TAG){
                String etiqueta = lectorxml.getName();
                Persona p=new Persona();
                p.setNombre(lectorxml.getName());
                if(etiqueta.compareTo("nombre")==0){
                    String atrib = lectorxml.getAttributeValue(null, "id");
                    String texto = lectorxml.nextText();

                    p.setNombre(texto);
                    p.setId(Long.parseLong(atrib));

                    telf=new ArrayList<String>();

                } else if(etiqueta.compareTo("telefono")==0){
                    String texto = lectorxml.nextText();
                    telf.add(texto);
                    aux.append("telefono: " + texto + "\n");
                }
                p.setTelf(telf);
                r.add(p);
            }
            evento = lectorxml.next();
        }
        return r;
    }
}
