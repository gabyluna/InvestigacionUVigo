/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import decisionmakertool.metrics.SmellErrors;
import decisionmakertool.service.SmellErrorsInterface;
import decisionmakertool.util.PathOntology;
import ionelvirgilpop.drontoapi.pitfallmanager.AffectedElement;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.collections.ListUtils;

@ManagedBean
@ViewScoped
public class PitfallsBean {

    private List<Pitfall> listPitfalls = new ArrayList<>();
    private Pitfall selectedPitfall = new Pitfall(0);
    private List<AffectedElement> listAffectedElements = new ArrayList<>();
    private PathOntology pathAutomaticOntology = new PathOntology();
    private SmellErrorsInterface smellErrorsInterface;
    private List<Pitfall> listCircularityErrors ;
    private List<Pitfall> listPartitionErrors;
    private List<Pitfall> listSemanticErrors ;
    private  List<Pitfall> listIncompletenessErrors;

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

    public void loadPitfalls() {
        listPitfalls = new ArrayList<>();
        smellErrorsInterface = new SmellErrors();
        validationListPitfalls();
    }

    public void validationListPitfalls(){
        listCircularityErrors = smellErrorsInterface.circularityErrors(pathAutomaticOntology.getPath());
        listPartitionErrors = smellErrorsInterface.partitionErrors(pathAutomaticOntology.getPath());
        listSemanticErrors = smellErrorsInterface.semanticErrors(pathAutomaticOntology.getPath());
        listIncompletenessErrors = smellErrorsInterface.incompletenessErrors(pathAutomaticOntology.getPath());

        if (!listCircularityErrors.isEmpty()) {
            listPitfalls = ListUtils.union(listPitfalls, listCircularityErrors);
        }else if (!listPartitionErrors.isEmpty()) {
            listPitfalls = ListUtils.union(listPitfalls, listPartitionErrors);
            // listPitfalls.addAll(listPartitionErrors) ;
        }else if (!listSemanticErrors.isEmpty()) {
            listPitfalls = ListUtils.union(listPitfalls, listSemanticErrors);
            //listPitfalls.addAll(listSemanticErrors) ;
        }else if(!listIncompletenessErrors.isEmpty()){
            listPitfalls = ListUtils.union(listPitfalls, listIncompletenessErrors);
            // listPitfalls.addAll(listIncompletenessErrors) ;
        }
    }

    public void loadAffectedElements1(Pitfall selectedPitfall1) {
        smellErrorsInterface = new SmellErrors();
        listAffectedElements = smellErrorsInterface.getElementsSmellErrors(pathAutomaticOntology.getPath(), selectedPitfall1);
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
