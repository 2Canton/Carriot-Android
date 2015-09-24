package com.nansoft.mipuribus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.adapter.EventoAdapter;
import com.nansoft.mipuribus.model.Evento;

import java.net.MalformedURLException;

public class EventosActivity extends AppCompatActivity {

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

        new AsyncTask<Void, Void, Boolean>() {

            MobileServiceClient mClient;
            MobileServiceTable<Evento> mProyectoTable;

            @Override
            protected void onPreExecute()
            {
                try {

                    mClient = new MobileServiceClient(
                            "https://puriscal.azure-mobile.net/",
                            "CtavDeXtaLeUclXFhrPrjLJiUeeEek84",
                            getApplicationContext()
                    );
                    mAdapter.clear();
                } catch (MalformedURLException e) {

                }
                mProyectoTable = mClient.getTable("Evento", Evento.class);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {

                    final MobileServiceList<Evento> result = mProyectoTable.where().field("idtipoevento").eq(ID_TIPO_EVENTO).orderBy("nombre", QueryOrder.Ascending).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (Evento item : result)
                            {
                                mAdapter.add(item);
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                    });
                    return true;
                } catch (Exception exception) {

                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success)
            {

                mSwipeRefreshLayout.setRefreshing(false);


                mSwipeRefreshLayout.setEnabled(true);

                if (!success || mAdapter.isEmpty()) {
                    includedLayout.setVisibility(View.VISIBLE);

                    TextView txtvMensaje = (TextView) includedLayout.findViewById(R.id.txtvError);
                    txtvMensaje.setText("Vaya parece que aún no tenemos items en esta sección");

                } else {

                    includedLayout.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onCancelled()
            {
                super.onCancelled();
            }
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eventos, menu);
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
}
