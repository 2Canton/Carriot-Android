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

import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.Opcion;


/**
 * Created by Carlos on 03/04/2015.
 */
public class OpcionAdapterListView extends ArrayAdapter<Opcion> {

    int mLayoutResourceId;
    ViewHolder holder;
    Context mContext;
    Resources res;

    public OpcionAdapterListView(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;

        res = this.mContext.getResources();
    }

    // regresa la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        final Opcion currentItem = getItem(position);


        // verificamos si la fila que se va dibujar no existe
        if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.imgLogoEmpresaa = (ImageView) row.findViewById(R.id.imgvLogoEmpresaRuta);
            holder.txtvTituloRuta = (TextView) row.findViewById(R.id.txtvTituloRuta);

            row.setTag(holder);

        }
            // en caso contrario la recuperamos
        ViewHolder holder = (ViewHolder) row.getTag();

        holder.imgLogoEmpresaa.setImageResource(res.getIdentifier(currentItem.getUrlImagen(),
                "drawable", mContext.getPackageName()));

        holder.txtvTituloRuta.setText(currentItem.getNombreOpcion());

        //holder.txtvCostoRuta = (TextView) row.findViewById(R.id.txtvCostoRuta);
        //holder.txtvCostoRuta.setText("Precio " + currentItem.getCosto());

        return row;

    }

    // guarda el estado de cada vista la primera vez que se dibuja
    static class ViewHolder {
        protected ImageView imgLogoEmpresaa;
        protected TextView txtvTituloRuta;
        //protected TextView txtvCostoRuta;
    }


}
