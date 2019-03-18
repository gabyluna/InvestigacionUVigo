/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.metrics;

import java.util.*;

import decisionmakertool.util.UtilClass;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class BaseMetrics {
    private final DefaultPrefixManager pm = new DefaultPrefixManager(
            "http://www.text2onto.org#");

    public BaseMetrics() {
        //
    }

    public int getNumSuperClasses(OWLOntology ontology) {
        List<String> listSupClass = new ArrayList<>();
        Set<OWLClass> classes = ontology.getClassesInSignature();

        for (OWLClass classAux : classes) {
            Set<OWLClassExpression> superclasses = classAux.getSuperClasses(ontology);
            for (OWLClassExpression sc : superclasses) {
                listSupClass.add(sc.getSignature().toString());
            }
        }

        return listSupClass.size();
    }

    public int getNumClasses(OWLOntology ontology) {
        List<String> listClasses = new ArrayList<>();
        Set<OWLClass> classes = ontology.getClassesInSignature();

        for (OWLClass classAux : classes) {
            listClasses.add(pm.getShortForm(classAux).replaceAll(":", ""));
    }

        return listClasses.size();
    }

    public int getNumSubClasses(OWLOntology ontology) {
        List<String> lisSubclasses = new ArrayList<>();
        Set<OWLSubClassOfAxiom> subClasses = ontology.getAxioms(AxiomType.SUBCLASS_OF);

        for (OWLSubClassOfAxiom clase : subClasses) {
            lisSubclasses.add(clase.getSignature().toString());
        }
        UtilClass.removeRepeatClasses(lisSubclasses);
        return lisSubclasses.size();
    }

    public int getNumInstances(OWLOntology ontology) {
        List<String> listInstances = new ArrayList<>();
        Set<OWLNamedIndividual> individualsSignature = ontology.getIndividualsInSignature();
        for (OWLNamedIndividual ind : individualsSignature) {
            String []individuals = UtilClass.cutString(ind.toString());

            for (int i = 0; i < (individuals.length - 1); i++) {
                listInstances.add(individuals[1].replaceAll(">", ""));
            }
        }
        Collections.sort(listInstances);
        UtilClass.removeRepeatClasses(listInstances);

        return listInstances.size();
    }

    public int getNumProperties(OWLOntology ontology) {
        List<String> listProperties = new ArrayList<>();
        for (OWLObjectProperty p : ontology.getObjectPropertiesInSignature()) {
            listProperties.add(pm.getShortForm(p).replaceAll(":", ""));
        }
        return listProperties.size();
    }

    public int getNumAnnotation(OWLOntology ontology) {
        List<String> listAnnotations = new ArrayList<>();
        Set<OWLAnnotationAssertionAxiom> annotations = ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION);
        for (OWLAnnotationAssertionAxiom anotation : annotations) {
            listAnnotations.add(anotation.getValue().toString());
        }
        return listAnnotations.size();
    }

    public int getNumRelationsOfThing(OWLOntology ontology) {
        List<String> listRelationsThing = new ArrayList<>();
        Set<OWLSubClassOfAxiom> subClasses = ontology.getAxioms(AxiomType.SUBCLASS_OF);
        for (OWLSubClassOfAxiom subClassAux : subClasses) {
            if (subClassAux.getSignature().toString().contains("owl:Thing")) {
                listRelationsThing.add(subClassAux.getSignature().toString());
            }
        }
        return listRelationsThing.size();
    }

    public int getNumClassesWithIndividuals(OWLOntology ontology) {
        List<String> listClassWithIndividuals = new ArrayList<>();
        Set<OWLClass> classes = ontology.getClassesInSignature();

        for (OWLClass classAux : classes) {
            Set<OWLIndividual> individuals = classAux.getIndividuals(ontology);
            if (!individuals.isEmpty()) {
                listClassWithIndividuals.add(classAux.getSignature().toString());
            }

        }
        return listClassWithIndividuals.size();
    }

}
