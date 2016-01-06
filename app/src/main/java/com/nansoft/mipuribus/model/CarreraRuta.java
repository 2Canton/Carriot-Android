package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class CarreraRuta
{
		  
    @SerializedName("id")
	public String id;

	@SerializedName("__version")
	public String version;

	@SerializedName("idruta")
	public String idRuta;

	@SerializedName("idsitiosalida")
	public String idSitioSalida;

	public String nombreSitioSalida;

	@SerializedName("idhorario")
	public String idHorario;

	@SerializedName("hora")
	public String descHora;
	
	@SerializedName("nota")
	public String nota;

}
