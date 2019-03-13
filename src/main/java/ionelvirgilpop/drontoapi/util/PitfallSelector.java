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
package ionelvirgilpop.drontoapi.util;

import ionelvirgilpop.drontoapi.filter.IFilter;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Ionel Virgil Pop
 *
 */
public class PitfallSelector {

    //the list of selected pitfalls
    public Set<Pitfall> selectedPitfalls = new TreeSet<Pitfall>();

    public PitfallSelector() {
    }

    //Retrieve all pitfalls in the selection
    public Set<Pitfall> getSelectedPitfalls() {
        return this.selectedPitfalls;
    }

    //Add a pitfall to the selection
    public void selectPitfall(int number) {
        selectedPitfalls.add(new Pitfall(number));
    }

    //Add a range of pitfalls to the selection
    public void selectPitfallRange(int fromNumber, int toNumber) {
        for (int i = fromNumber; i <= toNumber; i++) {
            selectedPitfalls.add(new Pitfall(i));
        }
    }

    public void selectPitfalls(IFilter filter) {
        selectedPitfalls.addAll(filter.getPitfalls());
    }

    @Override
    public String toString() {
        String pitfallsString = "";
        Iterator<Pitfall> pitfallIterator = selectedPitfalls.iterator();
        while (pitfallIterator.hasNext()) {
            Pitfall pitfall = pitfallIterator.next();
            pitfallsString += pitfall.getNumber();
            if (pitfallIterator.hasNext()) {
                pitfallsString += ",";
            }
        }
        return pitfallsString;
    }
}
