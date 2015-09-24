package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class SitioSalida 
{
	@SerializedName("id")
	private String IdSitioSalida;
	
	@SerializedName("nombre")
	private String NombreSitioSalida;

	public SitioSalida()
	{
		IdSitioSalida = "0";
		NombreSitioSalida = "Sin Definir";
	}
	/**
	 * @return the idSitioSalida
	 */
	public String getIdSitioSalida()
	{
		return IdSitioSalida;
	}

	/**
	 * @param idSitioSalida the idSitioSalida to set
	 */
	public void setIdSitioSalida(String idSitioSalida)
	{
		IdSitioSalida = idSitioSalida;
	}

	/**
	 * @return the nombreSitioSalida
	 */
	public String getNombreSitioSalida() 
	{
		return NombreSitioSalida;
	}

	/**
	 * @param nombreSitioSalida the nombreSitioSalida to set
	 */
	public void setNombreSitioSalida(String nombreSitioSalida) 
	{
		NombreSitioSalida = nombreSitioSalida;
	}
	
	
		  
}
