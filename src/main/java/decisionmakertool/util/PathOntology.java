/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import javax.faces.context.FacesContext;

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

    public String getPathBase() {
        return pathBase;
    }


}
