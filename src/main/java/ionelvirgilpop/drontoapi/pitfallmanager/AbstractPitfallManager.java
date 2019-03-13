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

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ionel Virgil Pop
 *
 */
public abstract class AbstractPitfallManager implements IPitfallManager {

    public AbstractPitfallManager() {
    }

    @Override
    public void generateReport(OutputStream stream, Report report) throws FileNotFoundException, UnsupportedEncodingException {
        Writer writer = new OutputStreamWriter(stream, "UTF-8");
        PrintWriter out = new PrintWriter(writer, true);
        switch (report) {
            case CSV_REPORT:
                generateCSVReport(out);
                break;
            case AFFECTED_ELEMENTS_REPORT:
                generateAffectedElementsReport(out);
                break;
            case PITFALL_REPORT:
                generatePitfallReport(out);
                break;
            default:
                break;
        }
    }

    private void generateCSVReport(PrintWriter out) {
        //header
        out.print("Pitfall Code,Pitfall Name,Pitfall Importance Level,Cases,Affected Elements\r\n");
        out.flush();

        Iterator<Pitfall> iterator = getPitfalls().iterator();
        while (iterator.hasNext()) {
            Pitfall pitfall = iterator.next();

            int affectedCount = getAffectedElements(pitfall).size();

            Iterator<AffectedElement> extendedElementsIterator = getAffectedElementsExtended(pitfall).iterator();
            String elementSet = "";
            int k = 0;
            int affectedCountEx = 0;
            while (extendedElementsIterator.hasNext()) {
                affectedCountEx++;
                AffectedElement affectedElement = extendedElementsIterator.next();
                if (affectedElement.getElementSet() != elementSet) {
                    k++;
                    elementSet = affectedElement.getElementSet();
                }
            }

            out.print(pitfall.getCode() + "," + pitfall.getName() + "," + pitfall.getImportanceLevel() + "," + (affectedCount + k) + "," + (affectedCount + affectedCountEx) + "\r\n");
            out.flush();
        }

    }

    private void generateAffectedElementsReport(PrintWriter out) {
        out.println("AFFECTED ELEMENTS REPORT");

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        out.println("Date: " + dateFormat.format(date));
        out.println();

        Iterator<AffectedElement> iterator = getAffectedElements().iterator();
        while (iterator.hasNext()) {
            AffectedElement affectedElement = iterator.next();

            out.println();
            String pitfallString = "Affected element: " + affectedElement.getURI();
            out.println(pitfallString);
            char[] chars = new char[pitfallString.length()];
            Arrays.fill(chars, '-');
            String lines = new String(chars);
            out.println(lines);

            out.println("This element has the following pitfalls:");

            Iterator<Pitfall> pitfallIterator = getPitfalls(affectedElement).iterator();
            while (pitfallIterator.hasNext()) {
                Pitfall pitfall = pitfallIterator.next();
                out.println("    " + pitfall.getCode() + ": " + pitfall.getName() + " (" + pitfall.getImportanceLevel() + ") - " + pitfall.getDescription());

                Iterator<AffectedElement> relatedIterator = getAffectedElements(pitfall, affectedElement).iterator();
                if (relatedIterator.hasNext()) {
                    out.println("        Related elements:");
                }
                while (relatedIterator.hasNext()) {
                    AffectedElement relatedElement = relatedIterator.next();
                    out.println("            " + relatedElement.getURI());
                }
            }

        }

    }

    private void generatePitfallReport(PrintWriter out) {
        out.println("PITFALL REPORT");

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        out.println("Date: " + dateFormat.format(date));
        out.println();

        //Print the pitfalls
        Iterator<Pitfall> iterator = getPitfalls().iterator();
        while (iterator.hasNext()) {
            Pitfall pitfall = iterator.next();
            out.println();
            String pitfallString = pitfall.getCode() + ": " + pitfall.getName() + " (" + pitfall.getImportanceLevel() + ")";
            out.println(pitfallString);
            char[] chars = new char[pitfallString.length()];
            Arrays.fill(chars, '-');
            String lines = new String(chars);
            out.println(lines);

            out.println(pitfall.getDescription());

            out.println();
            out.println("This pitfall affects the following elements:");

            //Print elements that are affected by a particular pitfall
            Iterator<AffectedElement> elementsIterator = getAffectedElements(pitfall).iterator();
            while (elementsIterator.hasNext()) {
                AffectedElement affectedElement = elementsIterator.next();
                out.println("    " + affectedElement.getURI());
            }

            //Print sets of elements that are affected by a particular pitfall				
            Iterator<AffectedElement> extendedElementsIterator = getAffectedElementsExtended(pitfall).iterator();
            String elementSet = "";
            int k = 0;
            while (extendedElementsIterator.hasNext()) {
                AffectedElement affectedElement = extendedElementsIterator.next();
                if (affectedElement.getElementSet() != elementSet) {
                    k++;
                    elementSet = affectedElement.getElementSet();
                }
                out.println("    (" + k + ") " + affectedElement.getURI());
            }

        }

    }

    @Override
    public abstract List<Pitfall> getPitfalls();

    @Override
    public abstract List<Pitfall> getPitfalls(AffectedElement affectedElement);

    @Override
    public abstract List<AffectedElement> getAffectedElements(Pitfall pitfall, AffectedElement affectedElement);

    @Override
    public abstract List<AffectedElement> getAffectedElements(Pitfall pitfall);

    @Override
    public abstract List<AffectedElement> getAffectedElementsExtended(Pitfall pitfall);

    @Override
    public abstract List<AffectedElement> getAffectedElements();

}
