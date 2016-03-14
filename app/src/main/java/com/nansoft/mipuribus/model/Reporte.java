package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by itadmin on 3/14/16.
 */
public class Reporte
{
    @SerializedName("id")
    public String id;

    @SerializedName("__version")
    public String version;

    @SerializedName("nombre")
    public String nombre;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("idtiporeporte")
    public String idtiporeporte;
}
