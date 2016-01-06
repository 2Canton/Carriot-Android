package com.nansoft.mipuribus.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.Empresa;

/**
 * Created by Carlos on 19/08/2015.
 */
public class EmpresaAdapter extends ArrayAdapter<Empresa>
{

    int mLayoutResourceId;
    ViewHolder holder;
    Context mContext;

    public EmpresaAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;

    }

    // regresa la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        final Empresa currentItem = getItem(position);


        // verificamos si la fila que se va dibujar no existe
        if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.imgLogo = (ImageView) row.findViewById(R.id.imgvLogoEmpresa);
            holder.txtvNombre = (TextView) row.findViewById(R.id.txtvTituloEmpresa);
            holder.txtvHorario = (TextView) row.findViewById(R.id.txtvHorarioEmpresa);
            holder.txtvDireccion = (TextView) row.findViewById(R.id.txtvDireccionEmpresa);
            holder.imgvTelefono = (ImageView) row.findViewById(R.id.imgvTelefonoEmpresa);
            holder.imgvEmail = (ImageView) row.findViewById(R.id.imgvEmailEmpresa);
            holder.imgvMapa = (ImageView) row.findViewById(R.id.imgvLocalizacionEmpresa);
            holder.imgvWebSite = (ImageView) row.findViewById(R.id.imgvWebSiteEmpresa);

            row.setTag(holder);

        }
        // en caso contrario la recuperamos
        ViewHolder holder = (ViewHolder) row.getTag();

        Glide.with(mContext)
                .load(currentItem.urlImagen.trim())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.picture)
                .error(R.drawable.picture_removed)
                .into(holder.imgLogo);


        holder.txtvNombre.setText(currentItem.nombre);
        holder.txtvHorario.setText(currentItem.horario);
        holder.txtvDireccion.setText(currentItem.direccion);

        holder.imgvTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent Llamada = new Intent(Intent.ACTION_CALL);
                    Llamada.setData(Uri.parse("tel:" + currentItem.telefonoPrincipal));
                    mContext.startActivity(Llamada);
                } catch (Exception activityException) {
                    Toast.makeText(mContext,"Error verifique que tenga una aplicación que permita realizar llamadas",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imgvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String[] pPara = {currentItem.email};
                    Intent Correo = new Intent(Intent.ACTION_SEND);
                    Correo.setData(Uri.parse("mailto:"));
                    Correo.putExtra(Intent.EXTRA_EMAIL, pPara);
                    Correo.putExtra(Intent.EXTRA_CC, "");
                    Correo.putExtra(Intent.EXTRA_SUBJECT, "Consulta");
                    Correo.putExtra(Intent.EXTRA_TEXT, "");
                    Correo.setType("message/rfc822");

                    mContext.startActivity(Intent.createChooser(Correo, "Email "));
                } catch (ActivityNotFoundException activityException) {

                    Toast.makeText(mContext, "Error verifique que tenga una aplicación que permita enviar correos electrónicos", Toast.LENGTH_SHORT).show();

                }
            }
        });

        holder.imgvMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String url = "waze://?ll=" + currentItem.latitud + "," + currentItem.longitud + "&navigate=yes";
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                    mContext.startActivity( intent );
                }
                catch ( ActivityNotFoundException ex  )
                {
                    Intent intent =
                            new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                    mContext.startActivity(intent);
                }
            }
        });

        holder.imgvWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.web));
                mContext.startActivity( intent );
            }
        });

        return row;

    }

    // guarda el estado de cada vista la primera vez que se dibuja
    static class ViewHolder {
        protected ImageView imgLogo;
        protected TextView txtvNombre;
        protected TextView txtvHorario;
        protected TextView txtvDireccion;
        protected ImageView imgvTelefono;
        protected ImageView imgvEmail;
        protected ImageView imgvMapa;
        protected ImageView imgvWebSite;
    }


}
