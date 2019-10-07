/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.dao;

import decisionmakertool.util.DataConnect;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Service
public class LoginDAO {

    public LoginDAO(){

    }

    public  boolean validate(String user, String password) {
        /*
        User user1 =  userService.findUser(user, password);
        if(user1 != null){
            return true;
        }else{
            return false;
        }*/

        Connection con;
        con = DataConnect.getConnection();
        String sqlStatement = "Select uname, upassword from User where uname = ? and upassword = ?";
        System.out.println("sql:" + sqlStatement);
        try (PreparedStatement ps = con.prepareStatement(sqlStatement)){
            ps.setString(1, user);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
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
}
