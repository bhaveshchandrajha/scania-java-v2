/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim detail.
 */
public record ClaimDto(
    String companyCode,
    String invoiceNr,
    String invoiceDate,
    String orderNr,
    String wete,
    String claimNr,
    String chassisNr,
    String licensePlate,
    Integer registrationDate,
    Integer repairDate,
    Integer mileage,
    Integer productType,
    String attachment,
    String foreigner,
    String customerNr,
    String customerName,
    String claimNrSde,
    Integer statusCodeSde,
    Integer errorCount,
    String area,
    String sdpsOrderNr
) {
}