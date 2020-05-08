/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.dao;

import decisionmakertool.entities.User;
import decisionmakertool.util.DataConnect;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Service
public class LoginDAO {

    /*private  EntityManager em;
    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("OntoFixerPU");*/

    public  boolean validate(String user, String password) {
        Connection con = DataConnect.getConnection();
        String sqlStatement = "Select uname, upassword from User where uname = ? and upassword = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlStatement)){
            ps.setString(1, user);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }

    /*
    public User findUser(String uname){
        User user = em.find(User.class, uname);
        return user;
    }

    public boolean validateUser(User user, String password){
        if(user.getUpassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }*/

}
