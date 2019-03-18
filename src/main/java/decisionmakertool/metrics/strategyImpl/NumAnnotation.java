package decisionmakertool.metrics.strategyImpl;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NumAnnotation implements BaseMetricsStrategy {
    public int calculateMetric(OWLOntology ontology){
        List<String> listAnnotations = new ArrayList<>();
        Set<OWLAnnotationAssertionAxiom> annotations = ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION);
        for (OWLAnnotationAssertionAxiom annotation : annotations) {
            listAnnotations.add(annotation.getValue().toString());
        }
        return listAnnotations.size();
    }
}
