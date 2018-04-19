package com.example.peyman.jani.weather.JavaClass;

import android.app.Activity;
import android.util.Log;

import com.example.peyman.jani.weather.Samples.Forecast;
import com.example.peyman.jani.weather.Samples.MainJason;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by PC_peyman on 4/9/2018.
 */

public class GetRequestWebService extends Activity {
    public int count;
    MainJason main;
    public static String response;

    public String getmainJson(String url){
            //String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+City+"%2C%20ir%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                    Log.d("false", "onFailure: ");

                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                   Gson gson = new Gson();
                    main=gson.fromJson(responseString,MainJason.class);
                    response = responseString;
                }
            });

           // if(main.getQuery().getCount().equals("0")){
        Log.i("true", response);  //   return null;}

        return response;
    }
}
