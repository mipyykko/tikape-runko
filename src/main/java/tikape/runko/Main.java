package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.ViestiDao;

public class Main {

    /* TODO:
    
       - collectorit?
       - viestialueen lisäys
        
    */
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:palsta.db");

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
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        post("/alue/:id", (req, res) -> {
            int alueid = Integer.parseInt(req.params("id"));
            
            // uusi viestiketju 
            // eli viestiDao.add
            
            res.redirect("/alue/" + alueid);
            return "";
        });
        
        get("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findTopic(Integer.parseInt(req.params("id"))));
            
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
        
        post("/ketju/:id", (req, res) -> {
            int ketjuid = Integer.parseInt(req.params("id"));
            
            // tähän postaus 
            // viestiDao.add
            
            res.redirect("/ketju/" + ketjuid);
            return "";
        });
    }
}
