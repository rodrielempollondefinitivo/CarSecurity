package com.example.pc.carsecurity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PedidosCatalogo extends AppCompatActivity {

    private List<Productos>list;
    private Context context;
    ArrayAdapter<Productos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_catalogo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context=this;

        GridView listProductos=(GridView)findViewById(R.id.listaProductos);
        adapter=new ListaAdapter(PedidosCatalogo.this, getListProductos());
        listProductos.setAdapter(adapter);
    }

    //Se crea una lista adapter que extiende de un adaptador de arrays donde se adaptaran productos

    public class ListaAdapter extends ArrayAdapter<Productos> {

        //Lista de productos
        private final List<Productos> list;
        //Contexto
        private final Context context;

        // Constructor al que se les pasa un contexto de la clase y una lista de productos que se crea en el main class.
        // Adaptará los datos que le pasemos de la actividad principal (MainActivityCatalogo) por cada fila.
        // Se hace referencia al layout que contiene el DISEÑO que tendra el GridView
        public ListaAdapter(Context context, List<Productos> list){
            super(context, R.layout.activity_pedidos, list);
            this.context=context;
            this.list=list;
        }
        // Por cada producto que tengamos se inflará la lista
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=LayoutInflater.from(context);
            final View view=inflater.inflate(R.layout.activity_pedidos_catalogo, null);

            ImageView imagenProducto=(ImageView)view.findViewById(R.id.imagenProducto);
            TextView nombreProducto=(TextView)view.findViewById(R.id.textNombre);
            TextView descripcionProducto=(TextView)view.findViewById(R.id.textDescripcion);
            TextView precioProducto=(TextView)view.findViewById(R.id.textPrecio);


            imagenProducto.setImageResource(list.get(position).getImagen());
            nombreProducto.setText(list.get(position).getNombre());
            descripcionProducto.setText(list.get(position).getDescripcion());
            precioProducto.setText(list.get(position).getPrecio()+"€");

            return view;
        }
    }
    // Creamos un método que obtendrá las filas con los productos
    private List<Productos>getListProductos(){
        //Creamos una lista de Productos
        list=new ArrayList<Productos>();
        //Creamos 3 arrays
        String [] productos;
        String [] descripciones;
        String [] precios;

        //Se almacenarán en cada array los productos, descripciones y precios obtenidos desde el xml correspondiente a cada información
        productos=context.getResources().getStringArray(R.array.Productos);
        descripciones=context.getResources().getStringArray(R.array.Descripcion);
        precios=context.getResources().getStringArray(R.array.Precios);

        //Creamos un array de imagenes que obtendrá todas las imagenes
        int [] imagenes=new int[]{
                R.drawable.uno,
                R.drawable.dos,
                R.drawable.tres,
                R.drawable.cuatro
        };

        //Por cada producto se añadirá a la lista el nombre, descripcion, precio e imagen del mismo
        for (int i=0; i<productos.length; i++){
            list.add(get(productos[i], descripciones[i], precios[i], imagenes[i]));
        }
        return list;
    }

    private Productos get(String producto, String descripcion, String precio, int imagen){
        return new Productos(producto, descripcion, precio, imagen);
    }


}

