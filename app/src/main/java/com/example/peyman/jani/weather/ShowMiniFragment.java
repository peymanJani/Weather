package com.example.peyman.jani.weather;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


/**
 * A simple {@link Fragment} subclass.
 */


    public class ShowMiniFragment extends android.support.v4.app.Fragment {

        public static int l;
        public static ShowMiniFragment fragmentA;
        public static ShowMiniFragment newInstans(int i){

                fragmentA=new ShowMiniFragment();

            l=i;
            return fragmentA;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_show_mini,container,false);

            TextView textView = (TextView)view.findViewById(R.id.aaaa);
            textView.setText(l+"");


            return view;
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
}
