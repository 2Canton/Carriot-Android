package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 22/07/2015.
 */
import com.google.gson.annotations.SerializedName;
public class eventoEmpresa {


    @SerializedName("id")
    private String id;

    @SerializedName("createdat")
    private String createdat;

    @SerializedName("updatedat")
    private String updatedat;

    @SerializedName("version")
    private String version;

    @SerializedName("idevento")
    private String idevento;

    @SerializedName("idempresa")
    private String idempresa;

    public eventoEmpresa(String id, String createdat, String updatedat, String version, String idevento, String idempresa) {
        this.id = id;
        this.createdat = createdat;
        this.updatedat = updatedat;
        this.version = version;
        this.idevento = idevento;
        this.idempresa = idempresa;
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

    public String getIdevento() {
        return idevento;
    }

    public void setIdevento(String idevento) {
        this.idevento = idevento;
    }

    public String getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(String idempresa) {
        this.idempresa = idempresa;
    }
}
