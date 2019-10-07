package decisionmakertool.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import decisionmakertool.metrics.qualitymetrics.QualityMetric;
import decisionmakertool.metrics.qualitymetrics.QualityMetricFactory;
import decisionmakertool.metrics.qualitymetrics.QualityMetricsStrategy;
import decisionmakertool.metrics.strategy.BaseMetric;
import decisionmakertool.metrics.strategy.BaseMetricsFactory;
import decisionmakertool.metrics.strategy.BaseMetricsStrategy;
import decisionmakertool.metrics.templateimpl.*;
import decisionmakertool.model.MetricOntologyBuilder;
import decisionmakertool.model.MetricOntologyModel;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.util.PathOntology;
import decisionmakertool.util.UtilClass;
import drontoapi.pitfallmanager.AffectedElement;
import drontoapi.pitfallmanager.Pitfall;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

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
        System.out.println("path:"+pathOntology);
        SmellErrorTemplate circularityErrorTemplate = SmellErrorFactory.getSmellError(SmellError.CIRCULARITY);
        List<Pitfall>  listCircularityErrors = circularityErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate partitionErrorTemplate = SmellErrorFactory.getSmellError(SmellError.PARTITION);
        List<Pitfall> listPartitionErrors = partitionErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate semanticErrorTemplate = SmellErrorFactory.getSmellError(SmellError.SEMANTIC);
        List<Pitfall> listSemanticErrors = semanticErrorTemplate.getListSmellErrors(pathOntology);
        SmellErrorTemplate incompletenessErrorTemplate = SmellErrorFactory.getSmellError(SmellError.INCOMPLETENESS);
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
       System.out.println("size:"+ listAffectedElements.size());
       if(listAffectedElements.size() == 0){
           System.out.println("aqui:"+ listAffectedElements.size());
           String []arrarEx ={ "http://www.text2onto.org#interaction_c", "http://www.text2onto.org#action_c",
                   "http://www.text2onto.org#objective_c", "http://www.text2onto.org#point_c ",
                   "http://www.text2onto.org#information_c", "http://www.text2onto.org#case_c",
                   "http:, http://www.text2onto.org#procedure_c", "http://www.text2onto.org#function_c",
                   "http://www.text2onto.org#type_c", "http://www.text2onto.org#relation_c",
                   "http://www.text2onto.org#case_c", "http://www.text2onto.org#plot_c",
                   "http://www.text2onto.org#region_c", "http//www.text2onto.org#set_c",
                   "http://www.text2onto.org#action_c", "›http://www.text2onto.org#distance_c",
                   "http://www.text2onto.org#region_c","http://www.text2onto.org#part_c",
                   "http://www.text2onto.org#point_c","›http://www.text2onto.org#design_c",
                   "http://www.text2onto.org#goal_c", "›http://www.text2onto.org#order_c",
                   "http://www.text2onto.org#relation_c","http://www.text2onto.org#way_c",
                   "http://www.text2onto.org#direction_c", "http://www.text2onto.org#line_c",
                   "›http://www.text2onto.org#section_c", "http://www.text2onto.org#part_c",
                   "http://www.text2onto.org#point_c", "›http://www.text2onto.org#interest_c",
                   "http://www.text2onto.org#part_c", "›http://www.text2onto.org#area_c",
                   "http://www.text2onto.org#domain_c","›http://www.text2onto.org#study_c",
                   "http://www.text2onto.org#part_c", "›http://www.text2onto.org#achievement_c",
                   "http://www.text2onto.org#use_c", "http://www.text2onto.org#action_c",
                   "›http://www.text2onto.org#number_c", "http://www.text2onto.org#figure_c"};

           for(int i = 0; i< arrarEx.length;i++){
               listAffectedElements.add(new AffectedElement(arrarEx[i],"http://www.w3.org/2001/XMLSchema#anyURI"));
           }

       }
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
        System.out.println("aquiAply");
        OntologyUtil ontologyUtil = new OntologyUtil(pathOntology);

        if(getQuickFixSelected().equals("1")){
            for(AffectedElement element:listAffectedElements){
                if(element.getSelected()){
                    System.out.println("data1:" + element.getDatatypeURI());
                    ontologyUtil.removeAxioms(element.getURI());
                }
            }

        }else{
            LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
            List<AffectedElement> listAffectedElemenstToRemove = new ArrayList<>();
            int sizeList = listAffectedElements.size();

            for(int i = 0; i < sizeList; i++){
                if(listAffectedElements.get(i).getSelected()){
                    System.out.println("element2:" + listAffectedElements.get(i).getURI());
                    String wordAux1 =  getWord(listAffectedElements.get(i).getURI());

                    for(int j=(sizeList-1);j >= 0 ; j--){
                        String wordAux2 =  getWord(listAffectedElements.get(j).getURI());
                        int distance = levenshteinDistance.apply(wordAux1,wordAux2);

                        if(distance > 0 && distance < 3) {
                            System.out.println("word1:" + wordAux1);
                            System.out.println("word2:" + wordAux2);
                            System.out.println("distance:" + distance);
                            listAffectedElemenstToRemove.add(listAffectedElements.get(j));
                        }
                    }
                }

            }

            listAffectedElemenstToRemove = listAffectedElemenstToRemove.stream().collect(Collectors.toCollection(()->
                    new TreeSet<>(Comparator.comparing(AffectedElement::getURI)))).stream().collect(Collectors.toList());
            System.out.println("sizeAff:" + listAffectedElemenstToRemove.size());
            for(AffectedElement element:listAffectedElemenstToRemove){
               System.out.println("dataadd:" + element.getURI());
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
        Integer[] dataOntology = new Integer[QualityMetric.values().length];
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

        MetricOntologyModel metricsOntology = new MetricOntologyBuilder("QuickFix1").setNumAnnotations(annotations).
                setNumProperties(properties).setNumClasses(classes).setNumInstances(instances).
                setNumSubclassOf(subClasses).setNumSuperClasses(superClasses).setRelationsThing(relationThing).
                setNumClassWithIndividuals(classWithIndividuals).build();

        return metricsOntology;
    }

    public String loadQualityMetrics(MetricOntologyModel metricsOntology, Integer[] data) {
        String jsonDataMetrics = "";

        try {
            QualityMetricsStrategy qualityMetricsStrategy;
            QualityMetricFactory qualityMetricFactory = new QualityMetricFactory();
            int totalMetricsQuality = QualityMetric.values().length;
            int positionMetric = 0;

            while(positionMetric < totalMetricsQuality){
                qualityMetricsStrategy = qualityMetricFactory.getQualityMetric((positionMetric+1));
                data[positionMetric] =  qualityMetricsStrategy.calculateQualityMetric(metricsOntology);
                positionMetric++;
            }

            jsonDataMetrics = UtilClass.arrayToJsonString(data);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonDataMetrics;
    }

    public void saveQuickFix(){
        //System.out.println("Delete File:" + pathOntology);
        //deleteFile(pathOntology);
        renameFile("/ontoFinal.owl","/ontoAux.owl");
        renameFile("/ontoQuickFix1.owl","/ontoFinal.owl");
        deleteFile("/ontoAux.owl");
        System.out.println("done");
        FacesMessage message = new FacesMessage("Successful", "Quick fix"
               + " is done.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        loadAffectedElements(selectedPitfall);
        loadPitfalls();
        selectAll = false;


    }

    public boolean deleteFile(String pathOntology){

        String path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        File file = new File(path + pathOntology);

        if(file.delete())
        {

            System.out.println("File deleted successfully");
            return true;
        }
        else
        {
            System.out.println("Failed to delete the file");
            return false;
        }

    }

    public void renameFile(String oldFile, String newFile){
        String path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        File oldfile = new File(path + oldFile);
        System.out.println("ppp:" + path+oldFile);
        File newfile = new File(path + newFile);
        if (oldfile.renameTo(newfile)) {
            System.out.println("archivo renombrado");
        } else {
            System.out.println("error");
        }

    }


    public void destroyWorld() {
        addMessage("System Error", "Please try again later.");
    }

    public void addMessage(String summary, String detail) {
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
