package domein;

public class Adres {
    public int adres_id;
    public String postcode;
    public String huisnummer;
    public String straat;
    public String woonplaats;
    public int reiziger_id;

    public Adres(int id, String pcode, String hn, String str, String woon, int reiziger_id){
        this.adres_id = id;
        this.postcode = pcode;
        this.huisnummer = hn;
        this.straat = str;
        this.woonplaats = woon;
        this.reiziger_id = reiziger_id;
    }

    public int getAdres_Id() {
        return adres_id;
    }

    public void setAdres_Id(int id) {
        this.adres_id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString(){
        return "Adres: "
                + this.adres_id + " "
                + this.postcode + " "
                + this.huisnummer + " "
                + this.straat + " "
                + this.woonplaats + " "
                + this.reiziger_id;
    }
}
