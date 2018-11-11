package br.edu.utfpr.diadodesafio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.edu.utfpr.diadodesafio.R;

public class MainActivity extends AppCompatActivity {

    private ListView lvPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvPrincipal = (ListView) findViewById(R.id.menuPrincipal);
        lvPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tratarMenuPrincipal(i);
            }
        });
    }

    private void tratarMenuPrincipal(int i) {
        if (i == 0){
            startActivity(new Intent(this, GruposActivity.class));

        } else if (i == 1){
            startActivity(new Intent(this, UsuariosActivity.class));

        } else if (i == 2){
            startActivity(new Intent(this, MeuPerfilActivity.class));

        } else if (i == 3){
            startActivity(new Intent(this, IniciarMonitoramentoActivity.class));

        }
    }
}
