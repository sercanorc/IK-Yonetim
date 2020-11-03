package sample.veriYapilari.heap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.eleman.Kisi;

public class Heap {
    private int boyut = 0;
    private int maksimumBoyut = 100;
    private heapDugum[] obekDizisi;

    public Heap() {
        obekDizisi = new heapDugum[maksimumBoyut];
    }

    public Heap(int maksimumBoyut) {
        this.maksimumBoyut = maksimumBoyut;
        obekDizisi = new heapDugum[maksimumBoyut];
    }

    public boolean ekle(Kisi kisi) {
        if (boyut == maksimumBoyut)
            return false;

        heapDugum eklenecekHeapDugum = new heapDugum(kisi);
        obekDizisi[boyut] = eklenecekHeapDugum;
        yukariTasi(boyut++);
        return true;
    }

    public void yukariTasi(int indis) {
        int ebeveyn = (indis - 1) / 2;
        heapDugum alt = obekDizisi[indis];

        while (indis > 0 && obekDizisi[ebeveyn].Uygunluk < alt.Uygunluk) {
            obekDizisi[indis] = obekDizisi[ebeveyn];
            indis = ebeveyn;
            ebeveyn = (ebeveyn - 1) / 2;
        }
        obekDizisi[indis] = alt;
    }

    public heapDugum enBuyuguSil() {
        heapDugum root = obekDizisi[0];
        obekDizisi[0] = obekDizisi[--boyut];
        asagiTasi(0);
        return root;
    }

    public void asagiTasi(int indis) {
        int buyukCocuk;
        heapDugum ust = obekDizisi[indis];

        while (indis < boyut / 2) {
            int solCocuk = 2 * indis + 1;
            int sagCocuk = solCocuk + 1;

            if (sagCocuk < boyut && obekDizisi[solCocuk].Uygunluk < obekDizisi[sagCocuk].Uygunluk)
                buyukCocuk = sagCocuk;
            else
                buyukCocuk = solCocuk;

            if (ust.Uygunluk >= obekDizisi[buyukCocuk].Uygunluk)
                break;

            obekDizisi[indis] = obekDizisi[buyukCocuk];
            indis = buyukCocuk;
        }
        obekDizisi[indis] = ust;
    }

    public boolean KisiAra(Kisi kisi) {
        boolean durum = false;
        for (int i = 0; i < boyut; i++) {
            if (obekDizisi[i].Kisi.Ad_Soyad == kisi.Ad_Soyad) {
                durum = true;
                break;
            }
        }
        return durum;
    }

    public heapDugum adaGoreKisiAra(String ad) {
        heapDugum kisi = null;
        for (int i = 0; i < boyut; i++) {
            if (obekDizisi[i].Kisi.Ad_Soyad.equals(ad)) {
                kisi = obekDizisi[i];
            }
        }
        return kisi;
    }

    public void kisiGuncelle(String ad, Kisi kisi) {
        for (int i = 0; i < boyut; i++) {
            if (obekDizisi[i].Kisi.Ad_Soyad.equals(ad)) {
                obekDizisi[i].Kisi = kisi;
            }
        }
    }

    public ObservableList<String> KisileriListele() {
        ObservableList<String> basvuranlar = FXCollections.observableArrayList();
        for (int i = 0; i < boyut; i++) {
            if (obekDizisi[i] != null) {
                String uygunluk = String.format("%1.2f", obekDizisi[i].Uygunluk);
                basvuranlar.add(uygunluk + " | " + obekDizisi[i].Kisi.Ad_Soyad);
            }
        }
        return basvuranlar;
    }

    public ObservableList<String> ingilizceBilenler() {
        ObservableList<String> ingilizceBilenler = FXCollections.observableArrayList();
        for (int i = 0; i < boyut; i++) {
            if (obekDizisi[i] != null) {
                if (obekDizisi[i].Kisi.YabanciDil.toLowerCase().contains("ingilizce")) {
                    ingilizceBilenler.add(obekDizisi[i].Kisi.Ad_Soyad);
                }
            }
        }
        return ingilizceBilenler;
    }
}
