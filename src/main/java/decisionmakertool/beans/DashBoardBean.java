/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.metrics.*;
import decisionmakertool.metrics.strategyImpl.*;
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
        MetricOntologyModel metricsOntology = new MetricOntologyModel();
        metricsOntology.setNameOntology(name);

        BaseMetricsStrategy baseMetricsStrategy;
        BaseMetricsFactory baseMetricsFactory = new BaseMetricsFactory();
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.ANNOTATIONS);
        metricsOntology.setNumAnnotations(baseMetricsStrategy.calculateMetric(ontology));
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.PROPERTIES);
        metricsOntology.setNumProperties(baseMetricsStrategy.calculateMetric(ontology));
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.CLASSES);
        metricsOntology.setNumClasses(baseMetricsStrategy.calculateMetric(ontology));
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.INSTANCES);
        metricsOntology.setNumInstances(baseMetricsStrategy.calculateMetric(ontology));
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.SUBCLASSES);
        metricsOntology.setNumSubclassOf(baseMetricsStrategy.calculateMetric(ontology));
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.SUPERCLASSES);
        metricsOntology.setNumSuperclasses(baseMetricsStrategy.calculateMetric(ontology));
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.RELATIONS_THING);
        metricsOntology.setRelationsThing(baseMetricsStrategy.calculateMetric(ontology));
        baseMetricsStrategy = baseMetricsFactory.getBaseMetric(BaseMetric.CLASS_WITH_INDIVIDUALS);
        metricsOntology.setNumClassWithIndividuals(baseMetricsStrategy.calculateMetric(ontology));

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
