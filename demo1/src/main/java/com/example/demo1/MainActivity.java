package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.demo1.custom.FGLView;

public class MainActivity extends AppCompatActivity {
    FGLView fglView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
         fglView = new FGLView(this);
        setContentView(fglView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fglView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fglView.onPause();
    }
}
