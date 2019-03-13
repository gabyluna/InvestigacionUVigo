/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

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

    public List<String> getProperties(OWLOntology ont) {
        List<String> listProperties = new ArrayList<>();
        for (OWLObjectProperty p : ont.getObjectPropertiesInSignature()) {
            //System.out.println("decisionmakertool.util.OwlUtil.getSuperClasses():"+pm.getShortForm(p));
            listProperties.add(pm.getShortForm(p).replaceAll(":", ""));
        }
        return listProperties;
    }

    public List<String> getAnnotation(OWLOntology ont) {
        List<String> listProperties = new ArrayList<>();
        Set<OWLAnnotationAssertionAxiom> annotations = ont.getAxioms(AxiomType.ANNOTATION_ASSERTION);
        for (OWLAnnotationAssertionAxiom anotation : annotations) {
            listProperties.add(anotation.getValue().toString());
        }
        return listProperties;
    }

    public List<String> getSubClassOfThing(OWLOntology ont) {
        List<String> listSubClass = new ArrayList<>();
        Set<OWLSubClassOfAxiom> subClasses = ont.getAxioms(AxiomType.SUBCLASS_OF);
        for (OWLSubClassOfAxiom clase : subClasses) {
            if (clase.getSignature().toString().contains("owl:Thing")) {
                listSubClass.add(clase.getSignature().toString());
            }
        }
        return listSubClass;
    }

    public List<String> getSubClassOfAll(OWLOntology ont) {
        List<String> listSubClass = new ArrayList<>();
        Set<OWLSubClassOfAxiom> subClasses = ont.getAxioms(AxiomType.SUBCLASS_OF);

        for (OWLSubClassOfAxiom clase : subClasses) {
            listSubClass.add(clase.getSignature().toString());
        }
        return listSubClass;
    }

    public List<String> getClasses(OWLOntology ont) {
        List<String> listClass = new ArrayList<>();
        Set<OWLClass> classes = ont.getClassesInSignature();

        for (OWLClass clase : classes) {
            listClass.add(pm.getShortForm(clase).replaceAll(":", ""));
        }

        return listClass;

    }

    public List<String> getSuperClasses(OWLOntology ont) {
        List<String> listSupClass = new ArrayList<>();
        Set<OWLClass> classes = ont.getClassesInSignature();

        for (OWLClass clas : classes) {
            Set<OWLClassExpression> superclasses = clas.getSuperClasses(ont);
            for (OWLClassExpression sc : superclasses) {
                listSupClass.add(sc.getSignature().toString());
            }
        }
        return listSupClass;

    }

    public List<String> printInd(OWLOntology ont) {
        List<String> listInd = new ArrayList<>();
        Set<OWLNamedIndividual> inds = ont.getIndividualsInSignature();
        for (OWLNamedIndividual ind : inds) {
            String cadena[] = cutString(ind.toString());

            for (int i = 0; i < (cadena.length - 1); i++) {
                // System.out.println(cadena[1].replaceAll(">", ""));
                listInd.add(cadena[1].replaceAll(">", ""));
            }
        }
        Collections.sort(listInd);
        return listInd;
    }

    public List<String> printIndByClass(OWLOntology ont) {

        List<String> listIndByClass = new ArrayList<>();
        Set<OWLClass> classes = ont.getClassesInSignature();

        for (OWLClass clas : classes) {
            Set<OWLIndividual> individuals = clas.getIndividuals(ont);
            if (individuals.size() > 0) {
                listIndByClass.add(clas.getSignature().toString());
            }

        }

        return listIndByClass;
    }

    public String[] cutString(String cadena) {
        return cadena.split("#");
    }

}
