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

import java.text.DecimalFormat;
import java.util.List;

public class MedirActivity extends ActionBarActivity implements View.OnClickListener, SensorEventListener{

    private SensorManager mSensorManager;
    //Sensor accelerometer;
    Sensor magnetometer;
    private float   mMagneticValues[] = new float[3];
    private TextView txtX;
    private TextView txtY;
    private TextView txtZ;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medir);

        txtX = (TextView) findViewById(R.id.txtX);
        txtY = (TextView) findViewById(R.id.txtY);
        txtZ = (TextView) findViewById(R.id.txtZ);
        txtTotal = (TextView) findViewById(R.id.txtTotal);

        View boton = findViewById(R.id.btnMeasure);
        boton.setOnClickListener(this);

    }

    public void onClick(View vista) {

        if(vista.getId()==findViewById(R.id.btnMeasure).getId()){
            Alerta("Sending data....");

        }
    }

    public void Alerta(String texto){
        AlertDialog alerta;

        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("Alerta");
        alerta.setMessage(texto);
        alerta.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)    {

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

        float tmpTotal;

        this.txtX.setText(String.valueOf(event.values[0]));
        this.txtY.setText(String.valueOf(event.values[1]));
        this.txtZ.setText(String.valueOf(event.values[2]));

        tmpTotal = (event.values[0] * event.values[0]) + (event.values[1] * event.values[1]) + (event.values[2] * event.values[2]);
        double total = Math.sqrt((double) tmpTotal);
        DecimalFormat formateador = new DecimalFormat("######.######");

        this.txtTotal.setText(formateador.format (total));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
