package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class Carrera 
{
		  
    @SerializedName("idCarreraRuta")
	private int IdCarrera;

	@SerializedName("idRuta")
	private int IdRuta;

	@SerializedName("idSitioSalida")
	private int IdSitioSalida;
	
	@SerializedName("nombreSitioSalida")
	private String NombreSitioSalida;

	@SerializedName("idHorario")
	private int IdHorario;

	@SerializedName("descHora")
	private String DescHora;
	
	@SerializedName("nota")
	private String Nota;

	/**
	 * @return the idCarrera
	 */
	public int getIdCarrera() 
	{
		return IdCarrera;
	}

	/**
	 * @param idCarrera the idCarrera to set
	 */
	public void setIdCarrera(int idCarrera) 
	{
		IdCarrera = idCarrera;
	}

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
	 * @return the idHorario
	 */
	public int getIdHorario() 
	{
		return IdHorario;
	}

	/**
	 * @param idHorario the idHorario to set
	 */
	public void setIdHorario(int idHorario) 
	{
		IdHorario = idHorario;
	}

	/**
	 * @return the descHora
	 */
	public String getDescHora() 
	{
		return DescHora;
	}

	/**
	 * @param descHora the descHora to set
	 */
	public void setDescHora(String descHora) 
	{
		DescHora = descHora;
	}

	/**
	 * @return the nota
	 */
	public String getNota() 
	{
		return Nota;
	}

	/**
	 * @param nota the nota to set
	 */
	public void setNota(String nota) 
	{
		Nota = nota;
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
