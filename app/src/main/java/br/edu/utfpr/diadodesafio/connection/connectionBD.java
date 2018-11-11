package br.edu.utfpr.diadodesafio.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class connectionBD {

        private static SQLiteDatabase BD;

        public static SQLiteDatabase getConnection (Context c){

            if (BD == null){
                BD = c.openOrCreateDatabase("BD", Context.MODE_PRIVATE, null);
                BD.execSQL(" CREATE TABLE IF NOT EXISTS grupo(_id INTEGER PRIMARY KEY AUTOINCREMENT, nomeGrupo TEXT)");
            }
            //db.execSQL("DROP TABLE grupo");
            return BD;
        }
}
