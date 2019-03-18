/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.owl.LoadOntologyClass;
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

@ManagedBean
@ViewScoped
public class FileUploadBean {
    private UploadedFile file;
    private String chooseMode;
    private LoadOntologyClass loadOntology;
    private String answer;

    @PostConstruct
    public void init() {
        answer = "";
        loadOntology = new LoadOntologyClass();
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

    public void upload() {
        if (file != null) {
            try {
                copyFile(file.getInputstream());
                FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (IOException e) {
                Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void copyFile(InputStream in) {
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");

         try {
            // write the inputStream to a FileOutputStream
            if (chooseMode.equals("A")) {
                loadFile(realPath + "/" + "ontoFinal.owl",in);
            }
            if (chooseMode.equals("B")) {
                loadFile(realPath + "/" + "ontoBase.owl",in);
            }

        } catch (IOException ex) {
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadFile(String path, InputStream in) throws IOException {
        OutputStream out = new FileOutputStream(new File(path));
        int read;
        byte[] bytes = new byte[1024];

        try{
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
        } catch (IOException e) {
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            out.close();
        }

    }

    public void validation() {
        PathOntology pathOntologyBase = new PathOntology();
        PathOntology pathOntologyAuto = new PathOntology();

        if (chooseMode.equals("A")) {
            answer = loadOntology.validationConsistency(pathOntologyAuto.getPath());
        }

        if (chooseMode.equals("B")) {
            answer = loadOntology.validationConsistency(pathOntologyBase.getPathBase());
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
