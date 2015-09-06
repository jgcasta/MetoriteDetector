package es.astroide.metoritedetector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.List;

public class MedirActivity extends ActionBarActivity implements View.OnClickListener, SensorEventListener{

    private SensorManager mSensorManager;
    //Sensor accelerometer;
    Sensor magnetometer;
    private float   mMagneticValues[] = new float[3];
    private TextView txtMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medir);

        txtMeasure = (TextView) findViewById(R.id.txtMeasure);

        View boton = findViewById(R.id.btnMeasure);
        boton.setOnClickListener(this);

        //mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void onClick(View vista) {

        if(vista.getId()==findViewById(R.id.btnMeasure).getId()){
            Alerta("Estoy dentro");

        }
    }

    public void Alerta(String texto){
        AlertDialog alerta;

        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("Alerta");
        alerta.setMessage(texto);
        alerta.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected  void onResume(){

        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if (sensors.size() > 0){
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_FASTEST);
        }
    }
    protected  void onPause(){
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, magnetometer);
        super.onPause();

    }
    protected  void onStop(){
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, magnetometer);
        super.onStop();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.txtMeasure.setText("X = " + event.values[0]);



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
