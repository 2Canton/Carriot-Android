package com.nansoft.mipuribus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.adapter.EventoAdapter;
import com.nansoft.mipuribus.model.Evento;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class EventosActivity extends BaseActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    EventoAdapter mAdapter;
    String ID_TIPO_EVENTO;

    // layout de error
    View includedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Set up action bar.
        ActionBar bar = getSupportActionBar();
        bar.show();
        bar.setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.app_name) + " - " + getIntent().getExtras().getString("nombre"));

        ID_TIPO_EVENTO = getIntent().getExtras().getString("id");

        includedLayout = findViewById(R.id.sindatos);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swpActualizar);

        mAdapter = new EventoAdapter(EventosActivity.this,R.layout.item_evento);
        ListView listView =(ListView)findViewById(R.id.lstvLista);
        listView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                cargarTipoEventos();
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        cargarTipoEventos();



    }

    private void cargarTipoEventos()
    {
        includedLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setEnabled(false);

        MobileServiceClient mClient;


        try {

            mClient = new MobileServiceClient(
                    "https://puriscal.azure-mobile.net/",
                    "CtavDeXtaLeUclXFhrPrjLJiUeeEek84",
                    getApplicationContext()
            );
            mAdapter.clear();

            List<Pair<String, String>> parameters = new ArrayList<Pair<String, String>>();
            parameters.add(new Pair<String, String>("id", ID_TIPO_EVENTO));

            ListenableFuture<JsonElement> lst = mClient.invokeApi("eventscategory", "GET", parameters);

            Futures.addCallback(lst, new FutureCallback<JsonElement>() {
                @Override
                public void onFailure(Throwable exc) {

                    estadoAdapter(false);
                }

                @Override
                public void onSuccess(JsonElement result) {

                    // se verifica si el resultado es un array Json
                    if (result.isJsonArray()) {
                        // obtenemos el resultado como un JsonArray
                        JsonArray jsonArray = result.getAsJsonArray();
                        Gson objGson = new Gson();


                        // se deserializa el array
                        final Evento[] myTypes = objGson.fromJson(jsonArray, Evento[].class);

                        for (Evento item : myTypes) {
                            mAdapter.add(item);
                            mAdapter.notifyDataSetChanged();
                        }

                    }

                    if (mAdapter.getCount() == 0) {
                        estadoAdapter(false);
                    } else {
                        estadoAdapter(true);
                    }


                }
            });

        } catch (MalformedURLException e) {

        }
        catch (Exception e)
        {

        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eventos, menu);
        return true;
    }


    public void onClick(View vista)
    {
        Recargar();
    }


    public void Recargar()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void estadoAdapter(boolean pEstadoError)
    {
        mSwipeRefreshLayout.setRefreshing(false);


        mSwipeRefreshLayout.setEnabled(true);

        if (!pEstadoError || mAdapter.isEmpty()) {
            includedLayout.setVisibility(View.VISIBLE);

            TextView txtvMensaje = (TextView) includedLayout.findViewById(R.id.txtvError);
            txtvMensaje.setText("Vaya parece que aún no tenemos items en esta sección");

        } else {

            includedLayout.setVisibility(View.GONE);
        }

    }
}
