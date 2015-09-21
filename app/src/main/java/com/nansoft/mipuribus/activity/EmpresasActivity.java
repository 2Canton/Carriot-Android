package com.nansoft.mipuribus.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.adapter.EmpresaAdapter;
import com.nansoft.mipuribus.model.Empresa;

import java.net.MalformedURLException;

public class EmpresasActivity extends Activity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    EmpresaAdapter mAdapter;
    String ID_TIPO_EMPRESA = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ID_TIPO_EMPRESA = getIntent().getExtras().getString("id");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swpActualizar);
        mAdapter = new EmpresaAdapter(EmpresasActivity.this,R.layout.item_empresa);
        ListView listView =(ListView)findViewById(R.id.lstvLista);
        listView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                cargarEmpresas();
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        cargarEmpresas();
    }

    private void cargarEmpresas()
    {
        mSwipeRefreshLayout.setEnabled(false);

        new AsyncTask<Void, Void, Boolean>() {

            MobileServiceClient mClient;
            MobileServiceTable<Empresa> mProyectoTable;

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
                mProyectoTable = mClient.getTable("Empresa", Empresa.class);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {

                    final MobileServiceList<Empresa> result = mProyectoTable.where().field("idtipoempresa").eq(ID_TIPO_EMPRESA).orderBy("nombre", QueryOrder.Ascending).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (Empresa item : result)
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
        getMenuInflater().inflate(R.menu.menu_empresas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
