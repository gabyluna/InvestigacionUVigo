/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
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
 * @author Gaby
 */
public class OWLTest {
     public static void main(String[] args) throws OWLOntologyCreationException  {
         OwlUtil owlUtil = new OwlUtil();
         // Get hold of an ontology manager
     OWLOntologyManager manager;
    // Load an ontology from local

     OWLOntology ontology;
     IRI documentIRI;
    
     OWLReasonerFactory factory = null;
     OWLReasoner reasoner;
OWLDataFactory dataFactory;
          PathOntology path = new PathOntology();

            File file = new File(path.getPath());

            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(file);
            documentIRI = manager.getOntologyDocumentIRI(ontology);
            factory = new JFactFactory();
            //OWLReasonerConfiguration config = new SimpleConfiguration(500);
            // Create a reasoner that will reason over our ontology and its imports
            // closure. Pass in the configuration.
            reasoner = factory.createReasoner(ontology);
            // Ask the reasoner to classify the ontology

            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
            dataFactory = manager.getOWLDataFactory();
            
            List<String> superclases = new ArrayList<String>();
        superclases=  owlUtil.getSuperClasses(ontology,dataFactory, reasoner);
        
        for(String superClase: superclases){
        
            System.out.println("SuperClases:"+ superClase);
        }
         
     }
}
