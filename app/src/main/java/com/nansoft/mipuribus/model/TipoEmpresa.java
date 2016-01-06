package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */

import com.google.gson.annotations.SerializedName;

public class TipoEmpresa {

    @SerializedName("id")
    public String id;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("urlimagen")
    public String urlImagen;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("cantidad_eventos")
    public int CantidadEventos;

    public TipoEmpresa(String id, String nombre, String urlimagen, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagen = urlimagen;
        this.descripcion = descripcion;
    }

}
