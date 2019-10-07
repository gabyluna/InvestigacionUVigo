package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.metrics.qualitymetrics.Range;
import decisionmakertool.model.MetricOntologyModel;

public class CBOntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float cboonto = (float) metricOntologyModel.getNumSuperclasses() /
                (metricOntologyModel.getNumClasses() - metricOntologyModel.getRelationsThing());
        return ComputeQualityMetrics.getTotalValue(cboonto);
    }
}
