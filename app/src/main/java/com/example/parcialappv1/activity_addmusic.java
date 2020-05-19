
package com.example.parcialappv1;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.parcialappv1.data.AsyncGetCanciones;
import com.example.parcialappv1.data.cancion;

public class activity_addmusic extends AppCompatActivity {

    public static EditText mcancionEditText;
    public static EditText martistaEditText;
    public static EditText malbumEditText;
    public static EditText mduracionEditText;

    public static Activity myActivity;
    public String idPet;
    private Object Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmusic);

        mcancionEditText = (EditText) findViewById(R.id.cancion_id);
        martistaEditText = (EditText) findViewById(R.id.artistaid);
        malbumEditText = (EditText) findViewById(R.id.album_id);
        mduracionEditText = (EditText) findViewById(R.id.duracion_id);
        myActivity = this;
        Bundle intentExtra = getIntent().getExtras();

        if (intentExtra != null) {
             idPet = intentExtra.getString("id");
            getSupportActionBar().setTitle("Modificar cancion");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu mmenu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_item, mmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                    insertCanciones();
                finish();
                return true;
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertCanciones(){
        cancion track= new cancion();
        track.cancion = mcancionEditText.getText().toString();
        track.artista = martistaEditText.getText().toString();
        track.album = malbumEditText.getText().toString();
        track.duracion = mduracionEditText.getText().toString();

         activity_catalogo.cCancion.add(track);
    }

    public void ObtenerCancion(View view){
        TextView search_cancion = findViewById(R.id.search_cancion);
        String nombre_cancion = search_cancion.getText().toString();
        AsyncGetCanciones http = new AsyncGetCanciones(this, nombre_cancion);
        http.execute();
    }

}
