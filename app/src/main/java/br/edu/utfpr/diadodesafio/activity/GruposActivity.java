package br.edu.utfpr.diadodesafio.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.edu.utfpr.diadodesafio.R;

public class GruposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);
    }

    public void btNovoGrupoOnClick(View view) {
        startActivity(new Intent(this, NovoGrupoActivity.class));
    }

    public void btMostrarGruposOnClick(View view) {
        startActivity(new Intent(this, MostrarGruposActivity.class));
    }
}
