package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tikape.runko.database.collector.AlueCollector;
import tikape.runko.database.collector.ViestiCollector;
import tikape.runko.domain.Alue;

/**
 *
 * @author pyykkomi
 */
public class AlueDao implements Dao<Alue, Integer> {

    private Database database;
    
    public AlueDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Alue findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        List<Alue> alueet = database.queryAndCollect("SELECT * FROM Alue", new AlueCollector());

        for (int i = 0; i < alueet.size(); i++) {
            // näissä ei ole vielä tarkistusta nollan tuloksen tapauksissa!
            Alue a = alueet.get(i);
            int viestimaara = database.queryAndCollectInt("SELECT COUNT(*) AS viestimaara FROM Viesti WHERE alue_id = ?", new ViestiCollector(), a.getId()).get(0);
            String uusinpvm = database.queryAndCollect("SELECT aika FROM Viesti WHERE alue_id = ? ORDER BY aika DESC LIMIT 1", new ViestiCollector(), a.getId()).get(0).getAika();
            
            a.setViestimaara(viestimaara);
            a.setUusinpvm(uusinpvm);
            
            alueet.set(i, a);
        }
        
        return alueet;
    }

    public void add(/* tähän parametreja */) throws SQLException {
    // lisää alue
    
    }    
    
    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
