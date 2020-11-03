package sample.veriYapilari.LinkedList;

import sample.eleman.EkBilgi;
//Abstract Data Type
public abstract class LinkedListADT {
    public LDugum Top;
    public int Boyut = 0;

    public abstract void InsertFirst(EkBilgi deger);
    public abstract void InsertLast(EkBilgi deger);
    public abstract void addToPos(int pozisyon, EkBilgi deger);
    public abstract void DeletePos(int pozisyon);
    public abstract String DisplayElements(int pozisyon);
}
