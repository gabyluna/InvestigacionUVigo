package decisionmakertool.metrics;

import org.semanticweb.owlapi.model.OWLOntology;

public class BaseMetricsContext {
    private BaseMetricsStrategy baseMetricsStrategy;

    public void setBaseMetricsStrategy(BaseMetricsStrategy baseMetricsStrategy){
        this.baseMetricsStrategy = baseMetricsStrategy;
    }

    public int getBaseMetricStrategy(OWLOntology ontology){
        return baseMetricsStrategy.getBaseMetric(ontology);
    }
}
