package sample.eleman;

public class Deneyim extends Bilgi {
    public String Deneyim;
    public String Adres;
    public String Tecrübe_Pozisyon;
    public double tecrübe;

    public Deneyim(String deneyim, String adres, String tecrübe_Pozisyon,double tecrübe) {
        this.Deneyim = deneyim;
        this.Adres = adres;
        this.Tecrübe_Pozisyon = tecrübe_Pozisyon;
        this.tecrübe=tecrübe;
    }
}
