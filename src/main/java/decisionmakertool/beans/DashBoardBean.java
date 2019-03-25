package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.metrics.qualitymetrics.QualityMetric;
import decisionmakertool.metrics.qualitymetrics.QualityMetricFactory;
import decisionmakertool.metrics.strategy.*;
import decisionmakertool.model.MetricOntologyBuilder;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
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
    private OWLOntology manualOntology;
    private  OWLOntology automaticOntology;
    private String resultMetricsManualOntology = "";
    private String resultMetricsAutomaticOntology = "";
    private String areaPolygonManualOntology = "";
    private String areaPolygonAutomaticOntology= "";
    private String labels;

    @PostConstruct
    public void init() {
        if (this.listDataOntology == null) {
            this.listDataOntology = new ArrayList<>();
        }

        loadOntologies();
        loadBaseMetrics();
        loadLabelsGraphic();
        loadGraphicMetrics();
    }

    private  void loadOntologies(){
        PathOntology path = new PathOntology();
        OntologyUtil loadManualOntology = new OntologyUtil(path.getPathManualOntology());
        manualOntology = loadManualOntology.getOntology();
        OntologyUtil loadAutomaticOntology  = new OntologyUtil(path.getPathAutomaticOntology());
        automaticOntology = loadAutomaticOntology.getOntology();
    }

    private void loadBaseMetrics() {
        loadBaseMetricsOntology(manualOntology, "Base Ontology");
        loadBaseMetricsOntology(automaticOntology,  "Automatic Ontology");
    }
    private void loadLabelsGraphic() {
        String[] arrayLabels = new String[QualityMetric.values().length];

        try {
            int i = 0;
            for(QualityMetric qualityMetric: QualityMetric.values()){
                    arrayLabels[i] = qualityMetric.name();
                    i++;
            }
            labels = UtilClass.arrayToJsonString(arrayLabels);
        } catch (JsonProcessingException e) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadGraphicMetrics(){
        Integer[] dataManualOntology = new Integer[QualityMetric.values().length];
        Integer[] dataAutomaticOntology = new Integer[QualityMetric.values().length];
        resultMetricsManualOntology = loadQualityMetrics(listDataOntology.get(0), dataManualOntology);
        resultMetricsAutomaticOntology = loadQualityMetrics(listDataOntology.get(1), dataAutomaticOntology);

        double areaManualOntology = UtilClass.getPolygonArea(dataManualOntology);
        double areaAutomaticOntology = UtilClass.getPolygonArea(dataAutomaticOntology);
        areaPolygonManualOntology = "Manual Ontology Area: " + String.format("%.2f", areaManualOntology);
        areaPolygonAutomaticOntology = "Automatic Ontology Area: " + String.format("%.2f", areaAutomaticOntology);
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
        String jsonDataMetrics = "";

         try {
             QualityMetricsStrategy qualityMetricsStrategy;
             QualityMetricFactory qualityMetricFactory = new QualityMetricFactory();
             int numMetricsQuality = QualityMetric.values().length;
             int cont = 0;

             while(cont< numMetricsQuality){
                 qualityMetricsStrategy = qualityMetricFactory.getQualityMetric((cont+1));
                 data[cont] =  qualityMetricsStrategy.calculateQualityMetric(metricsOntology);
                 cont++;
             }

             jsonDataMetrics = UtilClass.arrayToJsonString(data);
             System.out.println("json:" + jsonDataMetrics);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonDataMetrics;
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


    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }
}
