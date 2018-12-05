package br.edu.utfpr.diadodesafio.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;
import br.edu.utfpr.diadodesafio.model.Grupo;
import br.edu.utfpr.diadodesafio.service.GrupoService;
import br.edu.utfpr.diadodesafio.service.ServiceGenerator;
import retrofit2.Call;

public class CadastroGrupoActivity extends AppCompatActivity {

    public SQLiteDatabase bd;
    private TextView txtId;
    private TextView txtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_grupo);

        bd = DatabaseConnection.getConnection(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        txtId = (TextView) findViewById(R.id.edtId);
        txtDescricao = (TextView) findViewById(R.id.edtDescricao);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravarDados();
            }
        });


        if (this.getIntent().getStringExtra("codigo") != null){
            editar();
            btnSalvar.setText("Alterar");
            Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removerRegistro();
                }
            });
            btnExcluir.setVisibility(View.VISIBLE);
        }
    }

    private void removerRegistro() {

        try{
            Long codigo = Long.parseLong(this.getIntent().getStringExtra("codigo"));

            GrupoService grupoService = ServiceGenerator.createService(GrupoService.class);
            Call<Void> call = grupoService.delete(codigo);
            call.execute().body();
            bd.delete("grupo", "_id=?", new String[] {codigo.toString()});
            //Toast.makeText(this, "Registro removido com sucesso!", Toast.LENGTH_SHORT);
            abrirListaGrupo();

        }catch(Exception ex){
            ex.printStackTrace();
            //Toast.makeText(this, "Erro ao remover registro!", Toast.LENGTH_SHORT);
        }

    }

    public void editar() {

        try {

            Long codigo = Long.parseLong(this.getIntent().getStringExtra("codigo"));

            GrupoService grupoService = ServiceGenerator.createService(GrupoService.class);
            Call<Grupo> call = grupoService.getOne(codigo);
            Grupo grupo = call.execute().body();
            txtId.setText(grupo.getId().toString());
            txtDescricao.setText(grupo.getNome());
            Call<Void> call1;
            call1 = grupoService.update(grupo);
            call1.execute().body();

            ContentValues grupoBD =  new ContentValues();
            grupoBD.put("_id", grupo.getId());
            grupoBD.put("nome", grupo.getNome());
            bd.update("grupo", grupoBD, "_id= ?", new String[] {codigo.toString()});

        }catch (Exception ex){
            ex.printStackTrace();
            //Toast.makeText(this, "Erro ao editar registro!", Toast.LENGTH_SHORT);
        }
    }

    public void gravarDados(){

        try {

            final Grupo grupo  = new Grupo();

            if (txtId.getText().toString() != "" &&
                    !txtId.getText().toString().isEmpty()) {
                grupo.setId(Long.parseLong(txtId.getText().toString()));
            }
            grupo.setNome(txtDescricao.getText().toString());
            final GrupoService grupoService = ServiceGenerator.createService(GrupoService.class);

            Call<Void> call;

            if(grupo.getId() == null){
                call = grupoService.insert(grupo);

            }else{
                call = grupoService.update(grupo);
            }
            call.execute().body();
            //Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
            abrirListaGrupo();

        }catch (Exception ex){
            ex.printStackTrace();
            //Toast.makeText(this, "Erro ao editar registro!", Toast.LENGTH_SHORT);
        }

    }

    public void abrirListaGrupo(){
        Intent intent = new Intent(this, ListaGrupoActivity.class);
        startActivity(intent);
        finish();
    }
}
