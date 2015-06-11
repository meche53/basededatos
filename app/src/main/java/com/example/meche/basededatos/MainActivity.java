package com.example.meche.basededatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    EditText id, nombre, apellidop, apellidom, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText)findViewById(R.id.id_A);
        nombre = (EditText) findViewById(R.id.nombre);
        apellidop = (EditText) findViewById(R.id.apellidop);
        apellidom = (EditText) findViewById(R.id.apellidom);
        telefono = (EditText)findViewById(R.id.telefono);
    }
    public void registrar (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String e_id = id.getText().toString();
        String e_nombre = nombre.getText().toString();
        String e_apellidop = apellidop.getText().toString();
        String e_apellidom = apellidom.getText().toString();
        String e_telefono = telefono.getText().toString();

        if (e_id.matches("")) {
            Toast.makeText(this, "ID vacio", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Cursor fila = bd.rawQuery("select nombre, apellido_p, apellido_m, telefono from alumnos where id=" + e_id, null);
            if (fila.getCount() >= 1) {
                Toast.makeText(this, "Ingrese un nuevo ID", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues registro = new ContentValues();
                registro.put("id", e_id);
                registro.put("nombre", e_nombre);
                registro.put("apellido_p", e_apellidop);
                registro.put("apellido_m", e_apellidom);
                registro.put("telefono", e_telefono);
                bd.insert("alumnos", null, registro);
                bd.close();
                id.setText("");
                nombre.setText("");
                apellidop.setText("");
                apellidom.setText("");
                telefono.setText("");
                id.requestFocus();
                Toast.makeText(this, "Registro agregado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String e_id = id.getText().toString();
        if (e_id.matches("")) {
            Toast.makeText(this, "ID vacio!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Cursor fila = bd.rawQuery("select id, nombre, apellido_p, apellido_m, telefono from alumnos where id=" + e_id, null);
            if (fila.moveToFirst()) {
                id.setText(fila.getString(0));
                nombre.setText(fila.getString(1));
                apellidop.setText(fila.getString(2));
                apellidom.setText(fila.getString(3));
                telefono.setText(fila.getString(4));
            } else {
                Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }
    }
    public void baja(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String e_id = id.getText().toString();
        if (e_id.matches("")) {
            Toast.makeText(this, "ID vacio!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int cant = bd.delete("alumnos", "id=" + e_id, null);
            bd.close();
            id.setText("");
            nombre.setText("");
            apellidop.setText("");
            apellidom.setText("");
            telefono.setText("");
            if (cant == 1) {
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                id.requestFocus();
            } else {
                Toast.makeText(this, "No hay registro, ingrese un ID diferente", Toast.LENGTH_SHORT).show();
                id.requestFocus();
            }
        }
    }
    public void modificacion (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "alumnos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String e_id = id.getText().toString();
        String e_nombre = nombre.getText().toString();
        String e_apellidop = apellidop.getText().toString();
        String e_apellidom = apellidom.getText().toString();
        String e_telefono = telefono.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("id", e_id);
        registro.put("nombre", e_nombre);
        registro.put("apellido_p", e_apellidop);
        registro.put("apellido_m", e_apellidom);
        registro.put("telefono", e_telefono);
        if (e_id.matches("")) {
            Toast.makeText(this, "ID vacio!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int cant = bd.update("alumnos", registro, "id=" + e_id, null);
            bd.close();
            if (cant == 1) {
                Toast.makeText(this, "Se actulizo el registro", Toast.LENGTH_SHORT).show();
                id.requestFocus();
            } else {
                Toast.makeText(this, "No hay registro, ingrese un ID diferente", Toast.LENGTH_SHORT).show();
                id.requestFocus();
            }
        }
    }

    public void limpia (View v){
        id.setText("");
        nombre.setText("");
        apellidop.setText("");
        apellidom.setText("");
        telefono.setText("");
        id.requestFocus();
    }
}
