package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class SitioSalida 
{
	@SerializedName("idSitioSalida")
	private int IdSitioSalida;
	
	@SerializedName("nombreSitioSalida")
	private String NombreSitioSalida;

	public SitioSalida()
	{
		IdSitioSalida = 0;
		NombreSitioSalida = "Sin Definir";
	}
	/**
	 * @return the idSitioSalida
	 */
	public int getIdSitioSalida() 
	{
		return IdSitioSalida;
	}

	/**
	 * @param idSitioSalida the idSitioSalida to set
	 */
	public void setIdSitioSalida(int idSitioSalida) 
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
