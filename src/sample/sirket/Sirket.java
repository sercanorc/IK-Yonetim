package sample.sirket;

import sample.veriYapilari.heap.Heap;

public class Sirket {
    public String Ad;
    public Heap Basvuranlar;

    public  Sirket(){
        Basvuranlar= new Heap();
    }

    public Sirket(String ad){
        Basvuranlar= new Heap();
        this.Ad = ad;
    }

    public Sirket(String ad, String adres, String telefon, String eposta){
        Basvuranlar= new Heap();
        this.Ad = ad;
    }
}
