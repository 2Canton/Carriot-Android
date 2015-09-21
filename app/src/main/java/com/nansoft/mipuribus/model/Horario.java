package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class Horario 
{
	@SerializedName("idHorario")
	private int IdHorario;
	
	@SerializedName("dias")
	private String Dias;

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
