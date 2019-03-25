package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.model.MetricOntologyModel;

public class INROntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float inronto = (float) metricOntologyModel.getNumSubclassOf() / metricOntologyModel.getNumClasses();
        return ComputeQualityMetrics.calculateMetric(inronto);
    }
}
