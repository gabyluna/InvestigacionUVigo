package decisionmakertool.metrics.strategy;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public interface BaseMetricsStrategy {

    DefaultPrefixManager pm = new DefaultPrefixManager("http://www.text2onto.org#");

    int calculateMetric(OWLOntology ontology);
}
