package com.example.hv12.ejercicio_1_temporizador;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    TextView lblContador;
    Button btniIniciar;
    ContadorAsincrono contador;
    EditText segundos;
    ProgressBar progressB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblContador = findViewById(R.id.lblContador);
        btniIniciar = findViewById(R.id.btnIniciar);
        segundos =findViewById(R.id.Cantidad);
        progressB=findViewById(R.id.progress);

        btniIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniciarContador();
            }
        });
    }

    private int valores(){
        String numero = segundos.getText().toString();
        int segu = Integer.parseInt(numero);


   return segu;
    }

    private void iniciarContador() {

        int seg=0;
        String numero = segundos.getText().toString();
        if(!numero.isEmpty()){
            seg = Integer.parseInt(numero);
        }else {
            Toast.makeText(this,"Ingrese una cantidad",Toast.LENGTH_LONG).show();
        }

            /**si es primera vez -> se inicia **/
            if (contador == null) {
                contador = new ContadorAsincrono(this,progressB, lblContador,segundos);
                contador.execute(seg);
                btniIniciar.setText("Pausa");

                /**si ha terminado de ejecutar el hilo -> se crea otro hilo **/
            } else if (contador.getStatus() == AsyncTask.Status.FINISHED) {
                contador = new ContadorAsincrono(this,progressB, lblContador,segundos);
                contador.execute(seg);
                btniIniciar.setText("Pausa");
                /** si esta ejecutado y no esta pausado -> entonces se pausa**/
            } else if (contador.getStatus() == AsyncTask.Status.RUNNING && !contador.esPausa()) {
                contador.pausarContador();
                btniIniciar.setText("Reanudar");
                /** si no entro en las condiciones anteriores por defecto esta pausado -> se reanuda*/
            } else {
                contador.reanudarContador();
                btniIniciar.setText("Pausa");

            }

    }
}
