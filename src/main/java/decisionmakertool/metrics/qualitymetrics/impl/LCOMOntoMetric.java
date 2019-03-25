package decisionmakertool.metrics.qualitymetrics.impl;

import decisionmakertool.metrics.qualitymetrics.ComputeQualityMetrics;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.metrics.qualitymetrics.Range;
import decisionmakertool.model.MetricOntologyModel;

public class LCOMOntoMetric implements QualityMetricsStrategy {

    @Override
    public int calculateQualityMetric(MetricOntologyModel metricOntologyModel) {
        float lcomonto = (float) metricOntologyModel.getRelationsThing() / metricOntologyModel.getNumSubclassOf();
        return ComputeQualityMetrics.getPunctuation(lcomonto);
    }
}
