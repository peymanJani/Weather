package com.example.peyman.jani.weather;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.peyman.jani.weather.JavaClass.ConverterDate;
import com.example.peyman.jani.weather.JavaClass.GetDate;
import com.example.peyman.jani.weather.JavaClass.HelperCalendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

   AutoCompleteTextView editText;
    String[] Cites={"البرز", "اصفهان", "اردبیل", "ایلام", "بوشهر", "تهران", "چهارمحال و بختیاری", "مشهد","خوزستان", "زنجان", "سیستان و بلوچستان", "سمنان","فارس", "قم", "قزوین", "کهکیلویه و بویراحمد","کردستان", "کرمان","کرمانشاه", "گیلان", "گلستان", "لرستان","مازندران", "مرکزی", "هرمزگان", "همدان", "یزد",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConverterDate converterDate = new ConverterDate();
        converterDate.getCurrentShamsidate("Mon","29 Jan 2018");
        Toast.makeText(this, GetDate.Year+"", Toast.LENGTH_LONG).show();

        editText = (AutoCompleteTextView) findViewById(R.id.search_editTxt);
        Button search = (Button)findViewById(R.id.search);

        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_gallery_item,Cites);
        editText.setAdapter(adapter);
        editText.setThreshold(1);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString();
                if(s.length()!=0){
                    Intent intent = new Intent(MainActivity.this,ShowWeatherActivity.class);
                    intent.putExtra("city",s);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "لطفا نام را بدرستی وارد کنید!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        Intent intent = new Intent();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
