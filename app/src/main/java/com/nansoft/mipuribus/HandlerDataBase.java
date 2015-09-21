package com.nansoft.mipuribus;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nansoft.mipuribus.activity.HorariosActivity;
import com.nansoft.mipuribus.activity.RutasActivity;
import com.nansoft.mipuribus.model.Carrera;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Ruta;
import com.nansoft.mipuribus.model.SitioSalida;

public class HandlerDataBase 
{
	public static SQLiteDatabase db;
	private ContentValues nuevoRegistro;
	private DBSQLiteOpenHelper usdbh;
	private Context contexto;
	
	public HandlerDataBase(Context pContexto)
	{
		contexto = pContexto;
		//Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new DBSQLiteOpenHelper(contexto, "DBBusPurisco", null,2);
 
        db = usdbh.getWritableDatabase();
          
	}
	
	public boolean InsertarSitioSalida(SitioSalida pobjSitioSalida)
	{
		try
		{
			nuevoRegistro = new ContentValues();
        	nuevoRegistro.put("IdSitioSalida", pobjSitioSalida.getIdSitioSalida());
        	nuevoRegistro.put("NombreSitioSalida", pobjSitioSalida.getNombreSitioSalida());
        	
        	//db.beginTransaction();
        	//Insertamos el registro en la base de datos
        	db.insert("SitioSalida", null, nuevoRegistro);
        	//db.setTransactionSuccessful();
        	//db.endTransaction();
			return true;
		}
		catch (Exception e)
		{ 
			//db.endTransaction();
			return false;
		}
		
	}
	
	public boolean InsertarHorario(Horario pobjHorario)
	{
		try
		{
			nuevoRegistro = new ContentValues();
        	nuevoRegistro.put("IdHorario", pobjHorario.getIdHorario());
        	nuevoRegistro.put("Dias", pobjHorario.getDias());
        	
        	//db.beginTransaction();
        	//Insertamos el registro en la base de datos
        	db.insert("Horario", null, nuevoRegistro);
        	//db.setTransactionSuccessful();
        	//db.endTransaction();
			return true;
		}
		catch (Exception e)
		{ 
			//db.endTransaction();
			return false;
		}
	}
	
	public boolean InsertarRuta(Ruta pobjRuta)
	{
		try
		{
			nuevoRegistro = new ContentValues();
			nuevoRegistro.put("IdRuta", pobjRuta.getIdRuta());
        	nuevoRegistro.put("NombreRuta", pobjRuta.getNombreRuta());
        	nuevoRegistro.put("Costo", pobjRuta.getCosto());
            nuevoRegistro.put("IdEmpresa", pobjRuta.getIdEmpresa());
        	//db.beginTransaction();
        	//Insertamos el registro en la base de datos
        	db.insert("Ruta", null, nuevoRegistro);
        	//db.setTransactionSuccessful();
        	//db.endTransaction();
			return true;
		}
		catch (Exception e)
		{ 
			//db.endTransaction();
			return false;
		}
	}
	
	public boolean InsertarCarrera(Carrera pobjCarrera)
	{
		try
		{
			nuevoRegistro = new ContentValues();
        	nuevoRegistro.put("IdCarreraRuta", pobjCarrera.getIdCarrera());
        	nuevoRegistro.put("IdHorario", pobjCarrera.getIdHorario());
        	nuevoRegistro.put("IdRuta", pobjCarrera.getIdRuta());
        	nuevoRegistro.put("IdSitioSalida", pobjCarrera.getIdSitioSalida());
        	//nuevoRegistro.put("NombreSitioSalida", pobjCarrera.getNombreSitioSalida());
        	nuevoRegistro.put("DescHora", pobjCarrera.getDescHora());
        	nuevoRegistro.put("Nota", pobjCarrera.getNota());
        	
        	//db.beginTransaction();
        	//Insertamos el registro en la base de datos
        	db.insert("Carrera", null, nuevoRegistro);
        	//db.setTransactionSuccessful();
        	//db.endTransaction();
			return true;
		}
		catch (Exception e)
		{ 
			//db.endTransaction();
			return false;
		}
	}
	
	public boolean VerificarDatosRuta()
	{
		try
		{
			// realizamos una consulta a la base de datos y guardamos el resultado en un objeto de tipo Cursor
			Cursor c = db.rawQuery(" SELECT * FROM Ruta", null);
			
			
			// Veririficamos si la consulta devolvió al menos un resultado
			if (c.moveToFirst()) 
			{  	   
				return true;
			}

		}
		catch(Exception e)
		{
			
		}
		return false;
	}
	
	public boolean VerificarDatosCarrera()
	{
		try
		{
			// realizamos una consulta a la base de datos y guardamos el resultado en un objeto de tipo Cursor
			Cursor c = db.rawQuery(" SELECT * FROM Carrera", null);
			
			
			// Veririficamos si la consulta devolvió al menos un resultado
			if (c.moveToFirst()) 
			{  	   
				return true;
			}

		}
		catch(Exception e)
		{
			
		}
		return false;
	}
	
	
	public void CargarAdapter()
	{
		try
		{
			// realizamos una consulta a la base de datos y guardamos el resultado en un objeto de tipo Cursor
			Cursor c = db.rawQuery(" SELECT * FROM Ruta ORDER BY NombreRuta", null);
			Ruta objRuta;
			
			// Veririficamos si la consulta devolvio al menos un resultado
			if (c.moveToFirst()) 
			{  	   
				
				RutasActivity.mAdapter.clear();

				do
				{
					objRuta = new Ruta();

                    objRuta.setIdRuta(c.getInt(0));
                    objRuta.setNombreRuta(c.getString(1));
                    objRuta.setCosto(c.getString(2));
                    objRuta.setIdEmpresa(c.getInt(3));
                    RutasActivity.mAdapter.add(objRuta);

					
				}
				while(c.moveToNext());
				//MainActivity.swipeLayout.onRefreshComplete();
				RutasActivity.mSwipeRefreshLayout.setRefreshing(false);
			}
			else
			{
				RutasActivity objRutasActivity = new RutasActivity();
				objRutasActivity.VerificarEstadoAdapter();
			}
		}
		catch(Exception e)
		{
			//RutasActivity objRutasActivity = new RutasActivity();
			//objMainActivity.VerificarEstadoAdapter();
		}
	}
	
	public boolean CargarHorariosRuta(int pIdRuta)
	{
		try
		{
			// realizamos una consulta a la base de datos y guardamos el resultado en un objeto de tipo Cursor
			Cursor c = db.rawQuery(" SELECT DISTINCT H.IdHorario,H.Dias FROM Horario H,Carrera C WHERE H.IdHorario = C.IdHorario AND C.IdRuta = " + pIdRuta, null);
			Horario objHorario;
			
			// Veririficamos si la consulta devolvió al menos un resultado
			if (c.moveToFirst()) 
			{  	   

				HorariosActivity.mAdapter.clear();

				do
				{
					objHorario = new Horario();

                    objHorario.setIdHorario(c.getInt(0));
                    objHorario.setDias(c.getString(1));

                    HorariosActivity.mAdapter.add(objHorario);

				}
				while(c.moveToNext());
                HorariosActivity.mSwipeRefreshLayout.setRefreshing(false);
				return true;
			}

		}
		catch(Exception e)
		{
			
		}
		return false;
	}
	
	public ArrayList<Carrera>CargarHorario(int pIdRuta,int pIdHorario)
	{
		
		// realizamos una consulta a la base de datos y guardamos el resultado en un objeto de tipo Cursor
		Cursor c = db.rawQuery(" SELECT C.DescHora,S.NombreSitioSalida FROM Carrera C, SitioSalida S WHERE C.IdSitioSalida = S.IdSitioSalida AND C.IdRuta = " + pIdRuta + " AND C.IdHorario = " + pIdHorario, null);
		Carrera objCarrera;
		ArrayList <Carrera> listHorarios = new ArrayList<Carrera>();
		
		// Veririficamos si la consulta devolvió al menos un resultado
		if (c.moveToFirst()) 
		{  	   
			do
			{
				objCarrera = new Carrera();
				
				objCarrera.setDescHora(c.getString(0));
				objCarrera.setNombreSitioSalida(c.getString(1));
				
				
				listHorarios.add(objCarrera);
			}
			while(c.moveToNext());	
			
			// ordenamos el arreglo
		}
		
		return listHorarios;
	}
	

	
}
