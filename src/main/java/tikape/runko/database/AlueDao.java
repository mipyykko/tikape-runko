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
import tikape.runko.domain.Viesti;

/**
 *
 * @author pyykkomi
 */
public class AlueDao implements Dao<Alue, Integer> {

    private Database database;
    
    public AlueDao(Database database) {
        this.database = database;
    }
    
    /**
     * Hakee yksittäisen alueen.
     * 
     * @param alueid Alueen id
     * @return Haettu Alue, jos alueid:n muotoinen alue on olemassa tai null
     * @throws SQLException
     * @see Alue
     * @see findAll
     */
    @Override
    public Alue findOne(Integer alueid) throws SQLException {
        List<Alue> alueet = database.queryAndCollect("SELECT * FROM Alue WHERE id = ?", new AlueCollector(), alueid);
        
        if (!alueet.isEmpty()) {
            return alueet.get(0);
        } else {
            return null;
        }
    }

    /**
     * Hakee kaikki alueet.
     * 
     * @return List<Alue> kaikista alueista.
     * @throws SQLException 
     * @see Alue
     * @see findOne
     */
    
    @Override
    public List<Alue> findAll() throws SQLException {
        List<Alue> alueet = database.queryAndCollect("SELECT * FROM Alue", new AlueCollector());

        for (int i = 0; i < alueet.size(); i++) {
            Alue a = alueet.get(i);
            
            // Tässä haetaan siis joka alueen idn perusteella 
            List<Viesti> alueen_viestit = database.queryAndCollect("SELECT * FROM Viesti WHERE alue_id = ? ORDER BY aika DESC", new ViestiCollector(), a.getId());
            a.setViestimaara(alueen_viestit.size());

            if (!alueen_viestit.isEmpty()) {
                a.setUusinviesti(alueen_viestit.get(0));
            }
            
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
