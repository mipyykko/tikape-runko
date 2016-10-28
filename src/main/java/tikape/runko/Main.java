package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Viestilomake;

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
        Viestilomake viestilomake = new Viestilomake("", "", "");
        
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
            map.put("alue", alue);
            
            map.put("viestit", viestiDao.findAlue(Integer.parseInt(req.params("id"))));

            map.put("viestilomake", viestilomake);
            
            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        post("/alue/:id", (Request req, Response res) -> {
            int alueid = Integer.parseInt(req.params("id"));
            System.out.println(req.queryParams());
            //viestilomake = req.queryParams("viestilomake");
            
            // en tiiä miten tän tyhjän lomakkeen tarkistuksen saa toimimaan :/
            
            // uusi viestiketju 
            // eli viestiDao.add
            
            res.redirect("/alue/" + alueid);
            return "";
        });
        
        get("/alue/:id/ketju/:ketjuid", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findTopic(Integer.parseInt(req.params("ketjuid"))));
            
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
        
        post("/alue/:id/ketju/:ketjuid", (req, res) -> {
            int alueid = Integer.parseInt(req.params("id"));
            int ketjuid = Integer.parseInt(req.params("ketjuid"));
            
            // tähän postaus 
            // viestiDao.add
            
            res.redirect("/alue/" + alueid + "/ketju/" + ketjuid);
            return "";
        });
        
    }
}
