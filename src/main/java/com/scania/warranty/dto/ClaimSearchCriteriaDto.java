/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.dto;

/**
 * DTO for claim search criteria.
 */
public record ClaimSearchCriteriaDto(
        Integer filterAgeDays,
        String filterClaimType,
        Boolean filterOpenOnly,
        String filterStatus,
        String filterChassisNumber,
        String filterCustomerNumber,
        String filterClaimNumberSde,
        String searchString,
        Boolean showCompensationDifference
) {}