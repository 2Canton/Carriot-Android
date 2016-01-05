package com.nansoft.mipuribus.database;

/**
 * Created by Carlos on 24/09/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbSQLiteOpenHelper extends SQLiteOpenHelper
{
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateSitioSalida = "CREATE TABLE SitioSalida (IdSitioSalida TEXT PRIMARY KEY ,NombreSitioSalida TEXT)";
    String sqlCreateHorario = "CREATE TABLE Horario (IdHorario TEXT PRIMARY KEY ,Dias TEXT)";
    String sqlCreateRuta = "CREATE TABLE Ruta (IdRuta TEXT PRIMARY KEY,NombreRuta TEXT,Costo TEXT,IdEmpresa TEXT)";
    String sqlCreateCarrera = "CREATE TABLE CarreraRuta(IdCarreraRuta TEXT PRIMARY KEY ,IdRuta TEXT,IdSitioSalida TEXT,IdHorario TEXT,DescHora TEXT,Nota TEXT)";



    public DbSQLiteOpenHelper(Context contexto, String nombre,CursorFactory factory, int version)
    {
        super(contexto, nombre, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS SitioSalida");

        db.execSQL("DROP TABLE IF EXISTS Horario");
        db.execSQL("DROP TABLE IF EXISTS Ruta");
        db.execSQL("DROP TABLE IF EXISTS CarreraRuta");
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreateSitioSalida);
        db.execSQL(sqlCreateHorario);
        db.execSQL(sqlCreateRuta);
        db.execSQL(sqlCreateCarrera);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva)
    {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS SitioSalida");
        db.execSQL("DROP TABLE IF EXISTS Horario");
        db.execSQL("DROP TABLE IF EXISTS Ruta");
        db.execSQL("DROP TABLE IF EXISTS CarreraRuta");


        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreateSitioSalida);
        db.execSQL(sqlCreateHorario);
        db.execSQL(sqlCreateRuta);
        db.execSQL(sqlCreateCarrera);
    }

}
