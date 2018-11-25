package br.edu.utfpr.diadodesafio.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    private SensorManager mSensorManager;

    private Sensor sensorAc;

    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_monitoramento);

        bd = ConexaoDB.getConnection(this);

        tvNivelMovimento = (TextView) findViewById(R.id.tvNivelMovimento);
        chCronometro = (Chronometer) findViewById(R.id.chCronometro);
        tvNivelDeAtividadeDoMonitoramento = (TextView) findViewById(R.id.tvNivelDeAtividadeDoMonitoramento);
        btIniciarMonitoramento = (Button) findViewById(R.id.btIniciarMonitoramento);

        mSensorManager=(SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensorAc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, sensorAc, SensorManager.SENSOR_DELAY_UI);

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        user.getDisplayName();
        chCronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                segundos = (SystemClock.elapsedRealtime() - chCronometro.getBase()) / 1000;
                if(segundos%60==0){
                    //gravar atividade no banco.
                    ContentValues registro = new ContentValues();
                    registro.put("localizacao", monitoramento.getLocalizacao());
                    registro.put("usuario_id", monitoramento.getUsuario().getId());
                    registro.put("data", monitoramento.getData());
                    registro.put("mediaMonitora", monitoramento.getMediaMonitora());
                    bd.insert("monitoramento", null, registro);
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
