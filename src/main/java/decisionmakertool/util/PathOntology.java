package decisionmakertool.util;

import javax.faces.context.FacesContext;

public class PathOntology {

    private String pathAutomaticOntology;
    private String pathManualOntology;
    private String REAL_PATH = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");

    public PathOntology() {

        pathAutomaticOntology = REAL_PATH + "/ontoFinal.owl";
        pathManualOntology = REAL_PATH + "/ontoBase.owl";
    }

    public String getPathAutomaticOntology() {
        return pathAutomaticOntology;
    }

    public String getPathManualOntology() {
        return pathManualOntology;
    }

    public String getREAL_PATH() { return REAL_PATH; }


}
