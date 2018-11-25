package br.edu.utfpr.diadodesafio.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.ConexaoDB;

public class VizualizarGrupoActivity extends AppCompatActivity {

    private SQLiteDatabase bd;
    private Cursor grupos;
    private TextView tvId;
    private TextView tvNomeGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_grupo);

        bd = ConexaoDB.getConnection(this);
        this.grupos = bd.query("grupo", null, null, null, null, null, null, null);

        tvId = (TextView) findViewById(R.id.tvId);
        tvNomeGrupo = (TextView) findViewById(R.id.tvNomeGrupo);

        int qtdeReg = grupos.getCount();
        visualizar(qtdeReg);
    }

    private void visualizar(int position){
        grupos.moveToLast();
        tvId.setText(String.valueOf(grupos.getInt(grupos.getColumnIndex("_id"))));
        tvNomeGrupo.setText(grupos.getString(grupos.getColumnIndex("nomeGrupo")));
    }
}
