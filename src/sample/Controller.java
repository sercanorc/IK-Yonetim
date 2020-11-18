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
import sample.veriYapilari.binarySearchTree.AramaAgaci;
import sample.veriYapilari.binarySearchTree.AramaAgaciDugum;

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
    public ListView<String> kisilerList;
    @FXML
    public ListView<String> sirketlerList;

    ObservableList<String> sirketL = FXCollections.observableArrayList();

    private Parent guı;

    @Override   //Ekran yüklenirken kişi ağacını ve şirket hash tablosununu oluşturmayı sağlar
    public void initialize(URL location, ResourceBundle resources) {
        try {
            kisiAgaci();
            sirketTablosu();
        } catch (IOException exception) {
            System.out.println(exception);
        }

        kisilerList.setItems(ElemanController.Kisiler.inorder());

        Enumeration elements = SirketController.SirketTablosu.elements();
        while (elements.hasMoreElements()) {
            Sirket s = (Sirket) elements.nextElement();
            sirketL.add(s.Ad);
        }
        sirketlerList.setItems(sirketL);
    }

    private void kisiAgaci() {
        try{
            if (ElemanController.Kisiler == null || ElemanController.Kisiler.dugumSayisi() == 0) {
                AramaAgaciDugum dugum = new AramaAgaciDugum();
                String satir = null;
                Kisi aKisi = null;

                InputStream elemanlar = new FileInputStream("eleman.txt");
                InputStreamReader eOkuyucu = new InputStreamReader(elemanlar);
                BufferedReader okuyucu = new BufferedReader(eOkuyucu);

                int i = 0;
                while ((satir = okuyucu.readLine()) != null) {
                    String[] eklenenKisi = satir.split(", ");
                    aKisi = new Kisi(eklenenKisi[0],
                            eklenenKisi[1], eklenenKisi[2],
                            eklenenKisi[3], eklenenKisi[4],eklenenKisi[5],eklenenKisi[6]);
                    if (i == 0) {
                        dugum.kisi = aKisi;
                        ElemanController.Kisiler = new AramaAgaci(dugum, null, null);
                    } else {
                        ElemanController.Kisiler.kisiEkle(aKisi, null, null);
                    }
                    i++;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }

    private void sirketTablosu() throws IOException {
        if(SirketController.SirketTablosu == null) {
            InputStream sirketDosyasi = new FileInputStream("sirket.txt");
            InputStreamReader eOkuyucu = new InputStreamReader(sirketDosyasi, Charset.forName("UTF-8"));
            BufferedReader okuyucu = new BufferedReader(eOkuyucu);
            String satir = null;
            Sirket eklenecekSirketBilgisi = null;

            int i = 0;
            while ((satir = okuyucu.readLine()) != null) {
                String[] SirketBilgileri = satir.split(", ");
                eklenecekSirketBilgisi = new Sirket(SirketBilgileri[0]);
                if (i == 0) {
                    SirketController.SirketTablosu = new Hashtable();
                    SirketController.SirketTablosu.put(eklenecekSirketBilgisi.Ad,eklenecekSirketBilgisi);
                } else {
                    SirketController.SirketTablosu.put(eklenecekSirketBilgisi.Ad,eklenecekSirketBilgisi);
                }
                i++;
            }
        }
    }

    //Listeden seçilen elemanın sisteme giriş yapmasını sağlar.
    public void Eleman() {
        try{
            if (kisilerList.getSelectionModel().getSelectedItem() != null) {
                String[] sistemdekiBilgileri = kisilerList.getSelectionModel().getSelectedItem().split(" \\| ");
                ElemanController.DKisi = ElemanController.Kisiler.kisiAra(sistemdekiBilgileri[0]);
                guı = FXMLLoader.load(getClass().getResource("eleman.fxml"));
                Main.windows.setScene(new Scene(guı));
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
            if (sirketlerList.getSelectionModel().getSelectedItem() != null) {
                String anahtar = sirketlerList.getSelectionModel().getSelectedItem();
                SirketController.sirket = (Sirket) SirketController.SirketTablosu.get(anahtar);
                guı = FXMLLoader.load(getClass().getResource("sirket.fxml"));
                Main.windows.setScene(new Scene(guı));
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
            guı = FXMLLoader.load(getClass().getResource("elemanKayit.fxml"));
            Main.windows.setTitle("Eleman Kayıt Ekranı");
            Main.windows.setScene(new Scene(guı));
            System.out.println("Eleman Kayıt Ekranına Geçildi.");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void SirketKaydi() {
        try{
            guı = FXMLLoader.load(getClass().getResource("sirketKayit.fxml"));
            Main.windows.setTitle("Şirket Kayıt Ekranı");
            Main.windows.setScene(new Scene(guı));
            System.out.println("Şirket Kayıt Ekranına Geçildi.");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void elemaniSil() {
        if (kisilerList.getSelectionModel().getSelectedItem() != null) {
            String[] kisiDeleted = kisilerList.getSelectionModel().getSelectedItem().split(" \\| ");
            ElemanController.Kisiler.kisiSil(kisiDeleted[0]);
            kisilerList.setItems(ElemanController.Kisiler.inorder());
        } else {
            System.out.println("Eleman seçimi yapılmalı");
        }
    }

}
