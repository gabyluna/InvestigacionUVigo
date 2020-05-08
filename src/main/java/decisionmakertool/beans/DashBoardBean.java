package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.dao.OntologyDAO;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.util.PathOntology;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import decisionmakertool.util.Util;
import metrics.basemetrics.BaseMetricEnum;
import metrics.basemetrics.BaseMetricsFactory;
import metrics.basemetrics.BaseMetricsStrategy;
import metrics.qualitymetrics.QualityMetricEnum;
import metrics.qualitymetrics.QualityMetricFactory;
import metrics.qualitymetrics.QualityMetricsStrategy;
import model.MetricOntologyBuilder;
import model.MetricOntologyModel;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import util.UtilClass;

@Scope(value = "session")
@Component(value = "dashBoardBean")
public class DashBoardBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
    private List<MetricOntologyModel> listDataOntology;
    private OWLOntology manualOntology;
    private OWLOntology automaticOntology;
    private String resultMetricsManualOntology = "[0,0,0,0,0,0,0,0,0]";
    private String resultMetricsAutomaticOntology = "";
    private String areaPolygonManualOntology = "";
    private String areaPolygonAutomaticOntology= "";
    private String labels;
    private JSONObject json = new JSONObject();

    @PostConstruct
    public void init() {
    }

    public void loadData(){
        if (this.listDataOntology == null) {
            this.listDataOntology = new ArrayList<>();
        }
        loadOntologies();
        loadBaseMetrics();
        loadLabelsGraphic();
        loadGraphicMetrics();
    }

    private  void loadOntologies(){
        OntologyDAO ontologyDAO = new OntologyDAO();
        OntologyUtil loadManualOntology = new OntologyUtil(ontologyDAO.findPathOntologyActive("B"));
        manualOntology = loadManualOntology.getOntology();
        OntologyUtil loadAutomaticOntology  = new OntologyUtil(ontologyDAO.findPathOntologyActive("A"));
        automaticOntology = loadAutomaticOntology.getOntology();
    }

    private void loadBaseMetrics() {
        listDataOntology = new ArrayList<>();
        if(manualOntology!= null){
            loadBaseMetricsOntology(manualOntology, "Base Ontology");
        }

        if(automaticOntology != null){
           loadBaseMetricsOntology(automaticOntology,  "Automatic Ontology");
        }
    }
    private void loadLabelsGraphic() {
        String[] arrayLabels = new String[QualityMetricEnum.values().length];

        try {
            int i = 0;
            for(QualityMetricEnum qualityMetric: QualityMetricEnum.values()){
                    arrayLabels[i] = qualityMetric.name();
                    i++;
            }

            labels = Util.arrayToJsonString(arrayLabels);
        } catch (JsonProcessingException e) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadGraphicMetrics(){
        Integer[] dataManualOntology = new Integer[QualityMetricEnum.values().length];
        Integer[] dataAutomaticOntology = new Integer[QualityMetricEnum.values().length];
        if(listDataOntology.size() > 1){
            resultMetricsManualOntology = loadQualityMetrics(listDataOntology.get(0), dataManualOntology);
            resultMetricsAutomaticOntology = loadQualityMetrics(listDataOntology.get(1), dataAutomaticOntology);
            double areaManualOntology = UtilClass.getPolygonArea(dataManualOntology);
            double areaAutomaticOntology = UtilClass.getPolygonArea(dataAutomaticOntology);
            areaPolygonManualOntology = "Manual Ontology Area: " + String.format("%.2f", areaManualOntology);
            areaPolygonAutomaticOntology = "Automatic Ontology Area: " + String.format("%.2f", areaAutomaticOntology);



        }else {
            resultMetricsAutomaticOntology = loadQualityMetrics(listDataOntology.get(0), dataAutomaticOntology);

            double areaAutomaticOntology = UtilClass.getPolygonArea(dataAutomaticOntology);
            areaPolygonAutomaticOntology = "Automatic Ontology Area: " + String.format("%.2f", areaAutomaticOntology);
        }
    }

    public void generateJsonData(List<MetricOntologyModel> listDataOntology){
        Integer[] listQualityMetrics = new Integer[QualityMetricEnum.values().length];
        json.put("labels", labels);
        JSONArray array = new JSONArray();

        for(MetricOntologyModel data: listDataOntology){
            String resultQualityMetric = loadQualityMetrics(data, listQualityMetrics);
            double areaOntology = UtilClass.getPolygonArea(listQualityMetrics);
            String areaPolygonOntology = "Ontology Area: " + String.format("%.2f", areaOntology);
            JSONObject item = new JSONObject();
            item.put("label", areaPolygonOntology);
            item.put("backgroundColor", "rgba(3, 88, 106, 0.2)");
            item.put("borderColor", "rgba(3, 88, 106, 0.80)");
            item.put("pointBorderColor", "rgba(3, 88, 106, 0.80)");
            item.put("pointHoverBackgroundColor", "course1");
            item.put("pointHoverBorderColor", "course1");

            item.put("data", resultQualityMetric);
            array.put(item);
        }
        json.put("datasets", array);
    }

    private void loadBaseMetricsOntology(OWLOntology ontology, String name) {
        BaseMetricsStrategy baseMetricsStrategy;
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.ANNOTATIONS);
        int annotations = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.PROPERTIES);
        int properties = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.CLASSES);
        int classes = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.INSTANCES);
        int instances = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.SUBCLASSES);
        int subClasses = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.SUPERCLASSES);
        int superClasses = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.RELATIONS_THING);
        int relationThing = baseMetricsStrategy.calculateMetric(ontology);
        baseMetricsStrategy = BaseMetricsFactory.getBaseMetric(BaseMetricEnum.CLASS_WITH_INDIVIDUALS);
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
             int totalMetricsQuality = QualityMetricEnum.values().length;
             int positionMetric = 0;

             while(positionMetric < totalMetricsQuality){
                 qualityMetricsStrategy = qualityMetricFactory.getQualityMetric((positionMetric+1));
                 data[positionMetric] =  qualityMetricsStrategy.calculateQualityMetric(metricsOntology);
                 positionMetric++;
             }

             jsonDataMetrics = Util.arrayToJsonString(data);

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


    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
