package com.burak.bayrakquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.burak.bayrakquiz.databinding.ActivityFinishBinding;

public class FinishActivity extends AppCompatActivity {
    private ActivityFinishBinding tasarim;
    private int finalRight;
    private int finalWrong;
    private int percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasarim = DataBindingUtil.setContentView(this,R.layout.activity_finish);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            finalRight = bundle.getInt("dogruSayac");
            finalWrong = bundle.getInt("yanlisSayac");
        }else{
            Toast.makeText(this,"Bir hata oluştu!",Toast.LENGTH_SHORT);
        }
        tasarim.textFinalRight.setText("Doğru: "+finalRight);
        tasarim.textFinalWrong.setText("Yanlşı: "+finalWrong);
        percent = ((100/(finalRight+finalWrong))*finalRight)+10;
        tasarim.textPercent.setText("Yüzde: "+percent);

        tasarim.buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinishActivity.this,gameActivity.class));
                finish();
            }
        });

        onBackPressed();
    }
}