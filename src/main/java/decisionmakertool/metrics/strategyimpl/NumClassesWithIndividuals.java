package decisionmakertool.metrics.strategyimpl;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NumClassesWithIndividuals implements BaseMetricsStrategy {
    public int calculateMetric(OWLOntology ontology){
        List<String> listClassWithIndividuals = new ArrayList<>();
        Set<OWLClass> classes = ontology.getClassesInSignature();

        for (OWLClass classAux : classes) {
            Set<OWLIndividual> individuals = classAux.getIndividuals(ontology);
            if (!individuals.isEmpty()) {
                listClassWithIndividuals.add(classAux.getSignature().toString());
            }

        }
        return listClassWithIndividuals.size();
    }
}
