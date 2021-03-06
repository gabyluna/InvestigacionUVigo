package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.dao.OntologyDAO;
import decisionmakertool.entities.Historial;
import decisionmakertool.entities.QuickFixModel;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.service.QuickFixFactory;
import decisionmakertool.service.QuickFixInterface;
import decisionmakertool.util.PathOntology;
import decisionmakertool.util.SessionUtils;
import decisionmakertool.util.Util;
import metrics.basemetrics.BaseMetricEnum;
import metrics.basemetrics.BaseMetricsFactory;
import metrics.basemetrics.BaseMetricsStrategy;
import metrics.qualitymetrics.QualityMetricEnum;
import metrics.qualitymetrics.QualityMetricFactory;
import metrics.qualitymetrics.QualityMetricsStrategy;
import model.MetricOntologyBuilder;
import model.MetricOntologyModel;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import util.UtilClass;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class OptimizeOntologyBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
    private PathOntology path = new PathOntology();
    private String typeOntology = "";
    private int quickFixSelected = 0;
    private String area = "";
    private OntologyDAO ontologyDAO = new OntologyDAO();
    private String pathOntology = "";
    private String description = "";
    private boolean disabledSave = true;
    private HttpSession session = SessionUtils.getSession();
    private String user = session.getAttribute("username").toString();

    @PostConstruct
    public void init() {

    }

    public void applyQuickFix() {
        pathOntology = ontologyDAO.findPathOntologyActive(typeOntology);
        OntologyUtil ontologyUtil = new OntologyUtil(pathOntology);
        String pathAux = path.getREAL_PATH() + "/ontoQuickFix_" + user + ".owl";
        QuickFixFactory quickFixFactory = new QuickFixFactory();
        QuickFixInterface quickFixInterface = quickFixFactory.applyQuickFix(getQuickFixSelected());
        Set<OWLAxiom > axioms = quickFixInterface.validateOntology(ontologyUtil.getOntology(),new ArrayList<>());
        quickFixInterface.getOntologyResult(ontologyUtil.getOntology(),ontologyUtil.getManager(), axioms);
        try {
            ontologyUtil.saveOntology(pathAux);
            setArea(calculateArea());
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    public String  calculateArea(){
        Integer[] dataOntology = new Integer[QualityMetricEnum.values().length];
        if(getQuickFixSelected() != 0){
            int lastId = ontologyDAO.findLastId();
            String pathAux = path.getREAL_PATH() + "/ontoQuickFix_" + user +".owl";
            MetricOntologyModel metricOntologyModel = loadBaseMetrics(pathAux);
            loadQualityMetrics(metricOntologyModel, dataOntology);
            double areaOntology = UtilClass.getPolygonArea(dataOntology);
            return   String.valueOf(areaOntology);
        }
        return "";
    }

    public MetricOntologyModel loadBaseMetrics(String pathOntology){

        OntologyUtil loadOntology = new OntologyUtil(pathOntology);
        OWLOntology ontology = loadOntology.getOntology();
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

        return new MetricOntologyBuilder("QuickFix").setNumAnnotations(annotations).
                setNumProperties(properties).setNumClasses(classes).setNumInstances(instances).
                setNumSubclassOf(subClasses).setNumSuperClasses(superClasses).setRelationsThing(relationThing).
                setNumClassWithIndividuals(classWithIndividuals).build();
    }

    public String loadQualityMetrics(MetricOntologyModel metricsOntology, Integer[] data) {
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

    public void saveQuickFix() throws IOException {
        if(!description.isEmpty()){
            Historial historial = getHistorialData();
            if(!ontologyDAO.insert(historial, null)){
                int lastId = ontologyDAO.findLastId();
                String pathAux = path.getREAL_PATH() + "/ontoQuickFix_" + user +".owl";
                String pathBackup = path.getREAL_PATH() + "/ontoBackup_" + user +".owl";
                Util.pushChangesFile(pathAux,"ontology_"+user+"_"+ typeOntology +".owl",historial.getDescription());
                Util.renameFile(pathOntology,pathBackup);
                Util.renameFile(pathAux,pathOntology);
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error"));
        }

    }

    public Historial getHistorialData(){
        int lastId = ontologyDAO.findLastId();
        String pathAux = path.getREAL_PATH() + "/ontology_" + user + "_" + typeOntology +".owl";
        Historial historial = new Historial();
        historial.setUname(user);
        historial.setType(typeOntology);
        historial.setPath(pathAux);
        historial.setDescription(description);
        historial.setQuickFix(quickFixSelected);

        return historial;
    }

    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getTypeOntology() {
        return typeOntology;
    }

    public void setTypeOntology(String typeOntology) {
        this.typeOntology = typeOntology;
    }

    public PathOntology getPath() {
        return path;
    }

    public void setPath(PathOntology path) {
        this.path = path;
    }

    public int getQuickFixSelected() {
        return quickFixSelected;
    }

    public void setQuickFixSelected(int quickFixSelected) {
        this.quickFixSelected = quickFixSelected;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isDisabledSave() {
        return disabledSave;
    }

    public void setDisabledSave(boolean disabledSave) {
        this.disabledSave = disabledSave;
    }
}
