package br.edu.utfpr.diadodesafio.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;
import br.edu.utfpr.diadodesafio.adapter.AdapterGrupos;

public class MostrarGruposActivity extends AppCompatActivity {

    private SQLiteDatabase bd;
    private ListView lvMostrarGrupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_grupos);

        bd =  DatabaseConnection.getConnection(this);
        lvMostrarGrupos = (ListView) findViewById(R.id.lvMostrarGrupos);
        AdapterGrupos adapter = new AdapterGrupos(this, bd);
        lvMostrarGrupos.setAdapter(adapter);
    }
}
