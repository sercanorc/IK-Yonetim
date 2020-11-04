package sample.veriYapilari.binarySearchTree;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.eleman.Kisi;
import sample.veriYapilari.LinkedList.LinkedList;

public class IkiliAramaAgaci {
    public ObservableList<String> dugumler;
    private ikiliAramaAgaciDugum root;
    private int duzey = 0;
    public IkiliAramaAgaci() { }

    public IkiliAramaAgaci(ikiliAramaAgaciDugum kok) {
        this.root = kok;
    }

    public IkiliAramaAgaci(ikiliAramaAgaciDugum ikiliAramaAgaciDugum, LinkedList deneyim, LinkedList egitim) {
        this.root = ikiliAramaAgaciDugum;
        this.root.Deneyimler = deneyim;
        this.root.EgitimDurumu = egitim;
    }

    public int dugumSayisi() {
        return dugumSayisi(root);
    }

    private int dugumSayisi(ikiliAramaAgaciDugum ikiliAramaAgaciDugum) {

        if (ikiliAramaAgaciDugum != null) { // dügüm boş değilse recursive fonksiyon olarak kendisi sag ve sol düğümleri toplayarak dönecek
            return dugumSayisi(ikiliAramaAgaciDugum.sag)+dugumSayisi(ikiliAramaAgaciDugum.sol)+1;
        }
        return 0;
    }

    public int YaprakSayisi() { return  YaprakSayisi(root); }

    private int YaprakSayisi(ikiliAramaAgaciDugum ikiliAramaAgaciDugum) {
        if (ikiliAramaAgaciDugum == null)
            return -1;

        int solDerinlik = YaprakSayisi(ikiliAramaAgaciDugum.sol);
        int sagDerinlik = YaprakSayisi(ikiliAramaAgaciDugum.sag);

        if (solDerinlik > sagDerinlik)
            return solDerinlik + 1;

        return sagDerinlik + 1;
    }

    private int seviyeBul(ikiliAramaAgaciDugum ikiliAramaAgaciDugum, String ad, int seviye) {
        if (ikiliAramaAgaciDugum == null)
            return 0;

        if (ikiliAramaAgaciDugum.kisi.Ad_Soyad.equals(ad))
            return seviye;

        int seviyeDus = seviyeBul(ikiliAramaAgaciDugum.sol, ad, seviye + 1);

        if (seviyeDus != 0)
            return seviyeDus;

        seviyeDus = seviyeBul(ikiliAramaAgaciDugum.sag, ad, seviye + 1);
        return seviyeDus;
    }

    public int seviyeyiGetir(ikiliAramaAgaciDugum ikiliAramaAgaciDugum, String ad) {
        return seviyeBul(ikiliAramaAgaciDugum, ad, 0);
    }

    public void kisiEkle(Kisi kisi, LinkedList deneyimler, LinkedList egitimDurumu) {
        ikiliAramaAgaciDugum ebeveyn = new ikiliAramaAgaciDugum();
        ikiliAramaAgaciDugum arama = root;

        while (arama != null) {
            ebeveyn = arama;
            if (kisi.Ad_Soyad.compareTo(arama.kisi.Ad_Soyad) == 0)
                return;
            else if (kisi.Ad_Soyad.compareTo(arama.kisi.Ad_Soyad) < 0)
                arama = arama.sol;
            else
                arama = arama.sag;
        }

        if (root.kisi == null) {
            if (root == null)
                root = new ikiliAramaAgaciDugum();
            root.kisi = kisi;
            root.Deneyimler = deneyimler;
            root.EgitimDurumu = egitimDurumu;
        } else if (kisi.Ad_Soyad.compareTo(ebeveyn.kisi.Ad_Soyad) < 0) {
            ebeveyn.sol = new ikiliAramaAgaciDugum();
            ebeveyn.sol.kisi = kisi;
            ebeveyn.sol.Deneyimler = deneyimler;
            ebeveyn.sol.EgitimDurumu = egitimDurumu;
        } else {
            ebeveyn.sag = new ikiliAramaAgaciDugum();
            ebeveyn.sag.kisi = kisi;
            ebeveyn.sag.Deneyimler = deneyimler;
            ebeveyn.sag.EgitimDurumu = egitimDurumu;
        }
    }

    public ObservableList<String> dugumListesi() {
        return dugumler;
    }

    private void ziyaret(ikiliAramaAgaciDugum ikiliAramaAgaciDugum) {
        if (dugumler == null) {
            dugumler = FXCollections.observableArrayList(ikiliAramaAgaciDugum.kisi.bilgileriGetir() + " | Düzey: " + seviyeyiGetir(root, ikiliAramaAgaciDugum.kisi.Ad_Soyad));
        } else {
            if (ikiliAramaAgaciDugum.kisi != null)
                dugumler.add(ikiliAramaAgaciDugum.kisi.bilgileriGetir() + " | Düzey: " + seviyeyiGetir(root, ikiliAramaAgaciDugum.kisi.Ad_Soyad));
        }
    }

    public void kisiGuncelle(String kisininIsmi, ikiliAramaAgaciDugum yeniBilgiler) {
        guncelle(root, kisininIsmi, yeniBilgiler);
    }

    private void guncelle(ikiliAramaAgaciDugum ikiliAramaAgaciDugum, String kisininIsmi, ikiliAramaAgaciDugum yeniBilgiler) {
        if (ikiliAramaAgaciDugum == null)
            return;
        else if (kisininIsmi.compareTo(ikiliAramaAgaciDugum.kisi.Ad_Soyad) == 0)
            ikiliAramaAgaciDugum = yeniBilgiler;
        else if (kisininIsmi.compareTo(ikiliAramaAgaciDugum.kisi.Ad_Soyad) < 0)
            guncelle(ikiliAramaAgaciDugum.sol, kisininIsmi, yeniBilgiler);
        else
            guncelle(ikiliAramaAgaciDugum.sag, kisininIsmi, yeniBilgiler);
    }

    public ikiliAramaAgaciDugum kisiAra(String kisininIsmi) {
        return aramaYap(root, kisininIsmi);
    }

    private ikiliAramaAgaciDugum aramaYap(ikiliAramaAgaciDugum ikiliAramaAgaciDugum, String kisininIsmi) {
        if (ikiliAramaAgaciDugum == null)
            return null;
        else if (kisininIsmi.compareTo(ikiliAramaAgaciDugum.kisi.Ad_Soyad) == 0)
            return ikiliAramaAgaciDugum;
        else if (kisininIsmi.compareTo(ikiliAramaAgaciDugum.kisi.Ad_Soyad) < 0)
            return aramaYap(ikiliAramaAgaciDugum.sol, kisininIsmi);
        else
            return aramaYap(ikiliAramaAgaciDugum.sag, kisininIsmi);
    }

    private ikiliAramaAgaciDugum Successor(ikiliAramaAgaciDugum ikiliAramaAgaciDugum) {
        ikiliAramaAgaciDugum ebeveyn = ikiliAramaAgaciDugum;
        ikiliAramaAgaciDugum Successor = ikiliAramaAgaciDugum;
        ikiliAramaAgaciDugum current = ikiliAramaAgaciDugum.sag;

        while (current != null) {
            ebeveyn = Successor;
            Successor = current;
            current = current.sol;
        }
        if (Successor != ikiliAramaAgaciDugum.sag) {
            ebeveyn.sol = Successor.sag;
            Successor.sag = ikiliAramaAgaciDugum.sag;
        }
        return Successor;
    }

    public boolean kisiSil(String kisiAdi) {
        ikiliAramaAgaciDugum current = root;
        ikiliAramaAgaciDugum ebeveyn = root;
        boolean solMu = true;
        //Düğümü bul
        while (kisiAdi.compareTo(current.kisi.Ad_Soyad) != 0) {
            ebeveyn = current;
            if (kisiAdi.compareTo(current.kisi.Ad_Soyad) < 0) {
                solMu = true;
                current = current.sol;
            } else {
                solMu = false;
                current = current.sag;
            }
            if (current == null)
                return false;
        }
        //DURUM 1: YAPRAK DÜĞÜM
        if (current.sol == null && current.sag == null) {
            if (current == root)
                root = null;
            else if (solMu)
                ebeveyn.sol = null;
            else
                ebeveyn.sag = null;
        }//DURUM 2: TEK ÇOCUKLU DÜĞÜM
        else if (current.sag == null) {
            if (current == root)
                root = current.sol;
            else if (solMu)
                ebeveyn.sol = current.sol;
            else
                ebeveyn.sag = current.sol;
        } else if (current.sol == null) {
            if (current == root)
                root = current.sag;
            else if (solMu)
                ebeveyn.sol = current.sag;
            else
                ebeveyn.sag = current.sag;
        }//DURUM 3: İKİ ÇOCUKLU DÜĞÜM
        else {
            ikiliAramaAgaciDugum Successor = Successor(current);
            if (current == root)
                root = Successor;
            else if (solMu)
                ebeveyn.sol = Successor;
            else
                ebeveyn.sag = Successor;

            Successor.sol = current.sol;
        }
        return true;
    }

    //Root-Sol-Sağ
    public ObservableList<String> preorder() {
        dugumler = null;
        duzey = 0;
        koktenSaga(root);
        return this.dugumler;
    }

    private void koktenSaga(ikiliAramaAgaciDugum ikiliAramaAgaciDugum) {
        if (ikiliAramaAgaciDugum == null)
            return;
        ziyaret(ikiliAramaAgaciDugum);
        koktenSaga(ikiliAramaAgaciDugum.sol);
        koktenSaga(ikiliAramaAgaciDugum.sag);
    }
    //Sol-Root-Sağ
    public ObservableList<String> inorder() {
        dugumler = null;
        duzey = 0;
        soldanSaga(root);
        return this.dugumler;
    }

    private void soldanSaga(ikiliAramaAgaciDugum ikiliAramaAgaciDugum) {
        if (ikiliAramaAgaciDugum == null)
            return;
        soldanSaga(ikiliAramaAgaciDugum.sol);
        ziyaret(ikiliAramaAgaciDugum);
        duzey++;
        soldanSaga(ikiliAramaAgaciDugum.sag);
    }
    //Sol-Sağ-Root
    public ObservableList<String> postorder() {
        dugumler = null;
        duzey = 0;
        soldanKoke(root);
        return this.dugumler;
    }

    private void soldanKoke(ikiliAramaAgaciDugum ikiliAramaAgaciDugum) {
        if (ikiliAramaAgaciDugum == null)
            return;
        soldanKoke(ikiliAramaAgaciDugum.sol);
        soldanKoke(ikiliAramaAgaciDugum.sag);
        ziyaret(ikiliAramaAgaciDugum);
        duzey++;
    }
}