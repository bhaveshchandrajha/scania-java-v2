/**
 * Data transfer object for API or display.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 * Accepts Angular field names (openClaimsOnly, ascending) via @JsonProperty.
 */

package com.scania.warranty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scania.warranty.domain.SortDirection;

public record ClaimSearchRequestDto(
    @JsonProperty("companyCode") String companyCode,
    @JsonProperty("status") String status,
    @JsonProperty("statusCompareSign") String statusCompareSign,
    @JsonProperty("filterBranch") String filterBranch,
    @JsonProperty("filterCustomer") String filterCustomer,
    @JsonProperty("filterSde") String filterSde,
    @JsonProperty("filterArt") String filterArt,
    @JsonProperty("filterAgeDays") Integer filterAgeDays,
    @JsonProperty("openClaimsOnly") Boolean filterOpenOnly,
    @JsonProperty("filterMinimumOnly") Boolean filterMinimumOnly,
    @JsonProperty("searchString") String searchString,
    @JsonProperty("sortByClaimNr") Boolean sortByClaimNr,
    @JsonProperty("ascending") Boolean ascending
) { // @rpg-trace: n422
    public SortDirection sortDirection() {
        return Boolean.TRUE.equals(ascending) ? SortDirection.ASCENDING : SortDirection.DESCENDING;
    }
    public boolean filterOpenOnlyResolved() { return Boolean.TRUE.equals(filterOpenOnly); }
    public boolean filterMinimumOnlyResolved() { return Boolean.TRUE.equals(filterMinimumOnly); }
    public int filterAgeDaysResolved() { return filterAgeDays != null ? filterAgeDays : 0; }
}