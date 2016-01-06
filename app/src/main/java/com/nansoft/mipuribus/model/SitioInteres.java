package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 03/04/2015.
 */
public class SitioInteres
{
    public int idSitioInteres;
    public String nombreSitioInteres;
    public String numeroTelefono;
    public String urlImagen;

    public SitioInteres(int idSitioInteres, String nombreSitioInteres, String numeroTelefono, String urlImagen) {
        this.idSitioInteres = idSitioInteres;
        this.nombreSitioInteres = nombreSitioInteres;
        this.numeroTelefono = numeroTelefono;
        this.urlImagen = urlImagen;
    }

}
