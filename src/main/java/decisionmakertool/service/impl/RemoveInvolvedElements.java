package decisionmakertool.service.impl;

import decisionmakertool.service.QuickFixInterface;
import drontoapi.pitfallmanager.AffectedElement;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveInvolvedElements implements QuickFixInterface {
    @Override
    public  Set<OWLAxiom>  validateOntology(OWLOntology ontology, List<AffectedElement> listAffectedElements) {
        Set<OWLAxiom> axiomsToChange =  new HashSet<>();
        for ( OWLAxiom axiom : ontology.getAxioms ()) {
            for(AffectedElement affectedElement: listAffectedElements){
                if (axiom.getSignature().toString().contains(affectedElement.getURI())) {
                    axiomsToChange.add(axiom);
                }
            }
        }
        return  axiomsToChange;
    }

    @Override
    public List<String> getListChangesToApply(Set<OWLAxiom> axiomsToChange) {
        List<String> listChanges = new ArrayList<>();
        for ( OWLAxiom axiom : axiomsToChange) {
          listChanges.add("Axiom to remove:" + axiom);
        }
       return listChanges;
    }

    @Override
    public OWLOntology getOntologyResult(OWLOntology ontology, OWLOntologyManager manager, Set<OWLAxiom> axiomsToRemove) {
        manager.removeAxioms(ontology, axiomsToRemove);
        return ontology;
    }
}
