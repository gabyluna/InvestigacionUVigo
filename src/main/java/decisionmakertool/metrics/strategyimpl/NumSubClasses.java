package decisionmakertool.metrics.strategyimpl;

import decisionmakertool.util.UtilClass;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NumSubClasses implements BaseMetricsStrategy {

    public int calculateMetric(OWLOntology ontology){
        List<String> lisSubclasses = new ArrayList<>();
        Set<OWLSubClassOfAxiom> subClasses = ontology.getAxioms(AxiomType.SUBCLASS_OF);

        for (OWLSubClassOfAxiom clase : subClasses) {
            lisSubclasses.add(clase.getSignature().toString());
        }

        UtilClass.removeRepeatClasses(lisSubclasses);
        return lisSubclasses.size();
    }

}
