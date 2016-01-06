package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */

import com.google.gson.annotations.SerializedName;

public class TipoEvento {

    @SerializedName("id")
    public String id;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("urlimagen")
    public String urlImagen;

    @SerializedName("cantidad_eventos")
    public int CantidadEventos;

    public TipoEvento(String id, String nombre, String urlimagen) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagen = urlimagen;
    }
}
