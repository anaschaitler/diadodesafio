package br.edu.utfpr.diadodesafio.connection;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServerConnection {

    //É necessário ajustar o endereco de IP de acordo com o que a sua máquina está utilizando
    private static final String API_BASE_URL = "http://172.30.14.221:8084/diadodesafio/";

    private static Retrofit retrofit ;
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static String obterJson (String url) {

        String json = "";

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);

            InputStream in = httpclient.execute(request).getEntity().getContent();

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            br = new BufferedReader(new InputStreamReader(in));

            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();

            }

            json = sb.toString();

        } catch (Exception e){
            e.printStackTrace();
            Log.e("ERRO", "Falha ao acessar Web Service. Verifique seu endereço IP!", e);
        }

        return json;
    }
}
