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

public class AdapterGrupos extends BaseAdapter {

    private final Context c;
    private SQLiteDatabase bd;
    private Cursor grupos;
    LayoutInflater inflater;

    public AdapterGrupos(Context c, SQLiteDatabase bd) {
        this.c = c;
        this.bd = bd;
        this.grupos = bd.query("grupo", null, null, null, null, null, null, null);
        this.inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return grupos.getCount();
    }

    @Override
    public Object getItem(int i) {
        grupos.moveToPosition(i);
        return grupos;
    }

    @Override
    public long getItemId(int i) {
        grupos.moveToPosition(i);
        int id = grupos.getInt(grupos.getColumnIndex("id"));
        return id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = inflater.inflate(R.layout.activity_vizualizar_grupo, null);

        TextView tvId = (TextView) v.findViewById(R.id.tvId);
        TextView tvNomeGrupo = (TextView) v.findViewById(R.id.tvNomeGrupo);

        grupos.moveToPosition(i);

        final String id = String.valueOf(grupos.getInt(grupos.getColumnIndex("_id")));
        String nomeGrupo = grupos.getString(grupos.getColumnIndex("nomeGrupo"));

        tvId.setText(id);
        tvNomeGrupo.setText(nomeGrupo);

        return v;
    }
 }
