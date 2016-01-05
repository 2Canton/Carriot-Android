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
import com.nansoft.mipuribus.database.DbSQLiteOpenHelper;
import com.nansoft.mipuribus.model.CarreraRuta;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.parada;
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

    public boolean InsertarSitioSalida(parada pobjSitioSalida)
    {
        try
        {
            nuevoRegistro = new ContentValues();
            nuevoRegistro.put("IdSitioSalida", pobjSitioSalida.getId());
            nuevoRegistro.put("NombreSitioSalida", pobjSitioSalida.getNombre());

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

    public boolean InsertarCarrera(CarreraRuta pobjCarrera)
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

                    objRuta.setIdRuta(c.getString(0));
                    objRuta.setNombreRuta(c.getString(1));
                    objRuta.setCosto(c.getString(2));
                    objRuta.setIdEmpresa(c.getString(3));
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

                    objHorario.setIdHorario(c.getString(0));
                    objHorario.setDias(c.getString(1));

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

                objCarrera.setDescHora(c.getString(0));
                objCarrera.setNombreSitioSalida(c.getString(1));
                objCarrera.setNota(c.getString(2));

                listHorarios.add(objCarrera);
            }
            while(c.moveToNext());

            // ordenamos el arreglo
        }

        return listHorarios;
    }



}
