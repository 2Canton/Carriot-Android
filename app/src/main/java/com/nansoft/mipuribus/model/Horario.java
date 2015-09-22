package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class Horario 
{
	@SerializedName("id")
	private String IdHorario;
	
	@SerializedName("dias")
	private String Dias;

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
	 * @return the dias
	 */
	public String getDias() 
	{
		return Dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(String dias) 
	{
		Dias = dias;
	}
}
