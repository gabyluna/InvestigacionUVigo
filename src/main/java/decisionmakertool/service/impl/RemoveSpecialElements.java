package decisionmakertool.service.impl;

import decisionmakertool.service.QuickFixInterface;
import drontoapi.pitfallmanager.AffectedElement;
import org.apache.jena.base.Sys;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationAssertionAxiomImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveSpecialElements implements QuickFixInterface {
    @Override
    public  Set<OWLAxiom>  validateOntology(OWLOntology ontology,  List<AffectedElement> listAffectedElements) {
        Set<OWLAxiom> axiomsToChange =  new HashSet<>();
        for (OWLAxiom axiom : ontology.getAxioms()) {
            String value[] = axiom.getSignature().toString().split("#");
            System.out.println("value_" + value);
            String aux = value.length > 1 ? value[1].replace("]", "")
                    .replace(">","") : "";
            Pattern p = Pattern.compile("[a-zA-Z0-9_]");
            Matcher m = p.matcher(aux);
            boolean hasSpecial = m.find();
            if (hasSpecial || aux.startsWith("_") || aux.endsWith("_")) {
              axiomsToChange.add(axiom);
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
