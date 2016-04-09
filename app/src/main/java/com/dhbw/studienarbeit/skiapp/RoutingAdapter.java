package com.dhbw.studienarbeit.skiapp;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class RoutingAdapter extends BaseAdapter {
    private static final String LOG_TAG = RoutingAdapter.class.getSimpleName();
    String [] result, distance, Bildle;
    Context context;
    String[] Symbol;
    public static Integer zahl;

    private static LayoutInflater inflater=null;
    public RoutingAdapter(FragmentActivity mainActivity, String[] PiLi, String[] Pschilder, String[]Distanz, String[] PisLif, Integer Zahl) {
        // TODO Auto-generated constructor stub
        result=PiLi;
        distance=Distanz;
        context=mainActivity;
        Bildle=Pschilder;
        Symbol=PisLif;
        zahl=Zahl;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return zahl;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }




    public class Holder
    {
        TextView tv;
        TextView tvD;
        ImageView img;
        ImageView sym;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_routing, null);
        holder.tv=(TextView) rowView.findViewById(R.id.PisteLift);
        holder.tvD=(TextView) rowView.findViewById(R.id.Distanz);
        holder.sym=(ImageView) rowView.findViewById(R.id.Symbol);
        holder.img=(ImageView) rowView.findViewById(R.id.Nummer);
        holder.tv.setText(result[position]);
        holder.tvD.setText(distance[position]);
        String BB=Bildle[position];
        String Symbl=Symbol[position];
        Log.v(LOG_TAG, "XML Output:" + BB);
        holder.sym.setImageResource(context.getResources().getIdentifier(Symbl, "drawable", "com.dhbw.studienarbeit.skiapp"));
        holder.img.setImageResource(context.getResources().getIdentifier(BB, "drawable", "com.dhbw.studienarbeit.skiapp"));
        return rowView;


    }

}
