package cz.jandys.demo.api;

import cz.jandys.demo.db.Architype;
import cz.jandys.demo.db.DBController;
import cz.jandys.demo.json.JSONProducer;
import org.json.simple.JSONArray;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Random;

@RestController
public class HelloController {

    private long timechecker = System.currentTimeMillis();
    private DBController vinlandDBControler = null;


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public String Login(@RequestParam(value = "name") String name, @RequestParam(value = "pass") String pass){
        checkTiming();
        JSONProducer output = new JSONProducer();
        DBController dbController = getDBControler();
        if(dbController.loginAttempt(name,pass)){
            output.add("status","ok");
            output.add("session", dbController.createSession(name));
            output.add("name",encode(name));
            return output.toString();
        }
        output.add("status","error");
        output.add("error","1057");
        output.add("message","The account name is invalid or does not exist or the password is invalid for the account name specified.");
        output.add("name",name);

        return output.toString();
    }

    private String encode(String name) {
        return Base64.getEncoder().encodeToString(name.getBytes());
    }

    private String decode(String toDecode){
        return new String(Base64.getDecoder().decode(toDecode.getBytes()));
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public String Register(@RequestParam(value = "name") String name, @RequestParam(value = "pass") String pass, @RequestParam(value="email") String email){
        checkTiming();
        JSONProducer output = new JSONProducer();
        DBController dbController = getDBControler();
        if(dbController.registerAttempt(name,pass,email)){
            output.add("status","ok");
            output.add("message","register succesful");
            output.add("name",name);
            output.add("seesionend",String.valueOf(System.currentTimeMillis()+1800000));
            output.add("sesioncheck", String.valueOf(new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE)));

            return output.toString();
        }
        output.add("status","error");
        output.add("message","register failed");
        output.add("name",name);

        return output.toString();
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/session", method = RequestMethod.POST, produces = "application/json")
    public String Session(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name){
        checkTiming();
        DBController dbController = getDBControler();
        JSONProducer output = new JSONProducer();
        if(dbController.checkSession(decode(name),id)){
            output.add("status","ok");
            output.add("message", "Session is active and correct");
            return output.toString();
        }
        output.add("status","error");
        output.add("error","7001");
        output.add("message","The specified session name is invalid.");
        return output.toString();
    }

    private void checkTiming(){
        if(System.currentTimeMillis() - timechecker > 1000){
            DBController dbController = getDBControler();
            dbController.deleteOldSessions();
        }
    }

    private DBController getDBControler(){
        if(this.vinlandDBControler == null){
            this.vinlandDBControler = new DBController(Architype.MYSQL,"vinlandkings","test","test");
        }
        return this.vinlandDBControler;
    }



}
