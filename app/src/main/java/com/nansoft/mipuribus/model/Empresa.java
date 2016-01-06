package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
*/
import com.google.gson.annotations.SerializedName;

public class Empresa
{

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

    @SerializedName("direccion")
    public String direccion;

    @SerializedName("longitud")
    public double longitud;

    @SerializedName("latitud")
    public double latitud;

    @SerializedName("telefonoprincipal")
    public String telefonoPrincipal;

    @SerializedName("telefonosecundario")
    public String telefonoSecundario;

    @SerializedName("email")
    public String email;

    @SerializedName("web")
    public String web;

    @SerializedName("horario")
    public String horario;

    @SerializedName("idtipoempresa")
    public String idTipoEmpresa;

    @SerializedName("urlimagen")
    public String urlImagen;

    public Empresa(String id, String createdat, String updatedat, String version, String nombre, String direccion, double longitud, double latitud, String telefonoprincipal, String telefonosecundario, String email, String web, String horario, String idtipodeempresa, String urlimagen) {
        this.id = id;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.version = version;
        this.nombre = nombre;
        this.direccion = direccion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.telefonoPrincipal = telefonoprincipal;
        this.telefonoSecundario = telefonosecundario;
        this.email = email;
        this.web = web;
        this.horario = horario;
        this.idTipoEmpresa = idtipodeempresa;
        this.urlImagen = urlimagen;
    }



}
