/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import javax.faces.context.FacesContext;

/**
 *
 * @author usuario
 */
public class PathOntology {
    
   // private String path="C:\\tmp\\ontologyNexpose.owl";
    //private String path = "E:\\Documentos\\Gaby\\Maestría\\tesis\\Desarrollo\\30_01_2017_Development\\DecisionMakerTool\\target\\DecisionMakerTool-1.0.0-SNAPSHOT\\resources\\OntologyNexpose.owl";

    private String realpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
    //private String path = realpath + "/OntologyNexpose.owl";
     private String path = realpath + "/ontoFinal.owl";
     private String pathBase = realpath + "/ontoBase.owl";
   
    
    public PathOntology() {
    }

    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the pathBase
     */
    public String getPathBase() {
        return pathBase;
    }

    /**
     * @param pathBase the pathBase to set
     */
    public void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }
    
    
}
