package com.example.peyman.jani.weather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.peyman.jani.weather.JavaClass.ConverterDate;
import com.example.peyman.jani.weather.JavaClass.GetDate;
import com.example.peyman.jani.weather.JavaClass.GetRequestWebService;
import com.example.peyman.jani.weather.JavaClass.HelperCalendar;
import com.example.peyman.jani.weather.Samples.MainJason;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Boolean Found;

   AutoCompleteTextView editText;
    String[] Cites={"البرز", "اصفهان", "اردبیل", "ایلام", "بوشهر", "تهران", "چهارمحال و بختیاری", "مشهد","خوزستان", "زنجان", "سیستان و بلوچستان", "سمنان","فارس", "قم", "قزوین", "کهکیلویه و بویراحمد","کردستان", "کرمان","کرمانشاه", "گیلان", "گلستان", "لرستان","مازندران", "مرکزی", "هرمزگان", "همدان", "یزد",
    };
    String Mainresponse;
    RequestParams requestParams = new RequestParams();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (AutoCompleteTextView) findViewById(R.id.search_editTxt);
        Button search = (Button)findViewById(R.id.search);
        Button search2 = (Button)findViewById(R.id.search2);


        YoYo.with(Techniques.SlideInLeft
        ).duration(1500).repeat(1).playOn(findViewById(R.id.img));

                ArrayAdapter adapter = new
                        ArrayAdapter(this, android.R.layout.simple_gallery_item, Cites);
        editText.setAdapter(adapter);
        editText.setThreshold(1);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString();
                String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+s+"%2C%20ir%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                if (v.getId()==R.id.search){
                    if(s.length()!=0){

                        Rest(url);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "لطفا نام را بدرستی وارد کنید!", Toast.LENGTH_SHORT).show();
                    }

                }
                if (v.getId()==R.id.search2){


                }

            }
        });
    }

    public MainJason mainJason;
    public void Rest(final String url) {
        AsyncHttpClient client = new AsyncHttpClient();



        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

               Mainresponse=responseString;
                Gson gson = new Gson();
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("responce",responseString).apply();

                requestParams.add("response",responseString);
                Log.i("true",Mainresponse);
                mainJason=gson.fromJson(responseString,MainJason.class);
                if (mainJason.getQuery().getCount().toString().equals("0")){
                    Toast.makeText(MainActivity.this, "لطفا نام را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this,ShowWeatherActivity.class);
                    intent.putExtra("city",url);
                    startActivity(intent);
                }
            }
        });
        Log.i("true",PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("response",", ”a"));



    }



/*    public Boolean CheackServer() {
        String City;
        City = editText.getText().toString();
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+City+"%2C%20ir%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Found = false;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    String query =  jsonObject.getString("query");

                    JSONObject jsonObject1 = new JSONObject(query);
                   String query2=jsonObject1.getString("count");

                    Toast.makeText(MainActivity.this, query2+"", Toast.LENGTH_SHORT).show();
                    if (query2.equals("0")){
                        Found=false;
                    }else {
                        Found=true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return Found;
    }*/

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}