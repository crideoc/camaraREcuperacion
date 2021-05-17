package com.example.camara;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.CAMERA;

public class datos_foto extends AppCompatActivity {
    private static final int PERMISO_ESCRITURA = 1;
    private static final int CAMERA_REQUEST = 1;
    String RutaFoto;
    int CAMARA = 0;


    //Para mostrar foto
    ImageView imageView;

    //botones y demas
    Button btn_hacer_foto;
    Button btn_guardar;
    EditText comentario;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_foto);


        //declaramos botones y demas
        btn_hacer_foto = findViewById(R.id.btn_hacer_foto);
        btn_guardar = findViewById(R.id.btn_guardar);
        imageView = findViewById(R.id.imageView);
        comentario=findViewById(R.id.editTextTextComentario);

        btn_hacer_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permiso();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarDatos();
            }
        });

    }

    private void Permiso() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            HacerFoto();
        } else {
            ActivityCompat.requestPermissions
                    (this, new String[]{CAMERA}, PERMISO_ESCRITURA);
        }


    }


    private void ValidarDatos() {

        if (comentario.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Falta por agregar un comentario", Toast.LENGTH_SHORT).show();
        } else {
            if (CAMARA == 0) {
                Toast.makeText(this, "Falta por hacer la foto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Se ha realizado con exito", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void HacerFoto() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File ArchivoImagen = null;
            try {
                ArchivoImagen = CrearNombreFoto();
            } catch (IOException ex) {
                Log.i("info2", "no fue validado");
            }
            if (ArchivoImagen != null) {
                Uri fotoUri = FileProvider.getUriForFile(this, "com.example.camara.fileprovider", ArchivoImagen);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        } else {
            ActivityCompat.requestPermissions(datos_foto.this, new String[]{CAMERA}, 1);
        }

    }

    //Crear el nombre de nuestro fichero foto
    private File CrearNombreFoto() throws IOException {

        String fecha_hora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String NombreFoto = "photo" + fecha_hora;
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(NombreFoto, ".jpg", directorio);

        RutaFoto = imagen.getAbsolutePath();
        return imagen;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISO_ESCRITURA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                HacerFoto();
            } else {
                //permiso denegado
                Toast.makeText(this, "no tienes permisos para realizar una foto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                //Bundle extras=data.getExtras();
                //Bitmap imagenBitmap=(Bitmap)extras.get("data");
                Bitmap imagenBitmap = BitmapFactory.decodeFile(RutaFoto);
                CAMARA = 1;
                imageView.setImageBitmap(imagenBitmap);


            }
        }
    }
}