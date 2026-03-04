/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim creation result.
 */
public record ClaimCreationResult(
    boolean created,
    int failuresCreated,
    String message
) {
}