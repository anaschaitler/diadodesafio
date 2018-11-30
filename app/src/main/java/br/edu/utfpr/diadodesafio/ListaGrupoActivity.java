package br.edu.utfpr.diadodesafio;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.utfpr.diadodesafio.model.Grupo;
import br.edu.utfpr.diadodesafio.service.GrupoService;
import br.edu.utfpr.diadodesafio.service.ServiceGenerator;
import retrofit2.Call;

public class ListaGrupoActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_grupo);

        setContentView(R.layout.activity_lista_grupo);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lista = (ListView) findViewById(R.id.listaC);

        FloatingActionButton btnCadastrar = (FloatingActionButton) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCadastroGrupo();
            }
        });
        carregarDados();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Grupo grupo = (Grupo) parent.getItemAtPosition(position);
                Intent intent = new Intent(ListaGrupoActivity.this, CadastroGrupoActivity.class);
                intent.putExtra("codigo", grupo.getId().toString());
                startActivity(intent);
            }
        });
    }

    public void abrirCadastroGrupo(){
        Intent intent = new Intent(this, CadastroGrupoActivity.class);
        startActivity(intent);
    }

    private void carregarDados(){

        GrupoService grupoService = ServiceGenerator.createService(GrupoService.class);

        Call<List<Grupo>> call = grupoService.getAll();

        try{
            List<Grupo> grupos = call.execute().body();
            if(grupos != null){
                ArrayAdapter<Grupo> adapter =
                        new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1,
                                grupos);
                lista.setAdapter(adapter);
            }else{
                Toast.makeText(this, "Nenhum registro encontrado!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
        }
    }
}
