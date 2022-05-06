package com.burak.bayrakquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.burak.bayrakquiz.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.HashSet;

public class gameActivity extends AppCompatActivity {
    private ActivityGameBinding tasarim;

    private ArrayList<Bayraklar> sorularListe;
    private ArrayList<Bayraklar> yanlisSeceneklerListe;

    private Bayraklar dogruSoru;

    private int i;

    private Veritabani vt = new Veritabani(this);

    private int soruSayac = 0;
    private int yanlisSayac = 0;
    private int dogruSayac = 0;

    private HashSet<Bayraklar> secenekleriKaristirmaListe = new HashSet<>();
    private ArrayList<Bayraklar> secenekler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasarim = DataBindingUtil.setContentView(this, R.layout.activity_game);

        sorularListe = new BayraklarDao().rastgeleBesgetir(vt);

        soruYukle();

        tasarim.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(tasarim.button1);
                sayacKontrol();
            }
        });

        tasarim.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(tasarim.button2);
                sayacKontrol();
            }
        });

        tasarim.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(tasarim.button3);
                sayacKontrol();
            }
        });

        tasarim.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(tasarim.button4);
                sayacKontrol();
            }
        });
    }

    public void soruYukle(){
        tasarim.textCount.setText((soruSayac + 1) + ". soru");
        tasarim.textRight.setText("Doğru: "+dogruSayac);
        tasarim.textWrong.setText("Yanlış: "+yanlisSayac);
        Log.e("Kontrol noktası",String.valueOf(soruSayac));


        dogruSoru = sorularListe.get(soruSayac);

        yanlisSeceneklerListe = new BayraklarDao().rastgeleUcYanlisSecenekGetir(vt, dogruSoru.getBayrak_id());

        tasarim.imageFlag.setImageResource(getResources().getIdentifier
                (dogruSoru.getBayrak_resim(),"drawable",getPackageName()));

        secenekleriKaristirmaListe.clear();

        secenekleriKaristirmaListe.add(dogruSoru);

        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(0));
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(1));
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(2));

        secenekler.clear();

        for (Bayraklar b: secenekleriKaristirmaListe){
            secenekler.add(b);
        }

        tasarim.button1.setText(secenekler.get(0).getBayrak_ad());
        tasarim.button2.setText(secenekler.get(1).getBayrak_ad());
        tasarim.button3.setText(secenekler.get(2).getBayrak_ad());
        tasarim.button4.setText(secenekler.get(3).getBayrak_ad());
    }

    public void dogruKontrol(Button buton){
        String butonYazi = buton.getText().toString();

        String cevap = dogruSoru.getBayrak_ad();
        if (butonYazi.equals(cevap)){
            dogruSayac++;
        }else{
            yanlisSayac++;
        }
    }

    public void sayacKontrol(){
        soruSayac++;
        if(soruSayac <=4){
            soruYukle();
        }else{
            Intent intent = new Intent(gameActivity.this,FinishActivity.class);
            intent.putExtra("dogruSayac",dogruSayac);
            intent.putExtra("yanlisSayac",yanlisSayac);
            startActivity(intent);
            finish();
        }
    }

}