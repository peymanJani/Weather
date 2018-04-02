package com.example.peyman.jani.weather;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peyman.jani.weather.JavaClass.*;
import com.example.peyman.jani.weather.Samples.Condition;
import com.example.peyman.jani.weather.Samples.Forecast;
import com.example.peyman.jani.weather.Samples.MainJason;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import cz.msebera.android.httpclient.Header;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.peyman.jani.weather.R.layout.activity_show_weather;

public class ShowWeatherActivity extends AppCompatActivity {

    List<Forecast> forecasts;
    RelativeLayout relativeLayout;
    ListView listView;
    MainJason jason;
    private ImageView main_status_image;
    TextView high;
    TextView low;
    TextView main_status;
    TextView main_temp;
    TextView weekday_main;
    private TextView clock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_show_weather);

        bind();
        setBackgroundBlur();
        initializeRest();
        //String[] name={"شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه",};
        listView = (ListView)findViewById(R.id.future_list);



//        Toast.makeText(ShowWeatherActivity.this, forecasts.get(0).getDate().toString()+"", Toast.LENGTH_SHORT).show();




    }

    private void setBackgroundBlur() {
        Bitmap resultBmp = BluurBack.blur(this, BitmapFactory.decodeResource(getResources(), R.drawable.photo));
        BitmapDrawable background = new BitmapDrawable(resultBmp);
        relativeLayout.setBackgroundDrawable(background);
    }

    private void initializeRest() {

        String url=getUrlIntent();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(ShowWeatherActivity.this, "failure", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                jason = gson.fromJson(responseString,MainJason.class);
                List<Forecast> m = new ArrayList<>();
                m.addAll(jason.getQuery().getResults().getChannel().getItem().getForecast());
                forecasts = new ArrayList<>();
                forecasts.addAll(jason.getQuery().getResults().getChannel().getItem().getForecast());

                ConverterDate converterDate = new ConverterDate();
                converterDate.getCurrentShamsidate(forecasts.get(0).getDay()+"",forecasts.get(0).getDate()+"");
                set_status_main(forecasts.get(0).getText());

                Date date = new Date();
                String d = date.getHours()+":"+date.getMinutes();
                clock.setText(d+"");
                 weekday_main.setText(GetDate.WeekDay);
                 low.setText(convetDegree(forecasts.get(0).getLow())+"");
                 high.setText(convetDegree(forecasts.get(0).getHigh())+"");
                 main_temp.setText(convetDegree(jason.getQuery().getResults().getChannel().getItem().getCondition().getTemp())+"");




                ListAdapterMe listAdapter = new ListAdapterMe(ShowWeatherActivity.this,forecasts);
                listView.setAdapter(listAdapter);
                //Toast.makeText(ShowWeatherActivity.this, forecasts.get(0).getDate().toString()+"", Toast.LENGTH_SHORT).show();


                //Toast.makeText(ShowWeatherActivity.this, m.get(2).getDate().toString()+"", Toast.LENGTH_SHORT).show();
            }

            private void set_status_main(String text) {

                switch (text){
                    case "Mostly Cloudy":
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.cloudy_mostly_cloudy));
                        main_status.setText("احتمالا ابری");
                        break;
                    case "Cloudy":
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.cloudy_mostly_cloudy));
                        main_status.setText("ابری");
                        break;
                    case "Sunny":
                        main_status.setText("آفتابی");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                        break;
                    case "Clear":
                        main_status.setText("آفتابی");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                        break;
                    case "Breezy":
                        main_status.setText("آفتابی");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny_breezy_clear));
                        break;
                    case "Partly Cloudy":
                        main_status.setText("نیمه ابری");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.partly_cloudy));
                        break;
                    case "Rain":
                        main_status.setText("بارانی");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.rain));
                        break;
                    case "Scattered Showers":
                        main_status.setText("رعد و برق پراکنده");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.scattered_showers));
                        break;
                    case "windy":
                        main_status.setText("بادی");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.windy_burned));
                        break;
                    case "Mostly Sunny":
                        main_status.setText("نیمه افتابی");
                        main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.partly_cloudy));
                        break;
                        default:
                            main_status.setText("افتابی");
                            main_status_image.setBackground(main_status_image.getResources().getDrawable(R.drawable.sunny));

                }
            }
        });
    }

    private String getUrlIntent() {
        Intent intent = getIntent();
        String u=intent.getStringExtra("city");
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+u+"%2C%20ir%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        return url;
    }

    private void bind() {
        clock = (TextView)findViewById(R.id.clock);
        weekday_main = (TextView)findViewById(R.id.weekday_main);
        relativeLayout = (RelativeLayout)findViewById(R.id.main_layout);
        main_status_image =(ImageView) findViewById(R.id.main_status_image);
        main_status = (TextView)findViewById(R.id.main_status);
        low = (TextView)findViewById(R.id.down_temp);
        high = (TextView)findViewById(R.id.up_temp);
        main_temp = (TextView)findViewById(R.id.main_temp);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private int convetDegree(String f) {
            int m =(int)Integer.parseInt(f);
            int c = (int) Math.floor((m-32)/1.8);
            return c;
        }
}
