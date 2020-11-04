package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import sample.eleman.Kisi;
import sample.sirket.Sirket;
import sample.veriYapilari.binarySearchTree.IkiliAramaAgaci;
import sample.veriYapilari.binarySearchTree.ikiliAramaAgaciDugum;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
    // Program açıldığın karşılama ekranıdır sistemdeki kişilerin ve şirketlerin listelenmesi,yönlendirmelerin yapılması
    // gibi işlevler bu sınıf aracılığıyla  gerçekleştirilmektedir.
public class Controller implements Initializable {
    @FXML
    public ListView<String> kisiler;
    @FXML
    public ListView<String> sirketler;

    ObservableList<String> sirketL = FXCollections.observableArrayList();

    private Parent arayuz;

    @Override   //Ekran yüklenirken kişi ağacını ve şirket hash tablosununu oluşturmayı sağlar
    public void initialize(URL location, ResourceBundle resources) {
        try {
            kisiAgaci();
            sirketTablosu();
        } catch (IOException exception) {
            System.out.println(exception);
        }

        kisiler.setItems(ElemanController.Kisiler.inorder());

        Enumeration e = SirketController.Sirketler.elements();
        while (e.hasMoreElements()) {
            Sirket s = (Sirket) e.nextElement();
            sirketL.add(s.Ad);
        }
        sirketler.setItems(sirketL);
    }

    private void kisiAgaci() {
        try{
            if (ElemanController.Kisiler == null || ElemanController.Kisiler.dugumSayisi() == 0) {
                String satir = null;
                Kisi eklenecekKisi = null;
                ikiliAramaAgaciDugum d = new ikiliAramaAgaciDugum();
                ElemanController.Kisiler = null;

                InputStream elemanDosyasi = new FileInputStream("eleman.txt");
                InputStreamReader eOkuyucu = new InputStreamReader(elemanDosyasi, Charset.forName("UTF-8"));
                BufferedReader okuyucu = new BufferedReader(eOkuyucu);

                int i = 0;
                while ((satir = okuyucu.readLine()) != null) {
                    String[] eklenecekKisininBilgileri = satir.split(", ");
                    eklenecekKisi = new Kisi(eklenecekKisininBilgileri[0],
                            eklenecekKisininBilgileri[1], eklenecekKisininBilgileri[2],
                            eklenecekKisininBilgileri[3], eklenecekKisininBilgileri[4],eklenecekKisininBilgileri[5],eklenecekKisininBilgileri[6]);
                    if (i == 0) {
                        d.kisi = eklenecekKisi;
                        ElemanController.Kisiler = new IkiliAramaAgaci(d, null, null);
                    } else {
                        ElemanController.Kisiler.kisiEkle(eklenecekKisi, null, null);
                    }
                    i++;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }

    private void sirketTablosu() throws IOException {
        if(SirketController.Sirketler == null) {
            String satir = null;
            Sirket eklenecekSirket = null;
            SirketController.Sirketler = null;
            InputStream sirketDosyasi = new FileInputStream("sirket.txt");
            InputStreamReader eOkuyucu = new InputStreamReader(sirketDosyasi, Charset.forName("UTF-8"));
            BufferedReader okuyucu = new BufferedReader(eOkuyucu);

            int i = 0;
            while ((satir = okuyucu.readLine()) != null) {
                String[] eklenecekSirketinBilgileri = satir.split(", ");
                eklenecekSirket = new Sirket(eklenecekSirketinBilgileri[0]);
                if (i == 0) {
                    SirketController.Sirketler = new Hashtable();
                    SirketController.Sirketler.put(eklenecekSirket.Ad,eklenecekSirket);
                } else {
                    SirketController.Sirketler.put(eklenecekSirket.Ad,eklenecekSirket);
                }
                i++;
            }
        }
    }

    //Listeden seçilen elemanın sisteme giriş yapmasını sağlar.
    public void Eleman() {
        try{
            if (kisiler.getSelectionModel().getSelectedItem() != null) {
                String[] sistemdekiBilgileri = kisiler.getSelectionModel().getSelectedItem().split(" \\| ");
                ElemanController.SistemdekiKisi = ElemanController.Kisiler.kisiAra(sistemdekiBilgileri[0]);
                arayuz = FXMLLoader.load(getClass().getResource("eleman.fxml"));
                Main.pencere.setScene(new Scene(arayuz));
            } else {
                System.out.println("Eleman Secin");
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
    //Listeden seçilen sirketın sisteme giriş yapmasını sağlar.
    public void Sirket() {
        try {
            if (sirketler.getSelectionModel().getSelectedItem() != null) {
                String anahtar = sirketler.getSelectionModel().getSelectedItem();
                SirketController.sistemdekiSirket = (Sirket) SirketController.Sirketler.get(anahtar);
                arayuz = FXMLLoader.load(getClass().getResource("sirket.fxml"));
                Main.pencere.setScene(new Scene(arayuz));
            }
            else{
                System.out.println("Sirket Secin");
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void ElemanKaydi() {

        try {
            arayuz = FXMLLoader.load(getClass().getResource("elemanKayit.fxml"));
            Main.pencere.setTitle("Eleman Kayıt Ekranı");
            Main.pencere.setScene(new Scene(arayuz));
            System.out.println("Eleman Kayıt Ekranına Geçildi.");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void SirketKaydi() {
        try{
            arayuz = FXMLLoader.load(getClass().getResource("sirketKayit.fxml"));
            Main.pencere.setTitle("Şirket Kayıt Ekranı");
            Main.pencere.setScene(new Scene(arayuz));
            System.out.println("Şirket Kayıt Ekranına Geçildi.");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void elemaniSil() {
        if (kisiler.getSelectionModel().getSelectedItem() != null) {
            String[] silinecekKisininBilgileri = kisiler.getSelectionModel().getSelectedItem().split(" \\| ");
            ElemanController.Kisiler.kisiSil(silinecekKisininBilgileri[0]);
            kisiler.setItems(ElemanController.Kisiler.inorder());
        } else {
            System.out.println("Eleman seçimi yapılmalı");
        }
    }

}
