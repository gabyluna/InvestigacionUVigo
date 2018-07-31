/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;

import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 *
 * @author gaby_
 */
public class OwlUtil {

    //private static DefaultPrefixManager pm = new DefaultPrefixManager(
      //      "http://www.co-ode.org/ontologies/testont.owl#");
    private static DefaultPrefixManager pm = new DefaultPrefixManager(
            "http://www.text2onto.org#");

    public OwlUtil() {

    }

    public List<String> getProperties(OWLOntology ont){
        List<String> listProperties = new ArrayList<>();
         for (OWLObjectProperty p : ont.getObjectPropertiesInSignature()) {
              //System.out.println("decisionmakertool.util.OwlUtil.getSuperClasses():"+pm.getShortForm(p));
             listProperties.add(pm.getShortForm(p).replaceAll(":", ""));
         }
         return listProperties;
    }
    
     public List<String> getAnnotation(OWLOntology ont){
        List<String> listProperties = new ArrayList<>();
         Set<OWLAnnotationAssertionAxiom> annotations = ont.getAxioms(AxiomType.ANNOTATION_ASSERTION);
         for (OWLAnnotationAssertionAxiom anotation: annotations) {
               listProperties.add(anotation.getValue().toString());
         }
         return listProperties;
    }
    
    public List<String> getSubClassOf(OWLOntology ont, String clase, OWLDataFactory dataFactory, OWLReasoner reasoner) {
        List<String> listSubClass = new ArrayList<>();
        OWLClass person = ont.getOWLOntologyManager().getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/testont.owl#" + clase));
        Set<OWLClass> classes = reasoner.getSubClasses(person, false).getFlattened();
        for (OWLClass subClass : classes) {
            listSubClass.add(pm.getShortForm(subClass).replaceAll(":", ""));
        }
        return listSubClass;

    }
    
    
    
     public List<String> getSubClassOfThing(OWLOntology ont,OWLDataFactory dataFactory, OWLReasoner reasoner) {
        List<String> listSubClass = new ArrayList<>();
       
       // OWLClass person = ont.getOWLOntologyManager().getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/testont.owl#" + clase));
       Set<OWLSubClassOfAxiom> subClasses =  ont.getAxioms(AxiomType.SUBCLASS_OF);
       for(OWLSubClassOfAxiom clase: subClasses){
          if(clase.getSignature().toString().contains("owl:Thing")){
              
                  listSubClass.add(clase.getSignature().toString());
          }
                   
               
              
       }
         
        return listSubClass;

    }
 
     public List<String> getSubClassOfAll(OWLOntology ont,OWLDataFactory dataFactory, OWLReasoner reasoner) {
        List<String> listSubClass = new ArrayList<>();
        Set<OWLClass> classes = ont.getClassesInSignature();
       // OWLClass person = ont.getOWLOntologyManager().getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/testont.owl#" + clase));
       Set<OWLSubClassOfAxiom> subClasses =  ont.getAxioms(AxiomType.SUBCLASS_OF);
       for(OWLSubClassOfAxiom clase: subClasses){
          
           listSubClass.add(clase.getSignature().toString());
              
       }
         
        return listSubClass;

    }
     
    
    public List<String> getClasses(OWLOntology ont,  OWLDataFactory dataFactory, OWLReasoner reasoner){
       List<String> listClass = new ArrayList<>();
       Set<OWLClass> classes = ont.getClassesInSignature();
      
       for(OWLClass clase: classes){
            listClass.add(pm.getShortForm(clase).replaceAll(":", ""));
       }

        return listClass;

    }
    
    
    public List<String> getSuperClasses(OWLOntology ont,  OWLDataFactory dataFactory, OWLReasoner reasoner) {
       List<String> listSupClass = new ArrayList<>();
       Set<OWLClass> classes = ont.getClassesInSignature();
      
       for(OWLClass clas: classes){
           Set<OWLClass> superclasses = reasoner.getSuperClasses(clas, false).getFlattened();
          
           for(OWLClass sc:superclasses){
             
            listSupClass.add(pm.getShortForm(sc).replaceAll(":", ""));
           }
          
       }

        return listSupClass;

    }

    public List<String> printNode(Set<OWLClass> node) {

        List<String> listClases = new ArrayList<>();
        DefaultPrefixManager pm = new DefaultPrefixManager(
                "http://www.w3.org/2002/07/owl#");
        // Print out a node as a list of class names in curly brackets

        for (Iterator<OWLClass> it = node.iterator(); it
                .hasNext();) {
            OWLClass cls = it.next();
            // User a prefix manager to provide a slightly nicer shorter name
            String cadena[] = cortarCadena(pm.getShortForm(cls));

            for (int i = 0; i < (cadena.length - 1); i++) {
                //System.out.println(cadena[1].replaceAll(">", ""));
                listClases.add(cadena[1].replaceAll(">", ""));
            }
        }

        Collections.sort(listClases);

        return listClases;
    }

    public List<String> printInd(OWLOntology ont) {
        List<String> listInd = new ArrayList<>();
        Set<OWLNamedIndividual> inds = ont.getIndividualsInSignature();
        for (OWLNamedIndividual ind : inds) {
            String cadena[] = cortarCadena(ind.toString());

            for (int i = 0; i < (cadena.length - 1); i++) {
                // System.out.println(cadena[1].replaceAll(">", ""));
                listInd.add(cadena[1].replaceAll(">", ""));
            }
        }
        Collections.sort(listInd);
        return listInd;
    }

    public List<String> printIndByClass(OWLOntology ont, String clase, OWLDataFactory dataFactory, OWLReasoner reasoner) {

        List<String> listIndByClass = new ArrayList<>();
        String base = "http://www.text2onto.org";
        OWLClass claseInd = dataFactory.getOWLClass(IRI
                .create(base + "#" + clase));
        // Ask the reasoner for the instances of pet

        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(
                claseInd, true);
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        String individualClass = "";
        for (OWLNamedIndividual ind : individuals) {
            individualClass = pm.getShortForm(ind).replaceAll(":", "");
            individualClass = individualClass.replaceAll("_", " ");

            listIndByClass.add(individualClass);

        }

        return listIndByClass;
    }

    public List<String> printIndByClassDevice(OWLOntology ont, OWLDataFactory dataFactory, OWLReasoner reasoner) {

        List<String> listIndByClass = new ArrayList<>();

        for (OWLClass c : ont.getClassesInSignature()) {
            if (c.getIRI().getFragment().equals("Device")) {
                NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, Boolean.FALSE);
                System.out.println(c.getIRI().getFragment());
                for (OWLNamedIndividual i : instances.getFlattened()) {
                    
                    if (i.getIRI().getFragment().equals("Integrity") || i.getIRI().getFragment().equals("Availability")
                            || i.getIRI().getFragment().equals("Authenticity") || i.getIRI().getFragment().equals("Confidentiality")) {
                        System.out.println("Individual" + i.getIRI().getFragment());
                      
                    }
                    else{
                      listIndByClass.add(i.getIRI().getShortForm());
                    }
                }
            }
        }

        return listIndByClass;
    }

 
    public List<String> lstSolutions(String individual, OWLDataFactory dataFactory, OWLOntology ont, OWLReasoner reasoner) {
        ConsoleProgressMonitor progressMonitor;
        OWLReasonerConfiguration config;
        OWLReasonerFactory reasonerFactory;
        // OWLReasoner reasoner;

        progressMonitor = new ConsoleProgressMonitor();
        config = new SimpleConfiguration(progressMonitor);
        reasonerFactory = new StructuralReasonerFactory();
        reasoner = reasonerFactory.createReasoner(ont, config);

        // Ask the reasoner to classify the ontology
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        List<String> lstSolution = new ArrayList<>();
        for (OWLNamedIndividual i : ont.getIndividualsInSignature()) {

            if (pm.getShortForm(i).equals(":" + individual)) {

                OWLDataProperty paragraph
                        = dataFactory.getOWLDataProperty(":paragraph", pm);
                Set<OWLLiteral> assertParagraph = reasoner.getDataPropertyValues(i, paragraph);

                String solution = "";
                for (OWLLiteral ind : assertParagraph) {
                    solution = ind.getLiteral();
                    lstSolution.add(solution);
                }

            }

        }

        return lstSolution;
    }

    public String getDescripttion(String individual, OWLDataFactory dataFactory, OWLOntology ont, OWLReasoner reasoner) {
        ConsoleProgressMonitor progressMonitor;
        OWLReasonerConfiguration config;
        OWLReasonerFactory reasonerFactory;
        // OWLReasoner reasoner;

        progressMonitor = new ConsoleProgressMonitor();
        config = new SimpleConfiguration(progressMonitor);
        reasonerFactory = new StructuralReasonerFactory();
        reasoner = reasonerFactory.createReasoner(ont, config);

        // Ask the reasoner to classify the ontology
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        String description = "";
        for (OWLNamedIndividual i : ont.getIndividualsInSignature()) {

            if (pm.getShortForm(i).equals(":" + individual)) {

                OWLDataProperty paragraph
                        = dataFactory.getOWLDataProperty(":valueDescription", pm);
                Set<OWLLiteral> assertParagraph = reasoner.getDataPropertyValues(i, paragraph);
                String temp = "";
                for (OWLLiteral ind : assertParagraph) {
                    temp = ind.getLiteral();

                }

                description = temp;
            }

        }

        return description;
    }

    public List<String> loadInfoAffectedBy(String individual, OWLDataFactory dataFactory, OWLOntology ont, OWLReasoner reasoner) {
        // Print out a node as a list of class names in curly brackets
        List<String> listInfo = new ArrayList<>();
        //HardwareParams hardwareParams = new HardwareParams();
        for (OWLNamedIndividual i : ont.getIndividualsInSignature()) {
            for (OWLObjectProperty p : ont.getObjectPropertiesInSignature()) {
                NodeSet<OWLNamedIndividual> individualValues = reasoner.getObjectPropertyValues(i, p);
                Set<OWLNamedIndividual> values = individualValues.getFlattened();
                individual = individual.replaceAll(" ", "_");
                if (pm.getShortForm(i).equals(":" + individual)) {

                    if ((pm.getShortForm(p.getNamedProperty()).equals(":it_is_affected_by"))) {
                        for (OWLNamedIndividual ind : values) {
                            String affected_by = pm.getShortForm(ind).replaceAll(":", "");
                            //hardwareParams.setProperty(affected_by.replaceAll("_", " "));
                            listInfo.add(affected_by.replaceAll("_", " "));
                        }
                    }

                }

            }
        }

        return listInfo;
    }


    public List<PillarParams> loadInfoPillars(String individual, OWLDataFactory dataFactory, OWLOntology ont, OWLReasoner reasoner) throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {
        // Print out a node as a list of class names in curly brackets
        List<PillarParams> lstPillarParams = new ArrayList<>();
        Set<InferenceType> settt = reasoner.getPrecomputableInferenceTypes();
        for (InferenceType i : settt) {
            reasoner.precomputeInferences(i);
            // System.out.println("settt:" + i.name());
        }

        for (OWLNamedIndividual i : ont.getIndividualsInSignature()) {
            for (OWLObjectProperty p : ont.getObjectPropertiesInSignature()) {
                NodeSet<OWLNamedIndividual> individualValues = reasoner.getObjectPropertyValues(i, p);

                Set<OWLNamedIndividual> values = individualValues.getFlattened();
                //individual = individual.replaceAll(" ", "_");
                if (pm.getShortForm(i).equals(":" + individual)) {

                    if ((pm.getShortForm(p.getNamedProperty()).equals(":isAttackBy"))) {
                        for (OWLNamedIndividual ind : values) {
                            PillarParams pillarParams = new PillarParams();
                            pillarParams.setIt_is_attack_by(pm.getShortForm(ind).replaceAll(":", ""));
                            //System.out.println("pa" + pm.getShortForm(ind).replaceAll(":", ""));
                            lstPillarParams.add(pillarParams);
                        }
                    }
                }

            }
        }

        return lstPillarParams;
    }

   

    public String[] cortarCadena(String cadena) {
        return cadena.split("#");
    }

}
