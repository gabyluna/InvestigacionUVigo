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
package ionelvirgilpop.drontoapi.filter;

import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Ionel Virgil Pop
 *
 */
public class Filter extends DefaultFilter implements IFilter {

    public Filter() {
        Set<Pitfall> correctness = new TreeSet<Pitfall>();
        correctness.add(new Pitfall(6));
        correctness.add(new Pitfall(14));
        correctness.add(new Pitfall(15));
        correctness.add(new Pitfall(16));
        correctness.add(new Pitfall(19));
        correctness.add(new Pitfall(20));
        correctness.add(new Pitfall(24));
        correctness.add(new Pitfall(25));
        correctness.add(new Pitfall(26));
        correctness.add(new Pitfall(27));
        correctness.add(new Pitfall(28));
        correctness.add(new Pitfall(29));
        correctness.add(new Pitfall(31));
        super.map.put(Criteria.CORRECTNESS, correctness);

        Set<Pitfall> adaptability = new TreeSet<Pitfall>();
        adaptability.add(new Pitfall(36));
        adaptability.add(new Pitfall(37));
        adaptability.add(new Pitfall(38));
        adaptability.add(new Pitfall(39));
        adaptability.add(new Pitfall(40));
        super.map.put(Criteria.ADAPTABILITY, adaptability);

        Set<Pitfall> clarity = new TreeSet<Pitfall>();
        clarity.add(new Pitfall(1));
        clarity.add(new Pitfall(7));
        clarity.add(new Pitfall(8));
        clarity.add(new Pitfall(20));
        clarity.add(new Pitfall(22));
        clarity.add(new Pitfall(32));
        super.map.put(Criteria.CLARITY, clarity);

        Set<Pitfall> completeness = new TreeSet<Pitfall>();
        completeness.add(new Pitfall(4));
        completeness.add(new Pitfall(8));
        completeness.add(new Pitfall(9));
        completeness.add(new Pitfall(10));
        completeness.add(new Pitfall(11));
        completeness.add(new Pitfall(12));
        completeness.add(new Pitfall(13));
        completeness.add(new Pitfall(30));
        completeness.add(new Pitfall(34));
        completeness.add(new Pitfall(35));
        super.map.put(Criteria.COMPLETENESS, completeness);

        Set<Pitfall> conciseness = new TreeSet<Pitfall>();
        conciseness.add(new Pitfall(2));
        conciseness.add(new Pitfall(3));
        conciseness.add(new Pitfall(17));
        conciseness.add(new Pitfall(21));
        super.map.put(Criteria.CONCISENESS, conciseness);
    }

}
