package br.edu.utfpr.diadodesafio.service;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

//É necessário ajustar o endereco de IP de acordo com o que a sua máquina está utilizando. Por exemplo,
    private static final String API_BASE_URL = "http://172.30.9.71:8084/diadodesafio/";
//    private static final String API_BASE_URL = "http://192.168.100.5:8084/diadodesafio/";

    private static Retrofit retrofit ;
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
