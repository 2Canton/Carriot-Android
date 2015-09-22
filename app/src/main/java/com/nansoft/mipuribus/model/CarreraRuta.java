package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class CarreraRuta
{
		  
    @SerializedName("id")
	private String IdCarrera;

	@SerializedName("idruta")
	private String IdRuta;

	@SerializedName("idsitiosalida")
	private String IdSitioSalida;
	
	@SerializedName("nombresitiosalida")
	private String NombreSitioSalida;

	@SerializedName("idhorario")
	private String IdHorario;

	@SerializedName("hora")
	private String DescHora;
	
	@SerializedName("nota")
	private String Nota;

	/**
	 * @return the idCarrera
	 */
	public String getIdCarrera()
	{
		return IdCarrera;
	}

	/**
	 * @param idCarrera the idCarrera to set
	 */
	public void setIdCarrera(String idCarrera)
	{
		IdCarrera = idCarrera;
	}

	/**
	 * @return the idRuta
	 */
	public String getIdRuta()
	{
		return IdRuta;
	}

	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(String idRuta)
	{
		IdRuta = idRuta;
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
	 * @return the idHorario
	 */
	public String getIdHorario()
	{
		return IdHorario;
	}

	/**
	 * @param idHorario the idHorario to set
	 */
	public void setIdHorario(String idHorario)
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
