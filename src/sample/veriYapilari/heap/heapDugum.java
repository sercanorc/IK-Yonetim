package sample.veriYapilari.heap;

import sample.eleman.Kisi;

import java.util.Random;

public class heapDugum {
    public sample.eleman.Kisi Kisi;
    public double Uygunluk;
    private Random random;
    public heapDugum(Kisi kisi) {
        random = new Random();
        this.Kisi = kisi;
        Uygunluk = random.nextDouble() * 10.0;
    }
}
