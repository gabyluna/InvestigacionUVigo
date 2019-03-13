/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.owl.LoadOntologyClass;
import decisionmakertool.metrics.BaseMetrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import decisionmakertool.metrics.QualityMetrics;
import decisionmakertool.service.QualityMetricsInterface;
import decisionmakertool.model.MetricOntologyModel;
import decisionmakertool.util.PathOntology;
import decisionmakertool.util.UtilClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 *
 * @author gaby_
 */
@ManagedBean
@ViewScoped
public class DashBoardBean implements Serializable {

    private OWLOntology ontology;
    private OWLOntology ontologyBase;
    private OWLReasonerFactory factory = null;
    private OWLReasoner reasoner;
    private OWLDataFactory dataFactory;
    private OWLReasonerFactory factoryBase = null;
    private OWLReasoner reasonerBase;
    private OWLDataFactory dataFactoryBase;
    private List<MetricOntologyModel> listDataOntolgy;
    private Integer[] data = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private Integer[] data1 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private String json = "";
    private String json1 = "";
    private BaseMetrics baseMetricsOntology = new BaseMetrics();
    private LoadOntologyClass loadBaseOntology = new LoadOntologyClass();
    private LoadOntologyClass loadAutomaticOntology = new LoadOntologyClass();
    private QualityMetricsInterface qualityMetrics;
    private String areaPolygon1 = "";
    private String areaPolygon2 = "";

    @PostConstruct
    public void init() {
        try {
            if (this.listDataOntolgy == null) {
                this.listDataOntolgy = new ArrayList<MetricOntologyModel>();

            }
            baseMetricsOntology = new BaseMetrics();
            loadBaseOntology = new LoadOntologyClass();
            loadAutomaticOntology = new LoadOntologyClass();

            loadOntologies();
        } catch (Exception e) {
        }

        //  PathOntology pathAutomaticOntology = new PathOntology();
        //loadAutomaticOntology.validation(pathAutomaticOntology.getPath());
        //if(loadAutomaticOntology.validationConsistency(pathAutomaticOntology.getPath())){
        //   loadOntologies();
        // }
    }

    public void loadOntologies() {
        // Load an ontology from local
        PathOntology pathBaseOntoloy = new PathOntology();
        PathOntology pathAutomaticOntology = new PathOntology();
        MetricOntologyModel metricsBaseOntology = new MetricOntologyModel();
        MetricOntologyModel metricsAutomaticOntology = new MetricOntologyModel();

        loadBaseOntology.loadOntology(pathBaseOntoloy.getPathBase());
        loadAutomaticOntology.loadOntology(pathAutomaticOntology.getPath());

        ontology = loadAutomaticOntology.getOntology();
        ontologyBase = loadBaseOntology.getOntology();
        reasoner = loadAutomaticOntology.getReasoner();
        reasonerBase = loadBaseOntology.getReasoner();
        dataFactory = loadAutomaticOntology.getDataFactory();
        dataFactoryBase = loadBaseOntology.getDataFactory();

        loadBaseMetricsOntology(ontologyBase, dataFactoryBase, reasonerBase, "Base Ontology");
        loadBaseMetricsOntology(ontology, dataFactory, reasoner, "Automatic Ontology");
        //Data for the graphic
        metricsBaseOntology = listDataOntolgy.get(0);
        json = loadQualityMetrics(metricsBaseOntology, data);
        metricsAutomaticOntology = listDataOntolgy.get(1);
        json1 = loadQualityMetrics(metricsAutomaticOntology, data1);
        double areaManual = UtilClass.getPolygonArea(data, 9);
        double areaAutomathic = UtilClass.getPolygonArea(data1, 9);
        areaPolygon1 = "Manual Ontology Area: " + String.format("%.2f", areaManual);
        areaPolygon2 = "Automathic Ontology Area: " + String.format("%.2f", areaAutomathic);

    }

    public void loadBaseMetricsOntology(OWLOntology ontology, OWLDataFactory dataFactory, OWLReasoner reasoner, String name) {
        MetricOntologyModel metricsOntology = new MetricOntologyModel();
        metricsOntology.setNameOntology(name);
        metricsOntology.setNumAnnotations(baseMetricsOntology.getNumAnnotation(ontology));
        metricsOntology.setNumProperties(baseMetricsOntology.getNumProperties(ontology));
        metricsOntology.setNumClasses(baseMetricsOntology.getNumClasses(ontology));
        metricsOntology.setNumInstances(baseMetricsOntology.getNumInstances(ontology));
        metricsOntology.setNumSubclassOf(baseMetricsOntology.getNumSubClasses(ontology));
        metricsOntology.setNumSuperclasses(baseMetricsOntology.getNumSuperClasses(ontology));
        metricsOntology.setRelationsThing(baseMetricsOntology.getNumRelationsOfThing(ontology));
        metricsOntology.setNumClassWithIndividuals(baseMetricsOntology.getNumClassesWithIndividuals(ontology));
        listDataOntolgy.add(metricsOntology);

    }

    public String loadQualityMetrics(MetricOntologyModel metricsOntology, Integer[] data) {
        String jsonAux = "";
        int properties = metricsOntology.getNumProperties();
        int subclassOf = metricsOntology.getNumSubclassOf();
        int superclass = metricsOntology.getNumSuperclasses();
        int annotations = metricsOntology.getNumAnnotations();
        int instances = metricsOntology.getNumInstances();
        int classes = metricsOntology.getNumClasses();
        int relationsThing = metricsOntology.getRelationsThing();
        int classesWithIndividuals = metricsOntology.getNumClassWithIndividuals();
        ObjectMapper mapper = new ObjectMapper();
        qualityMetrics = new QualityMetrics();

        try {
            //RRonto metric
            data[0] = qualityMetrics.rrontoMetric(properties, subclassOf);
            //INronto Metric
            data[1] = qualityMetrics.inrontoMetric(subclassOf, classes);
            //ANNonton Metric
            data[2] = qualityMetrics.anontoMetric(annotations, classes);
            //CRonto Metric
            data[3] = qualityMetrics.crontoMetric(instances, classes);
            //RClass Metric
            data[8] = qualityMetrics.richnessClassMetric(classesWithIndividuals, classes);
            //Nomonto Metric
            data[4] = qualityMetrics.nomontoMetric(properties, classes);
            //RFConto
            data[5] = qualityMetrics.rfcontoMetric(properties, superclass, classes);
            //CBOOntoMetric
            data[6] = qualityMetrics.cboontoMetric(superclass, classes, relationsThing);
            //LCOmonto Metric
            data[7] = qualityMetrics.lcomontoMetric(relationsThing, subclassOf);

            //Load metrics to data
            jsonAux = mapper.writeValueAsString(data);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonAux;
    }

    public List<MetricOntologyModel> getListDataOntolgy() {
        return listDataOntolgy;
    }

    public void setListDataOntolgy(List<MetricOntologyModel> listDataOntolgy) {
        this.listDataOntolgy = listDataOntolgy;
    }

    public Integer[] getData() {

        return data;
    }

    public void setData(Integer[] data) {
        this.data = data;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Integer[] getData1() {
        return data1;
    }

    public void setData1(Integer[] data1) {
        this.data1 = data1;
    }

    public String getJson1() {
        return json1;
    }

    public void setJson1(String json1) {
        this.json1 = json1;
    }

    public String getAreaPolygon1() {
        return areaPolygon1;
    }

    public void setAreaPolygon1(String areaPolygon1) {
        this.areaPolygon1 = areaPolygon1;
    }

    public String getAreaPolygon2() {
        return areaPolygon2;
    }

    public void setAreaPolygon2(String areaPolygon2) {
        this.areaPolygon2 = areaPolygon2;
    }

}
