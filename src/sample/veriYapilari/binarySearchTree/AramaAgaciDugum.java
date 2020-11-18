package sample.veriYapilari.binarySearchTree;

import sample.eleman.Kisi;
import sample.veriYapilari.LinkedList.LinkedList;

public class AramaAgaciDugum {
    public Kisi kisi;

    public LinkedList Deneyim;
    public LinkedList Egitim;

    public AramaAgaciDugum sol;
    public AramaAgaciDugum sag;


    public AramaAgaciDugum() { }

    public AramaAgaciDugum(Kisi kisi) {
        this.kisi = kisi;
        this.Deneyim = null;
        this.Egitim = null;
        this.sol = null;
        this.sag = null;
    }

    public AramaAgaciDugum(Kisi kisi, LinkedList deneyim, LinkedList egitim) {
        this.kisi = kisi;
        this.Deneyim = deneyim;
        this.Egitim = egitim;
        this.sol = null;
        this.sag = null;
    }
}
