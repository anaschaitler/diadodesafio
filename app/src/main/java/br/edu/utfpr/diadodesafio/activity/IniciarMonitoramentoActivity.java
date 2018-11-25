package br.edu.utfpr.diadodesafio.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import br.edu.utfpr.diadodesafio.R;

public class IniciarMonitoramentoActivity extends AppCompatActivity  implements SensorEventListener {

    private TextView tvNivelMovimento;
    private Chronometer chCronometro;
    private TextView tvNivelDeAtividadeDoMonitoramento;
    private Button btIniciarMonitoramento;

    private Boolean click = false;
    private long segundos = 0;
    private float movTotal = 0;

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

        chCronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                segundos = (SystemClock.elapsedRealtime() - chCronometro.getBase()) / 1000;
                if(segundos%60==0){
                    //gravar atividade no banco.
                    
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        if(click == true){
            movTotal += Math.sqrt((x*x)+(y*y)+(z*z));

            //média movimento total desde o início do cronometro por segundo
            tvNivelMovimento.setText(String.valueOf(movTotal/segundos));
            //ou movimento do momento
            //tvNivelMovimento.setText(String.valueOf(Math.sqrt((x*x)+(y*y)+(z*z))));

            //Total da atividade desde que foi iniciado o cronometro
            tvNivelDeAtividadeDoMonitoramento.setText(String.valueOf(movTotal));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void btIniciarMonitoramentoOnClick(View view) {
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
    }
}
