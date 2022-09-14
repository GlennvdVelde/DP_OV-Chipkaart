import data.AdresDAO;
import data.OVChipkaartDAO;
import data.ReizigerDAO;
import domein.Adres;
import domein.OVChipkaart;
import domein.Reiziger;
import org.w3c.dom.ls.LSOutput;
import presentatielaag.dto.AdresDAOPsql;
import presentatielaag.dto.OVChipkaartDAOPsql;
import presentatielaag.dto.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        AdresDAO adao = new AdresDAOPsql(getConnection());
        ReizigerDAO rdao = new ReizigerDAOPsql(getConnection(), adao);
        OVChipkaartDAO odao = new OVChipkaartDAOPsql(getConnection(), rdao);
//        testReizigerDAO(rdao);
//        testAdresDAO(adao);
        testOVChipkaartDAO(odao);
    }

    private static Connection getConnection() throws SQLException{
        String url = "jdbc:postgresql://localhost:5001/ovchip";
        String user = "postgres";
        String password = "CB500f1999";
        return DriverManager.getConnection(url, user, password);
    }

    private static void closeConnection() throws SQLException{
        getConnection().close();
    }

//    private static void testReizigerDAO (ReizigerDAO rdao) throws SQLException {
//        System.out.println("\n---------- Test ReizigerDAO -------------");
//
//        // Haal alle reizigers op uit de database
//        List<Reiziger> reizigers = rdao.findAll();
//        System.out.println("\n[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
//        for (Reiziger r : reizigers) {
//            System.out.println(r);
//        }
//        System.out.println();
//
//        // Maak een nieuwe reiziger aan en persisteer deze in de database
//        String gbdatum = "1981-03-14";
//        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
//        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
//        rdao.save(sietske);
//        reizigers = rdao.findAll();
//        System.out.println(reizigers.size() + " reizigers\n");
//
//        // Update een bestaand reiziger met andere waarden
//        Reiziger peter = new Reiziger(77, "P", "", "boers", java.sql.Date.valueOf(gbdatum));
//        System.out.println("\n[TEST] Update test S. Boers moet P. Boers worden");
//        rdao.update(peter);
//        reizigers = rdao.findAll();
//        System.out.println(reizigers.size() + " reizigers\n");
//
//        //Delete een bestaand reiziger uit de Database
//        System.out.println("\n[TEST] Update test of het deleten van een reiziger werkt");
//        rdao.delete(peter);
//        reizigers = rdao.findAll();
//        System.out.println(reizigers.size() + " reizigers\n");
//
//        //FindByID voor reiziger
//        System.out.println("\n[TEST] Zoekt een reiziger met FindByID");
//        System.out.println(rdao.findById(1));
//
//        //FindByGdatum voor Reiziger
//        System.out.println("\n[TEST] Zoekt een reiziger object aan de hand van Geboortedatum");
//        System.out.println(rdao.findByGbdatum("2002-09-17"));
//    }

//    public static void testAdresDAO(AdresDAO adao) throws SQLException{
//        System.out.println("\n---------- Test AdresDAO -------------");
//
//        //Haal alle Adressen uit de database
//        List<Adres> adresList = adao.findAll();
//        System.out.println("\n[TEST] findALL() test voor Adressen");
//        for (Adres adres : adresList) {
//            System.out.println(adres.toString());
//        }
//
//        //Voeg een adres toe aan de database
//        System.out.println("\n[TEST] test of een adres gepersisteerd wordt in de Database");
//        Adres a1 = new Adres(24, "2894BD", "11", "Suikerlaan", "Utrecht", 6);
//        adao.save(a1);
//        adresList = adao.findAll();
//        System.out.println(adresList.size() + " Adressen gevonden in de Database");
//
//        //Update een bestaand Adres met verschillende waarden
//        System.out.println("\n[TEST] update waarden van een bestaand adres");
//        Adres a1_new = new Adres(24, "2745SE", "54", "Peperstraat", "Den Bosch", 6);
//        adao.update(a1_new);
//        adresList = adao.findAll();
//        System.out.println(adresList.size() + " Adressen gevonden in de Database");
//
//        // Delete een bestaand Adres in de database
//        System.out.println("\n[TEST] Deleten van een bestaand adres in de Database");
//        adao.delete(a1_new);
//        adresList = adao.findAll();
//        System.out.println(adresList.size() + " Adressen gevonden in de Database");
//
//        // Vind een adres aan de hand van het adres_id
//        System.out.println("\n[TEST] met gebruik van findByID() een adres opzoeken");
//        System.out.println(adao.findById(1));
//
//        //findByReiziger()
//        System.out.println("\n[TEST] findByReiziger()");
//        Reiziger reiziger = new Reiziger(69, "V", "van der", "Plas", Date.valueOf("1999-07-07"));
//        System.out.println(adao.findByReiziger(reiziger));
//    }

//    public static void testFindByReiziger(ReizigerDAO rdao) throws SQLException{
//        // FindByReiziger() Test voor Adres
//        System.out.println("\n[TEST] test de FindByReiziger()");
//        Reiziger reiziger = new Reiziger(69, "V", "van der", "Plas", Date.valueOf("1999-07-07"));
//        Adres adres = new Adres(6, "2804HB", "11", "Pokkeplein", "Haastrecht", reiziger.getId());
//        reiziger.setAdres(adres);
//        rdao.save(reiziger);
//
//
//    }

    public static void testOVChipkaartDAO(OVChipkaartDAO odao) throws SQLException{
        //Test de CRUD functies voor OVChipkaart
        System.out.println("[TEST] Saven van een chipkaart");
        Reiziger reiziger = new Reiziger(2000, "J", "vd", "Plank", Date.valueOf("2002-07-07"));
        OVChipkaart ovChipkaart = new OVChipkaart(9999, Date.valueOf("2024-06-06"),1, 9999.99, reiziger);
        odao.save(ovChipkaart);

        //TEST de findAll() functie voor OVChipkaart
//        System.out.println("[TEST] findAll() test voor Chipkaart");
//        odao.findAll();
    }

}

