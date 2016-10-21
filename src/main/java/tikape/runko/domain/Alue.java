package tikape.runko.domain;

public class Alue {
    
    private Integer id;
    private String nimi;
    private Integer viestimaara;
    private String uusinpvm;
    

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

    public String getUusinpvm() {
        return uusinpvm;
    }

    public Integer getViestimaara() {
        return viestimaara;
    }

    public void setUusinpvm(String uusinpvm) {
        this.uusinpvm = uusinpvm;
    }

    public void setViestimaara(Integer viestimaara) {
        this.viestimaara = viestimaara;
    }
    
    
    
}