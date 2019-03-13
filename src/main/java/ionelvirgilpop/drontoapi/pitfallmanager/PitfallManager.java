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
package ionelvirgilpop.drontoapi.pitfallmanager;

import ionelvirgilpop.drontoapi.except.UnexpectedErrorException;
import ionelvirgilpop.drontoapi.util.Convertion;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ionel Virgil Pop
 *
 */
public class PitfallManager extends AbstractPitfallManager implements IPitfallManager {

    private Model model;

    public PitfallManager(InputStream stream) throws UnexpectedErrorException {
        model = ModelFactory.createDefaultModel();
        model.read(stream, "RDF/XML");
        if (hasUnexpectedError()) {
            throw new UnexpectedErrorException(getUnexpectedError());
        }
    }

    @Override
    public List<Pitfall> getPitfalls() {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT DISTINCT ?code ?name ?description ?importanceLevel WHERE {"
                + "   ?problem rdf:type oops:pitfall ."
                + "   ?problem oops:hasCode ?code ."
                + "   ?problem oops:hasName ?name ."
                + "   ?problem oops:hasDescription ?description ."
                + "   ?problem oops:hasImportanceLevel ?importanceLevel ."
                + "} ORDER BY ?code";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            List<Pitfall> list = new LinkedList<Pitfall>();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal code = qs.getLiteral("code");
                Literal name = qs.getLiteral("name");
                Literal description = qs.getLiteral("description");
                Literal importanceLevel = qs.getLiteral("importanceLevel");
                Convertion convertion = new Convertion();
                Pitfall pitfall = new Pitfall(convertion.codeToInt(code.getString()));
                pitfall.setName(name.getString());
                pitfall.setDescription(description.getString());
                pitfall.setImportanceLevel(importanceLevel.getString());
                list.add(pitfall);
            }
            return list;
        } finally {
            qe.close();
        }
    }

    @Override
    public List<Pitfall> getPitfalls(AffectedElement affectedElement) {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT DISTINCT ?code ?name ?description ?importanceLevel WHERE {"
                + "   ?problem rdf:type oops:pitfall ."
                + "   ?problem oops:hasCode ?code ."
                + "   ?problem oops:hasName ?name ."
                + "   ?problem oops:hasDescription ?description ."
                + "   ?problem oops:hasImportanceLevel ?importanceLevel ."
                + "   {?problem oops:hasAffectedElement \"" + affectedElement.getURI() + "\"^^<" + affectedElement.getDatatypeURI() + "> .}"
                + "   UNION "
                + "   {?problem ?property ?element ."
                + "   ?element rdf:type ?elementType ."
                + "   ?element oops:hasAffectedElement \"" + affectedElement.getURI() + "\"^^<" + affectedElement.getDatatypeURI() + "> .}"
                + "} ORDER BY ?code";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            List<Pitfall> list = new LinkedList<Pitfall>();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal code = qs.getLiteral("code");
                Literal name = qs.getLiteral("name");
                Literal description = qs.getLiteral("description");
                Literal importanceLevel = qs.getLiteral("importanceLevel");
                Convertion convertion = new Convertion();
                Pitfall pitfall = new Pitfall(convertion.codeToInt(code.getString()));
                pitfall.setName(name.getString());
                pitfall.setDescription(description.getString());
                pitfall.setImportanceLevel(importanceLevel.getString());
                list.add(pitfall);
            }
            return list;
        } finally {
            qe.close();
        }
    }

    @Override
    public List<AffectedElement> getAffectedElements(Pitfall pitfall, AffectedElement affectedElement) {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT DISTINCT ?affectedElement ?element WHERE {"
                + "   ?problem rdf:type oops:pitfall ."
                + "   ?problem oops:hasCode \"" + pitfall.getCode() + "\" ."
                + "   ?problem ?property ?element ."
                + "   ?element rdf:type ?elementType ."
                + "   ?element oops:hasAffectedElement \"" + affectedElement.getURI() + "\" ."
                + "   ?element oops:hasAffectedElement ?affectedElement ."
                + "} ORDER BY ?element";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            List<AffectedElement> list = new LinkedList<AffectedElement>();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal affectedElementLiteral = qs.getLiteral("affectedElement");
                Resource element = qs.getResource("element");
                if (!affectedElementLiteral.getString().equals(affectedElement.getURI())) {
                    AffectedElement affected = new AffectedElement(affectedElementLiteral.getString(), affectedElement.getDatatypeURI());
                    affected.setElementSet(element.getURI());
                    list.add(affected);
                }
            }
            return list;
        } finally {
            qe.close();
        }
    }

    @Override
    public List<AffectedElement> getAffectedElements(Pitfall pitfall) {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT DISTINCT ?affectedElement WHERE {"
                + "   ?problem rdf:type oops:pitfall ."
                + "   ?problem oops:hasCode \"" + pitfall.getCode() + "\" ."
                + "   ?problem oops:hasAffectedElement ?affectedElement ."
                + "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            List<AffectedElement> list = new LinkedList<AffectedElement>();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal affectedElement = qs.getLiteral("affectedElement");
                AffectedElement affected = new AffectedElement(affectedElement.getString(), affectedElement.getDatatypeURI());
                list.add(affected);
            }
            return list;
        } finally {
            qe.close();
        }

    }

    @Override
    public List<AffectedElement> getAffectedElementsExtended(Pitfall pitfall) {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT DISTINCT ?affectedElement ?element WHERE {"
                + "   ?problem rdf:type oops:pitfall ."
                + "   ?problem oops:hasCode \"" + pitfall.getCode() + "\" ."
                + "   ?problem ?property ?element ."
                + "   ?element rdf:type ?elementType ."
                + "   ?element oops:hasAffectedElement ?affectedElement ."
                + "} ORDER BY ?element";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            List<AffectedElement> list = new LinkedList<AffectedElement>();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal affectedElement = qs.getLiteral("affectedElement");
                Resource element = qs.getResource("element");
                AffectedElement affected = new AffectedElement(affectedElement.getString(), affectedElement.getDatatypeURI());
                affected.setElementSet(element.getURI());
                list.add(affected);
            }
            return list;
        } finally {
            qe.close();
        }

    }

    @Override
    public List<AffectedElement> getAffectedElements() {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT DISTINCT ?affectedElement WHERE {"
                + "   ?problem oops:hasAffectedElement ?affectedElement ."
                + "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            List<AffectedElement> list = new LinkedList<AffectedElement>();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal affectedElement = qs.getLiteral("affectedElement");
                AffectedElement element = new AffectedElement(affectedElement.getString(), affectedElement.getDatatypeURI());
                list.add(element);
            }
            return list;
        } finally {
            qe.close();
        }

    }

    private Boolean hasUnexpectedError() {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT (EXISTS{<http://www.oeg-upm.net/oops/unexpected_error> rdf:type oops:response} AS ?error) WHERE {"
                + "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            Boolean result = false;
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal error = qs.getLiteral("error");
                result = error.getBoolean();
            }
            return result;
        } finally {
            qe.close();
        }
    }

    private UnexpectedError getUnexpectedError() {
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX oops: <http://www.oeg-upm.net/oops#>"
                + "SELECT DISTINCT ?title ?message WHERE {"
                + "   <http://www.oeg-upm.net/oops/unexpected_error> rdf:type oops:response ."
                + "   <http://www.oeg-upm.net/oops/unexpected_error> oops:hasTitle ?title ."
                + "   <http://www.oeg-upm.net/oops/unexpected_error> oops:hasMessage ?message ."
                + "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);

        try {
            UnexpectedError error = new UnexpectedError();
            ResultSet results = qe.execSelect();
            while (results.hasNext()) {
                QuerySolution qs = results.nextSolution();
                Literal title = qs.getLiteral("title");
                Literal message = qs.getLiteral("message");
                error.setTitle(title.getString());
                error.setMessage(error.getMessage() + message.getString());
            }
            return error;
        } finally {
            qe.close();
        }
    }

}
