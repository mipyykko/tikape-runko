package tikape.runko.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }
        
        return DriverManager.getConnection(databaseAddress);
    }
    
    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }
    
    public <T> List<T> queryAndCollect(String query, Collector<T> col, Object... params) throws SQLException {
        List<T> returnList = new ArrayList<>();
        
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            returnList.add(col.collect(rs));
        }
        
        rs.close();
        stmt.close();
        
        return returnList;
    }
    
    public int executeCommand(String query, Object... params) throws SQLException {
        Connection connection = getConnection();
        // tuon pitäisi palauttaa sitten uusin id
        PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        int uusin_id = 0;
        if (rs.next()) {
            uusin_id = rs.getInt(1);
        }
        stmt.close();
        
        return uusin_id;
    }
    
    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Viesti " +
                  "(id SERIAL PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  " otsikko varchar(128), " +
                  " sisalto varchar(16384) NOT NULL, " +
                  " aika DATETIME(64) NOT NULL, " +
                  " alue_id INTEGER NOT NULL, " +
                  " nimimerkki varchar(32) NOT NULL, " +
                  " viittaus_id INTEGER DEFAULT 'null', " +
                  "FOREIGN KEY(alue_id) REFERENCES Alue(id), " +
                  "FOREIGN KEY(viittaus_id) REFERENCES Viesti(id));");

        return lista;
    }
    
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Viesti " +
                  "(id integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                  " otsikko varchar(128), " +
                  " sisalto varchar(16384) NOT NULL, " +
                  " aika DATETIME(64) NOT NULL, " +
                  " alue_id INTEGER NOT NULL, " +
                  " nimimerkki varchar(32) NOT NULL, " +
                  " viittaus_id INTEGER DEFAULT 'null', " +
                  "FOREIGN KEY(alue_id) REFERENCES Alue(id), " +
                  "FOREIGN KEY(viittaus_id) REFERENCES Viesti(id));");

        return lista;
    }        
}
