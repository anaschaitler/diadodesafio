package br.edu.utfpr.diadodesafio.view;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;
import br.edu.utfpr.diadodesafio.adapter.AdapterGrupos;

public class MostrarGruposActivity extends AppCompatActivity {

    private static SQLiteDatabase bd;
    private ListView lvMostrarGrupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_grupos);

        bd =  DatabaseConnection.getConnection(this);
        lvMostrarGrupos = (ListView) findViewById(R.id.lvMostrarGrupos);
        AdapterGrupos adapterGrupos = new AdapterGrupos(this, bd,
                "SELECT grupo._id, grupo.nome, sum(monitoramento.mediamonitora) / count(usuario._id) as media " +
                        "FROM grupo " +
                        "LEFT JOIN usuarioGrupo " +
                        " ON usuarioGrupo.grupo_id = grupo._id " +
                        "LEFT JOIN usuario " +
                        " ON usuario._id = usuarioGrupo.usuario_id " +
                        "LEFT JOIN monitoramento " +
                        " ON monitoramento.usuario_id = usuario._id " +
                        "GROUP BY grupo._id, grupo.nome " +
                        "ORDER BY media ");
        lvMostrarGrupos.setAdapter(adapterGrupos);
    }
}
