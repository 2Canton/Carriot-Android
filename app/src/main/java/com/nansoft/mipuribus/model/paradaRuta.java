package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */
import com.google.gson.annotations.SerializedName;
public class ParadaRuta {


    @SerializedName("id")
    public String id;

    @SerializedName("createdat")
    public String createdat;

    @SerializedName("updatedat")
    public String updatedat;

    @SerializedName("version")
    public String version;

    @SerializedName("idparada")
    public String idParada;

    @SerializedName("idruta")
    public String idRuta;

    @SerializedName("costo")
    public String costo;

    @SerializedName("orden")
    public int orden;

    public ParadaRuta(String id, String createdat, String updatedat, String version, String idparada, String idtura, String costo, int orden) {
        this.id = id;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.version = version;
        this.idParada = idparada;
        this.idRuta = idtura;
        this.costo = costo;
        this.orden = orden;
    }


}
