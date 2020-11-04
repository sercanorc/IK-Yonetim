package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import sample.eleman.Kisi;
import sample.sirket.Sirket;
import sample.veriYapilari.binarySearchTree.ikiliAramaAgaciDugum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

    //Şirket kayıtt ve kontrollerinden sorumlu sınıf elemanların listelenmesi ve kontrolleri bu sınıf ile yönetilir
public class SirketController implements Initializable {
    public static Hashtable Sirketler;
    public static Sirket sistemdekiSirket;

    private Parent arayuz;
    private Sirket kaydedilecekSirket;

    @FXML
    private TextField isYeriAdi;
    @FXML
    private Label lblSistemdekiSirket;
    @FXML
    private ListView<String> listBasvurular;
    @FXML
    private TextField elemanİsim;
    @FXML
    private TextField deneyimSuresi;
    @FXML
    private TextField elemanYas;
    @FXML
    private TextField ehliyetTipi;
    private ObservableList<String> bilgiListesi = FXCollections.observableArrayList();
    public String ayrintilar = " ";


    @Override   //Sistemde şirket vara bilgilerini getirir
    public void initialize(URL location, ResourceBundle resources) {
        if (sistemdekiSirket != null) {
            isYeriAdi.setText(sistemdekiSirket.Ad);

            lblSistemdekiSirket.setText("Sistemdeki Şirket: " + sistemdekiSirket.Ad );
        }
    }

    public void KarsilamaEkraninaDon() throws Exception {
        arayuz = FXMLLoader.load(getClass().getResource("anaEkran.fxml"));
        Main.pencere.setTitle("İnsan Kaynakları Bilgi Sistemi");
        Main.pencere.setScene(new Scene(arayuz));
        System.out.println("Karşılama Ekranına Geri Dönüldü.");
        if (sistemdekiSirket != null) {
            sistemdekiSirket = null;
        }
    }

    public void SistemeKaydet() throws Exception {
        if (!isYeriAdi.getText().isEmpty() ) {
            if (sistemdekiSirket != null) {
                kaydedilecekSirket = new Sirket(isYeriAdi.getText());

                Sirketler.remove(sistemdekiSirket.Ad);
                Sirketler.put(kaydedilecekSirket.Ad, kaydedilecekSirket);
            } else {
                kaydedilecekSirket = new Sirket(isYeriAdi.getText());
                if (Sirketler == null) {
                    Sirketler = new Hashtable();
                    Sirketler.put(kaydedilecekSirket.Ad, kaydedilecekSirket);
                } else {
                    Sirketler.put(kaydedilecekSirket.Ad, kaydedilecekSirket);
                }
            }
            KarsilamaEkraninaDon();
        } else {
            System.out.println("Hata");
        }
    }

    //Tüm kisileri listeler
    public void BasvurulariListele() {
        listBasvurular.setItems(ElemanController.Kisiler.inorder());
    }

    //Listesinden seçilen kişinin ayrıntılı bilgisini gösterir
    public void AyrintilariGoster() {
        if (listBasvurular.getSelectionModel().getSelectedItem() != null) {
            String[] kisiBilgileri = listBasvurular.getSelectionModel().getSelectedItem().toString().split(" \\| ");
            ikiliAramaAgaciDugum secilen = ElemanController.Kisiler.kisiAra(kisiBilgileri[0]);

            ayrintilar += "Ad-Soyad:\t\t\t" + secilen.kisi.Ad_Soyad + "\n" +
                    "Adres:\t\t\t" + secilen.kisi.Adres + "\n" +
                    "Telefon:\t\t\t" + secilen.kisi.Telefon + "\n" +
                    "Eposta:\t\t\t" + secilen.kisi.Eposta + "\n" +
                    "Dogum Tarihi:\t\t\t" + secilen.kisi.DogumTarihi + "\n" +
                    "YabanciDil:\t\t" + secilen.kisi.YabanciDil + "\n"+
                    "EhliyetBilgisi:\t\t" + secilen.kisi.ehliyetBilgisi + "\n";



            if (secilen.Deneyimler != null)
                ayrintilar += "\nDeneyimler;\n\n" + secilen.Deneyimler.listele() + "\n";
            if (secilen.EgitimDurumu != null)
                ayrintilar += "\nEğitim Bilgileri;\n\n" + secilen.EgitimDurumu.listele() + "\n";
            System.out.println("Ayrıntılı Bilgiler");
            System.out.println(ayrintilar);
        } else {
           System.out.println("Hata");
        }
    }

    // İkili arama ağacının derinliğini ve eleman sayısını bulup konsola yazar.
    public void derinlikveElemanSayisi() {
        Integer elemanSayisi = 0;
        Integer derinlik = 0;
        if (ElemanController.Kisiler != null) {
            elemanSayisi = ElemanController.Kisiler.dugumSayisi();
            derinlik = ElemanController.Kisiler.YaprakSayisi();
            System.out.println("Kişiler ağacındaki eleman sayısı: " + elemanSayisi + "\nKişiler ağacındaki derinlik: " + derinlik);
        } else {
            System.out.println("hata");
        }
    }
    //Root-Sol-Sağ
    public void Preorder() {
        listBasvurular.setItems(ElemanController.Kisiler.preorder());
    }
    //Sol-Root-Sağ
    public void Inorder() {
        listBasvurular.setItems(ElemanController.Kisiler.inorder());
    }
    //Sol-Sağ-Root
    public void Postorder() {
        listBasvurular.setItems(ElemanController.Kisiler.postorder());
    }

    public void ingilizceBilenleriListele() {
      //  listBasvurular.setItems(sistemdekiSirket.Basvuranlar.ingilizceBilenler());
    }

    public void lisansMezunlarıListele(){
    }
    public void deneyimsizKisiler() {
    }

    public void Ara(){
      /*  String ad= elemanİsim.getText();

        System.out.println(sistemdekiSirket.Basvuranlar.adaGoreKisiAra(ad));*/
    }

    public void dosyayaYazdır(){
        File file = new File("ayrıntı.txt");
        try(BufferedWriter br = new BufferedWriter(new FileWriter(file))){
            if(ayrintilar==" "){
                System.out.println("Yazılacak birşey yok");
                return;
            }
            br.write(ayrintilar);
            br.newLine();
            System.out.println("Dosya yazma islemi basarılı");

        } catch (IOException e) {
            System.out.println("Bulunamadı" +file.toString());
        }
    }
}
