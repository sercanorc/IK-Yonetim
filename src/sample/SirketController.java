package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import sample.eleman.Kisi;
import sample.sirket.Sirket;
import sample.veriYapilari.binarySearchTree.AramaAgaciDugum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

//Şirket kayıtt ve kontrollerinden sorumlu sınıf elemanların listelenmesi ve kontrolleri bu sınıf ile yönetilir
public class SirketController implements Initializable {
    public static Hashtable SirketTablosu;
    public static Sirket sirket;
    ObservableList<Kisi> persons= FXCollections.observableArrayList();
    FilteredList<String> filteredList;
    public String ayrintilar = " ";
    private Parent guı;
    private Sirket yeniSirket;

    @FXML
    private TextField isYeriAdi;
    @FXML
    private Label lblSistemdekiSirket;
    @FXML
    private ListView<String> listBasvurular;
    @FXML
    private TextField elemanİsim;
    @FXML
    private TextField ehliyetTipi;
    @FXML
    private TextField dil;

    @Override   //Sistemde şirket varsa bilgilerini getirir
    public void initialize(URL location, ResourceBundle resources) {
        if (sirket != null) {
            isYeriAdi.setText(sirket.Ad);
            lblSistemdekiSirket.setText("Sistemdeki Şirket: " + sirket.Ad );
        }
    }

    public void AnaSayfa() throws Exception {
        guı = FXMLLoader.load(getClass().getResource("anaEkran.fxml"));
        Main.windows.setScene(new Scene(guı));
        if (sirket != null) {
            sirket = null;
        }
    }

    public void Kayit() throws Exception {
        if (!isYeriAdi.getText().isEmpty() ) {
            if (sirket != null) {
                yeniSirket = new Sirket(isYeriAdi.getText());

                SirketTablosu.remove(sirket.Ad);
                SirketTablosu.put(yeniSirket.Ad, yeniSirket);
            } else {
                yeniSirket = new Sirket(isYeriAdi.getText());
                if (SirketTablosu == null) {
                    SirketTablosu = new Hashtable();
                    SirketTablosu.put(yeniSirket.Ad, yeniSirket);
                } else {
                    SirketTablosu.put(yeniSirket.Ad, yeniSirket);
                }
            }
            AnaSayfa();
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
            AramaAgaciDugum secilen = ElemanController.Kisiler.kisiAra(kisiBilgileri[0]);

            ayrintilar += "Ad-Soyad:\t\t\t" + secilen.kisi.Ad_Soyad + "\n" +
                    "Adres:\t\t\t" + secilen.kisi.Adres + "\n" +
                    "Telefon:\t\t\t" + secilen.kisi.Telefon + "\n" +
                    "Eposta:\t\t\t" + secilen.kisi.Eposta + "\n" +
                    "Dogum Tarihi:\t\t\t" + secilen.kisi.DogumTarihi + "\n" +
                    "YabanciDil:\t\t" + secilen.kisi.YabanciDil + "\n"+
                    "EhliyetBilgisi:\t\t" + secilen.kisi.ehliyetBilgisi + "\n";



            if (secilen.Deneyim != null)
                ayrintilar += "\nDeneyimler;\n\n" + secilen.Deneyim.listele() + "\n";
            if (secilen.Egitim != null)
                ayrintilar += "\nEğitim Bilgileri;\n\n" + secilen.Egitim.listele() + "\n";
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


    public void Ara(){
        String ad= elemanİsim.getText();
        String ehliyet=ehliyetTipi.getText();
        String dilbilgisi= dil.getText();
        if(!elemanİsim.getText().isEmpty()){
            System.out.println(ElemanController.Kisiler.kisiAra(ad).kisi.bilgileriGetir());
        }else if(!ehliyetTipi.getText().isEmpty()){
            System.out.println(ElemanController.Kisiler.ehliyetAra(ehliyet).kisi.bilgileriGetir());
        } else if(!dil.getText().isEmpty()){
            System.out.println(ElemanController.Kisiler.dilAra(dilbilgisi).kisi.bilgileriGetir());
        }else{
            System.out.println("bos bırakmayın");
        }
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
