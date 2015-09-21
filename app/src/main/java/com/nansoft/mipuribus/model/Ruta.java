package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class Ruta 
{
	@SerializedName("idRuta")
	private int IdRuta;
	
	@SerializedName("nombreRuta")
	private String NombreRuta;
	
	@SerializedName("costo")
	private String Costo;

    @SerializedName("idEmpresa")
    private int IdEmpresa;



    /**
	 * @return the idRuta
	 */
	public int getIdRuta() 
	{
		return IdRuta;
	}

	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(int idRuta) 
	{
		IdRuta = idRuta;
	}

	/**
	 * @return the nombreRuta
	 */
	public String getNombreRuta() 
	{
		return NombreRuta;
	}

	/**
	 * @param nombreRuta the nombreRuta to set
	 */
	public void setNombreRuta(String nombreRuta) 
	{
		NombreRuta = nombreRuta;
	}

	/**
	 * @return the costo
	 */
	public String getCosto() 
	{
		return Costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(String costo) 
	{
		Costo = costo;
	}

    /**
     * @return the idEmpresa
     */
    public int getIdEmpresa()
    {
        return IdEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(int idEmpresa)
    {
        IdEmpresa = idEmpresa;
    }
	
}
