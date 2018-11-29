package br.edu.utfpr.diadodesafio.service;

import java.util.List;

import br.edu.utfpr.diadodesafio.model.UsuarioGrupo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioGrupoService {

    @GET("usuariogrupo/")
    Call<List<UsuarioGrupo>> getAll();

    @GET("usuariogrupo/{id}")
    Call<UsuarioGrupo> getOne(@Path("id") Long id);

    @POST("usuariogrupo/")
    Call<Void> insert(@Body UsuarioGrupo usuarioGrupo);

    @PUT("usuariogrupo/")
    Call<Void> update(@Body UsuarioGrupo usuarioGrupo);

    @DELETE("usuariogrupo/{id}")
    Call<Void> delete(@Path("id") Long id);
}
