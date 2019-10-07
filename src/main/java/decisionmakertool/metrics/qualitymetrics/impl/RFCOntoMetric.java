package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.metrics.qualitymetrics.Range;
import decisionmakertool.model.MetricOntologyModel;

public class RFCOntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float rfconto = (float) (metricOntologyModel.getNumProperties() + metricOntologyModel.getNumSuperclasses()) /
                metricOntologyModel.getNumClasses();
        return ComputeQualityMetrics.getTotalValue(rfconto);
    }


}
