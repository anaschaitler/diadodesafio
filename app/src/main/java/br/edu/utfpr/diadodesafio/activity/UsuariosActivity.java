package br.edu.utfpr.diadodesafio.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.edu.utfpr.diadodesafio.adapter.AdapterUsuario;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;

public class UsuariosActivity extends AppCompatActivity {

    private SQLiteDatabase bd;
    private ListView lvUsuarios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        bd =  DatabaseConnection.getConnection(this);
        lvUsuarios = (ListView) findViewById(R.id.lvUsuarios);
        AdapterUsuario adapter = new AdapterUsuario (this, bd);
        lvUsuarios.setAdapter(adapter);


    }
}
