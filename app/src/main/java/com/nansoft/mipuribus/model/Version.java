package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 24/09/2015.
 */
public class Version
{
    @SerializedName("version_remota")
    private int versionRemota;

    private int versionLocal;

    public Version(int pVersionLocal)
    {
        versionLocal = pVersionLocal;
    }

    public int getVersionRemota() {
        return versionRemota;
    }

    public void setVersionRemota(int versionRemota) {
        this.versionRemota = versionRemota;
    }

    public int getVersionLocal() {
        return versionLocal;
    }

    public void setVersionLocal(int versionLocal) {
        this.versionLocal = versionLocal;
    }

    public boolean estadoActualizarBaseDatos()
    {
        if(versionRemota != versionLocal)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}
