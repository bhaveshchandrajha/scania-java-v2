/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.dto;

/**
 * Request DTO for claim validation.
 */
public record ClaimValidationRequest(
    String kuerzel,
    String claimNumber,
    String errorNumber,
    String sequenceNumber,
    String customerDamageCause,
    Long campaignNumber,
    String customerMainCause,
    String customerSubCause,
    String workshopDamageCause,
    String workshopMainCause,
    String workshopSubCause,
    String damageCausingPartNumber
) {}