package com.burak.bayrakquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.burak.bayrakquiz.databinding.ActivityFinishBinding;

public class FinishActivity extends AppCompatActivity {
    private ActivityFinishBinding tasarim;
    private int finalRight = 0;
    private int finalWrong = 0;
    private int percent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasarim = DataBindingUtil.setContentView(this,R.layout.activity_finish);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            finalRight = bundle.getInt("dogruSayac");
            finalWrong = bundle.getInt("yanlisSayac");
        }
        tasarim.textFinalRight.setText("Doğru: "+finalRight);
        tasarim.textFinalWrong.setText("Yanlşı: "+finalWrong);
        percent = (100/(finalRight+finalWrong))*finalRight;
        tasarim.textPercent.setText("Yüzde: "+percent);

        tasarim.buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishActivity.this,gameActivity.class));
                finish();
            }
        });
    }
}