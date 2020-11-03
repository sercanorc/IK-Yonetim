package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.eleman.Deneyim;
import sample.eleman.Egitim;
import sample.eleman.Kisi;
import sample.veriYapilari.LinkedList.LinkedList;
import sample.veriYapilari.binarySearchTree.IkiliAramaAgaci;
import sample.veriYapilari.binarySearchTree.ikiliAramaAgaciDugum;

import java.net.URL;
import java.util.Enumeration;
import java.util.Optional;
import java.util.ResourceBundle;

public class ElemanController implements Initializable {
    @FXML
    private TextField ad;
    @FXML
    private TextArea adres;
    @FXML
    private TextField telefon;
    @FXML
    private TextField eposta;
    @FXML
    private TextField dogumTarihi;
    @FXML
    private TextField yabanciDil;
    @FXML
    private TextField ehliyetBilgisi;
    @FXML
    private ListView deneyimListesi;
    @FXML
    private ListView egitimListesi;
    @FXML
    private Label lblSistemdekiKisi;

    public static IkiliAramaAgaci Kisiler;
    public static ikiliAramaAgaciDugum SistemdekiKisi;

    private Parent arayuz;
    private Kisi yeniKisi; // kayıt edeilecek kisi
    private LinkedList deneyimleri = new LinkedList(); //kayıt edilen kisini Deneyimleri
    public ObservableList<String> lDeneyimler = null;
    private LinkedList egitimBilgileri = new LinkedList();//kayıt edilen kisini egitim bilgileri
    public ObservableList<String> lEgitim = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SistemdekiKisi == null) {
            deneyimListesi.setItems(lDeneyimler);
            egitimListesi.setItems(lEgitim);
        } else {
            lblSistemdekiKisi.setText("Sistemdeki Kişi : " + SistemdekiKisi.kisi.bilgileriGetir());
            ad.setText(SistemdekiKisi.kisi.Ad_Soyad);
            adres.setText(SistemdekiKisi.kisi.Adres);
            telefon.setText(SistemdekiKisi.kisi.Telefon);
            eposta.setText(SistemdekiKisi.kisi.Eposta);
            dogumTarihi.setText(SistemdekiKisi.kisi.DogumTarihi);
            yabanciDil.setText(SistemdekiKisi.kisi.YabanciDil);
            ehliyetBilgisi.setText(SistemdekiKisi.kisi.ehliyetBilgisi);

            if (SistemdekiKisi.Deneyimler != null) {
                for (int i = SistemdekiKisi.Deneyimler.Boyut; i > 0; i--) {
                    if (lDeneyimler != null)
                        lDeneyimler.add(SistemdekiKisi.Deneyimler.DisplayElements(i));
                    else
                        lDeneyimler = FXCollections.observableArrayList(SistemdekiKisi.Deneyimler.DisplayElements(i));
                }
                deneyimListesi.setItems(lDeneyimler);
            }

            if (SistemdekiKisi.EgitimDurumu != null) {
                for (int i = SistemdekiKisi.EgitimDurumu.Boyut; i > 0; i--) {
                    if (lEgitim != null)
                        lEgitim.add(SistemdekiKisi.EgitimDurumu.DisplayElements(i));
                    else
                        lEgitim = FXCollections.observableArrayList(SistemdekiKisi.EgitimDurumu.DisplayElements(i));
                }
                egitimListesi.setItems(lEgitim);
            }
        }
    }

    public void KarsilamaEkraninaDon() throws Exception {
        arayuz = FXMLLoader.load(getClass().getResource("anaEkran.fxml"));
        Main.pencere.setScene(new Scene(arayuz));
        System.out.println("Karşılama Ekranına Geri Dönüldü.");

        if (SistemdekiKisi != null) {
            SistemdekiKisi = null;
            lDeneyimler = null;
            lEgitim = null;
        }
    }

    public void EgitimBilgisiniSil() {
        if (egitimListesi.getSelectionModel().getSelectedIndex() != -1) {
            int secili = egitimListesi.getSelectionModel().getSelectedIndex();

            if (SistemdekiKisi != null)
                SistemdekiKisi.EgitimDurumu.DeletePos(secili + 1);
            else
                egitimBilgileri.DeletePos(secili + 1);

            lEgitim.remove(secili);
        } else {
            System.out.println("Seçim yapmalısınız!");
        }
    }

    public void EgitimBilgisiEkle() {
        Dialog<Egitim> dialog = new Dialog<>();
        ButtonType ekle = new ButtonType("Ekle", ButtonBar.ButtonData.OK_DONE);
        ButtonType iptal = new ButtonType("İptal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(ekle, iptal);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextField egitimAdi = new TextField();
        TextField egitimTuru = new TextField();
        TextField egitimBolumu = new TextField();
        TextField egitimBaslangic = new TextField();
        TextField egitimBitis = new TextField();
        TextField egitimNotOrtalamasi = new TextField();
        egitimAdi.setPromptText("Okul Adı");
        egitimTuru.setPromptText("Tur");
        egitimBolumu.setPromptText("Bölüm");
        egitimBaslangic.setPromptText("egitimBaslangıcı");
        egitimBitis.setPromptText("egitimBaslangıcı");
        egitimNotOrtalamasi.setPromptText("Not Ortalaması");

        grid.add(new Label("Ad:"), 0, 0);
        grid.add(egitimAdi, 1, 0);
        grid.add(new Label("Tür:"), 0, 1);
        grid.add(egitimTuru, 1, 1);
        grid.add(new Label("Bölüm:"), 0, 2);
        grid.add(egitimBolumu, 1, 2);
        grid.add(new Label("Başlangıç:"), 0, 3);
        grid.add(egitimBaslangic, 1, 3);
        grid.add(new Label("Bitiş:"), 0, 4);
        grid.add(egitimBitis, 1, 4);
        grid.add(new Label("Not Ortalaması:"), 0, 5);
        grid.add(egitimNotOrtalamasi, 1, 5);

        Node ekleOnayi = dialog.getDialogPane().lookupButton(ekle);
        ekleOnayi.setDisable(true);

        egitimAdi.textProperty().addListener((observable, oldValue, newValue) -> {
            ekleOnayi.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> egitimAdi.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ekle) {
                return new Egitim(egitimAdi.getText(), egitimTuru.getText(), egitimBolumu.getText(), Integer.valueOf(egitimBaslangic.getText())
                            ,Integer.valueOf(egitimBitis.getText()) ,
                            Double.parseDouble(egitimNotOrtalamasi.getText()));
            }
            return null;
        });

        Optional<Egitim> sonuc = dialog.showAndWait();

        sonuc.ifPresent(e -> {
            if (SistemdekiKisi != null) {
                if (SistemdekiKisi.EgitimDurumu == null)
                    SistemdekiKisi.EgitimDurumu = new LinkedList();
                SistemdekiKisi.EgitimDurumu.InsertLast(e);
            } else {
                egitimBilgileri.InsertLast(e);
            }

            if (lEgitim != null)
                lEgitim.add(e.Bitis + " : " + e.Okul_Ad +" - " + e.Tur + " - " + e.Bolum + " : " +
                            e.NotOrtalamasi);
            else
                lEgitim = FXCollections.observableArrayList(e.Baslangic+" : " + e.Bitis + " : " +
                            e.Okul_Ad +" - " + e.Tur + " - " + e.Bolum + " : " + e.NotOrtalamasi);

            egitimListesi.setItems(lEgitim);
        });
    }

    public void DeneyimSil() {
        if (deneyimListesi.getSelectionModel().getSelectedIndex() != -1) {
            int secili = deneyimListesi.getSelectionModel().getSelectedIndex();

            if (SistemdekiKisi != null)
                SistemdekiKisi.Deneyimler.DeletePos(secili + 1);
            else
                deneyimleri.DeletePos(secili + 1);

            lDeneyimler.remove(secili);

        } else {
           System.out.println("Seçim yapmalısınz");
        }
    }

    public void DeneyimEkle() {
        Dialog<Deneyim> dialog = new Dialog<>();

        ButtonType ekle = new ButtonType("Ekle", ButtonBar.ButtonData.OK_DONE);
        ButtonType iptal = new ButtonType("İptal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(ekle, iptal);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextField deneyimAdi = new TextField();
        TextArea deneyimAdresi = new TextArea();
        deneyimAdresi.setMaxHeight(100);
        TextField deneyimPozisyon = new TextField();TextField deneyimTecrübe = new TextField();
        deneyimAdi.setPromptText("İşyeri Adı");
        deneyimAdresi.setPromptText("Şirket Adresi");
        deneyimPozisyon.setPromptText("Şirketteki Pozisyonunuz ve Göreviniz");
        deneyimTecrübe.setPromptText("Tecrübe süresi");

        grid.add(new Label("Ad:"), 0, 0);
        grid.add(deneyimAdi, 1, 0);
        grid.add(new Label("Adres:"), 0, 1);
        grid.add(deneyimAdresi, 1, 1);
        grid.add(new Label("Pozisyon:"), 0, 2);
        grid.add(deneyimPozisyon, 1, 2);
        grid.add(new Label("Tecrübe:"), 0, 3);
        grid.add(deneyimTecrübe, 1, 3);

        Node ekleOnayi = dialog.getDialogPane().lookupButton(ekle);
        ekleOnayi.setDisable(true);

        deneyimAdi.textProperty().addListener((observable, oldValue, newValue) -> {
            ekleOnayi.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> deneyimAdi.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ekle) {
                return new Deneyim(deneyimAdi.getText(), deneyimAdresi.getText(), deneyimPozisyon.getText(),Double.parseDouble(deneyimTecrübe.getText()));
            }
            return null;
        });

        Optional<Deneyim> sonuc = dialog.showAndWait();

        sonuc.ifPresent(d -> {
            if (SistemdekiKisi != null) {
                if (SistemdekiKisi.Deneyimler == null)
                    SistemdekiKisi.Deneyimler = new LinkedList();

                SistemdekiKisi.Deneyimler.InsertLast(d);
            } else {
                deneyimleri.InsertLast(d);
            }

            if (lDeneyimler != null)
                lDeneyimler.add(d.Deneyim + " - " +d.Adres + " - " + d.Tecrübe_Pozisyon +" - " +d.tecrübe);
            else
                lDeneyimler = FXCollections.observableArrayList(d.Deneyim + " - " +d.Adres + " - " + d.Tecrübe_Pozisyon +" - " +d.tecrübe);

            deneyimListesi.setItems(lDeneyimler);
        });
    }

    public void SistemeKaydet() throws Exception {
        if (ad.getText().isEmpty() != true &&
                adres.getText().isEmpty() != true &&
                telefon.getText().isEmpty() != true &&
                eposta.getText().isEmpty() != true &&
                dogumTarihi.getText().isEmpty() != true &&
                yabanciDil.getText().isEmpty() != true
        ) {

            if (SistemdekiKisi != null) {
                Kisiler.kisiSil(SistemdekiKisi.kisi.Ad_Soyad);
                String kisiAd = SistemdekiKisi.kisi.Ad_Soyad;
                SistemdekiKisi.kisi = null;
                SistemdekiKisi.kisi = new Kisi(ad.getText(), adres.getText(),
                        telefon.getText(), eposta.getText(),dogumTarihi.getText(), yabanciDil.getText(),ehliyetBilgisi.getText()
                        );

                Kisiler.kisiEkle(SistemdekiKisi.kisi,SistemdekiKisi.Deneyimler,SistemdekiKisi.EgitimDurumu);
            } else {
                yeniKisi = new Kisi(ad.getText(), adres.getText(),
                        telefon.getText(), eposta.getText(),dogumTarihi.getText(), yabanciDil.getText(),ehliyetBilgisi.getText()
                        );

                if (Kisiler == null || Kisiler.dugumSayisi() == 0) {
                    ikiliAramaAgaciDugum d = new ikiliAramaAgaciDugum(yeniKisi);
                    Kisiler = new IkiliAramaAgaci(d, deneyimleri, egitimBilgileri);
                } else {
                    Kisiler.kisiEkle(yeniKisi, deneyimleri, egitimBilgileri);
                }
            }
            KarsilamaEkraninaDon();
        } else {
            if (SistemdekiKisi == null) {
                System.out.println("Kayıt hatası");
            } else {
                System.out.println("Güncelleme hatası");
            }
        }
    }

}