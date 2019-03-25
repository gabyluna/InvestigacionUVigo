package decisionmakertool.util;

import javax.faces.context.FacesContext;

public class PathOntology {

    private String pathAutomaticOntology;
    private String pathManualOntology;

    public PathOntology() {
        String realpath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        pathAutomaticOntology = realpath + "/ontoFinal.owl";
        pathManualOntology = realpath + "/ontoBase.owl";
    }

    public String getPathAutomaticOntology() {
        return pathAutomaticOntology;
    }

    public String getPathManualOntology() {
        return pathManualOntology;
    }


}
