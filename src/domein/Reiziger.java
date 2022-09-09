package domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    public int id;
    public String voorletters;
    public String tussenvoegsel;
    public String achternaam;
    public Date geboortedatum;

    public Reiziger(int id, String vl, String tsvg, String achternm, Date geboorte){
        this.id = id;
        this.voorletters = vl;
        this.tussenvoegsel = tsvg;
        this.achternaam = achternm;
        this.geboortedatum = geboorte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getNaam(){
        return this.voorletters + this.tussenvoegsel + this.achternaam;
    }

    @Override
    public String toString(){
        return "Reiziger_ID: " + this.id + "\n"
                + "Naam: " + this.voorletters + this.tussenvoegsel + this.achternaam + "\n"
                + "Geboortedatum: " + this.geboortedatum;
    }
}
