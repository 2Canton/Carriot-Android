package com.nansoft.mipuribus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLiteOpenHelper extends SQLiteOpenHelper
{
	//Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateSitioSalida = "CREATE TABLE SitioSalida (IdSitioSalida INTEGER PRIMARY KEY ,NombreSitioSalida TEXT)";
    String sqlCreateHorario = "CREATE TABLE Horario (IdHorario INTEGER PRIMARY KEY ,Dias TEXT)";
    String sqlCreateRuta = "CREATE TABLE Ruta (IdRuta INTEGER PRIMARY KEY,NombreRuta TEXT,Costo TEXT,IdEmpresa INTEGER)";
    String sqlCreateCarrera = "CREATE TABLE Carrera(IdCarreraRuta INTEGER PRIMARY KEY ,IdRuta INTEGER,IdSitioSalida INTEGER,IdHorario INTEGER,DescHora TEXT,Nota TEXT)";


    
    public DBSQLiteOpenHelper(Context contexto, String nombre,CursorFactory factory, int version) 
    {
        super(contexto, nombre, factory, version);

    }
 
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
    	db.execSQL("DROP TABLE IF EXISTS SitioSalida");
        
        db.execSQL("DROP TABLE IF EXISTS Horario");
        db.execSQL("DROP TABLE IF EXISTS Beca");
        db.execSQL("DROP TABLE IF EXISTS Ruta");
        db.execSQL("DROP TABLE IF EXISTS Carrera");
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
        db.execSQL("DROP TABLE IF EXISTS Beca");
        db.execSQL("DROP TABLE IF EXISTS Ruta");
        db.execSQL("DROP TABLE IF EXISTS Carrera");

 
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreateSitioSalida);
        db.execSQL(sqlCreateHorario);
        db.execSQL(sqlCreateRuta);
        db.execSQL(sqlCreateCarrera);
    }

}
