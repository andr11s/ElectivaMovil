package com.example.parcialappv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.parcialappv1.data.cancion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class activity_catalogo extends AppCompatActivity {
    public static  ArrayList<cancion> cCancion=new ArrayList<>();

    private RecyclerView mRecyclerView;
    private cancionListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_catalogo.this, activity_addmusic.class);
                startActivity(intent);
            }
        });

        new ListCanciones().execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_catalogo.this,  activity_addmusic.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu mmenu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menumsic_item, mmenu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_save:
               /// displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class ListCanciones extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(activity_catalogo.this, "Canciones", "Listando canciones...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            String uri = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=b284db959637031077380e7e2c6f2775&format=json";
            URL url = null;
            try {

                url = new URL(uri);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestProperty("Accept", "*/*");
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                os.close();

                int responseCode=urlConnection.getResponseCode();// conexion OK?
                if(responseCode== HttpURLConnection.HTTP_OK){
                    BufferedReader in= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuffer sb= new StringBuffer("");
                    String linea="";
                    while ((linea=in.readLine())!= null){
                        sb.append(linea);
                        break;
                    }
                    in.close();
                    String json = "";
                    json = sb.toString();
                    JSONObject jo = null;
                    jo = new JSONObject(json);

                    JSONObject jo_canciones = null;
                    jo_canciones = jo.getJSONObject("tracks");
                    JSONArray array_canciones = jo_canciones.getJSONArray("track");
                    for (int i=0; i<array_canciones.length();i++){
                        JSONObject r = array_canciones.getJSONObject(i);

                        String cancion  = r.getString("name");
                        JSONObject json_artista = r.getJSONObject("artist");
                        String artista = json_artista.getString("name");
                        String album = "Indefinido";
                        String duracion = String.valueOf(r.getInt("duration"));
                        cCancion.add(new cancion(cancion,artista,album,duracion));
                    }
                }
                else{
                    Toast.makeText(activity_catalogo.this,"Ocurrio un error al procesar la solicitud",Toast.LENGTH_LONG).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void mensaje) {
            super.onPostExecute(mensaje);
            progressDialog.dismiss();
                mRecyclerView = findViewById(R.id.recycler);
                mAdapter = new cancionListAdapter(activity_catalogo.this,cCancion);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity_catalogo.this));
            }
        }
}

