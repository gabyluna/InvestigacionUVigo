/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.owl.LoadOntologyClass;
import decisionmakertool.util.PathOntology;
import java.io.File;
import java.io.FileNotFoundException;
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
    private String realPath = "";
    private String chooseMode;
    private LoadOntologyClass loadOntology = new LoadOntologyClass();
    private String answer = "";

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
                copyFile(file.getFileName(), file.getInputstream());
                FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile(String fileName, InputStream in) throws FileNotFoundException {

        realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources"); // Sustituye "/" por el directorio ej: "/upload"

        System.out.println("path:" + realPath);
        try {
            // write the inputStream to a FileOutputStream
            if (chooseMode.equals("A")) {
                OutputStream out = new FileOutputStream(new File(realPath + "/" + "ontoFinal.owl"));
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                in.close();
                out.flush();
                out.close();
                System.out.println("New file created!");
            }
            if (chooseMode.equals("B")) {
                OutputStream out = new FileOutputStream(new File(realPath + "/" + "ontoBase.owl"));
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                in.close();
                out.flush();
                out.close();
                System.out.println("New file created!");
            }

        } catch (IOException ex) {
            Logger.getLogger(FileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void validation() {
        PathOntology pathOntologyBase = new PathOntology();
        PathOntology pathOntologyAuto = new PathOntology();
        loadOntology = new LoadOntologyClass();

        if (chooseMode.equals("A")) {
            System.out.println("pathBAuto:" + pathOntologyAuto.getPath());
            answer = loadOntology.validationConsistency(pathOntologyAuto.getPath());
            System.out.println("answer:" + answer);
        }

        if (chooseMode.equals("B")) {
            System.out.println("pathBBase:" + pathOntologyBase.getPathBase());
            answer = loadOntology.validationConsistency(pathOntologyBase.getPathBase());
            System.out.println("answer:" + answer);
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
