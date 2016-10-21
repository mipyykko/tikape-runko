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
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ? OR viittaus_id = ? ORDER BY aika ASC");
        /* viestiketju ensimmäisen postauksen id:llä:
             haetaan kaikki viestit joiden id joko on etsittävä tai jonka viittaus_id on etsittävä
        */
        
        stmt.setObject(1, key);
        stmt.setObject(2, key);
        
        ResultSet rs = stmt.executeQuery();
        
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            viestit.add(new Viesti(rs.getInt("id"), rs.getString("otsikko"), rs.getString("sisalto"),
                                   rs.getString("aika"), rs.getString("nimimerkki"), rs.getInt("alue_id"), 
                                   rs.getInt("viittaus_id")));
        }

        stmt.close();
        connection.close();
        
        // palauttaa viestiketjun
        return viestit;
    }
}
