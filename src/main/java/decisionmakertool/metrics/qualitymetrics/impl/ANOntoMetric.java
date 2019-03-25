package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.model.MetricOntologyModel;

public class ANOntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float anonto = (float) metricOntologyModel.getNumAnnotations() / metricOntologyModel.getNumClasses();
        return ComputeQualityMetrics.calculateMetric(anonto);
    }
}
