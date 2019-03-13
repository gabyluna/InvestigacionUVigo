/**
 * This file is part of DrOntoAPI.
 *
 * Copyright 2014 Ionel Virgil Pop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ionelvirgilpop.drontoapi.service;

import ionelvirgilpop.drontoapi.util.PitfallSelector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.hp.hpl.jena.util.FileManager;

/**
 * @author Ionel Virgil Pop
 *
 */
public class WebService implements IWebService {

    private HttpClient client;
    private HttpPost post;
    private HttpResponse response;

    private String ontologyIRI = "";
    private String ontologyFile = "";

    private PitfallSelector pitfallSelector = new PitfallSelector();

    public WebService() {
        client = new DefaultHttpClient();
        post = new HttpPost("http://oops-ws.oeg-upm.net/rest");
    }

    //Get the response from the web service
    @Override
    public InputStream getResponse() throws IOException {
        String content = "";
        if (!getOntologyFile().equals("")) {
            InputStream inputStream = new FileInputStream(getOntologyFile());
            FileManager fileManager = new FileManager();
            content = fileManager.readWholeFileAsUTF8(inputStream);
        }

        /* Note that here, pitfallSelector.toString() is used to shorten the code because it returns the number of each pitfall separated by commas as the OOPS! web service requires.
		 * For other formats, pifallSelector.getSelectedPitfalls() would have to be used to retrieve the selected pitfalls and format them as necessary.
         */
        StringEntity input = new StringEntity("<?xml version=\"1.0\" encoding=\"UTF-8\"?><OOPSRequest><OntologyUrl>" + ontologyIRI + "</OntologyUrl><OntologyContent>" + content + "</OntologyContent><Pitfalls>" + pitfallSelector.toString() + "</Pitfalls><OutputFormat>RDF/XML</OutputFormat></OOPSRequest>");
        post.setEntity(input);

        response = client.execute(post);
        return response.getEntity().getContent();
    }

    @Override
    public PitfallSelector getPitfallSelector() {
        return pitfallSelector;
    }

    @Override
    public void setPitfallSelector(PitfallSelector pitfallSelector) {
        this.pitfallSelector = pitfallSelector;
    }

    @Override
    public String getOntologyFile() {
        return ontologyFile;
    }

    @Override
    public void setOntologyFile(String ontologyFile) {
        this.ontologyFile = ontologyFile;
    }

    @Override
    public String getOntologyIRI() {
        return ontologyIRI;
    }

    @Override
    public void setOntologyIRI(String ontologyIRI) {
        this.ontologyIRI = ontologyIRI;
    }

}
