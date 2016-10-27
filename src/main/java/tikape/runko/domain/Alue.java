package tikape.runko.domain;

public class Alue {
    
    private Integer id;
    private String nimi;
    private Integer viestimaara;
    private Viesti uusinviesti;
    

    public Alue(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Viesti getUusinviesti() {
        return uusinviesti;
    }

    public Integer getViestimaara() {
        return viestimaara;
    }

    public void setUusinviesti(Viesti uusinviesti) {
        this.uusinviesti = uusinviesti;
    }

    public void setViestimaara(Integer viestimaara) {
        this.viestimaara = viestimaara;
    }
    
    
    
}