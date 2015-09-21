package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */
import com.google.gson.annotations.SerializedName;
public class paradaRuta {


    @SerializedName("id")
    private String id;

    @SerializedName("createdat")
    private String createdat;

    @SerializedName("updatedat")
    private String updatedat;

    @SerializedName("version")
    private String version;

    @SerializedName("idparada")
    private String idparada;

    @SerializedName("idruta")
    private String idtura;

    @SerializedName("costo")
    private String costo;

    @SerializedName("orden")
    private int orden;

    public paradaRuta(String id, String createdat, String updatedat, String version, String idparada, String idtura, String costo, int orden) {
        this.id = id;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.version = version;
        this.idparada = idparada;
        this.idtura = idtura;
        this.costo = costo;
        this.orden = orden;
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

    public String getIdparada() {
        return idparada;
    }

    public void setIdparada(String idparada) {
        this.idparada = idparada;
    }

    public String getIdtura() {
        return idtura;
    }

    public void setIdtura(String idtura) {
        this.idtura = idtura;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
