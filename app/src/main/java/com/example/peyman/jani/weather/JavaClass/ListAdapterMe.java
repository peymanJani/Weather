package com.example.peyman.jani.weather.JavaClass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peyman.jani.weather.R;
import com.example.peyman.jani.weather.Samples.Forecast;
import com.example.peyman.jani.weather.ShowWeatherActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc_peyman on 3/31/2018.
 */

public class ListAdapterMe extends BaseAdapter {
    Context context;
    List<Forecast> forecasts ;

    public ListAdapterMe(Context contextm ,List<Forecast> forecast1) {
        forecasts = new ArrayList<>();
        forecasts.addAll(forecast1);
        context=contextm;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return forecasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_forcast,parent,false);
        TextView textView=view.findViewById(R.id.week_day);
        TextView high=view.findViewById(R.id.high);
        TextView low=view.findViewById(R.id.low);
        ImageView status=(ImageView) view.findViewById(R.id.status1);

        ConverterDate converterDate = new ConverterDate();
        converterDate.getCurrentShamsidate(forecasts.get(position+1).getDay()+"",forecasts.get(position+1).getDate()+"");


        //Drawable drawable = ge
        switch (forecasts.get(position+1).getText()){
            case "Mostly Cloudy":
                status.setBackground(status.getResources().getDrawable(R.drawable.cloudy_mostly_cloudy));
                break;
            case "Cloudy":
                status.setBackground(status.getResources().getDrawable(R.drawable.cloudy_mostly_cloudy));
                break;
            case "Sunny":
                status.setBackground(status.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                break;
            case "Clear":
                status.setBackground(status.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                break;
            case "Breezy":
                status.setBackground(status.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                break;
            case "Partly Cloudy":
                status.setBackground(status.getResources().getDrawable(R.drawable.partly_cloudy));
                break;
            case "Rain":
                status.setBackground(status.getResources().getDrawable(R.drawable.rain));
                break;
            case "Scattered Showers":
                status.setBackground(status.getResources().getDrawable(R.drawable.scattered_showers));
                break;
            case "windy":
                status.setBackground(status.getResources().getDrawable(R.drawable.windy_burned));
                break;
            case "Mostly Sunny":
                status.setBackground(status.getResources().getDrawable(R.drawable.partly_cloudy));
                break;

        }

        textView.setText(GetDate.WeekDay+"");

        int h=convetDegree(forecasts.get(position+1).getHigh());
        int l=convetDegree(forecasts.get(position+1).getLow());
        high.setText(h+"");
        low.setText(l+"");


        return view;
    }


    private int convetDegree(String f) {
        int m =(int)Integer.parseInt(f);
        int c = (int) Math.floor((m-32)/1.8);

        return c;
    }
}
