package decisionmakertool.beans;

import decisionmakertool.metrics.templateimpl.*;
import decisionmakertool.util.PathOntology;
import ionelvirgilpop.drontoapi.pitfallmanager.AffectedElement;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PitfallsBean {
    private List<Pitfall> listPitfalls = new ArrayList<>();
    private Pitfall selectedPitfall = new Pitfall(0);
    private List<AffectedElement> listAffectedElements = new ArrayList<>();
    private PathOntology path = new PathOntology();
    private String pathOntology = "";
    private AffectedElement affectedElementSelected;
    private List<AffectedElement> listSelectedElements;

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


    public AffectedElement getAffectedElementSelected() {
        return affectedElementSelected;
    }

    public void setAffectedElementSelected(AffectedElement affectedElementSelected) {
        this.affectedElementSelected = affectedElementSelected;
    }

    public List<AffectedElement> getListSelectedElements() {
        return listSelectedElements;
    }

    public void setListSelectedElements(List<AffectedElement> listSelectedElements) {
        this.listSelectedElements = listSelectedElements;
    }
}
