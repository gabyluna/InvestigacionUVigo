package decisionmakertool.metrics.strategyimpl;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NumSuperClasses implements BaseMetricsStrategy {
    public int calculateMetric(OWLOntology ontology){
        List<String> listSupClass = new ArrayList<>();
        Set<OWLClass> classes = ontology.getClassesInSignature();

        for (OWLClass classAux : classes) {
            Set<OWLClassExpression> superclasses = classAux.getSuperClasses(ontology);
            for (OWLClassExpression sc : superclasses) {
                listSupClass.add(sc.getSignature().toString());
            }
        }

        return listSupClass.size();
    }
}
