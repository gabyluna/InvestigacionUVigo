/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.metrics.qualitymetrics;

public class ComputeQualityMetrics {
    private static final int FIRST_RANGE = 1;
    private static final int SECOND_RANGE = 2;
    private static final int THIRD_RANGE = 3;
    private static final int QUARTER_RANGE = 4;
    private static final int FIFTH_RANGE = 5;

    public static int calculateMetric(float value) {
        int result ;
        Range range1 = new Range(-1,20);
        Range range2 = new Range(20,40);
        Range range3 = new Range(40,60);
        Range range4 = new Range(60,80);

        value = value * 100;

        if (range1.includes(value)) {
            result = FIRST_RANGE;
        } else if (range2.includes(value)) {
            result = SECOND_RANGE;
        } else if (range3.includes(value)) {
            result = THIRD_RANGE;
        } else if (range4.includes(value)) {
            result = QUARTER_RANGE;
        } else {
            result = FIFTH_RANGE;
        }
        return result;
    }

    public static int getPunctuation(float value) {
        int total;
        Range range1 = new Range(6,8);
        Range range2 = new Range(4,6);
        Range range3 = new Range(2,4);
        value = value * 100;

        if (range1.includes(value)) {
            total = SECOND_RANGE;
        } else if (range2.includes(value)) {
            total = THIRD_RANGE;
        } else if (range3.includes(value)) {
            total = QUARTER_RANGE;
        } else if (value <= 2) {
            total = FIFTH_RANGE;
        } else {
            total = FIRST_RANGE;
        }
        return total;
    }

    public static int getTotalValue(float rfconto, Range range1, Range range2, Range range3, Range range4) {
        int totalRfconto = 0;
        if (rfconto > 12){
            totalRfconto = ComputeQualityMetrics.FIRST_RANGE;
        }else if(range1.includes(rfconto)) {
            totalRfconto = ComputeQualityMetrics.SECOND_RANGE;
        } else if (range2.includes(rfconto)) {
            totalRfconto = ComputeQualityMetrics.THIRD_RANGE;
        } else if (range3.includes(rfconto)) {
            totalRfconto = ComputeQualityMetrics.QUARTER_RANGE;
        } else if (range4.includes(rfconto)) {
            totalRfconto = ComputeQualityMetrics.FIFTH_RANGE;
        }

        return totalRfconto;
    }
}