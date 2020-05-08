package decisionmakertool.service;

import drontoapi.pitfallmanager.AffectedElement;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.util.List;
import java.util.Set;

public interface QuickFixInterface {
    Set<OWLAxiom> validateOntology(OWLOntology ontology, List<AffectedElement> listAffectedElements);
    List<String>  getListChangesToApply(Set<OWLAxiom> axiomsToChange);
    OWLOntology getResultQuickFix(OWLOntology ontology, OWLOntologyManager manager, Set<OWLAxiom> axiomsToChange);

}
