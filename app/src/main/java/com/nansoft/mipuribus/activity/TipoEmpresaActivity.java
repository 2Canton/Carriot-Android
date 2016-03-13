package com.nansoft.mipuribus.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.nansoft.mipuribus.adapter.TipoEmpresaAdapter;
import com.nansoft.mipuribus.model.TipoEmpresa;
import com.nansoft.mipuribus.model.TipoEvento;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class TipoEmpresaActivity extends BaseActivity {

    TipoEmpresaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        enableHomeActionBar();

        setTitle("Tipo de empresas");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swpActualizar);
        mAdapter = new TipoEmpresaAdapter(TipoEmpresaActivity.this,R.layout.item_ruta);
        ListView listView =(ListView)findViewById(R.id.lstvLista);
        listView.setAdapter(mAdapter);

        includedLayout = findViewById(R.id.sindatos);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TipoEmpresa objOpcion = (TipoEmpresa) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),EmpresasActivity.class);
                intent.putExtra("id",objOpcion.id);
                intent.putExtra("nombre",objOpcion.nombre);
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

            ListenableFuture<JsonElement> lst = mClient.invokeApi("companies", "GET", parameters);

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
                        final TipoEmpresa[] myTypes = objGson.fromJson(jsonArray, TipoEmpresa[].class);

                        for (TipoEmpresa item : myTypes) {
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
        getMenuInflater().inflate(R.menu.menu_tipo_empresa, menu);
        return true;
    }

    private void estadoAdapter(boolean pEstadoError) {
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
