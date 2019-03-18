package decisionmakertool.metrics;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.ArrayList;
import java.util.List;

public class NumProperties implements BaseMetricsStrategy {
    public int getBaseMetric(OWLOntology ontology){
        List<String> listProperties = new ArrayList<>();
        for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature()) {
            listProperties.add(BaseMetricsStrategy.pm.getShortForm(p).replaceAll(":", ""));
        }
        return listProperties.size();
    }
}
