/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Value object representing claim validation result with error messages.
 */
public class ClaimValidationResult {

    private final boolean valid;
    private final List<String> errorMessages;

    public ClaimValidationResult(boolean valid, List<String> errorMessages) {
        this.valid = valid;
        this.errorMessages = new ArrayList<>(errorMessages);
    }

    public static ClaimValidationResult success() {
        return new ClaimValidationResult(true, List.of());
    }

    public static ClaimValidationResult failure(List<String> errors) {
        return new ClaimValidationResult(false, errors);
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }

    public String getFormattedErrors() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < errorMessages.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(errorMessages.get(i));
        }
        return sb.toString();
    }
}