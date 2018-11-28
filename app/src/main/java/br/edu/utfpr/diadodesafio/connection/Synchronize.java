package br.edu.utfpr.diadodesafio.connection;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.edu.utfpr.diadodesafio.model.Grupo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Synchronize {

    //Receber Grupos do Servidor
    public static void synchronizeGrupos(final Context c){

        try{
            GrupoService grupoService = ServerConnection.createService(GrupoService.class);
            Call<List<Grupo>> call = grupoService.getAll();
            call.enqueue(new Callback<List<Grupo>>() {
                @Override
                public void onResponse(Call<List<Grupo>> call, Response<List<Grupo>> response) {
                    if (response.isSuccessful()) {
                        List<Grupo> grupos = response.body();
                        Log.i("GRUPOS", grupos.toString());

                    } else {
                        onFailure(call, null);
                    }
                }
                @Override
                public void onFailure(Call<List<Grupo>> call, Throwable t) {
                    Log.e("synchronizeGrupos", "Erro ao obter grupos", t);
                }
            });

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
