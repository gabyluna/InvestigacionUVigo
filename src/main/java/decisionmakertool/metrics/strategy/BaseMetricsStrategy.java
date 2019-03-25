package decisionmakertool.metrics.strategy;

import org.semanticweb.owlapi.model.OWLOntology;

public interface BaseMetricsStrategy {

    int calculateMetric(OWLOntology ontology);

}
