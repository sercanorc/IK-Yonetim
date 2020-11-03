package sample.eleman;

public class Kisi {
    public String Ad_Soyad;
    public String Adres;
    public String Telefon;
    public String Eposta;
    public String DogumTarihi;
    public String YabanciDil;
    public String ehliyetBilgisi;


    public Kisi() {}

    public Kisi(String ad) {
        Ad_Soyad = ad;
    }

    public Kisi(String ad_Soyad, String adres, String telefon, String eposta,String dogumTarihi,
                 String yabanciDil, String ehliyetBilgisi) {
        this.Ad_Soyad = ad_Soyad;
        this.Adres = adres;
        this.Telefon = telefon;
        this.Eposta = eposta;
        this.DogumTarihi= dogumTarihi;
        this.YabanciDil = yabanciDil;
        this.ehliyetBilgisi=ehliyetBilgisi;
    }

    public String bilgileriGetir() {
        return (Ad_Soyad + " | " + Adres + " | " + Telefon + " | " + Eposta + " | " + DogumTarihi + " | " + YabanciDil + " | " + ehliyetBilgisi);
    }
}
