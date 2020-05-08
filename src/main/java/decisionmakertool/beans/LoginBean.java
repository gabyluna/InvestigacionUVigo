/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.entities.User;
import decisionmakertool.util.SessionUtils;
import decisionmakertool.dao.LoginDAO;
import decisionmakertool.util.Util;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Scope(value = "session")
@Component(value = "loginBean")
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1094801825228386363L;
    private static final String PATH_FILE_PROPERTIES = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources")
            + "/loginKeys.properties";
    private String pwd;
    private String msg;
    private String username;
    private Boolean render = Boolean.FALSE;

    public void validateUsernamePassword()  {
        LoginDAO loginDAO = new LoginDAO();
        FacesContext context = FacesContext.getCurrentInstance();
        String pathURLApplication = context.getExternalContext().getRequestContextPath();

        try {
            String pwdEncrypted =encryptValue(pwd);
            boolean valid = loginDAO.validate(username, pwdEncrypted);

            if (valid) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", username);
                context.getExternalContext().redirect(pathURLApplication + "/index.xhtml");
            } else {
                render = Boolean.TRUE;
                msg = "Incorrect Username and Password";
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                msg,"Please enter correct username and Password"));
               context.getExternalContext().redirect(pathURLApplication+ "/login.xhtml");
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String encryptValue(String value) {
        String result = "";

        try (FileInputStream inputFile= new FileInputStream(PATH_FILE_PROPERTIES)) {
            Properties property=new Properties();
            property.load(inputFile);
            String key = property.getProperty("key");
            String initializationVector = property.getProperty("iv");
            result = Util.encrypt(key, initializationVector, value);
        } catch (Exception e) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login.xhtml";
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getRender() {
        return render;
    }

    public void setRender(Boolean render) {
        this.render = render;
    }
}
