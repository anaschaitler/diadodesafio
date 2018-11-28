package br.edu.utfpr.diadodesafio.connection;

import java.util.List;

import br.edu.utfpr.diadodesafio.model.MediaMonitoramento;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MediaMonitoramentoService {

    @GET("mediamonitoramento/")
    Call<List<MediaMonitoramento>> getAll();

    @GET("mediamonitoramento/{id}")
    Call<MediaMonitoramento> getOne(@Path("id") Long id);

    @POST("mediamonitoramento/")
    Call<Void> insert(@Body MediaMonitoramento mediaMonitoramento);

    @PUT("mediamonitoramento/")
    Call<Void> update(@Body MediaMonitoramento mediaMonitoramento);

    @DELETE("mediamonitoramento/{id}")
    Call<Void> delete(@Path("id") Long id);
}
