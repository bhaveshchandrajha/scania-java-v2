/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

/**
 * Value object representing the composite key for claim lookup (KEY_GPS).
 */
public record ClaimKey(
    String abbreviation,
    String claimNumber,
    Integer errorNumber
) {
    public ClaimKey {
        if (abbreviation == null || abbreviation.isBlank()) {
            throw new IllegalArgumentException("Abbreviation cannot be null or blank");
        }
        if (claimNumber == null || claimNumber.isBlank()) {
            throw new IllegalArgumentException("Claim number cannot be null or blank");
        }
        if (errorNumber == null || errorNumber < 0) {
            throw new IllegalArgumentException("Error number must be non-negative");
        }
    }
}