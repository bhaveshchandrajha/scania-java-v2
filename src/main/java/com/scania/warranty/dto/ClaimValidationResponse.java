/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.dto;

import java.util.List;

/**
 * Response DTO for claim validation result.
 */
public record ClaimValidationResponse(
    boolean valid,
    List<String> errorMessages,
    String formattedErrors
) {}