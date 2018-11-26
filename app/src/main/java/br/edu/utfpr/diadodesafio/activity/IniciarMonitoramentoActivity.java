package br.edu.utfpr.diadodesafio.activity;

<<<<<<< HEAD
import android.location.Location;
import android.location.LocationListener;
import android.os.SystemClock;
=======
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
<<<<<<< HEAD
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.text.SimpleDateFormat;
=======

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab
import java.util.Date;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.ConexaoDB;
import br.edu.utfpr.diadodesafio.model.Monitoramento;
import br.edu.utfpr.diadodesafio.model.Usuario;

public class IniciarMonitoramentoActivity extends AppCompatActivity  implements SensorEventListener {

    private TextView tvNivelMovimento;
    private Chronometer chCronometro;
    private TextView tvNivelDeAtividadeDoMonitoramento;
    private Button btIniciarMonitoramento;

    public SQLiteDatabase bd;

    private Boolean click = false;
    private long segundos = 0;
    private float movTotal = 0;

    private Monitoramento monitoramento;

<<<<<<< HEAD
public class IniciarMonitoramentoActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private TextView tvNivelMovimento;
    private Chronometer chCronometro;
    private TextView tvNivelDeAtividadeDoMonitoramento;
    private Button btIniciarMonitoramento;

    private Boolean iniciar = false;
    private long segundos = 0;
    private float movTotal = 0;
    private float movAnt = 0;
    private double lat;
    private double lon;
    private Date data;
    private SimpleDateFormat formataData;
    private String dataFormatada;


    private SensorManager mSensorManager;

    private Sensor sensorAc;
=======
    private SensorManager mSensorManager;
>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab

    private Sensor sensorAc;

    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_monitoramento);

<<<<<<< HEAD
=======
        bd = ConexaoDB.getConnection(this);

>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab
        tvNivelMovimento = (TextView) findViewById(R.id.tvNivelMovimento);
        chCronometro = (Chronometer) findViewById(R.id.chCronometro);
        tvNivelDeAtividadeDoMonitoramento = (TextView) findViewById(R.id.tvNivelDeAtividadeDoMonitoramento);
        btIniciarMonitoramento = (Button) findViewById(R.id.btIniciarMonitoramento);

        mSensorManager=(SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensorAc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, sensorAc, SensorManager.SENSOR_DELAY_UI);

<<<<<<< HEAD
        iniciar = false;
        segundos = 0;
        movTotal = 0;
        data = new Date();
        formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


=======
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        user.getDisplayName();
>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab
        chCronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                segundos = (SystemClock.elapsedRealtime() - chCronometro.getBase()) / 1000;
                if(segundos%60==0){
<<<<<<< HEAD
                    dataFormatada = formataData.format(data);
                    //gravar atividade no banco (movTotal - movAnt) que foi o movimento realizado em 60 segundos
                    //gravar latitude e longitudo das variaves (lat, lon)
                    //gravar data variavel dataFormatada

                    //após a gravação
                    movAnt = movTotal;
=======
                    //gravar atividade no banco.
                    ContentValues registro = new ContentValues();
                    registro.put("localizacao", monitoramento.getLocalizacao());
                    registro.put("usuario_id", monitoramento.getUsuario().getId());
                    registro.put("data", monitoramento.getData());
                    registro.put("mediaMonitora", monitoramento.getMediaMonitora());
                    bd.insert("monitoramento", null, registro);
>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

<<<<<<< HEAD
        double calcMov = Math.sqrt((x*x)+(y*y)+(z*z));

        if(iniciar == true){
            movTotal += calcMov;

            //média movimento total desde o início do cronometro por segundo
            //if(segundos!=0){
            //    tvNivelMovimento.setText(String.valueOf(movTotal/segundos));
            //}
            //ou movimento do momento
            tvNivelMovimento.setText(String.valueOf(calcMov));
=======
        if(click == true){
            movTotal += Math.sqrt((x*x)+(y*y)+(z*z));

            //média movimento total desde o início do cronometro por segundo
            tvNivelMovimento.setText(String.valueOf(movTotal/segundos));
            //ou movimento do momento
            //tvNivelMovimento.setText(String.valueOf(Math.sqrt((x*x)+(y*y)+(z*z))));
>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab

            //Total da atividade desde que foi iniciado o cronometro
            tvNivelDeAtividadeDoMonitoramento.setText(String.valueOf(movTotal));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void btIniciarMonitoramentoOnClick(View view) {
<<<<<<< HEAD
        if(iniciar==false){
            btIniciarMonitoramento.setText("Parar o Monitoramento");
            movTotal = 0;
            chCronometro.setBase(SystemClock.elapsedRealtime());
            chCronometro.start();
            iniciar = true;
        }else{
            chCronometro.stop();
            btIniciarMonitoramento.setText("Iniciar o Monitoramento");
            //Gravar no banco movTotal - movAnt
            movAnt = 0;
            iniciar = false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

=======
        if(click==false){
            btIniciarMonitoramento.setText("Parar o Monitoramento");
            chCronometro.setBase(SystemClock.elapsedRealtime());
            chCronometro.start();
            click = true;
        }else{
            btIniciarMonitoramento.setText("Iniciar o Monitoramento");
            chCronometro.stop();
            click = false;
        }
>>>>>>> 989c729d59fd18f8f8baf1ceafd610261caae7ab
    }
}
