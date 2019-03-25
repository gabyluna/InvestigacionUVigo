package decisionmakertool.util;

import javax.faces.context.FacesContext;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataConnect {
    private static final String PATH_FILE_PROPERTIES = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources")
            + "/loginKeys.properties";
    private static String msgError = "Database.getConnection() Error -->";

    private  DataConnect(){

    }

    public static Connection getConnection() {
        Connection con = null;
        Properties property = loadPropertiesFile();

        try {
            Class.forName(property.getProperty("driver"));
            String connectionString = property.getProperty("string_connection");
            String uname = property.getProperty("user");
            String upass = property.getProperty("pass");
            con = DriverManager.getConnection(connectionString,uname, upass);

        } catch (Exception ex) {
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, msgError, ex);
        }
        return con;
    }

    private static Properties loadPropertiesFile(){
        Properties property=new Properties();

        try(FileInputStream inputFile= new FileInputStream(PATH_FILE_PROPERTIES)) {
            property.load(inputFile);
        } catch (Exception ex) {
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, msgError, ex);
        }
        return  property;
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, msgError, ex);
        }
    }
}
