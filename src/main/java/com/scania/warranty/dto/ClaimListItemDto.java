/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim list item (subfile row).
 */
public record ClaimListItemDto(
    String companyCode,
    String claimNr,
    String invoiceNr,
    String invoiceDate,
    String chassisNr,
    String customerNr,
    String customerName,
    String demandCode,
    Integer statusCodeSde,
    Integer errorCount,
    String colorIndicator
) {
}