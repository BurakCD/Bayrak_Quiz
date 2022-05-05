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

        Log.e("game activity main","çalışıyor");

        sorularListe = new BayraklarDao().rastgeleBesgetir(vt);

        soruYukle();

        Log.e("game activity main","çalışıyor2");
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
        Log.e("Kontrol noktası","1");
        Log.e("Kontrol noktası",String.valueOf(soruSayac));


        dogruSoru = sorularListe.get(soruSayac);
        Log.e("Kontrol noktası","2");

        yanlisSeceneklerListe = new BayraklarDao().rastgeleUcYanlisSecenekGetir(vt, dogruSoru.getBayrak_id());
        Log.e("Kontrol noktası","3");

        tasarim.imageFlag.setImageResource(getResources().getIdentifier
                (dogruSoru.getBayrak_resim(),"drawable",getPackageName()));
        Log.e("Kontrol noktası","4");

        secenekleriKaristirmaListe.clear();
        Log.e("Kontrol noktası","5");

        secenekleriKaristirmaListe.add(dogruSoru);
        Log.e("Kontrol noktası","6");

        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(0));
        Log.e("Kontrol noktası","7");

        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(1));
        Log.e("Kontrol noktası","8");

        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(2));
        Log.e("Kontrol noktası","9");

        secenekler.clear();
        Log.e("Kontrol noktası","10");

        for (Bayraklar b: secenekleriKaristirmaListe){
            secenekler.add(b);
        }
        Log.e("Kontrol noktası","11");

        tasarim.button1.setText(secenekler.get(0).getBayrak_ad());
        tasarim.button2.setText(secenekler.get(1).getBayrak_ad());
        tasarim.button3.setText(secenekler.get(2).getBayrak_ad());
        tasarim.button4.setText(secenekler.get(3).getBayrak_ad());
        Log.e("Kontrol noktası","12");
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