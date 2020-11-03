package sample.sirket;

import sample.veriYapilari.heap.Heap;

import java.util.Random;

public class Ilan {
    public String IsTanimi;
    public Sirket Sirket;
    public int IlanNo;
    public Heap Basvuranlar;


    private Random r;

    public Ilan(){
        Basvuranlar = new Heap();
        r = new Random();
        this.IlanNo = r.nextInt(1000);
    }

    public Ilan(String tanim, Sirket s){
        Basvuranlar = new Heap();
        r = new Random();
        this.IsTanimi = tanim;
        this.Sirket = s;
        this.IlanNo = r.nextInt(1000);
    }
}
