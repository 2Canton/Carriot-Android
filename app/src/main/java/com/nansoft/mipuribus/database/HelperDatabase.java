package com.nansoft.mipuribus.database;

/**
 * Created by Carlos on 24/09/2015.
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nansoft.mipuribus.activity.HorariosActivity;
import com.nansoft.mipuribus.activity.RutasActivity;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Parada;
import com.nansoft.mipuribus.model.Ruta;

public class HelperDatabase
{
    public static SQLiteDatabase db;
    private ContentValues nuevoRegistro;
    private DbSQLiteOpenHelper usdbh;
    private Context contexto;

    public HelperDatabase(Context pContexto)
    {
        contexto = pContexto;
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new DbSQLiteOpenHelper(contexto, "DBBusPurisco", null,4);

        db = usdbh.getWritableDatabase();

    }

    public boolean InsertarSitioSalida(Parada pobjSitioSalida)
    {
        try
        {
            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("IdSitioSalida", pobjSitioSalida.id);
            nuevoRegistro.put("NombreSitioSalida", pobjSitioSalida.nombre);

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
            nuevoRegistro.put("IdHorario", pobjHorario.id);
            nuevoRegistro.put("Dias", pobjHorario.dias);

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
            nuevoRegistro.put("IdRuta", pobjRuta.id);
            nuevoRegistro.put("NombreRuta", pobjRuta.nombre);
            nuevoRegistro.put("Costo", pobjRuta.costo);
            nuevoRegistro.put("IdEmpresa", pobjRuta.idEmpresa);
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

    public boolean InsertarCarrera(CarreraRuta pobjCarrera)
    {
        try
        {
            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("IdCarreraRuta", pobjCarrera.id);
            nuevoRegistro.put("IdHorario", pobjCarrera.idHorario);
            nuevoRegistro.put("IdRuta", pobjCarrera.idRuta);
            nuevoRegistro.put("IdSitioSalida", pobjCarrera.idSitioSalida);
            //nuevoRegistro.put("NombreSitioSalida", pobjCarrera.getNombreSitioSalida());
            nuevoRegistro.put("DescHora", pobjCarrera.descHora);
            nuevoRegistro.put("Nota", pobjCarrera.nota);

            //db.beginTransaction();
            //Insertamos el registro en la base de datos
            db.insert("CarreraRuta", null, nuevoRegistro);
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
            Cursor c = db.rawQuery(" SELECT * FROM CarreraRuta", null);


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

                    objRuta.id = c.getString(0);
                    objRuta.nombre = c.getString(1);
                    objRuta.costo = c.getString(2);
                    objRuta.idEmpresa = c.getString(3);
                    RutasActivity.mAdapter.add(objRuta);
                    RutasActivity.mAdapter.notifyDataSetChanged();

                }
                while(c.moveToNext());
                //MainActivity.swipeLayout.onRefreshComplete();
                RutasActivity.mSwipeRefreshLayout.setRefreshing(false);
            }

        }
        catch(Exception e)
        {
            //RutasActivity objRutasActivity = new RutasActivity();
            //objMainActivity.VerificarEstadoAdapter();
        }
    }

    public boolean CargarHorariosRuta(String pIdRuta)
    {
        try
        {
            // realizamos una consulta a la base de datos y guardamos el resultado en un objeto de tipo Cursor
            Cursor c = db.rawQuery(" SELECT DISTINCT H.IdHorario,H.Dias FROM Horario H,CarreraRuta C WHERE H.IdHorario = C.IdHorario AND C.IdRuta = " + pIdRuta, null);
            Horario objHorario;

            // Veririficamos si la consulta devolvió al menos un resultado
            if (c.moveToFirst())
            {

                HorariosActivity.mAdapter.clear();

                do
                {
                    objHorario = new Horario();

                    objHorario.id = c.getString(0);
                    objHorario.dias = c.getString(1);

                    HorariosActivity.mAdapter.add(objHorario);

                }
                while(c.moveToNext());
                return true;
            }

        }
        catch(Exception e)
        {

        }
        return false;
    }

    public ArrayList<CarreraRuta>CargarHorario(String pIdRuta,String pIdHorario)
    {

        // realizamos una consulta a la base de datos y guardamos el resultado en un objeto de tipo Cursor
        Cursor c = db.rawQuery(" SELECT C.DescHora,S.NombreSitioSalida,C.Nota FROM CarreraRuta C, SitioSalida S WHERE C.IdSitioSalida = S.IdSitioSalida AND C.IdRuta = " + pIdRuta + " AND C.IdHorario = " + pIdHorario, null);
        CarreraRuta objCarrera;
        ArrayList <CarreraRuta> listHorarios = new ArrayList<CarreraRuta>();

        // Veririficamos si la consulta devolvió al menos un resultado
        if (c.moveToFirst())
        {
            do
            {
                objCarrera = new CarreraRuta();

                objCarrera.descHora = c.getString(0);
                objCarrera.nombreSitioSalida = c.getString(1);
                objCarrera.nota = c.getString(2);

                listHorarios.add(objCarrera);
            }
            while(c.moveToNext());

            // ordenamos el arreglo
        }

        return listHorarios;
    }



}
