/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim creation response.
 */
public record ClaimCreationResponse(
    String message,
    String claimNumber
) {
}