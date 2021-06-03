package cz.jandys.demo.db;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Random;
import java.util.UUID;

public class DBController {

    private Architype type = Architype.MYSQL;
    private String database = "test";
    private String user = "root";
    private String pass = "root";
    private Connection conn;
    private final char[] characters = "qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM0123456789".toCharArray();
    private Random rnd;


    //CONSTRUCTORS
    public DBController(Architype type, String database, String user, String pass) {
        this.type = type;
        this.database = database;
        this.user = user;
        this.pass = pass;
    }

    public DBController(String database, String user, String pass) {
        this.database = database;
        this.user = user;
        this.pass = pass;
    }

    //CONNECT
    private void connect() throws Exception {
        switch (type) {
            case MYSQL:
                this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.database, this.user, this.pass);
                break;
            default:
                throw new Exception("Architype is not chosen");
        }
    }

    //DISCONNECT
    private void disconnect() {
        try {
            this.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Attemt on login returns true if name and pass are correct
     */

    public boolean loginAttempt(String name, String pass) {
        try {
            connect();
            String sql = "select * from users where BINARY name = ?  and BINARY pass = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return true;
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    //Attempts to register return true if register went alright
    public boolean registerAttempt(String name, String pass, String email) {
        try {
            connect();
            if (accountDoesNotExist(name)) {
                String sql = "insert into users (name, pass, email) values(?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, pass);
                stmt.setString(3, email);
                stmt.executeUpdate();
                stmt.close();
                createCharacter(getUserIDbyName(name));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    private void createCharacter(int userID) {
        try {
            String sql = "insert into chatacters (userid) values(?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Returns true if account does not exist
    private boolean accountDoesNotExist(String name) {
        try {
            String sql = "select * from users where BINARY name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stmt.close();
                return false;
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //Creates session
    public String createSession(String name) {
        try {
            connect();
            int id = getUserIDbyName(name);
            deleteSessionsByID(id);
            String session;
            do {
                session = generateSession();
            } while (checkIfSessionExists(session));
            String sql = "insert into sessions (sessionid, userid) values(?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, session);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
            return session;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    public String generateSession() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private boolean checkIfSessionExists(String session) {
        try {
            String sql = "select * from sessions where sessionid like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, session);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stmt.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Returns true if sesion with name an sessionid exists
    public boolean checkSession(String name, String sessionid) {
        try {
            connect();
            String sql = "select * from sessions where sessionid like ? and userid like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sessionid);
            int id = getUserIDbyName(name);
            if (id == -1) return false;
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stmt.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    //Returns
    private int getUserIDbyName(String name) {
        try {
            String sql = "select id from users where name like ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //deletes sessions that are older than 20 minutes
    public void deleteOldSessions() {
        try {
            connect();
            String sql = "delete from sessions WHERE creation < (NOW() - INTERVAL 20 MINUTE)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void deleteSessionsByID(int id) {
        try {
            String sql = "delete from sessions where userid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int getUsersIDbyNameAndPass(String name, String pass) {

        System.out.println(pass);
        System.out.println(hashPassword(pass,"ělfaKkwdk"));

        return 1;
    }

    private String hashPassword(String pass, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
