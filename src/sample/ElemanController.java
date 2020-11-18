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
import sample.veriYapilari.binarySearchTree.AramaAgaci;
import sample.veriYapilari.binarySearchTree.AramaAgaciDugum;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
    //Eleman kayıdı ve kontrollerinden sorumlu sınıftır
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
    private Label lblDKisi;

    public static AramaAgaci Kisiler;
    public static AramaAgaciDugum DKisi;

    private Parent guı;
    private Kisi yeniKisi; // kayıt edeilecek kisi
    private LinkedList deneyimleri = new LinkedList(); //kayıt edilen kisini Deneyimleri
    public ObservableList<String> deneyimlerListesi = null;
    private LinkedList egitimBilgileri = new LinkedList();//kayıt edilen kisini egitim bilgileri
    public ObservableList<String> egitimlerListesi = null;

    @Override   //Ekran yüklenirken sistemde kişi var ise bilgilerini getiri
    public void initialize(URL location, ResourceBundle resources) {
        if (DKisi == null) {
            deneyimListesi.setItems(deneyimlerListesi);
            egitimListesi.setItems(egitimlerListesi);
        } else {
            lblDKisi.setText("Sistemdeki Kişi : " + DKisi.kisi.bilgileriGetir());
            ad.setText(DKisi.kisi.Ad_Soyad);
            adres.setText(DKisi.kisi.Adres);
            telefon.setText(DKisi.kisi.Telefon);
            eposta.setText(DKisi.kisi.Eposta);
            dogumTarihi.setText(DKisi.kisi.DogumTarihi);
            yabanciDil.setText(DKisi.kisi.YabanciDil);
            ehliyetBilgisi.setText(DKisi.kisi.ehliyetBilgisi);

            if (DKisi.Deneyim != null) {
                for (int i = DKisi.Deneyim.Boyut; i > 0; i--) {
                    if (deneyimlerListesi != null)
                        deneyimlerListesi.add(DKisi.Deneyim.DisplayElements(i));
                    else
                        deneyimlerListesi = FXCollections.observableArrayList(DKisi.Deneyim.DisplayElements(i));
                }
                deneyimListesi.setItems(deneyimlerListesi);
            }

            if (DKisi.Egitim != null) {
                for (int i = DKisi.Egitim.Boyut; i > 0; i--) {
                    if (egitimlerListesi != null)
                        egitimlerListesi.add(DKisi.Egitim.DisplayElements(i));
                    else
                        egitimlerListesi = FXCollections.observableArrayList(DKisi.Egitim.DisplayElements(i));
                }
                egitimListesi.setItems(egitimlerListesi);
            }
        }
    }

    // Sistemde kişi yoksa eleman ekleme işlemini varsa güncelleme işlemini gerçekleştirir.
    public void Kayit() throws Exception {
            if (ad.getText().isEmpty() != true &&
                    adres.getText().isEmpty() != true &&
                    telefon.getText().isEmpty() != true &&
                    eposta.getText().isEmpty() != true &&
                    dogumTarihi.getText().isEmpty() != true &&
                    yabanciDil.getText().isEmpty() != true &&
                    ehliyetBilgisi.getText().isEmpty() !=true

            ) {

                if (DKisi != null) {
                    Kisiler.kisiSil(DKisi.kisi.Ad_Soyad);
                    String kisiAd = DKisi.kisi.Ad_Soyad;
                    DKisi.kisi = null;
                    DKisi.kisi = new Kisi(ad.getText(), adres.getText(),
                            telefon.getText(), eposta.getText(),dogumTarihi.getText(), yabanciDil.getText(),ehliyetBilgisi.getText()
                    );

                    Kisiler.kisiEkle(DKisi.kisi,DKisi.Deneyim,DKisi.Egitim);
                } else {
                    yeniKisi = new Kisi(ad.getText(), adres.getText(),
                            telefon.getText(), eposta.getText(),dogumTarihi.getText(), yabanciDil.getText(),ehliyetBilgisi.getText()
                    );

                    if (Kisiler == null || Kisiler.dugumSayisi() == 0) {
                        AramaAgaciDugum d = new AramaAgaciDugum(yeniKisi);
                        Kisiler = new AramaAgaci(d, deneyimleri, egitimBilgileri);
                    } else {
                        Kisiler.kisiEkle(yeniKisi, deneyimleri, egitimBilgileri);
                    }
                }
                AnaSayfa();
            } else {
                if (DKisi == null) {
                    System.out.println("Kayıt hatası");
                } else {
                    System.out.println("Güncelleme hatası");
                }
            }
        }

    public void AnaSayfa() throws Exception {
        guı = FXMLLoader.load(getClass().getResource("anaEkran.fxml"));
        Main.windows.setScene(new Scene(guı));;

        if (DKisi != null) {
            DKisi = null;
            deneyimlerListesi = null;
            egitimlerListesi = null;
        }
    }

    public void EgitimSil() {
        if (egitimListesi.getSelectionModel().getSelectedIndex() != -1) {
            int secili = egitimListesi.getSelectionModel().getSelectedIndex();

            if (DKisi != null)
                DKisi.Egitim.DeletePos(secili + 1);
            else
                egitimBilgileri.DeletePos(secili + 1);

            egitimlerListesi.remove(secili);
        } else {
            System.out.println("Seçim yapmalısınız!");
        }
    }

    public void EgitimEkle() {
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
            if (DKisi != null) {
                if (DKisi.Egitim == null)
                    DKisi.Egitim = new LinkedList();
                DKisi.Egitim.InsertLast(e);
            } else {
                egitimBilgileri.InsertLast(e);
            }

            if (egitimlerListesi != null)
                egitimlerListesi.add(e.Bitis + " : " + e.Okul_Ad +" - " + e.Tur + " - " + e.Bolum + " : " +
                            e.NotOrtalamasi);
            else
                egitimlerListesi = FXCollections.observableArrayList(e.Baslangic+" : " + e.Bitis + " : " +
                            e.Okul_Ad +" - " + e.Tur + " - " + e.Bolum + " : " + e.NotOrtalamasi);

            egitimListesi.setItems(egitimlerListesi);
        });
    }

    public void DeneyimSil() {
        if (deneyimListesi.getSelectionModel().getSelectedIndex() != -1) {
            int secili = deneyimListesi.getSelectionModel().getSelectedIndex();

            if (DKisi != null)
                DKisi.Deneyim.DeletePos(secili + 1);
            else
                deneyimleri.DeletePos(secili + 1);

            deneyimlerListesi.remove(secili);

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
            if (DKisi != null) {
                if (DKisi.Deneyim == null)
                    DKisi.Deneyim = new LinkedList();

                DKisi.Deneyim.InsertLast(d);
            } else {
                deneyimleri.InsertLast(d);
            }

            if (deneyimlerListesi != null)
                deneyimlerListesi.add(d.Deneyim + " - " +d.Adres + " - " + d.Tecrübe_Pozisyon +" - " +d.tecrübe);
            else
                deneyimlerListesi = FXCollections.observableArrayList(d.Deneyim + " - " +d.Adres + " - " + d.Tecrübe_Pozisyon +" - " +d.tecrübe);

            deneyimListesi.setItems(deneyimlerListesi);
        });
    }


}