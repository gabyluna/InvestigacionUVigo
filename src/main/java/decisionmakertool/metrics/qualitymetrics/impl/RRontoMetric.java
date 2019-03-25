package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.model.MetricOntologyModel;

public class RRontoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        int properties = metricOntologyModel.getNumProperties();
        float rronto = (float) properties / (metricOntologyModel.getNumSubclassOf() + properties);
        return ComputeQualityMetrics.calculateMetric(rronto);
    }
}
