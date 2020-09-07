package decisionmakertool.dao;

import decisionmakertool.entities.Historial;
import decisionmakertool.util.DataConnect;
import decisionmakertool.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OntologyDAO {

     public  boolean insert(Historial historial, InputStream in) throws IOException {
        Connection con = DataConnect.getConnection();
        String pathFile = historial.getPath() + "/ontology_" + historial.getUname() + "_" + historial.getType()+ ".owl";

        if(in != null){
            Util.loadFile(pathFile,in);
            Util.pushChangesFile(pathFile, "ontology_"+ historial.getUname() + "_" + historial.getType() + ".owl", "upload file");
        }else {
            pathFile = historial.getPath();
        }

        pathFile = pathFile.replace("\\", "/");
        int lastId = findIdOntologyByState(true, historial.getType());
        updateStatusOntology(false, lastId);
        String sqlStatement = "INSERT INTO historial (uname, path, type, date, description,quickfix,state) VALUES " +
                "('"+ historial.getUname()+
                "','"+pathFile+"','"+
                historial.getType()+
                "',NOW(),'"+
                historial.getDescription()+
                "',"+historial.getQuickFix()+
                ",true)";

        try (PreparedStatement ps = con.prepareStatement(sqlStatement)){
            ps.executeUpdate();
        } catch (SQLException ex) {
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }

    public int findLastId(){
        Connection con = DataConnect.getConnection();
        String sqlStatement = "Select * from historial ORDER BY id DESC LIMIT 1";
        return executeSelectId(con, sqlStatement);
    }

    public int findIdOntologyByStateAndUname(Boolean status, String type, String uname){
        Connection con = DataConnect.getConnection();
        String sqlStatement = "Select * from historial where state ="+ status + " and type='" + type +"' and uname='"+ uname +"'";
        return executeSelectId(con, sqlStatement);
    }

    private int findIdOntologyByState(Boolean status, String type){
        Connection con = DataConnect.getConnection();
        String sqlStatement = "Select * from historial where state ="+ status + " and type='" + type +"'";
        return executeSelectId(con, sqlStatement);
    }

    public void updateStatusOntology(Boolean status, Integer id){
        Connection con = DataConnect.getConnection();
        String sqlStatement = "update historial set state =" + status + " where id ="+ id;
        try (PreparedStatement ps = con.prepareStatement(sqlStatement)){
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.checkError();
        } finally {
            DataConnect.close(con);
        }
    }

    public String findPathOntologyActive(String type){
        Connection con = DataConnect.getConnection();
        String sqlStatement = "select * from historial where state = true and type ='"+ type +"'";

        try (PreparedStatement ps = con.prepareStatement(sqlStatement)){
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return  rs.getString("path");
            }
            rs.close();
        } catch (SQLException ex) {
            return "";
        } finally {
            DataConnect.close(con);
        }
        return "";
    }

    public List<Historial> getHistorial(String username){
         List<Historial> listAux = new ArrayList<>();
         Connection con = DataConnect.getConnection();
         String sqlStatement = "select * from historial where type = 'A' and uname ='"+ username +"' order by date DESC";

        try (PreparedStatement ps = con.prepareStatement(sqlStatement)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Historial historial = new Historial();
                historial.setId(rs.getInt("id"));
                historial.setPath(rs.getString("path"));
                historial.setState(rs.getBoolean("state"));
                historial.setType(rs.getString("type"));
                historial.setUname(rs.getString("uname"));
                historial.setDate(rs.getDate("date"));
                historial.setDescription(rs.getString("description"));
                historial.setQuickFix(rs.getInt("quickfix"));
                listAux.add(historial);
            }
            rs.close();
        } catch (SQLException ex) {
            return null;
        } finally {
            DataConnect.close(con);
        }

        return listAux;
    }

    private int executeSelectId(Connection con, String sqlStatement){
        try (PreparedStatement ps = con.prepareStatement(sqlStatement)){
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return  rs.getInt("id");
            }
            rs.close();
        } catch (SQLException ex) {
            return 0;
        } finally {
            DataConnect.close(con);
        }
        return 0;
    }
}
