package decisionmakertool.service.impl;

import decisionmakertool.entities.QuickFixModel;
import decisionmakertool.owl.OntologyUtil;
import decisionmakertool.service.QuickFixInterface;
import decisionmakertool.util.Util;
import drontoapi.pitfallmanager.AffectedElement;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import java.util.*;
import java.util.stream.Collectors;

public class RemoveSimilarElements implements QuickFixInterface {
    @Override
    public Set<OWLAxiom> validateOntology(OWLOntology ontology, List<AffectedElement> listAffectedElements) {
        Set<OWLAxiom> axiomsToChange =  new HashSet<>();

        return  axiomsToChange;
    }

    @Override
    public List<String> getListChangesToApply(Set<OWLAxiom> axiomsToChange) {
        return null;
    }


    @Override
    public OWLOntology getResultQuickFix(OWLOntology ontology, OWLOntologyManager manager, Set<OWLAxiom> axiomsToChange) {
        return null;
    }

   /* @Override
    public List<String> getListChangesToApply(QuickFixModel quickFixModel) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        List<AffectedElement> listAffectedElemenstToRemove = new ArrayList<>();
        int sizeList = quickFixModel.getListAffectedElements().size();

        for(int i = 0; i < sizeList; i++){
            if(quickFixModel.getListAffectedElements().get(i).getSelected()){
                String wordAux1 =  Util.getWord(quickFixModel.getListAffectedElements().get(i).getURI());
                for(int j=(sizeList-1);j >= 0 ; j--){
                    String wordAux2 =  Util.getWord(quickFixModel.getListAffectedElements().get(j).getURI());
                    int distance = levenshteinDistance.apply(wordAux1,wordAux2);

                    if(distance > 0 && distance < 3) {
                        listAffectedElemenstToRemove.add(quickFixModel.getListAffectedElements().get(j));
                    }
                }
            }

        }

        listAffectedElemenstToRemove = listAffectedElemenstToRemove.stream().collect(Collectors.toCollection(()->
                new TreeSet<>(Comparator.comparing(AffectedElement::getURI)))).stream().collect(Collectors.toList());
        for(AffectedElement element:listAffectedElemenstToRemove){
            try {
                return  quickFixModel.getOntologyUtil().removeAxioms(element.getURI(), quickFixModel.getPath());
            } catch (OWLOntologyStorageException e) {
                e.printStackTrace();
            }
        }
        return null;
    }*/


}
