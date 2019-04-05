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

/**
 * @author Ionel Virgil Pop
 *
 */
public class Pitfall implements Comparable<Pitfall> {

    private int number;
    private String name;
    private String description;
    private String importanceLevel;

    public Pitfall(int number) {
        setNumber(number);
    }

    public String getCode() {
        return String.format("P%02d", getNumber());
    }

    public int getNumber() {
        return number;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public String getImportanceLevel() {
        return importanceLevel;
    }

    protected void setImportanceLevel(String importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    @Override
    public int compareTo(Pitfall p) {
        if (this.getNumber() < p.getNumber()) {
            return -1;
        } else if (this.getNumber() > p.getNumber()) {
            return 1;
        } else {
            return 0;
        }
    }


}
