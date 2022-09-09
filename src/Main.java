import data.AdresDAO;
import data.ReizigerDAO;
import domein.Adres;
import domein.Reiziger;
import presentatielaag.dto.AdresDAOPsql;
import presentatielaag.dto.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDAO rdao = new ReizigerDAOPsql(getConnection()){};
        AdresDAO adao = new AdresDAOPsql(getConnection());
        testReizigerDAO(rdao);
        testAdresDAO(adao);
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

    private static void testReizigerDAO (ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("\n[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update een bestaand reiziger met andere waarden
        Reiziger peter = new Reiziger(77, "P", "", "boers", java.sql.Date.valueOf(gbdatum));
        System.out.println("\n[TEST] Update test S. Boers moet P. Boers worden");
        rdao.update(peter);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //Delete een bestaand reiziger uit de Database
        System.out.println("\n[TEST] Update test of het deleten van een reiziger werkt");
        rdao.delete(peter);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        //Haal alle Adressen uit de database
        List<Adres> adresList = adao.findAll();
        System.out.println("\n[TEST] findALL() test voor Adressen");
        for (Adres adres : adresList) {
            System.out.println(adres.toString());
        }

        //Voeg een adres toe aan de database
        System.out.println("\n[TEST] test of een adres gepersisteerd wordt in de Database");
        Adres a1 = new Adres(24, "2894BD", "11", "Suikerlaan", "Utrecht", 5);
        adao.save(a1);
        adresList = adao.findAll();
        System.out.println(adresList.size() + " Adressen gevonden in de Database");

        //Update een bestaand Adres met verschillende waarden
        System.out.println("\n[TEST] update waarden van een bestaand adres");
        Adres a1_new = new Adres(24, "2745SE", "54", "Peperstraat", "Den Bosch", 5);
        adao.update(a1_new);
        adresList = adao.findAll();
        System.out.println(adresList.size() + " Adressen gevonden in de Database");

        // Delete een bestaand Adres in de database
        System.out.println("\n[TEST] Deleten van een bestaand adres in de Database");
        adao.delete(a1_new);
        adresList = adao.findAll();
        System.out.println(adresList.size() + " Adressen gevonden in de Database");

        // Vind een adres aan de hand van het adres_id
        System.out.println("\n[TEST] met gebruik van findByID() een adres opzoeken");
        adao.findById(33);
    }
}

