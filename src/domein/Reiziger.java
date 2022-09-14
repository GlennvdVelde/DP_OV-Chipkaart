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
    public Adres adres;
    public List<OVChipkaart> ov_chipkaarten_list = new ArrayList<>();

    public Reiziger(int id, String vl, String tsvg, String achternm, Date geboorte){
        this.id = id;
        this.voorletters = vl;
        this.tussenvoegsel = tsvg;
        this.achternaam = achternm;
        this.geboortedatum = geboorte;
    }

    public Reiziger(int id, String vl, String tsvg, String achternm, Date geboorte, Adres adres){
        this.id = id;
        this.voorletters = vl;
        this.tussenvoegsel = tsvg;
        this.achternaam = achternm;
        this.geboortedatum = geboorte;
        this.adres = adres;
    }

    public Reiziger(int id, String vl, String tsvg, String achternm, Date geboorte, Adres adres, OVChipkaart ovChipkaart) {
        this.id = id;
        this.voorletters = vl;
        this.tussenvoegsel = tsvg;
        this.achternaam = achternm;
        this.geboortedatum = geboorte;
        this.adres = adres;
        this.ov_chipkaarten_list.add(ovChipkaart);
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

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOv_chipkaarten_list() {
        return ov_chipkaarten_list;
    }

    public void setOv_chipkaarten_list(List<OVChipkaart> ov_chipkaarten_list) {
        this.ov_chipkaarten_list = ov_chipkaarten_list;
    }

    @Override
    public String toString(){
        if(this.adres != null){
            return "\nReiziger_ID: " + this.id + " "
                    + "Naam: " + this.voorletters + " "  + this.tussenvoegsel + " " + this.achternaam + " "
                    + "Geboortedatum: " + this.geboortedatum + " Woonplaats: " + this.adres;
        }else {
            return "\nReiziger_ID: " + this.id + " "
                    + "Naam: " + this.voorletters + " " + this.tussenvoegsel + " " + this.achternaam + " "
                    + "Geboortedatum: " + this.geboortedatum;
        }
    }
}
