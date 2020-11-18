package sample.veriYapilari.LinkedList;

import sample.eleman.Bilgi;
//Abstract Data Type
public abstract class LinkedListADT {
    public LDugum Top;
    public int Boyut = 0;

    public abstract void InsertFirst(Bilgi deger);
    public abstract void InsertLast(Bilgi deger);
    public abstract void addToPos(int pozisyon, Bilgi deger);
    public abstract void DeletePos(int pozisyon);
    public abstract String DisplayElements(int pozisyon);
}
