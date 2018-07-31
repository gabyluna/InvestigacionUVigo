/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.dao.LoginDAO;
import decisionmakertool.util.ValidatorUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gaby_
 */
@ManagedBean
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String pwd;
    private String msg;
    private String user;
    private Boolean render=Boolean.FALSE;

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

    
    //validate login
    public void validateUsernamePassword() throws IOException, Exception {
    

         String key = "92AE31A79FEEB2A3"; //llave
         String iv = "0123456789ABCDEF";
         String encriptar = "";
         
         encriptar = ValidatorUtil.encrypt(key, iv, pwd);
           
        try {   
        boolean valid = LoginDAO.validate(user, encriptar);
     
        
        if (valid) {
           
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/DecisionMakerTool-1.0.0-SNAPSHOT/index.xhtml");
            //contex.getExternalContext().redirect("/DecisionMakerTool/index.xhtml");
        }else {
            
             
                 render=Boolean.TRUE;
                 msg="Incorrect Username and Password";
                 
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Password",
                            "Please enter correct username and Password"));
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/DecisionMakerTool-1.0.0-SNAPSHOT/login.xhtml");
            
        }
        
        } catch (IOException ex) {
                 Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login.xhtml";
    }
}
