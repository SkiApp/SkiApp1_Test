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


public class WeatherDetailAdapter extends BaseAdapter{
    private static final String LOG_TAG = WeatherDetailAdapter.class.getSimpleName();
    String [] result;
    Context context;
    String [] Wicons;

    private static LayoutInflater inflater=null;
    public WeatherDetailAdapter(FragmentActivity mainActivity, String[] wetterDaten, String[] Icons) {
        // TODO Auto-generated constructor stub
        result=wetterDaten;
        context=mainActivity;
        Wicons=Icons;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
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
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_wettervorhersage, null);

            holder.tv=(TextView) rowView.findViewById(R.id.label);
            holder.img=(ImageView) rowView.findViewById(R.id.icon);
            holder.tv.setText(result[position]);
            String Bildle;
            Bildle = "wi" + Wicons[position];
            Log.v(LOG_TAG, "XML Output:" + Bildle);
            holder.img.setImageResource(context.getResources().getIdentifier(Bildle, "drawable", "com.dhbw.studienarbeit.skiapp"));

            return rowView;


    }

}
