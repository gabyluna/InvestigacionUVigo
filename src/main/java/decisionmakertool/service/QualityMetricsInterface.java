/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.service;

/**
 *
 * @author Gaby
 */
public interface QualityMetricsInterface {

    int rrontoMetric(int numProperties, int subclassesOf);

    int anontoMetric(int numAnnotation, int numClasses);

    int crontoMetric(int numInstances, int numClasses);

    int inrontoMetric(int subclassesOf, int numClasses);

    int richnessClassMetric(int numClassesWithIndividuals, int numClasses);

    int nomontoMetric(int numProperties, int numClasses);

    int rfcontoMetric(int numProperties, int subclassesOf, int numClasses);

    int cboontoMetric(int superclasses, int numClasses, int numRelationsThing);

    int lcomontoMetric(int numRelationsThing, int subclassesOf);

}
