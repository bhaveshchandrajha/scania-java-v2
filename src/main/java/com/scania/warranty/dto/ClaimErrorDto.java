/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.dto;

import java.math.BigDecimal;

/**
 * DTO for claim error response.
 */
public record ClaimErrorDto(
    String errorNumber,
    String mainGroup,
    String subGroup,
    String errorPart,
    String text1,
    String text2,
    String text3,
    String text4,
    BigDecimal requestedMaterial,
    BigDecimal requestedLabor,
    BigDecimal requestedSpecial,
    BigDecimal statusCode
) {
}