package com.example.memoriaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    private static  final String []  PERMISOS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ACCESO a los 3 tipos de memoria
        //Externo público
        ActivityCompat.requestPermissions(this, PERMISOS, 100);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //String ruta_f = getFilesDir().getPath()+"/hola.txt";//PRIVADÍSIMO "MEMORIA INTERNA"(nadie puede tocar y si borro app, se van) data/data/miapp
        String ruta_f = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath()+"/hola.txt";//PUBLICA_privada (sí pueden tocar otras apps, y si borro app, se eliminan) sdcard/Android/data/miapp
        //String ruta_f = Environment.getExternalStorageDirectory().getPath()+"/hola.txt";//PUBLICA total (sí pueden tocar otras apps, si borro app, siguen los datos) sdcard/
        informaEspacioEnDisco();
        try {
            File f = new File(ruta_f);
            if (f.createNewFile()){
                Log.d("MIAPP", "se pudo crear el fichero");
                FileWriter fw = new FileWriter(f);
                fw.append("HOLAAAAA");
                fw.close();

            }else {
                Log.d("MIAPP", "no se pudo crear el fichero false" );
            }
        }catch (Exception e) {
            Log.e("MIAPP", "Error al crear el fichero ", e );
        }
    }

    private void informaEspacioEnDisco ()
    {
        StatFs stat = null;
        float megabytes_disponibles = 0;
        String ruta = null;

        ruta = Environment.getExternalStorageDirectory().getPath();//==getExternalStoragePublicDirectory
        stat = new StatFs(ruta);
        megabytes_disponibles = ((long)stat.getBlockSize() * (long)stat.getAvailableBlocks()) / (1024.f * 1024.f);
        Log.d("MIAPP", "MEGABYTES DISPONIBLES EN MEM EXTERNA PÚBLICA /SDCARD/ " + megabytes_disponibles);

        ruta = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath()+"/..";
        stat = new StatFs(ruta);
        megabytes_disponibles = ((long)stat.getBlockSize() * (long)stat.getAvailableBlocks()) / (1024.f * 1024.f);
        Log.d("MIAPP", "MEGABYTES DISPONIBLES EN MEM EXTERNA PRIVADA /SCARD/ANDROID/DATA/miAPP " + megabytes_disponibles);

        ruta = getFilesDir().getAbsolutePath();
        stat = new StatFs(ruta);
        megabytes_disponibles = ((long)stat.getBlockSize() * (long)stat.getAvailableBlocks()) / (1024.f * 1024.f);
        Log.d("MIAPP", "MEGABYTES DISPONIBLES EN MEM INTERNA /DATA/DATA/miAPP " + megabytes_disponibles);

    }
}
