/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.runko.database.Collector;
import tikape.runko.domain.Viesti;

/**
 *
 * @author pyykkomi
 */
public class ViestiCollector implements Collector<Viesti> {

    @Override
    public Viesti collect(ResultSet rs) throws SQLException {
        return new Viesti(rs.getInt("id"), rs.getString("otsikko"), rs.getString("sisalto"),
                          rs.getString("aika"), rs.getString("nimimerkki"), rs.getInt("alue_id"), 
                          rs.getInt("viittaus_id"));
    }
    
}
