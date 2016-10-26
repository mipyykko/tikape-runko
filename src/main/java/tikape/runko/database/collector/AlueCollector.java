/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.runko.database.Collector;
import tikape.runko.domain.Alue;

/**
 *
 * @author pyykkomi
 */
public class AlueCollector implements Collector<Alue> {

    @Override
    public Alue collect(ResultSet rs) throws SQLException {
        return new Alue(rs.getInt("id"), rs.getString("nimi"));
    }
    
}
