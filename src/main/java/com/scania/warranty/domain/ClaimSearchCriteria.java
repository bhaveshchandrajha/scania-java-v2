/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.domain;

/**
 * Value object for claim search criteria.
 */
public record ClaimSearchCriteria(
    String companyCode,
    String statusFilter,
    String statusOperator,
    String chassisNumberFilter,
    String customerNumberFilter,
    String claimNumberSdeFilter,
    String claimTypeFilter,
    Integer ageFilterDays,
    boolean openClaimsOnly,
    String searchText,
    boolean ascending,
    String vehicleFilter,
    String customerFilter,
    String sdeClaimFilter,
    boolean minimumOnly
) {
}