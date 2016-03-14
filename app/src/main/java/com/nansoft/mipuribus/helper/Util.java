package com.nansoft.mipuribus.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.nansoft.mipuribus.model.Horario;
import com.nansoft.mipuribus.model.Reporte;
import com.nansoft.mipuribus.model.Ruta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Carlos on 21/09/2015.
 */
public class Util {
    public final String UrlMobileServices = "https://puriscal.azure-mobile.net/";
    public static String UrlHorarioRuta = "https://puriscal.azure-mobile.net/api/horarioruta";
    public final String LlaveMobileServices = "CtavDeXtaLeUclXFhrPrjLJiUeeEek84";

    public static MobileServiceClient mClient;

    private Context mContext;

    private MobileServiceSyncTable<Ruta> rutaTable;
    private Query mPullQuery;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Util(Context context)
    {
        mContext = context;

        try
        {
            mClient = new MobileServiceClient(
                    UrlMobileServices,
                    LlaveMobileServices,
                    mContext
            );
        }
        catch (Exception e)
        {

        }
    }

    public boolean inicializarBaseDatos()
    {
        try
        {
            SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "LocalStorage", null, 1);
            SimpleSyncHandler handler = new SimpleSyncHandler();
            MobileServiceSyncContext syncContext = mClient.getSyncContext();

            Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();

            // tabla ruta
            tableDefinition.put("id", ColumnDataType.String);
            tableDefinition.put("nombre", ColumnDataType.String);
            tableDefinition.put("costo", ColumnDataType.String);
            tableDefinition.put("idempresa", ColumnDataType.String);
            tableDefinition.put("__version", ColumnDataType.String);
            localStore.defineTable("Ruta", tableDefinition);

            //tabla horario
            tableDefinition = new HashMap<String, ColumnDataType>();
            tableDefinition.put("id", ColumnDataType.String);
            tableDefinition.put("dias", ColumnDataType.String);
            tableDefinition.put("__version", ColumnDataType.String);
            localStore.defineTable("Horario", tableDefinition);

            // tabla carrera ruta
            tableDefinition = new HashMap<String, ColumnDataType>();
            tableDefinition.put("id", ColumnDataType.String);
            tableDefinition.put("idruta", ColumnDataType.String);
            tableDefinition.put("idsitiosalida", ColumnDataType.String);
            tableDefinition.put("idhorario", ColumnDataType.String);
            tableDefinition.put("nota", ColumnDataType.String);
            tableDefinition.put("hora", ColumnDataType.String);
            tableDefinition.put("__version", ColumnDataType.String);
            localStore.defineTable("CarreraRuta", tableDefinition);

            //tabla sitio salida
            tableDefinition = new HashMap<String, ColumnDataType>();
            tableDefinition.put("id", ColumnDataType.String);
            tableDefinition.put("nombre", ColumnDataType.String);
            tableDefinition.put("__version", ColumnDataType.String);
            localStore.defineTable("Parada", tableDefinition);

            syncContext.initialize(localStore, handler).get();

            // indicamos que ha ido bien
            return true;
        }
        catch (Exception e)
        {
            // indicamos que ha sucedido un error
            return false;
        }

    }

    public boolean addReport(final Reporte report)
    {
        try {
            // Insert the new item
            return new AsyncTask<Void, Void, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... params) {
                    try {
                        MobileServiceTable<Reporte> tableReport = mClient.getTable("Reporte", Reporte.class);

                        tableReport.insert(report);

                        return true;
                    } catch (Exception exception) {
                        return false;
                    }
                }


            }.execute().get();
        }
        catch (Exception e)
        {

        }
        return false;
    }

}
