package br.edu.utfpr.diadodesafio.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnection {

    private static SQLiteDatabase BD;

    public static SQLiteDatabase getConnection(Context c) {

        //c.deleteDatabase("BD");

        if (BD == null) {

            BD = c.openOrCreateDatabase("BD", Context.MODE_PRIVATE, null);

            BD.execSQL(" CREATE TABLE IF NOT EXISTS grupo( _id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT)");
            BD.execSQL(" CREATE TABLE IF NOT EXISTS usuario( _id TEXT PRIMARY KEY, nome TEXT )");
            BD.execSQL(" CREATE TABLE IF NOT EXISTS " +
                    " usuarioGrupo( _id INTEGER PRIMARY KEY AUTOINCREMENT, usuario_id INTEGER, grupo_id INTEGER, " +
                    " FOREIGN KEY(usuario_id) REFERENCES usuario( _id), "+
                    " FOREIGN KEY(grupo_id) REFERENCES grupo( _id)) ");
            BD.execSQL(" CREATE TABLE IF NOT EXISTS monitoramento( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " localizacao TEXT, usuario_id INTEGER, data TEXT, mediaMonitora REAL, FOREIGN KEY (usuario_id) REFERENCES usuario( _id) )");
        }

        return BD;
    }
}
