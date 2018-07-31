/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.beans;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import decisionmakertool.model.MetricaOntologia;
import decisionmakertool.util.OwlUtil;
import decisionmakertool.util.PathOntology;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import uk.ac.manchester.cs.jfact.JFactFactory;

/**
 *
 * @author gaby_
 */
@ManagedBean
@ViewScoped
public class DashBoardBean implements Serializable {

    private OwlUtil print = new OwlUtil();

    // Get hold of an ontology manager
    private OWLOntologyManager manager;
    // Load an ontology from local
    private OWLOntology ontology;
    private OWLOntology ontologyBase;
    private IRI documentIRI;
    private OWLReasonerFactory factory = null;
    private OWLReasoner reasoner;
    private OWLDataFactory dataFactory;
    private int totalVulnerabilities;
    private List<MetricaOntologia> listaInfo;
    private int rrontoPercent = 0;
    private int totalRronto = 0;
    private int anontoPercent = 0;
    private int totalAnonto = 0;
    private int crontoPercent = 0;
    private int totalCronto = 0;
    private int nomontoPercent = 0;
    private int totalNomonto= 0;
    private int inrontoPercent= 0;
    private int totalInronto = 0;
    private int totalRiquezaClase = 0;
    private int riquezaClasePercent = 0;
    private int rfcontoPercent = 0;
    private int totalrfconto = 0;
    private int cboontoPercent = 0;
    private int totalCboonto = 0;
    private int lcomontoPercent = 0;
    private int totalLcomonto = 0;
    private Integer[] data = {0,0,0,0,0,0,0,0};
    private Integer[] data1 = {0,0,0,0,0,0,0,0};
    private String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
    private String json;
    private String json1;
 

    @PostConstruct
    public void init() {

        if (this.listaInfo == null) {
           this.listaInfo = new ArrayList<MetricaOntologia>();
        }

        cargarOntologiaDemo();
        cargarOntologiaBase();
        getMetricas();
        getMetricasOntoBase();
        loadMetricas();
        loadMetricasManual();
  }

    public void cargarOntologiaBase(){
         try {
            // Load an ontology from local
            PathOntology path = new PathOntology();
            File file = new File(path.getPathBase());
            manager = OWLManager.createOWLOntologyManager();
            ontologyBase = manager.loadOntologyFromOntologyDocument(file);
            System.out.println("file:" + file.getName());
            System.out.println("Loaded ontology: " + ontologyBase);
            documentIRI = manager.getOntologyDocumentIRI(ontologyBase);
            factory = new JFactFactory();
            //OWLReasonerConfiguration config = new SimpleConfiguration(500);
            // Create a reasoner that will reason over our ontology and its imports
            // closure. Pass in the configuration.
            reasoner = this.factory.createReasoner(ontologyBase);
            // Ask the reasoner to classify the ontology
            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
            dataFactory = manager.getOWLDataFactory();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cargarOntologiaDemo(){
        try {
            // Load an ontology from local
            PathOntology path = new PathOntology();

            File file = new File(path.getPath());

            manager = OWLManager.createOWLOntologyManager();
           
            ontology = manager.loadOntologyFromOntologyDocument(file);
            System.out.println("file:" + file.getName());
            System.out.println("Loaded ontology: " + ontology);
            documentIRI = manager.getOntologyDocumentIRI(ontology);
            factory = new JFactFactory();
            //OWLReasonerConfiguration config = new SimpleConfiguration(500);
            // Create a reasoner that will reason over our ontology and its imports
            // closure. Pass in the configuration.
            reasoner = this.factory.createReasoner(ontology);
            // Ask the reasoner to classify the ontology

            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
            dataFactory = manager.getOWLDataFactory();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getTotalVulnerabilities() {
        return totalVulnerabilities;
    }

  
    public void loadMetricas(){
        try {
            MetricaOntologia metricaOntologia = new MetricaOntologia();
            metricaOntologia = listaInfo.get(0);
            float rronto = 0;
            float anonto = 0;
            float cronto = 0;
            float inronto = 0;
            float lcomonto = 0;
            
            System.out.println("lista:" + listaInfo.size());
            rronto = metricaOntologia.getNumPropiedates() /(metricaOntologia.getNumSubclasesDe() + metricaOntologia.getNumPropiedates());
            rronto = rronto * 100;
            
            if(rronto >=0 && rronto <=20){
                totalRronto = 1;
            }else if(rronto >20 && rronto <=40){
                totalRronto = 2;
            }else if(rronto >40 && rronto <=60){
                totalRronto = 3;
            }else if(rronto >60 && rronto <=80){
                totalRronto = 4;
            }else{
                totalRronto = 5;
            }
            
            rrontoPercent = totalRronto * 100 /5;
            
            anonto = metricaOntologia.getNumAnotaciones()/metricaOntologia.getNumClases();
            anonto = anonto * 100;
            
            if(anonto >=0 && anonto <=20){
                totalAnonto = 1;
            }else if(anonto >20 && anonto <=40){
                totalAnonto = 2;
            }else if(anonto >40 && anonto <=60){
                totalAnonto = 3;
            }else if(anonto >60 && anonto <=80){
                totalAnonto = 4;
            }else{
                totalAnonto = 5;
            }
            
            
            anontoPercent = totalAnonto * 100/5;
            
            cronto = metricaOntologia.getNumInstancias() / metricaOntologia.getNumClases();
            cronto = cronto * 100;
            
            if(cronto >=0 && cronto <=20){
                totalCronto = 1;
            }else if(cronto >20 && cronto <=40){
                totalCronto = 2;
            }else if(cronto >40 && cronto <=60){
                totalCronto = 3;
            }else if(cronto >60 && cronto <=80){
                totalCronto = 4;
            }else{
                totalCronto = 5;
            }
            
            crontoPercent = totalCronto * 100/5;
            
            inronto = metricaOntologia.getNumSubclasesDe() / metricaOntologia.getNumClases();
            inronto = inronto  * 100;
            
            if(inronto >=0 && inronto <=20){
                totalInronto = 1;
            }else if(inronto >20 && inronto <=40){
                totalInronto = 2;
            }else if(inronto >40 && inronto <=60){
                totalInronto = 3;
            }else if(inronto >60 && inronto <=80){
                totalInronto = 4;
            }else{
                totalInronto = 5;
            }
            
            inrontoPercent = totalInronto * 100/5;
            
            List<String> listClases = new ArrayList<String>();
            listClases = print.getClasses(ontology, dataFactory, reasoner);
            int clasesConIndividuos = 0;
            
            for(String clase:listClases){
                List<String> individuos = new ArrayList<String>();
                individuos = print.printIndByClass(ontology,clase , dataFactory, reasoner);
                
                if(!individuos.isEmpty()){
                    clasesConIndividuos ++;
                }
            }
            
            float rClase = 0;
            rClase = clasesConIndividuos / metricaOntologia.getNumClases();
            rClase = rClase * 100;
            
            if(rClase >=0 && rClase <=20){
                totalRiquezaClase = 1;
            }else if(rClase >20 && rClase <=40){
                totalRiquezaClase = 2;
            }else if(rClase >40 && rClase <=60){
                totalRiquezaClase = 3;
            }else if(rClase >60 && rClase <=80){
                totalRiquezaClase = 4;
            }else{
                totalRiquezaClase = 5;
            }
            
            riquezaClasePercent = totalRiquezaClase * 100/5;
            
            float nomonto= 0;
            nomonto = metricaOntologia.getNumPropiedates() / metricaOntologia.getNumClases();
            nomonto = nomonto * 100;
            
            if(nomonto >6 && nomonto <=8){
                totalNomonto = 2;
            }else if(nomonto >4 && nomonto <=6){
                totalNomonto = 3;
            }else if(nomonto >2 && nomonto <=4){
                totalNomonto = 4;
            }else if(nomonto <=2){
                totalNomonto = 5;
            }else{
                totalNomonto = 1;
            }
            
            nomontoPercent = totalNomonto * 100/5;
            
            float rfconto = 0;
            rfconto = (metricaOntologia.getNumPropiedates() + metricaOntologia.getNumSuperclases()) / metricaOntologia.getNumClases();
            rfconto = rfconto * 100;
            
            if(rfconto >8 && rfconto <=12){
                totalrfconto = 2;
            }else if(rfconto >6 && rfconto <=8){
                totalrfconto = 3;
            }else if(rfconto >3 && rfconto <=6){
                totalrfconto = 4;
            }else if(rfconto >= 1 && rfconto <=3){
                totalrfconto = 5;
            }else{
                totalrfconto = 1;
            }
            
            rfcontoPercent = totalrfconto * 100 / 5;
            
            float cboonto = 0;
            cboonto = metricaOntologia.getNumSuperclases() / (metricaOntologia.getNumClases() - metricaOntologia.getRelacionesThing());
            if(cboonto >6 && cboonto <=8){
                totalCboonto = 2;
            }else if(rfconto >4 && rfconto <=6){
                totalCboonto = 3;
            }else if(rfconto >2 && rfconto <=4){
                totalCboonto = 4;
            }else if(rfconto >= 1 && rfconto <=2){
                totalCboonto = 5;
            }else{
                totalCboonto = 1;
            }
            
            
            cboontoPercent = totalCboonto * 100 / 5;
            
            lcomonto = metricaOntologia.getRelacionesThing() / metricaOntologia.getNumSubclasesDe();
            lcomonto = lcomonto * 100;
            
            if(lcomonto >6 && lcomonto <=8){
                totalLcomonto = 2;
            }else if(rClase >4 && rClase <=6){
                totalLcomonto = 3;
            }else if(rClase >2 && rClase <=4){
                totalLcomonto = 4;
            }else if(rClase <=2){
                totalLcomonto = 5;
            }else{
                totalLcomonto = 1;
            }
            
            lcomontoPercent = totalLcomonto * 100 /5;
            
            System.out.println("decisionmakertool.beans.DashBoardBean.loadMetricas():" + totalLcomonto);
            data[0] = totalRronto;
            data[1] = totalInronto;
            data[2] = totalAnonto;
            data[3] = totalCronto;
            data[4] = totalNomonto;
            data[5] = totalrfconto;
            data[6] = totalCboonto;
            data[7] = totalLcomonto;
            
            ObjectMapper mapper = new ObjectMapper();
            
            json = mapper.writeValueAsString(data);
            System.out.println("SALIDA JSON: \n" + json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
     public void loadMetricasManual(){
        try {
            MetricaOntologia metricaOntologia = new MetricaOntologia();
            metricaOntologia = listaInfo.get(1);
            float rronto = 0;
            float anonto = 0;
            float cronto = 0;
            float inronto = 0;
            float lcomonto = 0;
            
            System.out.println("lista:" + listaInfo.size());
            rronto = metricaOntologia.getNumPropiedates() /(metricaOntologia.getNumSubclasesDe() + metricaOntologia.getNumPropiedates());
            rronto = rronto * 100;
            
            if(rronto >=0 && rronto <=20){
                totalRronto = 1;
            }else if(rronto >20 && rronto <=40){
                totalRronto = 2;
            }else if(rronto >40 && rronto <=60){
                totalRronto = 3;
            }else if(rronto >60 && rronto <=80){
                totalRronto = 4;
            }else{
                totalRronto = 5;
            }
            
            rrontoPercent = totalRronto * 100 /5;
            
            anonto = metricaOntologia.getNumAnotaciones()/metricaOntologia.getNumClases();
            anonto = anonto * 100;
            
            if(anonto >=0 && anonto <=20){
                totalAnonto = 1;
            }else if(anonto >20 && anonto <=40){
                totalAnonto = 2;
            }else if(anonto >40 && anonto <=60){
                totalAnonto = 3;
            }else if(anonto >60 && anonto <=80){
                totalAnonto = 4;
            }else{
                totalAnonto = 5;
            }
            
            
            anontoPercent = totalAnonto * 100/5;
            
            cronto = metricaOntologia.getNumInstancias() / metricaOntologia.getNumClases();
            cronto = cronto * 100;
            
            if(cronto >=0 && cronto <=20){
                totalCronto = 1;
            }else if(cronto >20 && cronto <=40){
                totalCronto = 2;
            }else if(cronto >40 && cronto <=60){
                totalCronto = 3;
            }else if(cronto >60 && cronto <=80){
                totalCronto = 4;
            }else{
                totalCronto = 5;
            }
            
            crontoPercent = totalCronto * 100/5;
            
            inronto = metricaOntologia.getNumSubclasesDe() / metricaOntologia.getNumClases();
            inronto = inronto  * 100;
            
            if(inronto >=0 && inronto <=20){
                totalInronto = 1;
            }else if(inronto >20 && inronto <=40){
                totalInronto = 2;
            }else if(inronto >40 && inronto <=60){
                totalInronto = 3;
            }else if(inronto >60 && inronto <=80){
                totalInronto = 4;
            }else{
                totalInronto = 5;
            }
            
            inrontoPercent = totalInronto * 100/5;
            
            List<String> listClases = new ArrayList<String>();
            listClases = print.getClasses(ontology, dataFactory, reasoner);
            int clasesConIndividuos = 0;
            
            for(String clase:listClases){
                List<String> individuos = new ArrayList<String>();
                individuos = print.printIndByClass(ontology,clase , dataFactory, reasoner);
                
                if(!individuos.isEmpty()){
                    clasesConIndividuos ++;
                }
            }
            
            float rClase = 0;
            rClase = clasesConIndividuos / metricaOntologia.getNumClases();
            rClase = rClase * 100;
            
            if(rClase >=0 && rClase <=20){
                totalRiquezaClase = 1;
            }else if(rClase >20 && rClase <=40){
                totalRiquezaClase = 2;
            }else if(rClase >40 && rClase <=60){
                totalRiquezaClase = 3;
            }else if(rClase >60 && rClase <=80){
                totalRiquezaClase = 4;
            }else{
                totalRiquezaClase = 5;
            }
            
            riquezaClasePercent = totalRiquezaClase * 100/5;
            
            float nomonto= 0;
            nomonto = metricaOntologia.getNumPropiedates() / metricaOntologia.getNumClases();
            nomonto = nomonto * 100;
            
            if(nomonto >6 && nomonto <=8){
                totalNomonto = 2;
            }else if(nomonto >4 && nomonto <=6){
                totalNomonto = 3;
            }else if(nomonto >2 && nomonto <=4){
                totalNomonto = 4;
            }else if(nomonto <=2){
                totalNomonto = 5;
            }else{
                totalNomonto = 1;
            }
            
            nomontoPercent = totalNomonto * 100/5;
            
            float rfconto = 0;
            rfconto = (metricaOntologia.getNumPropiedates() + metricaOntologia.getNumSuperclases()) / metricaOntologia.getNumClases();
            rfconto = rfconto * 100;
            
            if(rfconto >8 && rfconto <=12){
                totalrfconto = 2;
            }else if(rfconto >6 && rfconto <=8){
                totalrfconto = 3;
            }else if(rfconto >3 && rfconto <=6){
                totalrfconto = 4;
            }else if(rfconto >= 1 && rfconto <=3){
                totalrfconto = 5;
            }else{
                totalrfconto = 1;
            }
            
            rfcontoPercent = totalrfconto * 100 / 5;
            
            float cboonto = 0;
            cboonto = metricaOntologia.getNumSuperclases() / (metricaOntologia.getNumClases() - metricaOntologia.getRelacionesThing());
            if(cboonto >6 && cboonto <=8){
                totalCboonto = 2;
            }else if(rfconto >4 && rfconto <=6){
                totalCboonto = 3;
            }else if(rfconto >2 && rfconto <=4){
                totalCboonto = 4;
            }else if(rfconto >= 1 && rfconto <=2){
                totalCboonto = 5;
            }else{
                totalCboonto = 1;
            }
            
            
            cboontoPercent = totalCboonto * 100 / 5;
            
            lcomonto = metricaOntologia.getRelacionesThing() / metricaOntologia.getNumSubclasesDe();
            lcomonto = lcomonto * 100;
            
            if(lcomonto >6 && lcomonto <=8){
                totalLcomonto = 2;
            }else if(rClase >4 && rClase <=6){
                totalLcomonto = 3;
            }else if(rClase >2 && rClase <=4){
                totalLcomonto = 4;
            }else if(rClase <=2){
                totalLcomonto = 5;
            }else{
                totalLcomonto = 1;
            }
            
            lcomontoPercent = totalLcomonto * 100 /5;
            
            System.out.println("decisionmakertool.beans.DashBoardBean.loadMetricas():" + totalLcomonto);
            data1[0] = totalRronto;
            data1[1] = totalInronto;
            data1[2] = totalAnonto;
            data1[3] = totalCronto;
            data1[4] = totalNomonto;
            data1[5] = totalrfconto;
            data1[6] = totalCboonto;
            data1[7] = totalLcomonto;
            
            ObjectMapper mapper = new ObjectMapper();
            
            json1 = mapper.writeValueAsString(data1);
            System.out.println("SALIDA JSON: \n" + json1);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DashBoardBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }



    public void getMetricas(){
        int numSuperClases = 0;
        int numClases = 0;
        int numSubClases = 0;
        int numInstancias = 0;
        int numPropiedades = 0;
        int numAnnotation = 0;
        int numRelacionesThing = 0;
        Set<String> hs = new HashSet<>();
        Set<String> hs1 = new HashSet<>();
        Set<String> hs2 = new HashSet<>();
        List<String> superclases = new ArrayList<String>();
        List<String> clases = new ArrayList<String>();
        List<String> subclases = new ArrayList<String>();
        List<String> instancias = new ArrayList<String>();
        List<String> propiedades = new ArrayList<String>();
        List<String> anotaciones = new ArrayList<String>();
        List<String> relacionesThing = new ArrayList<String>();
                
        
        //Superclases
        superclases=  print.getSuperClasses(ontology,dataFactory, reasoner);
       
        //Elimina clases repetidas
        hs.addAll(superclases);
        superclases.clear();
        superclases.addAll(hs);
      
        numSuperClases = superclases.size();
        System.out.println("# superclases:" + numSuperClases);
        
        //Clases
        clases = print.getClasses(ontology, dataFactory, reasoner);
        numClases = clases.size();
        System.out.println("# clases:" + numClases);
        
        //Subclases
        subclases = print.getSubClassOfAll(ontology, dataFactory, reasoner);
         //Elimina clases repetidas
        hs1.addAll(subclases);
        subclases.clear();
        subclases.addAll(hs1);
        numSubClases = subclases.size();
        System.out.println("# subclases:" + numSubClases);
          
        //instancias
        instancias = print.printInd(ontology);
         //Elimina clases repetidas
        hs2.addAll(instancias);
        instancias.clear();
        instancias.addAll(hs2);
        numInstancias = instancias.size();
         System.out.println("# instancias:" + numInstancias);
         
         //Propiedades
         propiedades = print.getProperties(ontology);
         numPropiedades = propiedades.size();
         System.out.println("# propiedades:" + numPropiedades);
        
         anotaciones = print.getAnnotation(ontology);
         numAnnotation = anotaciones.size();
         System.out.println("# anotaciones:" + numAnnotation);
         
         //relaciones de thing
         relacionesThing = print.getSubClassOfThing(ontology, dataFactory, reasoner);
         numRelacionesThing = relacionesThing.size();
         System.out.println("# relacionesThing:" + numRelacionesThing);
         
         
         MetricaOntologia metricaOntologia = new MetricaOntologia();
         metricaOntologia.setNombreOntologia("Automatic Ontology");
         metricaOntologia.setNumAnotaciones(numAnnotation);
         metricaOntologia.setNumPropiedates(numPropiedades);
         metricaOntologia.setNumClases(numClases);
         metricaOntologia.setNumInstancias(numInstancias);
         metricaOntologia.setNumSubclasesDe(numSubClases);
         metricaOntologia.setNumSuperclases(numSuperClases);
         metricaOntologia.setRelacionesThing(numRelacionesThing);
         listaInfo.add(metricaOntologia);
                
         
        
    }
    
    public void getMetricasOntoBase(){
        int numSuperClases = 0;
        int numClases = 0;
        int numSubClases = 0;
        int numInstancias = 0;
        int numPropiedades = 0;
        int numAnnotation = 0;
        int numRelacionesThing = 0;
        Set<String> hs = new HashSet<>();
        Set<String> hs1 = new HashSet<>();
        Set<String> hs2 = new HashSet<>();
        List<String> superclases = new ArrayList<String>();
        List<String> clases = new ArrayList<String>();
        List<String> subclases = new ArrayList<String>();
        List<String> instancias = new ArrayList<String>();
        List<String> propiedades = new ArrayList<String>();
        List<String> anotaciones = new ArrayList<String>();
        List<String> relacionesThing = new ArrayList<String>();
                
        
        //Superclases
        superclases=  print.getSuperClasses(ontologyBase,dataFactory, reasoner);
       
        //Elimina clases repetidas
        hs.addAll(superclases);
        superclases.clear();
        superclases.addAll(hs);
      
        numSuperClases = superclases.size();
        System.out.println("# superclases:" + numSuperClases);
        
        //Clases
        clases = print.getClasses(ontologyBase, dataFactory, reasoner);
        numClases = clases.size();
        System.out.println("# clases:" + numClases);
        
        //Subclases
        subclases = print.getSubClassOfAll(ontologyBase, dataFactory, reasoner);
         //Elimina clases repetidas
        hs1.addAll(subclases);
        subclases.clear();
        subclases.addAll(hs1);
        numSubClases = subclases.size();
        System.out.println("# subclases:" + numSubClases);
          
        //instancias
        instancias = print.printInd(ontologyBase);
         //Elimina clases repetidas
        hs2.addAll(instancias);
        instancias.clear();
        instancias.addAll(hs2);
        numInstancias = instancias.size();
         System.out.println("# instancias:" + numInstancias);
         
         //Propiedades
         propiedades = print.getProperties(ontologyBase);
         numPropiedades = propiedades.size();
         System.out.println("# propiedades:" + numPropiedades);
        
         anotaciones = print.getAnnotation(ontologyBase);
         numAnnotation = anotaciones.size();
         System.out.println("# anotaciones:" + numAnnotation);
         
         //relaciones de thing
         relacionesThing = print.getSubClassOfThing(ontologyBase, dataFactory, reasoner);
         numRelacionesThing = relacionesThing.size();
         System.out.println("# relacionesThing:" + numRelacionesThing);
         
         
         MetricaOntologia metricaOntologia = new MetricaOntologia();
           metricaOntologia.setNombreOntologia("Manual Ontology");
         metricaOntologia.setNumAnotaciones(numAnnotation);
         metricaOntologia.setNumPropiedates(numPropiedades);
         metricaOntologia.setNumClases(numClases);
         metricaOntologia.setNumInstancias(numInstancias);
         metricaOntologia.setNumSubclasesDe(numSubClases);
         metricaOntologia.setNumSuperclases(numSuperClases);
         metricaOntologia.setRelacionesThing(numRelacionesThing);
         listaInfo.add(metricaOntologia);
                
         
        
    }
    
    public List<MetricaOntologia> getListaInfo() {
        return listaInfo;
    }

    public void setListaInfo(List<MetricaOntologia> listaInfo) {
        this.listaInfo = listaInfo;
    }

    public int getRrontoPercent() {
        return rrontoPercent;
    }

    public void setRrontoPercent(int rrontoPercent) {
        this.rrontoPercent = rrontoPercent;
    }

    public int getTotalRronto() {
        return totalRronto;
    }

    public void setTotalRronto(int totalRronto) {
        this.totalRronto = totalRronto;
    }

    public int getAnontoPercent() {
        return anontoPercent;
    }

    public void setAnontoPercent(int anontoPercent) {
        this.anontoPercent = anontoPercent;
    }

    public int getTotalAnonto() {
        return totalAnonto;
    }

    public void setTotalAnonto(int totalAnonto) {
        this.totalAnonto = totalAnonto;
    }

    public int getCrontoPercent() {
        return crontoPercent;
    }

    public void setCrontoPercent(int crontoPercent) {
        this.crontoPercent = crontoPercent;
    }

    public int getTotalCronto() {
        return totalCronto;
    }

    public void setTotalCronto(int totalCronto) {
        this.totalCronto = totalCronto;
    }

    public int getNomontoPercent() {
        return nomontoPercent;
    }

    public void setNomontoPercent(int nomontoPercent) {
        this.nomontoPercent = nomontoPercent;
    }

    public int getTotalNomonto() {
        return totalNomonto;
    }

    public void setTotalNomonto(int totalNomonto) {
        this.totalNomonto = totalNomonto;
    }

    public int getInrontoPercent() {
        return inrontoPercent;
    }

    public void setInrontoPercent(int inrontoPercent) {
        this.inrontoPercent = inrontoPercent;
    }

    public int getTotalInronto() {
        return totalInronto;
    }

    public void setTotalInronto(int totalInronto) {
        this.totalInronto = totalInronto;
    }

    public int getTotalRiquezaClase() {
        return totalRiquezaClase;
    }

    public void setTotalRiquezaClase(int totalRiquezaClase) {
        this.totalRiquezaClase = totalRiquezaClase;
    }

    public int getRiquezaClasePercent() {
        return riquezaClasePercent;
    }

    public void setRiquezaClasePercent(int riquezaClasePercent) {
        this.riquezaClasePercent = riquezaClasePercent;
    }

    public int getRfcontoPercent() {
        return rfcontoPercent;
    }

    public void setRfcontoPercent(int rfcontoPercent) {
        this.rfcontoPercent = rfcontoPercent;
    }

    public int getTotalrfconto() {
        return totalrfconto;
    }

    public void setTotalrfconto(int totalrfconto) {
        this.totalrfconto = totalrfconto;
    }

    public int getCboontoPercent() {
        return cboontoPercent;
    }

    public void setCboontoPercent(int cboontoPercent) {
        this.cboontoPercent = cboontoPercent;
    }

    public int getTotalCboonto() {
        return totalCboonto;
    }

    public void setTotalCboonto(int totalCboonto) {
        this.totalCboonto = totalCboonto;
    }

    /**
     * @return the data
     */
    public Integer[] getData() {
        
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Integer[] data) {
        this.data = data;
    }

    public int getLcomontoPercent() {
        return lcomontoPercent;
    }

    public void setLcomontoPercent(int lcomontoPercent) {
        this.lcomontoPercent = lcomontoPercent;
    }

    public int getTotalLcomonto() {
        return totalLcomonto;
    }

    public void setTotalLcomonto(int totalLcomonto) {
        this.totalLcomonto = totalLcomonto;
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

    
}
