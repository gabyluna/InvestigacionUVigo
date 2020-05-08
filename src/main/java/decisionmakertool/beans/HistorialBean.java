package decisionmakertool.beans;

import decisionmakertool.dao.OntologyDAO;
import decisionmakertool.entities.Historial;
import decisionmakertool.util.SessionUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.faces.context.FacesContext.getCurrentInstance;

@Scope(value = "session")
@Component(value = "historialBean")
public class HistorialBean implements Serializable {
    private List<Historial> listHistorialOntology = new ArrayList<>();
    private Historial historialSelected = new Historial();
    private StreamedContent file;

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

    public void updateState(){
        OntologyDAO ontologyDAO = new OntologyDAO();
        for(Historial historial: listHistorialOntology){
            ontologyDAO.updateStatusOntology(historial.isState(),historial.getId());
        }
        addMessage("The state has been updated", "");
        loadHistorial();
    }

    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        getCurrentInstance().addMessage(null, message);
    }

    public void downloadFile(){
        try {
            InputStream stream = new FileInputStream(historialSelected.getPath());
            String nameOntology = "ontology_version_" + historialSelected.getId() + ".owl";
            String contentType = getCurrentInstance().getExternalContext().getMimeType(historialSelected.getPath());
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
}
