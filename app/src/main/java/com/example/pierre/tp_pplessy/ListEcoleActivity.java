package com.example.pierre.tp_pplessy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListEcoleActivity extends AppCompatActivity {
    ListView myListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ecole);

        new recoveryDataSchool().execute();

    }

    class recoveryDataSchool extends AsyncTask<String, String, String> {
        List<EcolePrimaire> listEcole = new ArrayList<>();
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;

            String content = "";

            try {
                //récupère les datas
                url = new URL("http://10.69.0.71:8081/getEcolePrimaire");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                StringBuilder sb = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((content = reader.readLine()) != null) {
                    sb.append(content);
                }

                content = sb.toString();
                Log.i("LIST", "Content " + content);
                //json les datas
                JSONArray ja = new JSONArray(content);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    EcolePrimaire ecolePrimaire = new EcolePrimaire();
                    ecolePrimaire.setAddresse(jo.getString("nom"));
                    ecolePrimaire.setAddresse(jo.getString("nbEleve"));
                    ecolePrimaire.setAddresse(jo.getString("addresse"));
                    listEcole.add(ecolePrimaire);
                }
            }
            catch (Exception ex) {
                Log.e("MON ERREUR", "Erreur lors de l'accès au WS", ex);
            }
            Log.i("LIST", "List of all school" + listEcole);

            onPostExecute(listEcole);
            return content;
        }

        protected void onPostExecute(List<EcolePrimaire> listEcole) {
//            super.onPostExecute();

            Log.i("LIST", "Create list");
            //chercher la listview
            myListView = findViewById(R.id.listView);

            //creation d'un adaptateur
            ListAdaptater adapter = new ListAdaptater(ListEcoleActivity.this, listEcole);

            //rajoute l'adaptateur à la vue list
            myListView.setAdapter(adapter);

        }
    }

}
