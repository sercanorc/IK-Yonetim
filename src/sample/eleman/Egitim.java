package sample.eleman;

public class Egitim extends EkBilgi {
    public String Okul_Ad;
    public String Tur;
    public String Bolum;
    public int Baslangic;
    public int Bitis;
    public double NotOrtalamasi;

    public Egitim(String okul_ad, String tur, String bolum, int baslangic, int bitis, double notOrtalamasi) {
        this.Okul_Ad = okul_ad;
        this.Tur = tur;
        this.Bolum = bolum;
        this.Baslangic = baslangic;
        this.Bitis = bitis;
        this.NotOrtalamasi = notOrtalamasi;
    }
}
