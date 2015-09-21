package com.nansoft.mipuribus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;

import com.nansoft.mipuribus.R;
import com.nansoft.mipuribus.model.Horario;

import java.util.ArrayList;
import java.util.Random;

public class HorarioAdapterListView extends ArrayAdapter<Horario>
{

	int mLayoutResourceId;
	ViewHolder holder;
	Context mContext;
    ArrayList <Integer> arrayList;
	int numeroAleatorio;
    boolean estadoNumero;
    String rutaImagen = "";
    Resources res;
    Random rand;

	public HorarioAdapterListView(Context context, int layoutResourceId) 
	{
		super(context, layoutResourceId);
		mContext = context;
		mLayoutResourceId = layoutResourceId;
        arrayList = new ArrayList<Integer>();
        numeroAleatorio = 0;
        estadoNumero = false;
        res = this.mContext.getResources();
        rand = new Random();
	}
	
	// regresa la vista de cada elemento de la lista
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
 
		View row = convertView;
		final ViewHolder holder;
		final Horario currentItem = getItem(position);
		
		// verificamos si la fila que se va dibujar no existe
		if (row == null) 
		{
			// si es así la creamos
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
			holder = new ViewHolder();
			
		}
		else
		{
			// en caso contrario la recuperamos
			holder = (ViewHolder) convertView.getTag();
		}

        holder.imgLogoEmpresaa = (ImageView) row.findViewById(R.id.imgvLogoEmpresaRuta);

        numeroAleatorio = rand.nextInt(10);

        rutaImagen = "calendar" + String.valueOf(numeroAleatorio);
        holder.imgLogoEmpresaa.setImageResource(res.getIdentifier(rutaImagen,
                    "drawable", mContext.getPackageName()));

		
		holder.txtvTituloRuta = (TextView) row.findViewById(R.id.txtvTituloRuta);
		holder.txtvTituloRuta.setText(currentItem.getDias());
		
		holder.txtvCostoRuta = (TextView) row.findViewById(R.id.txtvCostoRuta);
		//holder.txtvCostoRuta.setText("Precio " + currentItem.getCosto());
		
		
		row.setTag(holder);
		return row;
		 
	}
		
	// guarda el estado de cada vista la primera vez que se dibuja
	static class ViewHolder
	{   
		protected ImageView imgLogoEmpresaa; 
		protected TextView txtvTituloRuta;
		protected TextView txtvCostoRuta;
	} 
	
	
}