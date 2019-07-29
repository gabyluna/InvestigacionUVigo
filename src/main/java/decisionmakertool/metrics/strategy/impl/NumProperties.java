package decisionmakertool.metrics.strategy.impl;

import decisionmakertool.metrics.strategy.BaseMetricsStrategy;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.ArrayList;
import java.util.List;

public class NumProperties implements BaseMetricsStrategy {

    private static final DefaultPrefixManager pm = new DefaultPrefixManager("http://www.text2onto.org#");

    public int calculateMetric(OWLOntology ontology){
        List<String> listProperties = new ArrayList<>();

        for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature()) {
            listProperties.add(pm.getShortForm(p).replaceAll(":", ""));
        }
        return listProperties.size();
    }

}
