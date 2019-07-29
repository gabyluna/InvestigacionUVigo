
package decisionmakertool.metrics.qualitymetrics;

import decisionmakertool.model.MetricOntologyModel;

public interface QualityMetricsStrategy {
    int calculateQualityMetric(MetricOntologyModel metricOntologyModel);

}
