package br.edu.utfpr.diadodesafio.service;

import java.util.List;

import br.edu.utfpr.diadodesafio.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("usuario/listjson")
    Call<List<Usuario>> getAll();

    @GET("usuario/{id}")
    Call<Usuario> getOne(@Path("id") Long id);

    @POST("usuario/savejson")
    Call<Void> insert(@Body Usuario usuario);

    @PUT("usuario/editjson")
    Call<Void> update(@Body Usuario usuario);

    @DELETE("usuario/{id}")
    Call<Void> delete(@Path("id") Long id);
}
