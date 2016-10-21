package tikape.runko.domain;

public class Alue {
    
    private Integer id;
    private String otsikko;
    private String sisalto;
    private String aika;
    private String nimimerkki;
    private Integer alue_id;
    private Integer viittaus_id;

    public Alue(Integer id, String otsikko, String sisalto, String aika, String nimimerkki, Integer alue_id, Integer viittaus_id) {
        this.id = id;
        this.otsikko = otsikko;
        this.sisalto = sisalto;
        this.aika = aika;
        this.nimimerkki = nimimerkki;
        this.alue_id = alue_id;
        this.viittaus_id = viittaus_id;
    }

    public String getAika() {
        return aika;
    }

    public Integer getAlue_id() {
        return alue_id;
    }

    public Integer getId() {
        return id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Integer getViittaus_id() {
        return viittaus_id;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public void setAlue_id(Integer alue_id) {
        this.alue_id = alue_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setViittaus_id(Integer viittaus_id) {
        this.viittaus_id = viittaus_id;
    }
    
    
        
}


