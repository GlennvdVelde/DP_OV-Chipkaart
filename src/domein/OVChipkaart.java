package domein;

import java.sql.Date;

public class OVChipkaart {
    public int kaart_nummer;
    public Date geldig_tot;
    public int klasse;
    public double saldo;
    public Reiziger reiziger;

    public OVChipkaart(int kaartnr, Date geldig, int klas, double sald, Reiziger reiziger){
        this.kaart_nummer = kaartnr;
        this.geldig_tot = geldig;
        this.klasse = klas;
        this.saldo = sald;
        this.reiziger = reiziger;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString(){
        return "\nKaartnummer: " + this.kaart_nummer + " saldo: " + this.saldo;
    }
}
