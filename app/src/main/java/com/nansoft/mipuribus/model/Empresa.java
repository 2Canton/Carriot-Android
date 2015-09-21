package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
*/
import com.google.gson.annotations.SerializedName;

public class Empresa
{

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

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("longitud")
    private double longitud;

    @SerializedName("latitud")
    private double latitud;

    @SerializedName("telefonoprincipal")
    private String telefonoprincipal;

    @SerializedName("telefonosecundario")
    private String telefonosecundario;

    @SerializedName("email")
    private String email;

    @SerializedName("web")
    private String web;

    @SerializedName("horario")
    private String horario;

    @SerializedName("idtipoempresa")
    private String idtipodeempresa;

    @SerializedName("urlimagen")
    private String urlimagen;

    public Empresa(String id, String createdat, String updatedat, String version, String nombre, String direccion, double longitud, double latitud, String telefonoprincipal, String telefonosecundario, String email, String web, String horario, String idtipodeempresa, String urlimagen) {
        this.id = id;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.version = version;
        this.nombre = nombre;
        this.direccion = direccion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.telefonoprincipal = telefonoprincipal;
        this.telefonosecundario = telefonosecundario;
        this.email = email;
        this.web = web;
        this.horario = horario;
        this.idtipodeempresa = idtipodeempresa;
        this.urlimagen = urlimagen;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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


    public String getTelefonoprincipal() {
        return telefonoprincipal;
    }

    public void setTelefonoprincipal(String telefonoprincipal) {
        this.telefonoprincipal = telefonoprincipal;
    }

    public String getTelefonosecundario() {
        return telefonosecundario;
    }

    public void setTelefonosecundario(String telefonosecundario) {
        this.telefonosecundario = telefonosecundario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getIdtipodeempresa() {
        return idtipodeempresa;
    }

    public void setIdtipodeempresa(String idtipodeempresa) {
        this.idtipodeempresa = idtipodeempresa;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

}
