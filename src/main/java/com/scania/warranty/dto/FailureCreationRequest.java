/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for failure creation request.
 */
public record FailureCreationRequest(
    Integer failureNumber,
    String groups,
    String partNumber,
    boolean maintenance,
    List<String> textLines,
    BigDecimal valueMaterial,
    BigDecimal valueLabor,
    BigDecimal valueSpecial
) {
}