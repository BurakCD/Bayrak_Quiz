package com.burak.bayrakquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.burak.bayrakquiz.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding tasarim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasarim = DataBindingUtil.setContentView(this, R.layout.activity_main);

        veritabaniKopyala();

        tasarim.StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, gameActivity.class));
            }
        });
    }

    public void veritabaniKopyala(){
        DatabaseCopyHelper helper = new DatabaseCopyHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();
    }
}