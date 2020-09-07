package decisionmakertool.beans;

import decisionmakertool.dao.OntologyDAO;
import decisionmakertool.entities.Historial;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.util.PathOntology;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import decisionmakertool.util.SessionUtils;
import org.primefaces.model.UploadedFile;



@ManagedBean
@SessionScoped
public class FileUploadBean implements Serializable {

    private UploadedFile file;
    private String chooseMode;
    private OntologyUtil loadOntology;
    private String answer;
    private OntologyDAO ontologyDAO = new OntologyDAO();

    @PostConstruct
    public void init() {
        answer = "";
        loadOntology = new OntologyUtil();
    }

    public void upload() {
        if (file != null) {
            try {
                copyFile(file.getInputstream());
                FacesMessage message = new FacesMessage("Successful", file.getFileName()
                        + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (IOException e) {
                Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void copyFile(InputStream in) {
        try {

            if (chooseMode.equals(OntologyType.AUTOMATIC.getType())) {
                ontologyDAO.insert(getDataHistorial(OntologyType.AUTOMATIC.getType()), in);
            }
            if (chooseMode.equals(OntologyType.BASE.getType())) {
                ontologyDAO.insert(getDataHistorial(OntologyType.BASE.getType()), in);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Historial getDataHistorial(String typeOntology){
        PathOntology pathOntology = new PathOntology();
        HttpSession session = SessionUtils.getSession();
        String user = session.getAttribute("username").toString();
        Historial historial = new Historial();
        historial.setPath(pathOntology.getREAL_PATH());
        historial.setType(typeOntology);
        historial.setDescription("");
        historial.setUname(user);
        historial.setQuickFix(0);
        return historial;
    }


    public void validation() {
        PathOntology pathOntologyBase = new PathOntology();
        PathOntology pathOntologyAuto = new PathOntology();

        if (chooseMode.equals(OntologyType.AUTOMATIC.getType())) {
            answer = loadOntology.validationConsistency(pathOntologyAuto.getPathAutomaticOntology());
        }

        if (chooseMode.equals(OntologyType.BASE.getType())) {
            answer = loadOntology.validationConsistency(pathOntologyBase.getPathOntology());
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getChooseMode() {
        return chooseMode;
    }

    public void setChooseMode(String chooseMode) {
        this.chooseMode = chooseMode;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
