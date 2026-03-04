/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim subfile list item.
 */
public record ClaimSubfileItemDto(
        String companyCode,
        String claimNumber,
        String invoiceNumber,
        String invoiceDate,
        String chassisNumber,
        String customerNumber,
        String customerName,
        String demandCode,
        Integer statusCode,
        String statusText,
        Integer errorCount,
        String colorIndicator
) {}