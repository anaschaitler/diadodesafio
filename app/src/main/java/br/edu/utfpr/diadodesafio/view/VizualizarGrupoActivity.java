package br.edu.utfpr.diadodesafio.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;

public class VizualizarGrupoActivity extends AppCompatActivity {

    private SQLiteDatabase bd;
    private Cursor grupos;
    private TextView tvId;
    private TextView tvNomeGrupo;
    private TextView tvMedia;
    private TextView tvClassificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_grupo);

        bd = DatabaseConnection.getConnection(this);
        this.grupos = bd.rawQuery("SELECT * FROM grupo", null);

        tvId = (TextView) findViewById(R.id.tvId);
        tvNomeGrupo = (TextView) findViewById(R.id.tvNomeGrupo);
        tvMedia = (TextView) findViewById(R.id.tvMedia);
        tvClassificacao = (TextView) findViewById(R.id.tvClassificacao);

        int qtdeReg = grupos.getCount();
        visualizar(qtdeReg);
    }

    private void visualizar(int position){
        grupos.moveToLast();
        tvId.setText(String.valueOf(grupos.getInt(grupos.getColumnIndex("_id"))));
        tvNomeGrupo.setText(grupos.getString(grupos.getColumnIndex("nome")));
    }
}
