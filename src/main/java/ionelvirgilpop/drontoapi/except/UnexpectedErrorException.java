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
package ionelvirgilpop.drontoapi.except;

import ionelvirgilpop.drontoapi.pitfallmanager.UnexpectedError;

/**
 * @author Ionel Virgil Pop
 *
 */
public class UnexpectedErrorException extends Exception {

    private static final long serialVersionUID = 1000L;

    public String unexpectedErrorTitle = "";
    public String unexpectedErrorMessage = "";

    public UnexpectedErrorException(UnexpectedError error) {
        super("Error Title: " + error.getTitle() + System.lineSeparator() + "Error Message: " + error.getMessage());
        setUnexpectedErrorTitle(error.getTitle());
        setUnexpectedErrorMessage(error.getMessage());
    }

    public String getUnexpectedErrorMessage() {
        return unexpectedErrorMessage;
    }

    private void setUnexpectedErrorMessage(String unexpectedErrorMessage) {
        this.unexpectedErrorMessage = unexpectedErrorMessage;
    }

    public String getUnexpectedErrorTitle() {
        return unexpectedErrorTitle;
    }

    private void setUnexpectedErrorTitle(String unexpectedErrorTitle) {
        this.unexpectedErrorTitle = unexpectedErrorTitle;
    }

}
