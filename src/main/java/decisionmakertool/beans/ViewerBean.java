package decisionmakertool.beans;

import decisionmakertool.util.PathOntology;
import decisionmakertool.util.SessionUtils;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class ViewerBean implements Serializable {

    private String urlRawOntology = "";

    @PostConstruct
    public void init() {
        getPathRaw();
    }

    public void getPathRaw(){
        HttpSession session = SessionUtils.getSession();
        String username = session.getAttribute("username").toString();
        PathOntology path = new PathOntology();
        String viewerUrl = "http://visualdataweb.de/webvowl/#iri=";
        System.out.println(viewerUrl + path.getPathRawRepo() + "ontology_" + username + "A.owl");
        setUrlRawOntology(viewerUrl + path.getPathRawRepo() + "ontology_" + username + "_A.owl");
    }

    public String getUrlRawOntology() {
        return urlRawOntology;
    }

    public void setUrlRawOntology(String urlRawOntology) {
        this.urlRawOntology = urlRawOntology;
    }
}
