package com.example.pierre.tp_pplessy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


//Au lancement de l'application:
/*
    ça passe par create et resume
 */
//Changer orientation
/*
    ça met en pause, puis sauvegarde l'instance. Ensuite il détruit l'instance
    puis créer une nouvelle instance et recupère l'état de la derniere instance et reprend l'activité.
 */
//Fermez l'application
/*
    Pause puis sauvegarde de l'instance et destruit l'instance
 */
//application arrière plan
/*
    Mise en pause de l'insance et sauvegarde de l'instance
 */
//Reload
/*
    Mise en pause, sauvegarde de l'instance, destruction et au redémarrage creation et reprise de l'activité
 */
public class MainActivity extends AppCompatActivity {
    TextView login;
    TextView pwd;
    CheckBox box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("ON Create", "Création de l'activité");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        login = findViewById(R.id.textName);
        pwd = findViewById(R.id.textPassword);
        box = findViewById(R.id.checkBoxRemember);

        //test si il y a déjà des preférences
        if (!TextUtils.isEmpty(prefs.getString("login", "default")) && !TextUtils.isEmpty(prefs.getString("password", "default"))) {
            login.setText(prefs.getString("login", ""));
            pwd.setText(prefs.getString("password", ""));
        }

        Button send = findViewById(R.id.buttonSend);
        Button empty = findViewById(R.id.buttonEmpty);

        send.setOnClickListener(genericOnClickListener);
        empty.setOnClickListener(genericOnClickListener);
    }

    final View.OnClickListener genericOnClickListener = new View.OnClickListener(){
        public void  onClick(final View v){
            switch(v.getId()){
                case R.id.buttonSend:
                    if(TextUtils.isEmpty(login.getText())) {
                        login.setError("Ce champs est requis");
                    }

                    if(TextUtils.isEmpty(pwd.getText())) {
                        pwd.setError("Ce champs est requis");
                    }

                    if(!TextUtils.isEmpty(login.getText()) && !TextUtils.isEmpty(pwd.getText())) {
                        Boolean checkBox = box.isChecked();
                        //regarde si le boutton est cocher et créer une ref le cas échéant
                        if(checkBox) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            prefs.edit()
                                    .putString("login", login.getText().toString())
                                    .putString("password", pwd.getText().toString())
                                    .apply();
                        }
                        else {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            prefs.edit()
                                    .remove("login")
                                    .remove("password")
                                    .apply();
                        }

                        new Authentification().execute(login.getText().toString(), pwd.getText().toString());
                    }
                    break;
                case R.id.buttonEmpty:
                    login.setText(null);
                    pwd.setText(null);
                    break;
                default:
                    break;
            }
        }
    };

    class Authentification extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;

            String content = "";

            try {
                url = new URL("http://10.69.0.71:8081/authorization?login=" + strings[0] + "&mdp=" + strings[1]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                StringBuilder sb = new StringBuilder(); // +=

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((content = reader.readLine()) != null) {
                    sb.append(content);
                }

                content = sb.toString();

            }
            catch (Exception ex) {
                Log.e("MON ERREUR", "Erreur lors de l'accès au WS", ex);
                //TOAS...
            }

            publishProgress(content);

            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            if ("true".equals(values[0])) {
                Intent intent = new Intent(MainActivity.this, ListEcoleActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Utilisateur non authorisé", Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("ON Destroy", "Arrête de l'activité");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i("ON Pause", "Activité en pause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("ON Resume", "Reprise de l'activité");
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("ON Save Instance", "Sauvegarde de l'état de l'activité");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("ON Restaure instance", "Restoration de l'activité au dernier état sauvegardé");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
