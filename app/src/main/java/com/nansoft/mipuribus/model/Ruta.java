package com.nansoft.mipuribus.model;

import com.google.gson.annotations.SerializedName;

public class Ruta 
{
	@SerializedName("id")
	public String id;

	@SerializedName("__version")
	public String version;

	@SerializedName("nombre")
	public String nombre;
	
	@SerializedName("costo")
	public String costo;

    @SerializedName("idempresa")
	public String idEmpresa;

	
}
