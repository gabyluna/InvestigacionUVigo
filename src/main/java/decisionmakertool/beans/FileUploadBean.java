package decisionmakertool.beans;

import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.util.PathOntology;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component
@Scope("singleton")
public class FileUploadBean {

    private UploadedFile file;
    private String chooseMode;
    private OntologyUtil loadOntology;
    private String answer;

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
        PathOntology pathOntology = new PathOntology();

        try {
            if (chooseMode.equals(OntologyType.AUTOMATIC.getType())) {
                loadFile(pathOntology.getPathAutomaticOntology(),in);
            }
            if (chooseMode.equals(OntologyType.BASE.getType())) {
                loadFile(pathOntology.getPathManualOntology(),in);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadFile(String path, InputStream in) throws IOException {
        int read;
        byte[] bytes = new byte[1024];

        try(OutputStream out = new FileOutputStream(new File(path))){
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
        }

    }

    public void validation() {
        PathOntology pathOntologyBase = new PathOntology();
        PathOntology pathOntologyAuto = new PathOntology();

        if (chooseMode.equals(OntologyType.AUTOMATIC.getType())) {
            answer = loadOntology.validationConsistency(pathOntologyAuto.getPathAutomaticOntology());
        }

        if (chooseMode.equals(OntologyType.BASE.getType())) {
            answer = loadOntology.validationConsistency(pathOntologyBase.getPathManualOntology());
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
