package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */

import com.google.gson.annotations.SerializedName;
public class Parada {

    @SerializedName("id")
    public String id;

    @SerializedName("__version")
    public String version;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("latitud")
    public double latitud;

    @SerializedName("longitud")
    public double longitud;

    public Parada(String id, String version, String nombre, double latitud, double longitud) {
        this.id = id;
        this.version = version;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }


}
