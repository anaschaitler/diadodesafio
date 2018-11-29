package br.edu.utfpr.diadodesafio.service;

import java.util.List;

import br.edu.utfpr.diadodesafio.model.Monitoramento;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MonitoramentoService {

    @GET("monitoramento/")
    Call<List<Monitoramento>> getAll();

    @GET("monitoramento/{id}")
    Call<Monitoramento> getOne(@Path("id") Long id);

    @POST("monitoramento/")
    Call<Void> insert(@Body Monitoramento monitoramento);

    @PUT("monitoramento/")
    Call<Void> update(@Body Monitoramento monitoramento);

    @DELETE("monitoramento/{id}")
    Call<Void> delete(@Path("id") Long id);
}
