package com.nansoft.mipuribus.model;

/**
 * Created by Carlos on 03/04/2015.
 */
public class Opcion
{
    private int idOpcion;
    private String nombreOpcion;
    private String urlImagen;

    public Opcion(int idOpcion, String nombreOpcion, String urlImagen) {
        this.idOpcion = idOpcion;
        this.nombreOpcion = nombreOpcion;
        this.urlImagen = urlImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }
}
