package com.nansoft.mipuribus.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nansoft.mipuribus.adapter.OpcionAdapterListView;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.Opcion;


public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ActionBar bar = getSupportActionBar();
        bar.show();



        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swpActualizar);
        swipeRefreshLayout.setEnabled(false);

        OpcionAdapterListView mAdapter = new OpcionAdapterListView(MenuActivity.this,R.layout.item_ruta);
        ListView listView =(ListView)findViewById(R.id.lstvLista);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Opcion objOpcion = (Opcion) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                boolean isMail = false;

                switch (objOpcion.idOpcion) {
                    case 0:
                        intent = new Intent(getApplicationContext(), HistoriaActivity.class);
                        break;

                    case 1:
                        intent = new Intent(getApplicationContext(), RutasActivity.class);
                        break;

                    case 2:
                        intent = new Intent(getApplicationContext(), TipoEmpresaActivity.class);
                        break;

                    case 3:
                        // eventos
                        intent = new Intent(getApplicationContext(), TipoEventoActivity.class);
                        break;

                    case 4:
                        // religión
                        intent = new Intent(getApplicationContext(), HorarioMisaActivity.class);
                        break;

                    case 5:
                        // recolecciónd de basura
                        intent = new Intent(getApplicationContext(), HorarioBasuraActivity.class);
                        break;

                    case 6:
                        // noticias
                        intent = new Intent(getApplicationContext(), NoticiaActivity.class);
                        break;

                    case 7:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mipuribus"));
                        break;

                    case 8:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mipuribus.com"));
                        break;

                    case 9:
                        intent = new Intent(getApplicationContext(),ContactoActivity.class);
                        break;

                    default:
                        break;

                }


                try {
                    startActivity(intent);
                } catch (Exception activityException) {

                    Toast.makeText(getApplicationContext(), "Error verifique que tenga un navegador instalado", Toast.LENGTH_SHORT).show();

                }

            }
        });

        mAdapter.add(new Opcion(0,"Historia","history"));
        mAdapter.add(new Opcion(1,"Rutas","routes"));
        mAdapter.add(new Opcion(2,"Sitios de interés","phone"));
        mAdapter.add(new Opcion(3,"Eventos","event"));
        mAdapter.add(new Opcion(4,"Religión","church"));
        //mAdapter.add(new Opcion(5,"Recolección de basura","trash"));
        //mAdapter.add(new Opcion(6,"Noticias","news"));
        mAdapter.add(new Opcion(7,"Facebook","facebook"));
        mAdapter.add(new Opcion(8,"Sitio Web","website"));
        mAdapter.add(new Opcion(9,"Contacto","message"));


    }



}
