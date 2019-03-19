/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.metrics.*;
import decisionmakertool.metrics.strategy.*;
import decisionmakertool.model.MetricOntologyBuilder;
import decisionmakertool.owl.LoadOntologyClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import decisionmakertool.service.QualityMetricsInterface;
import decisionmakertool.model.MetricOntologyModel;
import decisionmakertool.util.PathOntology;
import decisionmakertool.util.UtilClass;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.semanticweb.owlapi.model.OWLOntology;

@ManagedBean
@ViewScoped
public class DashBoardBean {
    private List<MetricOntologyModel> listDataOntology;
    private Integer[] dataManualOntology = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private Integer[] dataAutomaticOntology = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private String resultMetricsManualOntology = "";
    private String resultMetricsAutomaticOntology = "";
    private String areaPolygonManualOntology = "";
    private String areaPolygonAutomaticOntology= "";

    @PostConstruct
    public void init() {
        if (this.listDataOntology == null) {
            this.listDataOntology = new ArrayList<>();
        }

        loadOntologies();
        loadGraphicMetrics();
    }

    private void loadOntologies() {
        // Load an ontology from local
        PathOntology pathBaseOntology = new PathOntology();
        PathOntology pathAutomaticOntology = new PathOntology();
        LoadOntologyClass loadBaseOntology;
        LoadOntologyClass loadAutomaticOntology;
        loadBaseOntology = new LoadOntologyClass(pathBaseOntology.getPathBase());
        loadAutomaticOntology = new LoadOntologyClass(pathAutomaticOntology.getPath());
        OWLOntology ontology = loadAutomaticOntology.getOntology();
        OWLOntology ontologyBase = loadBaseOntology.getOntology();
        loadBaseMetricsOntology(ontologyBase, "Base Ontology");
        loadBaseMetricsOntology(ontology,  "Automatic Ontology");
    }

    private void loadGraphicMetrics(){
        //Data for the graphic
        MetricOntologyModel metricsBaseOntology = listDataOntology.get(0);
        resultMetricsManualOntology = loadQualityMetrics(metricsBaseOntology, dataManualOntology);
        MetricOntologyModel metricsAutomaticOntology = listDataOntology.get(1);
        resultMetricsAutomaticOntology = loadQualityMetrics(metricsAutomaticOntology, dataAutomaticOntology);
        double areaManual = UtilClass.getPolygonArea(dataManualOntology, 9);
        double areaAutomatic = UtilClass.getPolygonArea(dataAutomaticOntology, 9);
        areaPolygonManualOntology = "Manual Ontology Area: " + String.format("%.2f", areaManual);
        areaPolygonAutomaticOntology = "Automatic Ontology Area: " + String.format("%.2f", areaAutomatic);
    }

    private void loadBaseMetricsOntology(OWLOntology ontology, String name) {
        BaseMetricsStrategy baseMetricsStrategy;
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.ANNOTATIONS);
        int annotations = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.PROPERTIES);
        int properties = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.CLASSES);
        int classes = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.INSTANCES);
        int instances = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.SUBCLASSES);
        int subClasses = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.SUPERCLASSES);
        int superClasses = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.RELATIONS_THING);
        int relationThing = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetric.CLASS_WITH_INDIVIDUALS);
        int classWithIndividuals = baseMetricsStrategy.calculateMetric(ontology);

        MetricOntologyModel metricsOntology = new MetricOntologyBuilder(name).setNumAnnotations(annotations).
                setNumProperties(properties).setNumClasses(classes).setNumInstances(instances).
                setNumSubclassOf(subClasses).setNumSuperClasses(superClasses).setRelationsThing(relationThing).
                setNumClassWithIndividuals(classWithIndividuals).build();

        listDataOntology.add(metricsOntology);
    }

    private String loadQualityMetrics(MetricOntologyModel metricsOntology, Integer[] data) {
        QualityMetricsInterface qualityMetrics = new QualityMetrics();
        int properties = metricsOntology.getNumProperties();
        int subclassOf = metricsOntology.getNumSubclassOf();
        int superclass = metricsOntology.getNumSuperclasses();
        int annotations = metricsOntology.getNumAnnotations();
        int instances = metricsOntology.getNumInstances();
        int classes = metricsOntology.getNumClasses();
        int relationsThing = metricsOntology.getRelationsThing();
        int classesWithIndividuals = metricsOntology.getNumClassWithIndividuals();
        ObjectMapper mapper = new ObjectMapper();
        String jsonAux = "";

        try {
            data[0] = qualityMetrics.rrontoMetric(properties, subclassOf);
            data[1] = qualityMetrics.inrontoMetric(subclassOf, classes);
            data[2] = qualityMetrics.anontoMetric(annotations, classes);
            data[3] = qualityMetrics.crontoMetric(instances, classes);
            data[4] = qualityMetrics.nomontoMetric(properties, classes);
            data[5] = qualityMetrics.rfcontoMetric(properties, superclass, classes);
            data[6] = qualityMetrics.cboontoMetric(superclass, classes, relationsThing);
            data[7] = qualityMetrics.lcomontoMetric(relationsThing, subclassOf);
            data[8] = qualityMetrics.richnessClassMetric(classesWithIndividuals, classes);

            //Load metrics to data
            jsonAux = mapper.writeValueAsString(data);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonAux;
    }

    public List<MetricOntologyModel> getListDataOntology() {
        return listDataOntology;
    }

    public String getResultMetricsManualOnto() {
        return resultMetricsManualOntology;
    }

    public String getResultMetricsAutomaticOntology() {
        return resultMetricsAutomaticOntology;
    }

    public String getAreaPolygonManualOntology() {
        return areaPolygonManualOntology;
    }

    public void setAreaPolygonManualOntology(String areaPolygonManualOntology) {
        this.areaPolygonManualOntology = areaPolygonManualOntology;
    }

    public String getAreaPolygonAutomaticOntology() {
        return areaPolygonAutomaticOntology;
    }

    public void setAreaPolygonAutomaticOntology(String areaPolygonAutomaticOntology) {
        this.areaPolygonAutomaticOntology = areaPolygonAutomaticOntology;
    }

}
