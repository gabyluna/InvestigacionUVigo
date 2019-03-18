/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.metrics;

import decisionmakertool.service.QualityMetricsInterface;

public class QualityMetrics implements QualityMetricsInterface {
    private static final int FIRST_RANGE = 1;
    private static final int SECOND_RANGE = 2;
    private static final int THIRD_RANGE = 3;
    private static final int QUARTER_RANGE = 4;
    private static final int FIFTH_RANGE = 5;

    @Override
    public int rrontoMetric(int numProperties, int subclassesOf) {
        float rronto = (float) numProperties / (subclassesOf + numProperties);
        return calculateMetric(rronto);
    }

    @Override
    public int anontoMetric(int numAnnotation, int numClasses) {
        float anonto = (float) numAnnotation / numClasses;
        return calculateMetric(anonto);
    }

    @Override
    public int crontoMetric(int numInstances, int numClasses) {
        float cronto = (float) numInstances / numClasses;
        return calculateMetric(cronto);
    }

    @Override
    public int inrontoMetric(int subclassesOf, int numClasses) {
        float inronto = (float) subclassesOf / numClasses;
        return calculateMetric(inronto);
    }

    @Override
    public int richnessClassMetric(int numClassesWithIndividuals, int numClasses) {
        float rClass = (float) numClassesWithIndividuals / numClasses;
        return calculateMetric(rClass);
    }

    private int calculateMetric(float range) {
        int result ;
        range = range * 100;

        if (range >= 0 && range <= 20) {
            result = FIRST_RANGE;
        } else if (range > 20 && range <= 40) {
            result = SECOND_RANGE;
        } else if (range > 40 && range <= 60) {
            result = THIRD_RANGE;
        } else if (range > 60 && range <= 80) {
            result = QUARTER_RANGE;
        } else {
            result = FIFTH_RANGE;
        }
        return result;
    }

    @Override
    public int nomontoMetric(int numProperties, int numClasses) {
        float nomonto = (float) numProperties / numClasses;
        return getPunctuation(nomonto);
    }

    @Override
    public int lcomontoMetric(int numRelationsThing, int subclassesOf) {
        float lcomonto = (float) numRelationsThing / subclassesOf;
        return getPunctuation(lcomonto);
    }

    private int getPunctuation(float nomonto) {
        int total;
        nomonto = nomonto * 100;

        if (nomonto > 6 && nomonto <= 8) {
            total = SECOND_RANGE;
        } else if (nomonto > 4 && nomonto <= 6) {
            total = THIRD_RANGE;
        } else if (nomonto > 2 && nomonto <= 4) {
            total = QUARTER_RANGE;
        } else if (nomonto <= 2) {
            total = FIFTH_RANGE;
        } else {
            total = FIRST_RANGE;
        }
        return total;
    }


    @Override
   public int rfcontoMetric(int numProperties, int subclassesOf, int numClasses) {
       int totalRfconto;
       float rfconto = (float) (numProperties + subclassesOf) / numClasses;
       rfconto = rfconto * 100;

       if (rfconto > 8 && rfconto <= 12) {
           totalRfconto = SECOND_RANGE;
       } else if (rfconto > 6 && rfconto <= 8) {
           totalRfconto = THIRD_RANGE;
       } else if (rfconto > 3 && rfconto <= 6) {
           totalRfconto = QUARTER_RANGE;
       } else if (rfconto >= 1 && rfconto <= 3) {
           totalRfconto = FIFTH_RANGE;
       } else {
           totalRfconto = FIRST_RANGE;
       }
       return totalRfconto;
   }

   @Override
   public int cboontoMetric(int superclass, int numClasses, int numRelationsThing) {
        int totalCboonto ;
        float cboonto = (float) superclass / (numClasses - numRelationsThing);
        if (cboonto > 6 && cboonto <= 8) {
            totalCboonto = SECOND_RANGE;
        } else if (cboonto > 4 && cboonto <= 6) {
            totalCboonto = THIRD_RANGE;
        } else if (cboonto > 2 && cboonto <= 4) {
            totalCboonto = QUARTER_RANGE;
        } else if (cboonto >= 1 && cboonto <= 2) {
            totalCboonto = FIFTH_RANGE;
        } else {
            totalCboonto = FIRST_RANGE;
        }
        return totalCboonto;
    }

}