package decisionmakertool.util;

import javax.faces.context.FacesContext;

public class PathOntology {

    private String pathAutomaticOntology;
    private String pathOntology;
    private String REAL_PATH = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
    private String pathRawRepo = "https://raw.githubusercontent.com/gabyluna/ontologies/master/";
    private String pathRepo = "/root/ontologies";

    public PathOntology() {

        pathAutomaticOntology = REAL_PATH + "/ontoFinal.owl";
        pathOntology = REAL_PATH + "/ontoBase.owl";
    }

    public String getPathAutomaticOntology() {
        return pathAutomaticOntology;
    }

    public String getPathOntology() {
        return pathOntology;
    }

    public String getREAL_PATH() { return REAL_PATH; }


    public String getPathRawRepo() {
        return pathRawRepo;
    }

    public String getPathRepo() {
        return pathRepo;
    }
}
