package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 19/08/2015.
 */
public class Noticia
{
    @SerializedName("id")
    public String id;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("fecha")
    public String fecha;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("urlimagen")
    public String urlImagen;

    @SerializedName("autor")
    public String autor;


}
