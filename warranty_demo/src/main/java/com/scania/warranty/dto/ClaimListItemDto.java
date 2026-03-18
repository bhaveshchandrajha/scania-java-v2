/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 * Uses @JsonProperty so Angular/HS1210D schema get expected field names (claimNumber, invoiceNumber, etc.).
 */

package com.scania.warranty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClaimListItemDto(
    @JsonProperty("companyCode") String companyCode,
    @JsonProperty("invoiceNumber") String invoiceNr,
    @JsonProperty("formattedInvoiceDate") String invoiceDate,
    @JsonProperty("orderNumber") String orderNr,
    @JsonProperty("claimType") String claimType,
    @JsonProperty("claimNumber") String claimNr,
    @JsonProperty("chassisNumber") String chassisNr,
    @JsonProperty("registrationNr") String registrationNr,
    @JsonProperty("repairDate") String repairDate,
    @JsonProperty("mileage") String mileage,
    @JsonProperty("customerNumber") String customerNr,
    @JsonProperty("customerName") String customerName,
    @JsonProperty("sdeClaimNr") String sdeClaimNr,
    @JsonProperty("statusCode") int statusCode,
    @JsonProperty("statusDescription") String statusText,
    @JsonProperty("demandCode") String demandCode,
    @JsonProperty("errorCount") int errorCount,
    @JsonProperty("colorIndicator") String colorIndicator
) {} // @rpg-trace: n471