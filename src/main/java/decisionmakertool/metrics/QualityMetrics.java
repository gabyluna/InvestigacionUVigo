/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.metrics;

import decisionmakertool.service.QualityMetricsInterface;

/**
 *
 * @author Gaby
 */
public class QualityMetrics implements QualityMetricsInterface {
    public static final int FIRST_RANGE = 1;
    public static final int SECOND_RANGE = 2;
    public static final int THIRD_RANGE = 3;
    public static final int QUARTER_RANGE = 4;
    public static final int FIFTH_RANGE = 5;

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
        int result = 0;
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
        int totalNomonto = 0;
        float nomonto = (float) numProperties / numClasses;
        nomonto = nomonto * 100;

        if (nomonto > 6 && nomonto <= 8) {
            totalNomonto = SECOND_RANGE;
        } else if (nomonto > 4 && nomonto <= 6) {
            totalNomonto = THIRD_RANGE;
        } else if (nomonto > 2 && nomonto <= 4) {
            totalNomonto = QUARTER_RANGE;
        } else if (nomonto <= 2) {
            totalNomonto = FIFTH_RANGE;
        } else {
            totalNomonto = FIRST_RANGE;
        }

        return totalNomonto;
    }


   @Override
   public int rfcontoMetric(int numProperties, int subclassesOf, int numClasses) {
       int totalRfconto = 0;
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
        int totalCboonto = 0;
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

   @Override
   public int lcomontoMetric(int numRelationsThing, int subclassesOf) {
       int totalLcomonto = 0;
       float lcomonto = (float) numRelationsThing / subclassesOf;
       lcomonto = lcomonto * 100;
       if (lcomonto > 6 && lcomonto <= 8) {
           totalLcomonto = SECOND_RANGE;
       } else if (lcomonto > 4 && lcomonto <= 6) {
           totalLcomonto = THIRD_RANGE;
       } else if (lcomonto > 2 && lcomonto <= 4) {
           totalLcomonto = QUARTER_RANGE;
       } else if (lcomonto <= 2) {
           totalLcomonto = FIFTH_RANGE;
       }
       else {
           totalLcomonto = FIRST_RANGE;
      }
       return totalLcomonto;
    }
}