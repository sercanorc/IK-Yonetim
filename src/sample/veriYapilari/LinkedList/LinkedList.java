package sample.veriYapilari.LinkedList;

import sample.eleman.Deneyim;
import sample.eleman.Egitim;
import sample.eleman.Bilgi;

public class LinkedList extends LinkedListADT {
    @Override
    public void InsertFirst(Bilgi value) {
        LDugum temp = new LDugum(value);

        if (Top == null)
            Top = temp;
        else {
            temp.Next = Top;
            Top = temp;
        }
        Boyut++;
    }

    @Override
    public void InsertLast(Bilgi value) {
        LDugum temp = new LDugum(value);

        if (Top == null)
            Top = temp;
        else {
            LDugum sayac = Top;
            while (sayac.Next != null)
                sayac = sayac.Next;
            sayac.Next = temp;
        }
        Boyut++;
    }

    @Override
    public void addToPos(int pozisyon, Bilgi value) {
        LDugum temp = new LDugum(value);

        if (pozisyon > Boyut || pozisyon < 0) {
            throw new IndexOutOfBoundsException("Hatalı Pozisyon!");
        }
        else if (pozisyon == 1)
            InsertFirst(value);
        else {
            LDugum sayac = Top;
            for (int i = 1; i < pozisyon - 1; i++) {
                if (sayac.Next != null)
                    sayac = sayac.Next;
            }
            temp.Next = sayac.Next;
            sayac.Next = temp;
        }
        Boyut++;
    }

    @Override
    public void DeletePos(int pozisyon) {
        if (pozisyon > Boyut || pozisyon < 0) {
            throw new IndexOutOfBoundsException("Hatalı Pozisyon!");
        }
        else if (pozisyon == 1)
           System.out.println("bas");// basiSil();
        else {
            LDugum sayac = Top;
            LDugum temp = new LDugum();
            for (int i = 1; i < pozisyon - 1; i++) {
                if (sayac.Next != null)
                    sayac = sayac.Next;
            }
            temp = sayac;
            sayac = sayac.Next;
            temp.Next = sayac.Next;
            sayac = null;
            Boyut--;
        }
    }

    @Override
    public String DisplayElements(int pozisyon) {
        LDugum sayac = Top;
        if (pozisyon > Boyut || pozisyon < 0) {
            throw new IndexOutOfBoundsException("Hatalı Pozisyon!");
        } else {
            for (int i = 1; i < pozisyon; i++) {
                if (sayac.Next != null)
                    sayac = sayac.Next;
            }
        }

        if (sayac.Data instanceof Deneyim)
            return ((Deneyim) sayac.Data).Deneyim + " - " + ((Deneyim) sayac.Data).Adres;
        else if (sayac.Data instanceof Egitim)
            return ((Egitim) sayac.Data).Bitis + " - " + ((Egitim) sayac.Data).Okul_Ad + " - " + ((Egitim) sayac.Data).Bolum + " - " + ((Egitim) sayac.Data).NotOrtalamasi;

        return null;
    }

    public String listele() {
        String sonuc = "";
        if (Top != null) {
            for (int i = Boyut; i > 0; i--) {
                sonuc += DisplayElements(i) + "\n";
            }
        }
        return sonuc;
    }

}
