package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");
        
        ResultSet rs = stmt.executeQuery();

        List<Alue> alueet = new ArrayList<>();

        while (rs.next()) {
            Alue alue = new Alue(rs.getInt("id"), rs.getString("nimi"));
            PreparedStatement stmt2 = connection.prepareStatement("SELECT COUNT(*) AS viestimaara FROM Viesti WHERE alue_id = ?");
            stmt2.setObject(1, alue.getId());
            
            ResultSet rs2 = stmt2.executeQuery();
            
            alue.setViestimaara(rs2.getInt("viestimaara"));
            
            stmt2 = connection.prepareStatement("SELECT aika FROM Viesti WHERE alue_id = ? ORDER BY aika DESC LIMIT 1");
            stmt2.setObject(1, alue.getId());
            
            rs2 = stmt2.executeQuery();
            
            if (!rs2.next()) {
                alue.setUusinpvm("ei viestejä");
            } else {
                alue.setUusinpvm(rs2.getString("aika"));
            }
            
            stmt2.close();
            
            alueet.add(alue);
        }
        
        stmt.close();
        connection.close();
        
        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
