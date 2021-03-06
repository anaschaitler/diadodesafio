package br.edu.utfpr.diadodesafio.view;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.provider.Settings;
import android.content.pm.PackageManager;

import android.os.SystemClock;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.List;

import br.edu.utfpr.diadodesafio.R;
import br.edu.utfpr.diadodesafio.connection.DatabaseConnection;
import br.edu.utfpr.diadodesafio.model.Monitoramento;
import br.edu.utfpr.diadodesafio.model.Usuario;
import br.edu.utfpr.diadodesafio.service.MonitoramentoService;
import br.edu.utfpr.diadodesafio.service.ServiceGenerator;
import retrofit2.Call;

public class IniciarMonitoramentoActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private TextView txtId;
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
    private SQLiteDatabase bd;

    private SensorManager mSensorManager;

    private Sensor sensorAc;

    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_monitoramento);
		
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if ( ! lm.isProviderEnabled( LocationManager.NETWORK_PROVIDER ) ) {

            AlertDialog.Builder alerta = new AlertDialog.Builder( this );
            alerta.setTitle( "Atenção" );
            alerta.setMessage( "GPS não habilitado. Deseja Habilitar??" );
            alerta.setCancelable( false );
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                    startActivity( i );
                }
            } );
            alerta.setNegativeButton( "Cancelar", null );
            alerta.show();
        }

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);

        ActivityCompat.requestPermissions( this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1 );

        bd = DatabaseConnection.getConnection(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        txtId = (TextView) findViewById(R.id.txtId);
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
                    gravar();

                    gravarDados();

                    movAnt = movTotal;
                }
            }
        });
    }
	
	public void gravar(){
		dataFormatada = formataData.format(data);
		
		ContentValues registro = new ContentValues();
		registro.put("localizacao", String.valueOf(lat)+";"+String.valueOf(lon));
		registro.put("data", dataFormatada);
		registro.put("mediaMonitora", movTotal - movAnt);
		bd.insert("monitoramento", null, registro);
	}

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
		float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
		
		double calcMov = Math.sqrt((x*x)+(y*y)+(z*z));

        if(iniciar == true){
            movTotal += calcMov;

            tvNivelMovimento.setText(String.valueOf(calcMov));
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
            gravar();

            gravarDados();

            movAnt = 0;
            btIniciarMonitoramento.setText("Iniciar o Monitoramento");            
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

    public void gravarDados(){

        try {

            if (iniciar == true){
                final Monitoramento monitoramento  = new Monitoramento();

                if (txtId.getText().toString() != "" &&
                        !txtId.getText().toString().isEmpty()) {
                    monitoramento.setId(Long.parseLong(txtId.getText().toString()));
                }

                monitoramento.setLocalizacao(String.valueOf(lat)+";"+String.valueOf(lon));
                Usuario usuario = new Usuario(1L, "Admin", "admin@admin", "123");
                monitoramento.setUsuario(usuario);
                dataFormatada = formataData.format(data);
                monitoramento.setData(dataFormatada);
                monitoramento.setMediaMonitora(Double.parseDouble(String.valueOf(movTotal)) - Double.parseDouble(String.valueOf(movAnt)));

                final MonitoramentoService monitoramentoService = ServiceGenerator.createService(MonitoramentoService.class);

                Call<Void> call;

                if(monitoramento.getId() == null){
                    call = monitoramentoService.insert(monitoramento);

                }else{
                    call = monitoramentoService.update(monitoramento);
                }
                call.execute().body();
                //Toast.makeText(this, "Registro salvo com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("INICIAR == FALSE", "Não enviou ao servidor");
            }

        }catch (Exception ex){
            ex.printStackTrace();
            //Toast.makeText(this, "Erro ao editar registro!", Toast.LENGTH_SHORT);
        }
    }

    public void persistMonitoramento(Context c, List<Monitoramento> monitoramentos) {
        bd = DatabaseConnection.getConnection(c);
        try {

            for (Monitoramento monitoramento : monitoramentos) {

                dataFormatada = formataData.format(data);

                ContentValues monitoramentoBD = new ContentValues();
                monitoramentoBD.put("_id", monitoramento.getId());
                monitoramentoBD.put("localizacao", String.valueOf(lat) + ";" + String.valueOf(lon));
                monitoramentoBD.put("data", dataFormatada);
                monitoramentoBD.put("mediaMonitora", movTotal - movAnt);
                bd.insert("monitoramento", null, monitoramentoBD);
                Log.i("Monitoramento", monitoramentoBD.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarDados(){

        MonitoramentoService monitoramentoService = ServiceGenerator.createService(MonitoramentoService.class);

        Call<List<Monitoramento>> call = monitoramentoService.getAll();

        try{
            List<Monitoramento> monitoramentos = call.execute().body();
            if(monitoramentos != null){
                persistMonitoramento(this, monitoramentos);
            }else{
                //Toast.makeText(this, "Nenhum registro encontrado!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}