package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 03/04/2015.
 */
public class SitioInteres
{
    private int idSitioInteres;
    private String nombreSitioInteres;
    private String numeroTelefono;
    private String urlImagen;

    public SitioInteres(int idSitioInteres, String nombreSitioInteres, String numeroTelefono, String urlImagen) {
        this.idSitioInteres = idSitioInteres;
        this.nombreSitioInteres = nombreSitioInteres;
        this.numeroTelefono = numeroTelefono;
        this.urlImagen = urlImagen;
    }

    public int getIdSitioInteres() {
        return idSitioInteres;
    }

    public void setIdSitioInteres(int idSitioInteres) {
        this.idSitioInteres = idSitioInteres;
    }

    public String getNombreSitioInteres() {
        return nombreSitioInteres;
    }

    public void setNombreSitioInteres(String nombreSitioInteres) {
        this.nombreSitioInteres = nombreSitioInteres;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

}
