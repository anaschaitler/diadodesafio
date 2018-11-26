package br.edu.utfpr.diadodesafio.activity;

import android.location.Location;
import android.location.LocationListener;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.utfpr.diadodesafio.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_monitoramento);

        tvNivelMovimento = (TextView) findViewById(R.id.tvNivelMovimento);
        chCronometro = (Chronometer) findViewById(R.id.chCronometro);
        tvNivelDeAtividadeDoMonitoramento = (TextView) findViewById(R.id.tvNivelDeAtividadeDoMonitoramento);
        btIniciarMonitoramento = (Button) findViewById(R.id.btIniciarMonitoramento);

        mSensorManager=(SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensorAc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, sensorAc, SensorManager.SENSOR_DELAY_UI);

        iniciar = false;
        segundos = 0;
        movTotal = 0;
        data = new Date();
        formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        chCronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                segundos = (SystemClock.elapsedRealtime() - chCronometro.getBase()) / 1000;
                if(segundos%60==0){
                    dataFormatada = formataData.format(data);
                    //gravar atividade no banco (movTotal - movAnt) que foi o movimento realizado em 60 segundos
                    //gravar latitude e longitudo das variaves (lat, lon)
                    //gravar data variavel dataFormatada

                    //após a gravação
                    movAnt = movTotal;
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        double calcMov = Math.sqrt((x*x)+(y*y)+(z*z));

        if(iniciar == true){
            movTotal += calcMov;

            //média movimento total desde o início do cronometro por segundo
            //if(segundos!=0){
            //    tvNivelMovimento.setText(String.valueOf(movTotal/segundos));
            //}
            //ou movimento do momento
            tvNivelMovimento.setText(String.valueOf(calcMov));

            //Total da atividade desde que foi iniciado o cronometro
            tvNivelDeAtividadeDoMonitoramento.setText(String.valueOf(movTotal));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void btIniciarMonitoramentoOnClick(View view) {
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

    }
}
