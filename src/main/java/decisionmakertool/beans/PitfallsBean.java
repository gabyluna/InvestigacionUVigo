package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.util.PathOntology;
import decisionmakertool.util.Util;
import drontoapi.pitfallmanager.AffectedElement;
import drontoapi.pitfallmanager.Pitfall;
import metrics.basemetrics.BaseMetricEnum;
import metrics.basemetrics.BaseMetricsFactory;
import metrics.basemetrics.BaseMetricsStrategy;
import metrics.qualitymetrics.QualityMetricEnum;
import metrics.qualitymetrics.QualityMetricFactory;
import metrics.qualitymetrics.QualityMetricsStrategy;
import metrics.smellerrors.SmellErrorEnum;
import metrics.smellerrors.SmellErrorFactory;
import metrics.smellerrors.SmellErrorTemplate;
import model.MetricOntologyBuilder;
import model.MetricOntologyModel;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import util.UtilClass;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class PitfallsBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
    private List<Pitfall> listPitfalls = new ArrayList<>();
    private Pitfall selectedPitfall = new Pitfall(0);
    private List<AffectedElement> listAffectedElements = new ArrayList<>();
    private PathOntology path = new PathOntology();
    private String pathOntology = "";
    private boolean selectAll = false;
    private String quickFixSelected = "";
    private String area = "";

    @PostConstruct
    public void init() {
        if (this.listPitfalls == null) {
            this.listPitfalls = new ArrayList<>();
        }
        if (this.listAffectedElements == null) {
            this.listAffectedElements = new ArrayList<>();
        }
    }

    public void loadPitfalls(){
        listPitfalls = new ArrayList<>();
        SmellErrorTemplate circularityErrorTemplate = SmellErrorFactory.getSmellError(SmellErrorEnum.CIRCULARITY);
        List<Pitfall>  listCircularityErrors = circularityErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate partitionErrorTemplate = SmellErrorFactory.getSmellError(SmellErrorEnum.PARTITION);
        List<Pitfall> listPartitionErrors = partitionErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate semanticErrorTemplate = SmellErrorFactory.getSmellError(SmellErrorEnum.SEMANTIC);
        List<Pitfall> listSemanticErrors = semanticErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate incompletenessErrorTemplate = SmellErrorFactory.getSmellError(SmellErrorEnum.INCOMPLETENESS);
        List<Pitfall> listIncompletenessErrors = incompletenessErrorTemplate.getListSmellErrors(pathOntology);

        addPitfallsAtList(listCircularityErrors);
        addPitfallsAtList(listPartitionErrors);
        addPitfallsAtList(listSemanticErrors);
        addPitfallsAtList(listIncompletenessErrors);
 }

    private void addPitfallsAtList(List<Pitfall>  listPitfallErrors){
        if (!listPitfallErrors.isEmpty()) {
            listPitfalls =union(listPitfalls, listPitfallErrors);
        }
    }

    private <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }

    public void loadAffectedElements(Pitfall selectedPitfall1) {
       setSelectedPitfall(selectedPitfall);
       listAffectedElements = SmellErrorTemplate.getElementsSmellErrors(pathOntology,selectedPitfall1);
    }

    public void selectAllElements(){
        if(selectAll){
            for (AffectedElement affectedElement: listAffectedElements){
                affectedElement.setSelected(true);
            }
        }else {
            for (AffectedElement affectedElement: listAffectedElements){
                affectedElement.setSelected(false);
            }
        }
    }

    public void applyQuickFix() throws OWLOntologyStorageException {
        OntologyUtil ontologyUtil = new OntologyUtil(pathOntology);

        if(getQuickFixSelected().equals("1")){
            for(AffectedElement element:listAffectedElements){
                if(element.getSelected()){
                    ontologyUtil.removeAxioms(element.getURI());
                }
            }

        }else{
            LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
            List<AffectedElement> listAffectedElemenstToRemove = new ArrayList<>();
            int sizeList = listAffectedElements.size();

            for(int i = 0; i < sizeList; i++){
                if(listAffectedElements.get(i).getSelected()){
                    String wordAux1 =  getWord(listAffectedElements.get(i).getURI());

                    for(int j=(sizeList-1);j >= 0 ; j--){
                        String wordAux2 =  getWord(listAffectedElements.get(j).getURI());
                        int distance = levenshteinDistance.apply(wordAux1,wordAux2);

                        if(distance > 0 && distance < 3) {
                            listAffectedElemenstToRemove.add(listAffectedElements.get(j));
                        }
                    }
                }

            }

            listAffectedElemenstToRemove = listAffectedElemenstToRemove.stream().collect(Collectors.toCollection(()->
                    new TreeSet<>(Comparator.comparing(AffectedElement::getURI)))).stream().collect(Collectors.toList());
            for(AffectedElement element:listAffectedElemenstToRemove){
               ontologyUtil.removeAxioms(element.getURI());
            }


        }
        //Poner mensaje cuando no se remueve o no encuentra que remover
        setArea(calculateArea());
        selectAll = false;
    }

    public String getWord(String word){
        String []arrayAux = word.split("#");
        String []result = arrayAux[arrayAux.length-1].split("_");
        return result[0];
    }

    public String  calculateArea(){
        Integer[] dataOntology = new Integer[QualityMetricEnum.values().length];
        if(!getQuickFixSelected().isEmpty()){
            String path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
            path = path + "/ontoQuickFix1.owl";
            MetricOntologyModel metricOntologyModel = loadBaseMetrics(path);
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

        return new MetricOntologyBuilder("QuickFix1").setNumAnnotations(annotations).
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

    public void saveQuickFix(){
        if(renameFile("/ontoFinal.owl","/ontoAux.owl") &&
            renameFile("/ontoQuickFix1.owl","/ontoFinal.owl") &&
            deleteFile("/ontoAux.owl")){
            FacesMessage message = new FacesMessage("Successful", "Quick fix"
                   + " is done.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            loadAffectedElements(selectedPitfall);
            loadPitfalls();
            selectAll = false;
        }
    }

    private boolean deleteFile(String pathOntology){
        String path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        File file = new File(path + pathOntology);
        return file.delete();
    }

    private boolean  renameFile(String oldFile, String newFile){
        String path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        File oldFileAux = new File(path + oldFile);
        File newFileAux = new File(path + newFile);
       return oldFileAux.renameTo(newFileAux);
    }


    public void destroyWorld() {
        addMessage("System Error", "Please try again later.");
    }

    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Pitfall> getListPitfalls() {
        return listPitfalls;
    }

    public void setListPitfalls(List<Pitfall> listPitfalls) {
        this.listPitfalls = listPitfalls;
    }

    public Pitfall getSelectedPitfall() {
        return selectedPitfall;
    }

    public void setSelectedPitfall(Pitfall selectedPitfall) {
        this.selectedPitfall = selectedPitfall;
    }

    public List<AffectedElement> getListAffectedElements() {
        return listAffectedElements;
    }

    public void setListAffectedElements(List<AffectedElement> listAffectedElements) {
        this.listAffectedElements = listAffectedElements;
    }

    public String getPathOntology() {
        return pathOntology;
    }

    public void setPathOntology(String pathOntology) {
        this.pathOntology = pathOntology;
    }

    public PathOntology getPath() {
        return path;
    }

    public void setPath(PathOntology path) {
        this.path = path;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public String getQuickFixSelected() {
        return quickFixSelected;
    }

    public void setQuickFixSelected(String quickFixSelected) {
        this.quickFixSelected = quickFixSelected;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
