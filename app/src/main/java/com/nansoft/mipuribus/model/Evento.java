package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */
import com.google.gson.annotations.SerializedName;

public class Evento {

    @SerializedName("id")
    public String id;

    @SerializedName("createdat")
    public String createdat;

    @SerializedName("updatedat")
    public String updatedat;

    @SerializedName("version")
    public String version;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("costo")
    public String costo;

    @SerializedName("fecha")
    public String fecha;

    @SerializedName("hora")
    public String hora;

    @SerializedName("urlimagen")
    public String urlImagen;

    @SerializedName("idtipoevento")
    public String idTipoEvento;

    @SerializedName("longitud")
    public double longitud;

    @SerializedName("latitud")
    public double latitud;

    @SerializedName("visible")
    public boolean visible;


    public Evento(String id, String createdat, String updatedat, String version, String nombre, String descripcion, String costo, String fecha, String hora, String urlimagen, String idtipoevento, double longitud, double latitud) {
        this.id = id;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.version = version;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.fecha = fecha;
        this.hora = hora;
        this.urlImagen = urlimagen;
        this.idTipoEvento = idtipoevento;
        this.longitud = longitud;
        this.latitud = latitud;
    }
}
