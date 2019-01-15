package com.example.pc.carsecurity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity {
    private Button btnagenda;
    private Button btnpedido;
    private Button btnpartner;
    private Button btninforme;
    private Button btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu_principal);
        btnagenda=(Button) findViewById(R.id.btnagenda);
        btnpedido=(Button)findViewById(R.id.btnpedido);
        btnpartner=(Button)findViewById(R.id.btnpartner);
        btninforme=(Button)findViewById(R.id.btninforme);
        btnsalir=(Button)findViewById(R.id.btnsalir);
        btnagenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarAgenda();
            }
        });
        btnpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lanzarPedido();
            }
        });
        btnpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarPartner();
            }
        });
        btninforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarInforme();
            }
        });
        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarSalir();
            }
        });

    }

    public void lanzarAgenda(){
        Intent intento= new Intent(MenuPrincipal.this,Agenda.class);
        startActivity(intento);
    }
    public void lanzarPedido(){
        Intent intento=new Intent(MenuPrincipal.this, Pedidos.class);
        startActivity(intento);
    }
    public void lanzarPartner(){
        Intent intento=new Intent(MenuPrincipal.this, Partners.class);
        startActivity(intento);
    }
    public void lanzarInforme(){
        Intent intento=new Intent(MenuPrincipal.this, Informes.class);
        startActivity(intento);
    }
    public void lanzarSalir(){
        finishAffinity();
    }
}