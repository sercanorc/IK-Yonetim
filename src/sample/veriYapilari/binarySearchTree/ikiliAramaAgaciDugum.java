package sample.veriYapilari.binarySearchTree;

import sample.eleman.Kisi;
import sample.veriYapilari.LinkedList.LinkedList;

public class ikiliAramaAgaciDugum {
    public Kisi kisi;

    public LinkedList Deneyimler;
    public LinkedList EgitimDurumu;

    public ikiliAramaAgaciDugum sol;
    public ikiliAramaAgaciDugum sag;


    public ikiliAramaAgaciDugum() { }

    public ikiliAramaAgaciDugum(Kisi kisi) {
        this.kisi = kisi;
        this.Deneyimler = null;
        this.EgitimDurumu = null;
        this.sol = null;
        this.sag = null;
    }

    public ikiliAramaAgaciDugum(Kisi kisi, LinkedList deneyimler, LinkedList egitimDurumu) {
        this.kisi = kisi;
        this.Deneyimler = deneyimler;
        this.EgitimDurumu = egitimDurumu;
        this.sol = null;
        this.sag = null;
    }
}
