package br.edu.utfpr.diadodesafio.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.edu.utfpr.diadodesafio.R;

public class AdapterUsuario extends BaseAdapter {


    private final Context c;
    private SQLiteDatabase bd;
    private Cursor usuario;
    LayoutInflater inflater;

    public AdapterUsuario(Context c, SQLiteDatabase bd) {
        this.c = c;
        this.bd = bd;
        this.usuario = bd.query("usuario", null, null, null, null, null, null, null);
        this.inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return usuario.getCount();
    }

    @Override
    public Object getItem(int i) {
        usuario.moveToPosition(i);
        return usuario;
    }

    @Override
    public long getItemId(int i) {
        usuario.moveToPosition(i);
        int id = usuario.getInt(usuario.getColumnIndex("id"));
        return id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.activity_meu_perfil, null);

        TextView tvId = (TextView) v.findViewById(R.id.tvId);
        TextView tvUsuario = (TextView) v.findViewById(R.id.tvNomeUsuario);

        usuario.moveToPosition(i);

        final String id = String.valueOf(usuario.getInt(usuario.getColumnIndex("_id")));
        String nome = usuario.getString(usuario.getColumnIndex("nome"));

        tvId.setText(id);
        tvUsuario.setText(nome);

        return v;
    }
}
