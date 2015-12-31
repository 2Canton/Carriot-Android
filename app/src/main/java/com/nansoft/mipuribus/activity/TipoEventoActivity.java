package com.nansoft.mipuribus.activity;

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
import android.widget.AdapterView;
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
import com.nansoft.mipuribus.adapter.TipoEventoAdapter;
import com.nansoft.mipuribus.model.Evento;
import com.nansoft.mipuribus.model.TipoEvento;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class TipoEventoActivity extends AppCompatActivity
{
    SwipeRefreshLayout mSwipeRefreshLayout;
    TipoEventoAdapter mAdapter;

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

        setTitle(getString(R.string.app_name) + " - Tipo de eventos");

        includedLayout = findViewById(R.id.sindatos);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swpActualizar);
        mAdapter = new TipoEventoAdapter(TipoEventoActivity.this,R.layout.item_ruta);
        ListView listView =(ListView)findViewById(R.id.lstvLista);
        listView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TipoEvento objOpcion = (TipoEvento) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),EventosActivity.class);
                intent.putExtra("id", objOpcion.getId());
                intent.putExtra("nombre",objOpcion.getNombre());
                startActivity(intent);
            }
        });

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
        mSwipeRefreshLayout.setEnabled(false);
        includedLayout.setVisibility(View.GONE);
        MobileServiceClient mClient;


        try {

            mClient = new MobileServiceClient(
                    "https://puriscal.azure-mobile.net/",
                    "CtavDeXtaLeUclXFhrPrjLJiUeeEek84",
                    getApplicationContext()
            );
            mAdapter.clear();

            List<Pair<String, String>> parameters = new ArrayList<Pair<String, String>>();

            ListenableFuture<JsonElement> lst = mClient.invokeApi("events", "GET", parameters);

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
                        final TipoEvento[] myTypes = objGson.fromJson(jsonArray, TipoEvento[].class);

                        for (TipoEvento item : myTypes) {
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
        getMenuInflater().inflate(R.menu.menu_tipo_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch(item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
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
