package com.example.parcialappv1.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.parcialappv1.activity_addmusic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AsyncGetCanciones extends AsyncTask<Void, Void , String> {

    private Context httpContext;
    ProgressDialog progressDialog;

    public String cancion_nombre;
    public cancion cancion = new cancion();

    public AsyncGetCanciones(Context httpContext, String cancion) {
        this.httpContext = httpContext;
        this.cancion_nombre = cancion;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "Canciones", "buscando cancion...");
    }

    @Override
    protected String doInBackground(Void... params) {
        String uriApi = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="+cancion_nombre+"&api_key=b284db959637031077380e7e2c6f2775&format=json";
        URL url = null;
            try {
                url = new URL(uriApi);
                HttpURLConnection ConnectApi = (HttpURLConnection) url.openConnection();


                ConnectApi.setReadTimeout(15000);
                ConnectApi.setConnectTimeout(15000);
                ConnectApi.setRequestProperty("Accept", "*/*");
                ConnectApi.setRequestMethod("GET");
                ConnectApi.setDoInput(true);
                ConnectApi.setDoOutput(true);

                OutputStream outputStream = ConnectApi.getOutputStream();
                outputStream.close();
                int responseCode=ConnectApi.getResponseCode();
                if(responseCode== HttpURLConnection.HTTP_OK){
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(ConnectApi.getInputStream()));
                    StringBuffer stringBuffer= new StringBuffer("");
                    String linea="";
                    while ((linea=bufferedReader.readLine())!= null){
                        stringBuffer.append(linea);
                        break;
                    }
                    bufferedReader.close();
                    String Datajson = "";
                    Datajson = stringBuffer.toString();
                    JSONObject jo = null;
                    jo = new JSONObject(Datajson);

                    JSONObject jsonTrack = null;
                    jsonTrack = jo.getJSONObject("results");
                    JSONObject json_trackmatches = jsonTrack.getJSONObject("trackmatches");

                    JSONArray array_canciones = json_trackmatches.getJSONArray("track");
                    for (int i=0; i<array_canciones.length();i++){

                        JSONObject DataJson = array_canciones.getJSONObject(i);

                        cancion.cancion = DataJson.getString("name");
                        cancion.artista = DataJson.getString("artist");
                        cancion.album = "not found" ;
                        cancion.duracion = "0";
                        return "succes";
                    }
                }
                else{
                    return "Error al buscar la informacion";
                }
            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return "Error Encontrado";
    }



    @Override
    protected void onPostExecute(String mensaje) {
        super.onPostExecute(mensaje);
        progressDialog.dismiss();
        if (mensaje.equals("succes")){
            activity_addmusic.myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity_addmusic.mcancionEditText.setText(cancion.getCancion());
                    activity_addmusic.martistaEditText.setText(cancion.getArtista());
                    activity_addmusic.malbumEditText.setText(cancion.getAlbum());
                    activity_addmusic.mduracionEditText.setText((cancion.getDuracion()));
                }
            });
        }else{
            Toast.makeText(httpContext,mensaje, Toast.LENGTH_LONG).show();
        }

    }
}
