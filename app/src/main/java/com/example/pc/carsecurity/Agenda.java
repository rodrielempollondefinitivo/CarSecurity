package com.example.pc.carsecurity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Agenda extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private FloatingActionButton crearevento, volveraplicacion;
    private ArrayAdapter<String> arrayejemplo;
    private ListView citas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_agenda);
        arrayejemplo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        crearevento = (FloatingActionButton) findViewById(R.id.btncrearevento);
        volveraplicacion = (FloatingActionButton) findViewById(R.id.btnsalir);
        volveraplicacion.setImageResource(R.drawable.icono_atras);
        citas = (ListView) findViewById(R.id.listArray);
        citas.setOnItemClickListener(this);
        crearevento.setOnClickListener(this);
        volveraplicacion.setOnClickListener(this);
        mostrarCitas();
    }

    @Override

    public void onClick(View v) {
        if (v == crearevento) {
            Intent intento = new Intent(Agenda.this, AgendaCrearEvento.class);
            startActivity(intento);
        } else if (v == volveraplicacion) {
            finish();
        }

    }

    public void mostrarCitas() {
        try {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    openFileInput("agenda_int.txt")));

            String texto = fin.readLine();

            while (texto != null) {
                arrayejemplo.add(texto);
                texto = fin.readLine();
            }
            fin.close();
            citas.setAdapter(arrayejemplo);


        } catch (FileNotFoundException e) {
            Toast.makeText(this, "No Hay Citas", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, View view, final int i, final long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[2];
        items[0] = "Eliminar eventos";
        items[1] = "Cancelar";
        builder.setTitle("Seleccione una opción")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int w) {
                        if (w== 0) {
                            arrayejemplo.remove(arrayejemplo.getItem(i));
                            citas.setAdapter(arrayejemplo);

                            try {
                                BufferedWriter bw =
                                        new BufferedWriter(
                                                new OutputStreamWriter(
                                                        openFileOutput("agenda_int.txt", MODE_PRIVATE)));
                                for (int j = 0; j < arrayejemplo.getCount(); j++) {
                                    bw.write(arrayejemplo.getItem(j));
                                    bw.newLine();
                                }
                                mostrarCitas();
                                bw.close();
                            } catch (FileNotFoundException e) {
                                Toast.makeText(Agenda.this, "Error", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}