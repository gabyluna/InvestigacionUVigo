/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@ManagedBean
@ViewScoped
public class FileUploadView {

    private UploadedFile file;
    private String realPath = "";
    private String chooseMode;

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
        } catch (IOException ex) {
            Logger.getLogger(FileUploadView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
