package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */
import com.google.gson.annotations.SerializedName;

public class Evento {

    @SerializedName("id")
    private String id;

    @SerializedName("createdat")
    private String createdat;

    @SerializedName("updatedat")
    private String updatedat;

    @SerializedName("version")
    private String version;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("costo")
    private String costo;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("hora")
    private String hora;

    @SerializedName("urlimagen")
    private String urlimagen;

    @SerializedName("idtipoevento")
    private String idtipoevento;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("visible")
    private boolean visible;


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
        this.urlimagen = urlimagen;
        this.idtipoevento = idtipoevento;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public String getIdtipoevento() {
        return idtipoevento;
    }

    public void setIdtipoevento(String idtipoevento) {
        this.idtipoevento = idtipoevento;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
