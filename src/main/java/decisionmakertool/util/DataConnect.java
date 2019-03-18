/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataConnect {
    private static final String UNAME = "root";
    private static final String UPASS = "root";

    private  DataConnect(){

    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://localhost/dbdecisionmaker?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            con = DriverManager.getConnection(connectionString,UNAME, UPASS);

        } catch (Exception ex) {
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, "Database.getConnection() Error -->", ex);
        }
        return con;
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, "Database.getConnection() Error -->", ex);
        }
    }
}
