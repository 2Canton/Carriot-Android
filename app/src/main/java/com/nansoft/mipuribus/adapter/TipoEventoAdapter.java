package com.nansoft.mipuribus.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nansoft.mipuribus.R;

import com.nansoft.mipuribus.model.TipoEvento;

/**
 * Created by Carlos on 17/08/2015.
 */
public class TipoEventoAdapter extends ArrayAdapter<TipoEvento>
{

        int mLayoutResourceId;
        ViewHolder holder;
        Context mContext;
        Resources res;

    public TipoEventoAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId);
            mContext = context;
            mLayoutResourceId = layoutResourceId;

            res = this.mContext.getResources();
            }

    // regresa la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;

        final TipoEvento currentItem = getItem(position);


            // verificamos si la fila que se va dibujar no existe
            if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.imgLogoEmpresaa = (ImageView) row.findViewById(R.id.imgvLogoEmpresaRuta);
            holder.txtvTituloRuta = (TextView) row.findViewById(R.id.txtvTituloRuta);
            holder.txtvCostoRuta = (TextView) row.findViewById(R.id.txtvCostoRuta);
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
                    .into(holder.imgLogoEmpresaa);


            holder.txtvTituloRuta.setText(currentItem.nombre);


            holder.txtvCostoRuta.setText("Cantidad " + currentItem.CantidadEventos);

            return row;

            }

    // guarda el estado de cada vista la primera vez que se dibuja
    static class ViewHolder {
        protected ImageView imgLogoEmpresaa;
        protected TextView txtvTituloRuta;
        protected TextView txtvCostoRuta;
    }


}
