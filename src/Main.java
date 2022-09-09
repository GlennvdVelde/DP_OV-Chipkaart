import data.ReizigerDAO;
import domein.Reiziger;
import presentatielaag.dto.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDAO rdao = new ReizigerDAOPsql(getConnection()){};
        testReizigerDAO(rdao);
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
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Update een bestaand reiziger met andere waarden
        Reiziger peter = new Reiziger(77, "P", "", "boers", java.sql.Date.valueOf(gbdatum));
        System.out.println("[TEST] Update test S. Boers moet P. Boers worden");
        rdao.update(peter);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //Delete een bestaand reiziger uit de Database
        System.out.println("[TEST] Update test of het deleten van een reiziger werkt");
        rdao.delete(peter);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }
}

