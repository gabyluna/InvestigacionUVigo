package decisionmakertool.entities;

import decisionmakertool.owl.OntologyUtil;
import drontoapi.pitfallmanager.AffectedElement;

import java.util.List;

public class QuickFixModel {
    private OntologyUtil ontologyUtil;
    private List<AffectedElement> listAffectedElements;
    private String path;

    public OntologyUtil getOntologyUtil() {
        return ontologyUtil;
    }

    public void setOntologyUtil(OntologyUtil ontologyUtil) {
        this.ontologyUtil = ontologyUtil;
    }

    public List<AffectedElement> getListAffectedElements() {
        return listAffectedElements;
    }

    public void setListAffectedElements(List<AffectedElement> listAffectedElements) {
        this.listAffectedElements = listAffectedElements;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
