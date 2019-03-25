package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.model.MetricOntologyModel;

public class RCOntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float rClass = (float) metricOntologyModel.getNumClassWithIndividuals()/ metricOntologyModel.getNumClasses();
        return ComputeQualityMetrics.calculateMetric(rClass);
    }
}
