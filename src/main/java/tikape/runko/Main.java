package tikape.runko;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.jsoup.Jsoup;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Viesti;

public class Main {

    /* TODO:
    
        
    */
    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:palsta.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        } 
        
        
        Database database = new Database(jdbcOsoite);

        AlueDao alueDao = new AlueDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        
        get("/", (req, res) -> {
            res.redirect("/alueet");
            return "";
        });

        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());
            
            
            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Alue alue = alueDao.findOne(Integer.parseInt(req.params("id")));

            if (alue == null) {
                res.redirect("/alueet");
            }
            
            List<Viesti> viestit = viestiDao.findAlue(Integer.parseInt(req.params("id")));
            for (int i = 0; i < viestit.size(); i++) {
                Viesti viestiketju = viestit.get(i);
                viestiketju.setViestimaara(viestiDao.findMessageCountInTopic(viestiketju.getId()));
                viestit.set(i, viestiketju);
            }
            
            map.put("alue", alue);
            map.put("viestit", viestit);
            
            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        post("/alue/:id", (Request req, Response res) -> {
            int alueid = Integer.parseInt(req.params("id"));
            //int ketjuid = Integer.parseInt(req.params("ketjuid"));

            String aika = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            // tossa lopussa on nyt 1 koska ei ole tiedossa uuden ketjun id:tä
            String otsikko = siivoaHTML(req.queryParams("otsikko"));
            String sisalto = siivoaHTML(req.queryParams("sisalto"));
            String nimimerkki = siivoaHTML(req.queryParams("nimimerkki"));
            
            int ketjuid = viestiDao.add(otsikko, sisalto, aika, nimimerkki, alueid, 1);
            viestiDao.modifyViittaus(ketjuid, ketjuid);
            // tän pitäis nyt palauttaa se uus id ja ohjata oikeaan paikkaan
            
            res.redirect("/alue/" + alueid + "/ketju/" + ketjuid);
            return "";            
        });
        
        get("/alue/:alueid/ketju/:ketjuid", (req, res) -> {
            HashMap map = new HashMap<>();
            int ketjuid = Integer.parseInt(req.params("ketjuid"));
            int alueid = Integer.parseInt(req.params("alueid"));
            List<Viesti> viestit = viestiDao.findTopic(ketjuid);

            if (viestit.isEmpty()) {
                res.redirect("/alue/" + alueid);
            }
            Alue alue = alueDao.findOne(alueid);
            map.put("viestit", viestit);
            map.put("alue", alue);
            map.put("ketjuid", ketjuid);
            
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
        
        post("/alue/:id/ketju/:ketjuid", (req, res) -> {
            int alueid = Integer.parseInt(req.params("id"));
            int ketjuid = Integer.parseInt(req.params("ketjuid"));

            String aika = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            String otsikko = siivoaHTML(req.queryParams("otsikko"));
            String sisalto = siivoaHTML(req.queryParams("sisalto"));
            String nimimerkki = siivoaHTML(req.queryParams("nimimerkki"));
            
            if (!otsikko.isEmpty() && !sisalto.isEmpty() && !nimimerkki.isEmpty()) {
                int uusi_id = viestiDao.add(otsikko, sisalto, aika, nimimerkki, alueid, ketjuid);
            }
            
            res.redirect("/alue/" + alueid + "/ketju/" + ketjuid);
            return "";
        });
        
        get("/alueet/uusi", (req, res) -> {
            return new ModelAndView(new HashMap<>(), "uusialue");
        }, new ThymeleafTemplateEngine());
        
        post("/alueet/uusi", (req, res) -> {
            String nimi = siivoaHTML(req.queryParams("nimi"));
            if (!nimi.isEmpty()) {
                alueDao.add(nimi);
            }
            
            res.redirect("/alueet");
            return "";
        });
    }
    
    private static String siivoaHTML(String teksti) {
        return (teksti == null || teksti.isEmpty()) ? "" : Jsoup.parse(teksti).text();
    }
}
