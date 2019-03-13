/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.metrics;

import decisionmakertool.util.OwlUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 *
 * @author Gaby
 */
public class BaseMetrics {

    private int numSuperClasses;
    private int numClasses;
    private int numSubClasses;
    private int numInstances;
    private int numProperties;
    private int numAnnotation;
    private int numRelationsOfThing;
    private int numClassesWithIndividuals;
    private final OwlUtil print = new OwlUtil();

    public BaseMetrics() {
    }

    public int getNumSuperClasses(OWLOntology ontology) {
        Set<String> hs = new HashSet<>();
        List<String> listSuperClass;
        listSuperClass = print.getSuperClasses(ontology);
        listSuperClass = removeRepeatClasses(listSuperClass);
        numSuperClasses = listSuperClass.size();
        System.out.println("# superclases:" + numSuperClasses);
        return numSuperClasses;
    }

    public int getNumClasses(OWLOntology ontology) {
        List<String> listClasses = new ArrayList<String>();
        listClasses = print.getClasses(ontology);
        numClasses = listClasses.size();
        System.out.println("# clases:" + numClasses);
        return numClasses;
    }

    public int getNumSubClasses(OWLOntology ontology) {
        List<String> lisSubclasses = new ArrayList<String>();
        Set<String> hs = new HashSet<>();
        lisSubclasses = print.getSubClassOfAll(ontology);
        lisSubclasses = removeRepeatClasses(lisSubclasses);
        numSubClasses = lisSubclasses.size();
        System.out.println("# subclases:" + numSubClasses);
        return numSubClasses;
    }

    public int getNumInstances(OWLOntology ontology) {
        List<String> listInstances = new ArrayList<String>();
        Set<String> hs = new HashSet<>();
        //instancias
        listInstances = print.printInd(ontology);
        listInstances = removeRepeatClasses(listInstances);
        numInstances = listInstances.size();
        System.out.println("# instancias:" + numInstances);

        return numInstances;
    }

    public int getNumProperties(OWLOntology ontology) {
        List<String> listProperties = new ArrayList<String>();
        listProperties = print.getProperties(ontology);
        numProperties = listProperties.size();
        System.out.println("# propiedades:" + numProperties);
        return numProperties;
    }

    public int getNumAnnotation(OWLOntology ontology) {
        List<String> listAnnotations;
        listAnnotations = print.getAnnotation(ontology);
        numAnnotation = listAnnotations.size();
        System.out.println("# anotaciones:" + numAnnotation);

        return numAnnotation;
    }

    public int getNumRelationsOfThing(OWLOntology ontology) {
        List<String> listRelationsThing;
        listRelationsThing = print.getSubClassOfThing(ontology);
        numRelationsOfThing = listRelationsThing.size();
        System.out.println("# relacionesThing:" + numRelationsOfThing);
        return numRelationsOfThing;
    }

    public int getNumClassesWithIndividuals(OWLOntology ontology) {
        List<String> listClassWithIndividuals = new ArrayList<String>();
        listClassWithIndividuals = print.printIndByClass(ontology);
        numClassesWithIndividuals = listClassWithIndividuals.size();
        System.out.println("# numClassesWithIndividuals:" + numClassesWithIndividuals);
        return numClassesWithIndividuals;
    }

     public List<String> removeRepeatClasses(List<String>  listClassesRepeat){
         List<String> listResult = new ArrayList<>();
         Set<String> hs = new HashSet<>();
         hs.addAll(listClassesRepeat);
         listClassesRepeat.clear();
         listClassesRepeat.addAll(hs);
         listResult = listClassesRepeat;
         return  listResult;
    }

}
