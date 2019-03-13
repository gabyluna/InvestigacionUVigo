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
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Ionel Virgil Pop
 *
 */
public interface IPitfallManager {

    public List<Pitfall> getPitfalls();

    public List<Pitfall> getPitfalls(AffectedElement affectedElement);

    public List<AffectedElement> getAffectedElements(Pitfall pitfall, AffectedElement affectedElement);

    public List<AffectedElement> getAffectedElements(Pitfall pitfall);

    public List<AffectedElement> getAffectedElementsExtended(Pitfall pitfall);

    public List<AffectedElement> getAffectedElements();

    public void generateReport(OutputStream stream, Report report) throws FileNotFoundException, UnsupportedEncodingException;

}
