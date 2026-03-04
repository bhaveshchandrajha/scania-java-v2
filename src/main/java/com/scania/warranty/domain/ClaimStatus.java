/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.domain;

/**
 * Enum for claim status codes (STATUS CODE SDE).
 */
public enum ClaimStatus {
    PENDING(0, "Pending"),
    CREATED(3, "Created"),
    MINIMUM(5, "Minimum"),
    APPROVED(20, "Approved"),
    EXCLUDED(99, "Excluded");

    private final int code;
    private final String description;

    ClaimStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // @origin HS1210 L919-996 (IF)
    public static ClaimStatus fromCode(int code) {
        for (ClaimStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return PENDING;
    }
}