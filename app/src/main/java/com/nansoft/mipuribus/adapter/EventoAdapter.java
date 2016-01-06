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
import com.nansoft.mipuribus.model.Evento;

/**
 * Created by Carlos on 18/08/2015.
 */
public class EventoAdapter extends ArrayAdapter<Evento> {

    int mLayoutResourceId;
    ViewHolder holder;
    Context mContext;

    public EventoAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;

    }

    // regresa la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        final Evento currentItem = getItem(position);


        // verificamos si la fila que se va dibujar no existe
        if (row == null) {
            // si es así la creamos
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.imgLogoEvento = (ImageView) row.findViewById(R.id.imgLogoEvento);
            holder.txtvTituloEvento = (TextView) row.findViewById(R.id.txtvTituloEvento);
            holder.txtvFechaEvento = (TextView) row.findViewById(R.id.txtvFechaEvento);
            holder.txtvHoraEvento = (TextView) row.findViewById(R.id.txtvHoraEvento);
            holder.txtvCostoEvento = (TextView) row.findViewById(R.id.txtvCostoEvento);
            holder.txtvDescripcionEvento = (TextView) row.findViewById(R.id.txtvDescripcionEvento);

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
                .into(holder.imgLogoEvento);

        holder.txtvTituloEvento.setText(currentItem.nombre);
        holder.txtvFechaEvento.setText("Fecha: " + currentItem.fecha);
        holder.txtvHoraEvento.setText("Hora: " + currentItem.hora);
        holder.txtvCostoEvento.setText("Costo: " + currentItem.costo);
        holder.txtvDescripcionEvento.setText(currentItem.descripcion);

        //holder.txtvCostoRuta = (TextView) row.findViewById(R.id.txtvCostoRuta);
        //holder.txtvCostoRuta.setText("Precio " + currentItem.getCosto());

        return row;

    }

    // guarda el estado de cada vista la primera vez que se dibuja
    static class ViewHolder {
        protected ImageView imgLogoEvento;
        protected TextView txtvTituloEvento;
        protected TextView txtvFechaEvento;
        protected TextView txtvHoraEvento;
        protected TextView txtvCostoEvento;
        protected TextView txtvDescripcionEvento;
    }


}
