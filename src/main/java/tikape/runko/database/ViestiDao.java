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
    
    public void add(/* tähän parametreja */) throws SQLException {
    // lisää viesti
    
    }
    
    public List<Viesti> findTopic(Integer key) throws SQLException {
        List<Viesti> viestit = new ArrayList<>();
        viestit = database.queryAndCollect("SELECT * FROM Viesti WHERE id = ? OR viittaus_id = ? ORDER BY aika ASC", new ViestiCollector(), key, key);
        /* viestiketju ensimmäisen postauksen id:llä:
             haetaan kaikki viestit joiden id joko on etsittävä tai jonka viittaus_id on etsittävä
        */
        
        return viestit;
    }
    
    public List<Viesti> findAlue(Integer key) throws SQLException {
        List<Viesti> viestit = new ArrayList<>();
        // vaihdettu ketjun aloitusviestin viittaus itseensä
        viestit = database.queryAndCollect("SELECT * FROM Viesti WHERE alue_id = ? AND viittaus_id = id ORDER BY aika DESC", new ViestiCollector(), key);
        
        return viestit;
    }
}
