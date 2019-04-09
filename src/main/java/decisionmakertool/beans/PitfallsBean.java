package decisionmakertool.beans;

import decisionmakertool.metrics.templateimpl.*;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.util.PathOntology;
import drontoapi.pitfallmanager.AffectedElement;
import drontoapi.pitfallmanager.Pitfall;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class PitfallsBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
    private List<Pitfall> listPitfalls = new ArrayList<>();
    private Pitfall selectedPitfall = new Pitfall(0);
    private List<AffectedElement> listAffectedElements = new ArrayList<>();
    private PathOntology path = new PathOntology();
    private String pathOntology = "";
    private boolean selectAll = false;

    @PostConstruct
    public void init() {
        if (this.listPitfalls == null) {
            this.listPitfalls = new ArrayList<>();
        }
        if (this.listAffectedElements == null) {
            this.listAffectedElements = new ArrayList<>();
        }
    }

    public void loadPitfalls(){
        listPitfalls = new ArrayList<>();
        SmellErrorTemplate circularityErrorTemplate = SmellErrorFactory.getSmellError(SmellError.CIRCULARITY);
        List<Pitfall>  listCircularityErrors = circularityErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate partitionErrorTemplate = SmellErrorFactory.getSmellError(SmellError.PARTITION);
        List<Pitfall> listPartitionErrors = partitionErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate semanticErrorTemplate = SmellErrorFactory.getSmellError(SmellError.SEMANTIC);
        List<Pitfall> listSemanticErrors = semanticErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate incompletenessErrorTemplate = SmellErrorFactory.getSmellError(SmellError.INCOMPLETENESS);
        List<Pitfall> listIncompletenessErrors = incompletenessErrorTemplate.getListSmellErrors(pathOntology);

        addPitfallsAtList(listCircularityErrors);
        addPitfallsAtList(listPartitionErrors);
        addPitfallsAtList(listSemanticErrors);
        addPitfallsAtList(listIncompletenessErrors);
 }

    private void addPitfallsAtList(List<Pitfall>  listPitfallErrors){
        if (!listPitfallErrors.isEmpty()) {
            listPitfalls =union(listPitfalls, listPitfallErrors);
        }
    }

    private <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }

    public void loadAffectedElements(Pitfall selectedPitfall1) {
       listAffectedElements = SmellErrorTemplate.getElementsSmellErrors(pathOntology,selectedPitfall1);
    }

    public void selectAllElements(){
        if(selectAll){
            for (AffectedElement affectedElement: listAffectedElements){
                affectedElement.setSelected(true);
            }
        }else {
            for (AffectedElement affectedElement: listAffectedElements){
                affectedElement.setSelected(false);
            }
        }
    }

    public void applyQuickFix() throws OWLOntologyStorageException {
        OntologyUtil ontologyUtil = new OntologyUtil(pathOntology);

        for(AffectedElement element:listAffectedElements){
            if(element.getSelected()){
                ontologyUtil.removeAxioms(element.getURI());
            }
        }
        FacesMessage message = new FacesMessage("Successful", "Quick fix"
                + " is done.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        loadPitfalls();
        selectAll = false;
    }

    public List<Pitfall> getListPitfalls() {
        return listPitfalls;
    }

    public void setListPitfalls(List<Pitfall> listPitfalls) {
        this.listPitfalls = listPitfalls;
    }

    public Pitfall getSelectedPitfall() {
        return selectedPitfall;
    }

    public void setSelectedPitfall(Pitfall selectedPitfall) {
        this.selectedPitfall = selectedPitfall;
    }

    public List<AffectedElement> getListAffectedElements() {
        return listAffectedElements;
    }

    public void setListAffectedElements(List<AffectedElement> listAffectedElements) {
        this.listAffectedElements = listAffectedElements;
    }

    public String getPathOntology() {
        return pathOntology;
    }

    public void setPathOntology(String pathOntology) {
        this.pathOntology = pathOntology;
    }

    public PathOntology getPath() {
        return path;
    }

    public void setPath(PathOntology path) {
        this.path = path;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

}
