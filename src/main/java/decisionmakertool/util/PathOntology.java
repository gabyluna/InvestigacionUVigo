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

    private String realpath;
    private String path;
    private String pathBase;

    public PathOntology() {
        realpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        path = realpath + "/ontoFinal.owl";
        pathBase = realpath + "/ontoBase.owl";
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
