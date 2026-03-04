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
    CREATED(0),
    PENDING(0),
    MINIMUM(5),
    APPROVED(20),
    EXCLUDED(99);

    private final int code;

    ClaimStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        switch (this) {
            case CREATED:
            case PENDING:
                return "Pending";
            case MINIMUM:
                return "Minimum";
            case APPROVED:
                return "Approved";
            case EXCLUDED:
                return "Excluded";
            default:
                return "";
        }
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