package decisionmakertool.metrics.strategyimpl;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NumClasses implements BaseMetricsStrategy {
    public int calculateMetric(OWLOntology ontology){
        List<String> listClasses = new ArrayList<>();
        Set<OWLClass> classes = ontology.getClassesInSignature();

        for (OWLClass classAux : classes) {
            listClasses.add(BaseMetricsStrategy.pm.getShortForm(classAux).replaceAll(":", ""));
        }

        return listClasses.size();
    }
}
