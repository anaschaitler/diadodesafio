package br.edu.utfpr.diadodesafio.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;
import br.edu.utfpr.diadodesafio.model.Grupo;
import br.edu.utfpr.diadodesafio.service.GrupoService;
import br.edu.utfpr.diadodesafio.service.ServiceGenerator;
import retrofit2.Call;

public class NovoGrupoActivity extends AppCompatActivity {

    public SQLiteDatabase bd;
    private EditText etNomeGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_grupo);

        bd = DatabaseConnection.getConnection(this);
        etNomeGrupo = (EditText) findViewById(R.id.etNomeGrupo);
    }

    public void btSalvarGrupoOnClick(View view) {
        ContentValues grupo = new ContentValues();
        grupo.put("nomeGrupo", etNomeGrupo.getText().toString());
        bd.insert("grupo", null, grupo);
        Toast.makeText(this, "Grupo incluído com sucesso!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
