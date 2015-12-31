package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */

import com.google.gson.annotations.SerializedName;

public class TipoEvento {

    @SerializedName("id")
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("urlimagen")
    private String urlimagen;

    @SerializedName("cantidad_eventos")
    public int CantidadEventos;

    public TipoEvento(String id, String nombre, String urlimagen) {
        this.id = id;
        this.nombre = nombre;
        this.urlimagen = urlimagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }
}
