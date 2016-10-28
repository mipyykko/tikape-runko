/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.collector.ViestiCollector;
import tikape.runko.domain.Viesti;

/**
 *
 * @author pyykkomi
 */
public class ViestiDao implements Dao<Viesti, Integer>{

    private Database database;
    
    public ViestiDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Viesti findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Toimiskohan --Leevi
    // values ja kysymysmerkit puuttuivat, id tulee automaattisesti tietokannasta
    public void add(String otsikko, String sisalto, String aika, String nimimerkki, Integer alue_id, Integer viittaus_id) throws SQLException {
   
      database.executeCommand("INSERT INTO Viesti (otsikko, sisalto, aika, nimimerkki, alue_id, viittaus_id) VALUES (?, ?, ?, ?, ?, ?)", otsikko, sisalto, aika, nimimerkki, alue_id, viittaus_id);
    }
    
    public List<Viesti> findTopic(Integer viittaus_id) throws SQLException {
        List<Viesti> viestit = new ArrayList<>();
        viestit = database.queryAndCollect("SELECT * FROM Viesti WHERE viittaus_id = ? ORDER BY aika ASC", new ViestiCollector(), viittaus_id);
        /* muutettu niin että viestiketjun eka viittaa itseensä */
        
        return viestit;
    }
    
    public List<Viesti> findAlue(Integer alueid) throws SQLException {
        List<Viesti> viestit = new ArrayList<>();
        // vaihdettu ketjun aloitusviestin viittaus itseensä
        viestit = database.queryAndCollect("SELECT * FROM Viesti WHERE alue_id = ? AND viittaus_id = id ORDER BY aika DESC", new ViestiCollector(), alueid);
        // tän varmaan vois tehdä jotenkin järkevämmin mutta tällä viestiketjuun uusin viesti
        for (int i = 0; i < viestit.size(); i++) {
            Viesti viesti = viestit.get(i);
            List<Viesti> uusin = database.queryAndCollect("SELECT * FROM Viesti WHERE alue_id = ? and viittaus_id = ? ORDER BY aika DESC LIMIT 1", 
                    new ViestiCollector(), 
                    alueid, viesti);
            if (!uusin.isEmpty()) {
                viesti.setUusinviesti(uusin.get(0));
            } else {
                // olkoon sitten null
                //viesti.setUusinviesti(viesti);
            }
            viestit.set(i, viesti);
        }
        return viestit;
    }
    
    public List<Viesti> findNewestFromTopic(Integer alue_id, Integer viittaus_id) throws SQLException {
        List<Viesti> viestit = new ArrayList<>();
        viestit = database.queryAndCollect("SELECT * FROM Viesti WHERE alue_id = ? AND viittaus_id = ? ORDER BY aika DESC", new ViestiCollector(), alue_id, viittaus_id);
    
        return viestit;
    }
}
