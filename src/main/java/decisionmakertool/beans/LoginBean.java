/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.util.SessionUtils;
import decisionmakertool.dao.LoginDAO;
import decisionmakertool.util.UtilClass;
import org.springframework.web.context.annotation.SessionScope;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScope
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1094801825228386363L;
    private static final String PATH_FILE_PROPERTIES = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources")
            + "/loginKeys.properties";
    private String pwd;
    private String msg;
    private String user;
    private Boolean render = Boolean.FALSE;

    public void validateUsernamePassword()  {
        LoginDAO loginDAO = new LoginDAO();
        FacesContext context = FacesContext.getCurrentInstance();
        String pathURLApplication = context.getExternalContext().getRequestContextPath();

        try {
            String pwdEncrypted =encryptValue(pwd);
            boolean valid = loginDAO.validate(user, pwdEncrypted);

            if (valid) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", user);
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
            result = UtilClass.encrypt(key, initializationVector, value);
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getRender() {
        return render;
    }

    public void setRender(Boolean render) {
        this.render = render;
    }
}
