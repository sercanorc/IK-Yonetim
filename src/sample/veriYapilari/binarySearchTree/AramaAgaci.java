package sample.veriYapilari.binarySearchTree;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.eleman.Kisi;
import sample.veriYapilari.LinkedList.LinkedList;

public class AramaAgaci {
    public ObservableList<String> dugumL;
    private AramaAgaciDugum root;
    private int duzey = 0;
    //Arama agacı constructor'ı
    public AramaAgaci(AramaAgaciDugum AramaAgaciDugum, LinkedList deneyim, LinkedList egitim) {
        this.root = AramaAgaciDugum;
        this.root.Deneyim = deneyim;
        this.root.Egitim = egitim;
    }

    public int dugumSayisi() {
        return dugumSayisi(root);
    }

    private int dugumSayisi(AramaAgaciDugum AramaAgaciDugum) {

        if (AramaAgaciDugum != null) { // dügüm boş değilse recursive fonksiyon olarak kendisi sag ve sol düğümleri toplayarak dönecek
            return dugumSayisi(AramaAgaciDugum.sag)+dugumSayisi(AramaAgaciDugum.sol)+1;
        }
        return 0;
    }

    public int YaprakSayisi() { return  YaprakSayisi(root); }

    private int YaprakSayisi(AramaAgaciDugum AramaAgaciDugum) {
        if (AramaAgaciDugum == null)
            return -1;

        int solDerinlik = YaprakSayisi(AramaAgaciDugum.sol);
        int sagDerinlik = YaprakSayisi(AramaAgaciDugum.sag);

        if (solDerinlik > sagDerinlik)
            return solDerinlik + 1;

        return sagDerinlik + 1;
    }

    private int seviyeBul(AramaAgaciDugum AramaAgaciDugum, String ad, int seviye) {
        if (AramaAgaciDugum == null)
            return 0;

        if (AramaAgaciDugum.kisi.Ad_Soyad.equals(ad))
            return seviye;

        int seviyeDus = seviyeBul(AramaAgaciDugum.sol, ad, seviye + 1);

        if (seviyeDus != 0)
            return seviyeDus;

        seviyeDus = seviyeBul(AramaAgaciDugum.sag, ad, seviye + 1);
        return seviyeDus;
    }

    public int seviyeyiGetir(AramaAgaciDugum AramaAgaciDugum, String ad) {
        return seviyeBul(AramaAgaciDugum, ad, 0);
    }
    public void kisiEkle(Kisi kisi, LinkedList deneyimler, LinkedList egitimDurumu) {
        AramaAgaciDugum ebeveyn = new AramaAgaciDugum();
        AramaAgaciDugum arama = root;

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
                root = new AramaAgaciDugum();
            root.kisi = kisi;
            root.Deneyim = deneyimler;
            root.Egitim = egitimDurumu;
        } else if (kisi.Ad_Soyad.compareTo(ebeveyn.kisi.Ad_Soyad) < 0) {
            ebeveyn.sol = new AramaAgaciDugum();
            ebeveyn.sol.kisi = kisi;
            ebeveyn.sol.Deneyim = deneyimler;
            ebeveyn.sol.Egitim = egitimDurumu;
        } else {
            ebeveyn.sag = new AramaAgaciDugum();
            ebeveyn.sag.kisi = kisi;
            ebeveyn.sag.Deneyim = deneyimler;
            ebeveyn.sag.Egitim = egitimDurumu;
        }
    }

    public ObservableList<String> dugumListesi() {
        return dugumL;
    }

    private void ziyaret(AramaAgaciDugum AramaAgaciDugum) {
        if (dugumL == null) {
            dugumL = FXCollections.observableArrayList(AramaAgaciDugum.kisi.bilgileriGetir() + " | Düzey: " + seviyeyiGetir(root, AramaAgaciDugum.kisi.Ad_Soyad));
        } else {
            if (AramaAgaciDugum.kisi != null)
                dugumL.add(AramaAgaciDugum.kisi.bilgileriGetir() + " | Düzey: " + seviyeyiGetir(root, AramaAgaciDugum.kisi.Ad_Soyad));
        }
    }

    public void kisiGuncelle(String kisininIsmi, AramaAgaciDugum yeniBilgiler) {
        guncelle(root, kisininIsmi, yeniBilgiler);
    }

    private void guncelle(AramaAgaciDugum AramaAgaciDugum, String kisininIsmi, AramaAgaciDugum yeniBilgiler) {
        if (AramaAgaciDugum == null)
            return;
        else if (kisininIsmi.compareTo(AramaAgaciDugum.kisi.Ad_Soyad) == 0)
            AramaAgaciDugum = yeniBilgiler;
        else if (kisininIsmi.compareTo(AramaAgaciDugum.kisi.Ad_Soyad) < 0)
            guncelle(AramaAgaciDugum.sol, kisininIsmi, yeniBilgiler);
        else
            guncelle(AramaAgaciDugum.sag, kisininIsmi, yeniBilgiler);
    }

    private AramaAgaciDugum Successor(AramaAgaciDugum AramaAgaciDugum) {
        AramaAgaciDugum ebeveyn = AramaAgaciDugum;
        AramaAgaciDugum Successor = AramaAgaciDugum;
        AramaAgaciDugum current = AramaAgaciDugum.sag;

        while (current != null) {
            ebeveyn = Successor;
            Successor = current;
            current = current.sol;
        }
        if (Successor != AramaAgaciDugum.sag) {
            ebeveyn.sol = Successor.sag;
            Successor.sag = AramaAgaciDugum.sag;
        }
        return Successor;
    }

    public boolean kisiSil(String kisiAdi) {
        AramaAgaciDugum current = root;
        AramaAgaciDugum ebeveyn = root;
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
            AramaAgaciDugum Successor = Successor(current);
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
        dugumL = null;
        duzey = 0;
        koktenSaga(root);
        return this.dugumL;
    }

    private void koktenSaga(AramaAgaciDugum AramaAgaciDugum) {
        if (AramaAgaciDugum == null)
            return;
        ziyaret(AramaAgaciDugum);
        koktenSaga(AramaAgaciDugum.sol);
        koktenSaga(AramaAgaciDugum.sag);
    }
    //Sol-Root-Sağ
    public ObservableList<String> inorder() {
        dugumL = null;
        duzey = 0;
        soldanSaga(root);
        return this.dugumL;
    }

    private void soldanSaga(AramaAgaciDugum AramaAgaciDugum) {
        if (AramaAgaciDugum == null)
            return;
        soldanSaga(AramaAgaciDugum.sol);
        ziyaret(AramaAgaciDugum);
        duzey++;
        soldanSaga(AramaAgaciDugum.sag);
    }
    //Sol-Sağ-Root
    public ObservableList<String> postorder() {
        dugumL = null;
        duzey = 0;
        soldanKoke(root);
        return this.dugumL;
    }

    private void soldanKoke(AramaAgaciDugum AramaAgaciDugum) {
        if (AramaAgaciDugum == null)
            return;
        soldanKoke(AramaAgaciDugum.sol);
        soldanKoke(AramaAgaciDugum.sag);
        ziyaret(AramaAgaciDugum);
        duzey++;
    }

    public AramaAgaciDugum kisiAra(String kisininIsmi) {
        return this.aramaYap(this.root, kisininIsmi);
    }

    private AramaAgaciDugum aramaYap(AramaAgaciDugum aramaAgaciDugum, String kisininIsmi) {
        if (aramaAgaciDugum == null) {
            return null;
        } else if (kisininIsmi.compareTo(aramaAgaciDugum.kisi.Ad_Soyad) == 0) {
            return aramaAgaciDugum;
        } else {
            return kisininIsmi.compareTo(aramaAgaciDugum.kisi.Ad_Soyad) < 0 ? aramaYap(aramaAgaciDugum.sol, kisininIsmi) : aramaYap(aramaAgaciDugum.sag, kisininIsmi);
        }
    }

    public AramaAgaciDugum ehliyetAra(String ehliyetTipi) {
        return this.ehliyetTipi(this.root, ehliyetTipi);
    }
    private AramaAgaciDugum ehliyetTipi(AramaAgaciDugum aramaAgaciDugum, String ehliyetTipi) {
        if (aramaAgaciDugum == null) {
            return null;
        } else if (ehliyetTipi.compareTo(aramaAgaciDugum.kisi.ehliyetBilgisi) == 0) {
            return aramaAgaciDugum;
        } else {
            return ehliyetTipi.compareTo(aramaAgaciDugum.kisi.ehliyetBilgisi) < 0 ? this.ehliyetTipi(aramaAgaciDugum.sol, ehliyetTipi) : this.ehliyetTipi(aramaAgaciDugum.sag, ehliyetTipi);
        }
    }

    public AramaAgaciDugum dilAra(String dil) {
        return this.dilbilgisi(this.root, dil);
    }

    private AramaAgaciDugum dilbilgisi(AramaAgaciDugum aramaAgaciDugum, String dil) {
        if (aramaAgaciDugum == null) {
            return null;
        } else if (dil.compareTo(aramaAgaciDugum.kisi.YabanciDil) == 0) {
            return aramaAgaciDugum;
        } else {
            return dil.compareTo(aramaAgaciDugum.kisi.YabanciDil) < 0 ? this.dilbilgisi(aramaAgaciDugum.sol, dil) : this.dilbilgisi(aramaAgaciDugum.sag, dil);
        }
    }


}