/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.metrics.templateimpl.*;
import decisionmakertool.metrics.templateimpl.impl.CircularityErrors;
import decisionmakertool.metrics.templateimpl.impl.IncompletenessErrors;
import decisionmakertool.metrics.templateimpl.impl.PartitionErrors;
import decisionmakertool.metrics.templateimpl.impl.SemanticErrors;
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
    private PathOntology pathAutomaticOntology = new PathOntology();

    @PostConstruct
    public void init() {
        if (this.listPitfalls == null) {
            this.listPitfalls = new ArrayList<>();
        }
        if (this.listAffectedElements == null) {
            this.listAffectedElements = new ArrayList<>();
        }

        loadPitfalls();
    }

    private void loadPitfalls() {
        listPitfalls = new ArrayList<>();
        validationListPitfalls();
    }

    private void validationListPitfalls(){
        SmellErrorTemplate smellErrorTemplate;
        smellErrorTemplate = SmellErrorFactory.getSmellError(SmellError.CIRCULARITY);
        List<Pitfall>  listCircularityErrors = smellErrorTemplate.getListSmellErrors(pathAutomaticOntology.getPath());
        smellErrorTemplate = SmellErrorFactory.getSmellError(SmellError.PARTITION);
        List<Pitfall> listPartitionErrors = smellErrorTemplate.getListSmellErrors(pathAutomaticOntology.getPath());
        smellErrorTemplate = SmellErrorFactory.getSmellError(SmellError.SEMANTIC);
        List<Pitfall> listSemanticErrors = smellErrorTemplate.getListSmellErrors(pathAutomaticOntology.getPath());
        smellErrorTemplate = SmellErrorFactory.getSmellError(SmellError.INCOMPLETENESS);
        List<Pitfall> listIncompletenessErrors = smellErrorTemplate.getListSmellErrors(pathAutomaticOntology.getPath());

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
        SmellErrorTemplate smellErrorTemplate = null;
        listAffectedElements = SmellErrorFactory.getSmellError(SmellError.CIRCULARITY).
                getElementsSmellErrors(pathAutomaticOntology.getPath(),selectedPitfall1);
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
}
