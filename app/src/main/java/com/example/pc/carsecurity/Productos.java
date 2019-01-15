package com.example.pc.carsecurity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

public class Productos {

    private String nombre, descripcion, precio;
    private int imagen;

    public Productos(String nom, String descr, String prec, int imagen){
        nombre=nom;
        descripcion=descr;
        precio=prec;
        this.imagen=imagen;
    }

    public int getImagen(){
        return imagen;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getPrecio(){
        return precio;
    }

}
