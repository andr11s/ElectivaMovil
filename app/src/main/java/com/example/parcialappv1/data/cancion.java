package com.example.parcialappv1.data;

public class cancion {
    public String cancion;
    public String artista;
    public String album;
    public String duracion;

    public cancion(String cancion, String artista, String album, String duracion) {
        this.cancion = cancion;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
    }

    public cancion() {
    }

    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
