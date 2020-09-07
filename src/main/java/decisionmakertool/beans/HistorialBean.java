package decisionmakertool.beans;

import decisionmakertool.dao.OntologyDAO;
import decisionmakertool.entities.Historial;
import decisionmakertool.util.SessionUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import util.UtilClass;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import decisionmakertool.util.PathOntology;
import decisionmakertool.util.SessionUtils;
import decisionmakertool.util.Util;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@ManagedBean
@SessionScoped
public class HistorialBean implements Serializable {
    private List<Historial> listHistorialOntology = new ArrayList<>();
    private Historial historialSelected = new Historial();
    private StreamedContent file;
    private HttpSession session = SessionUtils.getSession();
    private String user = session.getAttribute("username").toString();
    private OntologyDAO ontologyDAO = new OntologyDAO();
    private boolean disableRevertBtn = false;
    private PathOntology path = new PathOntology();

    @PostConstruct
    public void init() {
        if (this.getListHistorialOntology() == null) {
            this.setListHistorialOntology(new ArrayList<>());
        }
        loadHistorial();
    }

    public void loadHistorial(){
        HttpSession session = SessionUtils.getSession();
        String username = session.getAttribute("username").toString();
        OntologyDAO ontologyDAO = new OntologyDAO();
        listHistorialOntology= ontologyDAO.getHistorial(username);
    }

    public void updateState()  throws IOException{
        boolean revert = validateRevert();
        if(revert){
            String pathOntology = path.getREAL_PATH() + "/ontology_" + user + "_A.owl";
            String pathBackup = path.getREAL_PATH() + "/ontoBackup_" + user +".owl";
            Util.deleteFile(pathOntology);
            Util.renameFile(pathBackup,pathOntology);
            Util.pushChangesFile(pathOntology,"ontology_"+user+"_A.owl","Revert version");
            Historial historial = getHistorialData("Revert version");

            if(!ontologyDAO.insert(historial, null)) {

                loadHistorial();
            }
        }else {
            addMessage("It is not possible to reverse this version of the ontology", "");
        }


    }

    public boolean validateRevert()
    {
        String pathBackup = path.getREAL_PATH() + "/ontoBackup_" + user +".owl";
        File file = new File(pathBackup);
        if(!file.exists()) {
            System.out.println("no existe");
            return false;
        }
        return true;
    }

    public Historial getHistorialData(String message){
        String pathAux = path.getREAL_PATH() + "/ontology_" + user  +"_A.owl";
        Historial historial = new Historial();
        historial.setUname(user);
        historial.setType("A");
        historial.setPath(pathAux);
        historial.setDescription(message);
        historial.setQuickFix(0);
        return historial;
    }

    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void downloadFile(){
        try {
            InputStream stream = new FileInputStream(historialSelected.getPath());
            String nameOntology = "ontology_version_" + historialSelected.getId() + ".owl";
            String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(historialSelected.getPath());
            file = new DefaultStreamedContent(stream, contentType, nameOntology);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public List<Historial> getListHistorialOntology() {
        return listHistorialOntology;
    }

    public void setListHistorialOntology(List<Historial> listHistorialOntology) {
        this.listHistorialOntology = listHistorialOntology;
    }

    public Historial getHistorialSelected() {
        return historialSelected;
    }

    public void setHistorialSelected(Historial historialSelected) {
        this.historialSelected = historialSelected;
    }

    public StreamedContent getFile() {
        return file;
    }


    public boolean isDisableRevertBtn() {
        return disableRevertBtn;
    }

    public void setDisableRevertBtn(boolean disableRevertBtn) {
        this.disableRevertBtn = disableRevertBtn;
    }
}
