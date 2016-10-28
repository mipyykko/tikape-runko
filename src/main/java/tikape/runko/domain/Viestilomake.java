/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author pyykkomi
 */
public class Viestilomake {

    private String otsikko;
    private String nimimerkki;
    private String sisalto;

    public Viestilomake(String otsikko, String nimimerkki, String sisalto) {
        this.otsikko = otsikko;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
    }

    public boolean tarkistaOtsikko() {
        return otsikko.isEmpty();
    }
    
    public boolean tarkistaNimimerkki() {
        return nimimerkki.isEmpty();
    }
    
    public boolean tarkistaSisalto() {
        return sisalto.isEmpty();
    }
    
    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }
    
}
