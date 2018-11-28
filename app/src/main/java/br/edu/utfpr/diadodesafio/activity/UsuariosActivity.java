package br.edu.utfpr.diadodesafio.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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
        //lvUsuarios = (ListView) findViewById(R.id.lvUsuarios);
        //AdapterUsuarios adapter = new AdapterUsuarios (this, bd);
        //lvUsuarios.setAdapter(adapter);
    }
}
