/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.service;

import ionelvirgilpop.drontoapi.pitfallmanager.AffectedElement;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import java.util.List;

/**
 *
 * @author Gaby
 */
public interface SmellErrorsInterface {
    public List<Pitfall> circularityErrors(String path);
    public List<Pitfall> partitionErrors(String path);
    public List<Pitfall> semanticErrors(String path);
    public List<Pitfall> incompletenessErrors(String path);
    public void redundandyErrors(String path);
    public List<AffectedElement> getElementsSmellErrors(String path, Pitfall pitfall);
}
