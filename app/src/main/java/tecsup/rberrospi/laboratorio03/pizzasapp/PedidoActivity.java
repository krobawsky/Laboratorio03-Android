package tecsup.rberrospi.laboratorio03.pizzasapp;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class PedidoActivity extends AppCompatActivity {

    private Spinner spinner1;

    private RadioGroup radioGroup;
    private TextView displayText;
    private String masa;

    private CheckBox checkBox;
    private int precioTipo;
    private int precioExtra;
    private int precioTotal;
    private int precio1;
    private int precio2;

    private TextView direc;
    private Notification notification;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        radioGroup =  (RadioGroup) findViewById(R.id.radioGroup);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        direc = (EditText) findViewById(R.id.direc);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(parent.getContext(),
                        "Haz seleccionado: " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    public void radioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // This check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton1:
                if (checked){
                    masa = "masa gruesa";
                }
                    //Do something when radio button is clicked
                    Toast.makeText(getApplicationContext(), "Escogío masa gruesa.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton2:
                 masa = "masa delgaga";
                //Do something when radio button is clicked
                Toast.makeText(getApplicationContext(), "Escogío masa delgaga.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton3:
                masa = "masa artesanal";
                //Do something when radio button is clicked
                Toast.makeText(getApplicationContext(), "Escogío masa artesanal.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void androidCheckBoxClicked(View view) {
        CheckBox checkBox1;
        CheckBox checkBox2;
        String resu="";
        switch (view.getId()) {
            case R.id.checkBox:
                checkBox1 = (CheckBox) view;
                checkBox2 = (CheckBox) view;
                if(checkBox1.isChecked()==false) {
                    precio1 = 0;
                }else if(checkBox1.isChecked()==true ) {
                    precio1 = 8;
                }
                break;
            case R.id.checkBox2:
                checkBox1 = (CheckBox) view;
                checkBox2 = (CheckBox) view;
                if(checkBox2.isChecked()==false) {
                    precio2 = 0;
                }else if(checkBox2.isChecked()==true ) {
                    precio2 = 10;
                }
                break;
        }

    }


    public void Ordenar(View view){

        String item = (String)spinner1.getSelectedItem();
        String direccion = direc.getText().toString();
        precioExtra = precio1 + precio2;

        if(item.equalsIgnoreCase("Americana")){
            precioTipo = 40;
        }else if(item.equalsIgnoreCase("Meet lower")){
            precioTipo = 60;
        }else if(item.equalsIgnoreCase("Hawaiana")){
            precioTipo = 45;
        }else if(item.equalsIgnoreCase("Super Suprema")){
            precioTipo = 65;
        }

        precioTotal = precioTipo + precioExtra;

        if(masa == null || direccion == null){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Ingrese el tipo de masa.");
            // Alert dialog button
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Alert dialog action goes here
                            dialog.dismiss();// use dismiss to cancel alert dialog
                        }
                    });
            alertDialog.show();
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Confirmación de pedido");
            alertDialog.setMessage("Su pedido es una pizza "+ item + " con " + masa + ", el precio del pedído es: " +precioTotal+ " y se enviará a la siguiente dirección: "+direccion);
            // Alert dialog button
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Alert dialog action goes here
                            dialog.dismiss();// use dismiss to cancel alert dialog
                        }
                    });
            alertDialog.show();

            Intent intent = new Intent(this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Calendar now = Calendar.getInstance();
            int second = now.get(Calendar.SECOND);

            // Notification
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Pizzas Steve")
                    .setContentText("Su pedido esta en camino.")
                    .setSmallIcon(R.drawable.bg_pizza)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            new CountDownTimer(10000, 1000) {
                public void onFinish() {
                    // Notification manager

                    notificationManager.notify(0, notification);
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();



        }
    }

}
