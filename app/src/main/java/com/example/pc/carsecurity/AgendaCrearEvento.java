package com.example.pc.carsecurity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class AgendaCrearEvento extends AppCompatActivity implements View.OnClickListener {
    private Button bfecha, bhora, bguardar,batras;
    private EditText etitulo, eubicacion, enotas;
    private EditText efecha, ehora;
    private int dia, mes, anio, horas, minutos;
    Calendar calendario;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_agenda_crear_evento);

        etitulo = (EditText) findViewById(R.id.etxtnombre);
        efecha = (EditText) findViewById(R.id.txtfecha);
        ehora = (EditText) findViewById(R.id.etxthora);
        eubicacion = (EditText) findViewById(R.id.etxtUbicación);
        enotas = (EditText) findViewById(R.id.etxtnotas);

        bhora = (Button) findViewById(R.id.btnhora);
        bguardar = (Button) findViewById(R.id.btnguardar);
        bfecha = (Button) findViewById(R.id.btnfecha);
        batras=(Button)findViewById(R.id.btnatras);


        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);
        bguardar.setOnClickListener(this);
        batras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == bfecha) {
            calendario = Calendar.getInstance();
            anio = calendario.get(Calendar.YEAR);
            mes = calendario.get(Calendar.MONTH);
            dia = calendario.get(Calendar.DAY_OF_MONTH);


            datePickerDialog = new DatePickerDialog(AgendaCrearEvento.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    efecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    , dia, mes, anio);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
            efecha.setError(null);
        } else if (v == bhora) {
            final Calendar calendario = Calendar.getInstance();
            horas = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    String minutos=null;
                    minutos= String.valueOf(minute);
                    if(minute<10){
                        minutos="0" + minute;
                    }
                    ehora.setText(hourOfDay + ":" + minutos);
                }
            }
                    , horas, minutos, true);


            timePickerDialog.show();
            ehora.setError(null);
        } else if (v == bguardar) {
            guardarDatos();
        }else if (v==batras){
            finish();
        }
    }

    public void guardarDatos() {
        String titulo, fecha, hora, ubicacion, notas;
        if (etitulo.getText().toString().equals("")) {
            etitulo.setError("Introduce el titulo");
            etitulo.requestFocus();
        }else if(efecha.getText().toString().equals("")) {
            efecha.setError("Introduce la fecha");
            efecha.requestFocus();
        }else if(ehora.getText().toString().equals("")) {
            ehora.setError("Introduce la hora");
            ehora.requestFocus();
        }else if(eubicacion.getText().toString().equals("")) {
            eubicacion.setError("Introduce la ubicación");
            eubicacion.requestFocus();
        }else {
            titulo = etitulo.getText().toString();
            fecha = efecha.getText().toString();
            hora = ehora.getText().toString();
            ubicacion = eubicacion.getText().toString();
            notas = enotas.getText().toString();
            try {
                BufferedWriter bw =
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        openFileOutput("agenda_int.txt", MODE_APPEND)));
                bw.write(titulo + " " + fecha + " " + hora + " " + ubicacion + " " + notas);
                bw.newLine();
                bw.close();
                Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(AgendaCrearEvento.this, Agenda.class);
                startActivity(intento);


            } catch (FileNotFoundException e) {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}