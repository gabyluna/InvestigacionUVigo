/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.util.SessionUtils;
import decisionmakertool.dao.LoginDAO;
import decisionmakertool.util.UtilClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1094801825228386363L;
    private static final String PATH_FILE_PROPERTIES = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources")
            + "/loginKeys.properties";
    private String pwd;
    private String msg;
    private String user;
    private Boolean render = Boolean.FALSE;

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

    public void validateUsernamePassword() throws Exception {
        Properties property=new Properties();
        FileInputStream inputFile= new FileInputStream(PATH_FILE_PROPERTIES);
        property.load(inputFile);
        String key = property.getProperty("key");
        String initializationVector = property.getProperty("iv");
        String encrypt = UtilClass.encrypt(key, initializationVector, pwd);

        try {
            boolean valid = LoginDAO.validate(user, encrypt);
            FacesContext context = FacesContext.getCurrentInstance();

            if (valid) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", user);

                context.getExternalContext().redirect("/DecisionMakerTool-1.0.0-SNAPSHOT/index.xhtml");
            } else {
                render = Boolean.TRUE;
                msg = "Incorrect Username and Password";
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Incorrect Username and Password",
                                "Please enter correct username and Password"));
               context.getExternalContext().redirect("/DecisionMakerTool/login.xhtml");
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login.xhtml";
    }
}
