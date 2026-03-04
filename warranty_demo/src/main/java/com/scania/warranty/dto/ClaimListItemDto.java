/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim list item (subfile record HS1210S1/S2).
 */
public record ClaimListItemDto(
        String pakz,
        String claimNr,
        String rechNr,
        String rechDatum,
        String auftragsNr,
        String chassisNr,
        String kdNr,
        String kdName,
        String claimNrSde,
        String statusCodeSde,
        String statusText,
        int errorCount,
        String color
) {
}