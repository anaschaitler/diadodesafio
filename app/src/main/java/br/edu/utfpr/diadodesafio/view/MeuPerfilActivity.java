package br.edu.utfpr.diadodesafio.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;


public class MeuPerfilActivity extends AppCompatActivity {


    private SQLiteDatabase bd;
    private Cursor usuario;
    private Cursor MediaMonitoramento;
   // private TextView tvId;
    private TextView tvNomeUsuario;
    private TextView tvNivelDeAtividadeMeuPerfil;
    private ListView lvMeusGrupos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        bd = DatabaseConnection.getConnection(this);


//        this.usuario = bd.query("usuario", null, null, null, null, null, null, null);

        //tvId = (TextView) findViewById(R.id.tvId);
        tvNomeUsuario = (TextView) findViewById(R.id.tvNomeUsuario);

        visualizar();
//        tvNivelDeAtividadeMeuPerfil = (TextView) findViewById(R.id.tvNivelDeAtividadeMeuPerfil);
//        lvMeusGrupos = (ListView)  findViewById(R.id.lvMeusGrupos);


//        int qtdeReg = usuario.getCount();
//        visualizar(qtdeReg);
    }

    private void visualizar() {
        Cursor registros = bd.query("usuario", new String[]{"nome"}, null, null, null, null, null);

        String id = registros.getString(registros.getColumnIndex("nome"));
        Cursor registrosgrupo = bd.query("grupo", new String[]{"nome"}, "_id=" + id, null, null, null, null);

        if (registros.moveToNext()) {

            tvNomeUsuario.setText(registros.getString(registros.getColumnIndex("nome")));
//          tvNivelDeAtividadeMeuPerfil.setText(String.valueOf(MediaMonitoramento.getDouble(MediaMonitoramento.getColumnIndex("mediaGeral"))));

        }

    }
}

