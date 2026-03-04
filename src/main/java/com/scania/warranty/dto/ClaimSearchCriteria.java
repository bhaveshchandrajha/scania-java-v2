/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * Value object for claim search criteria.
 */
public record ClaimSearchCriteria(
    String companyCode,
    String statusFilter,
    String statusOperator,
    String chassisFilter,
    String customerFilter,
    String sdeClaimFilter,
    String claimTypeFilter,
    boolean openClaimsOnly,
    int claimAgeDays,
    String searchString,
    boolean sortAscending,
    boolean sortByClaimNumber
) {}