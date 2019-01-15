package com.example.pc.carsecurity;

import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import android.content.Intent;

import android.widget.CalendarView;

import android.view.View;
import android.widget.EditText;


import org.w3c.dom.Document;


import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import android.widget.ListView;


public class Informes extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);
    }

    public void volver(View v){
        finish();
    }

    public void enviarEmail(View v){
        ArrayList<Uri> uris = new ArrayList<Uri>();
        String[] filePaths = new String[] {"partners.xml", "pedidos.xml"};
        for (String file : filePaths)
        {
            File fileIn = new File(this.getExternalFilesDir(null),file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {"ejemplo@gmail.com"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Envio");
        startActivity(Intent.createChooser(emailIntent , "Enviar email..."));
    }

    public void abrirArchivoPartners(View v) throws IOException {
        String xmlPath = "partners.xml";
        File xmlFile = new File(this.getExternalFilesDir(null), xmlPath);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(xmlFile),"text/plain");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // no Activity to handle this kind of files
        }
    }

    public void abrirArchivoPedidos(View v) throws IOException {
        String xmlPath = "pedidos.xml";
        File xmlFile = new File(this.getExternalFilesDir(null), xmlPath);

        if(xmlFile.exists()==false) {
            xmlFile.createNewFile();
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(xmlFile),"text/plain");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // no Activity to handle this kind of files
        }
    }
}

