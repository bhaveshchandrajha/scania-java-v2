/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Value object for claim creation context.
 */
public record ClaimCreationContext(
    String companyCode,
    String claimNumber,
    String invoiceNumber,
    String invoiceDate,
    String orderNumber,
    List<FailureData> failures
) {
    public ClaimCreationContext {
        if (failures == null) {
            failures = new ArrayList<>();
        }
    }
    
    public static record FailureData(
        int failureNumber,
        String mainGroup,
        String subGroup,
        String partNumber,
        boolean isMaintenance,
        List<String> textLines,
        BigDecimal materialValue,
        BigDecimal laborValue,
        BigDecimal specialValue
    ) {
        public FailureData {
            if (textLines == null) {
                textLines = new ArrayList<>();
            }
        }
    }
}