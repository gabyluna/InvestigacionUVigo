/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.model;

/**
 *
 * @author Gaby
 */
public class MetricaOntologia {
    
    private String nombreOntologia;
    private int numClases;
    private int numSubclasesDe;
    private int numInstancias;
    private int numPropiedates;
    private int numAnotaciones;
    private int numSuperclases;
    private int relacionesThing;
    
    public int getNumClases() {
        return numClases;
    }

    public void setNumClases(int numClases) {
        this.numClases = numClases;
    }

    public int getNumSubclasesDe() {
        return numSubclasesDe;
    }

    public void setNumSubclasesDe(int numSubclasesDe) {
        this.numSubclasesDe = numSubclasesDe;
    }

    public int getNumInstancias() {
        return numInstancias;
    }

    public void setNumInstancias(int numInstancias) {
        this.numInstancias = numInstancias;
    }

    public int getNumPropiedates() {
        return numPropiedates;
    }

    public void setNumPropiedates(int numPropiedates) {
        this.numPropiedates = numPropiedates;
    }

    public int getNumAnotaciones() {
        return numAnotaciones;
    }

    public void setNumAnotaciones(int numAnotaciones) {
        this.numAnotaciones = numAnotaciones;
    }

    public int getNumSuperclases() {
        return numSuperclases;
    }

    public void setNumSuperclases(int numSuperclases) {
        this.numSuperclases = numSuperclases;
    }

    public int getRelacionesThing() {
        return relacionesThing;
    }

    public void setRelacionesThing(int relacionesThing) {
        this.relacionesThing = relacionesThing;
    }

    public String getNombreOntologia() {
        return nombreOntologia;
    }

    public void setNombreOntologia(String nombreOntologia) {
        this.nombreOntologia = nombreOntologia;
    }
    
    
}
