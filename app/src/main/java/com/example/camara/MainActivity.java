package com.example.camara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private Button btnCamara;
    private Button btnMostrar;
    private Button btnRadar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //declarar botones
        btnCamara=findViewById(R.id.btn_camara);
        btnMostrar=findViewById(R.id.btn_motrar);
        btnRadar=findViewById(R.id.btn_radar);


        //ventana para agregar una foto
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datos_foto = new Intent(MainActivity.this,datos_foto.class);
                startActivity(datos_foto);
            }
        });
        //ventana para mostrar las fotos
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_buscador=new Intent(MainActivity.this,activity_buscador.class);
                startActivity(activity_buscador);
            }
        });
        //ventana radar
        btnRadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_radar=new Intent(MainActivity.this,activity_radar.class);
                startActivity(activity_radar);
            }
        });

    }

    }


