package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.model.MetricOntologyModel;

public class CROntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float cronto = (float) metricOntologyModel.getNumInstances() / metricOntologyModel.getNumClasses();
        return ComputeQualityMetrics.calculateMetric(cronto);
    }
}
