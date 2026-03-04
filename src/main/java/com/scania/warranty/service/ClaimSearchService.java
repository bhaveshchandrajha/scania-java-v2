/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.dto.ClaimListItemDto;
import com.scania.warranty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for claim search and list operations.
 */
@Service
@Transactional(readOnly = true)
public class ClaimSearchService {

    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;
    private final InvoiceRepository invoiceRepository;
    private final WorkPositionRepository workPositionRepository;
    private final ExternalServiceRepository externalServiceRepository;
    private final ReleaseRequestRepository releaseRequestRepository;

    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    public ClaimSearchService(ClaimRepository claimRepository,
                              ClaimErrorRepository claimErrorRepository,
                              InvoiceRepository invoiceRepository,
                              WorkPositionRepository workPositionRepository,
                              ExternalServiceRepository externalServiceRepository,
                              ReleaseRequestRepository releaseRequestRepository) {
        this.claimRepository = claimRepository;
        this.claimErrorRepository = claimErrorRepository;
        this.invoiceRepository = invoiceRepository;
        this.workPositionRepository = workPositionRepository;
        this.externalServiceRepository = externalServiceRepository;
        this.releaseRequestRepository = releaseRequestRepository;
    }

    public List<ClaimListItemDto> searchClaims(ClaimSearchCriteria criteria) {
        List<Claim> claims = fetchClaims(criteria);

        return claims.stream()
            .filter(claim -> applyFilters(claim, criteria))
            .map(claim -> mapToListItem(claim, criteria))
            .collect(Collectors.toList());
    }

    private List<Claim> fetchClaims(ClaimSearchCriteria criteria) {
        // @origin HS1210 L830-833 (IF)
        if (criteria.ascending()) {
            // @origin HS1210 L941-941 (CHAIN)
            return claimRepository.findActiveClaimsByCompanyCodeAscending(criteria.companyCode());
        } else {
            return claimRepository.findActiveClaimsByCompanyCodeDescending(criteria.companyCode());
        }
    }

    private boolean applyFilters(Claim claim, ClaimSearchCriteria criteria) {
        // @origin HS1210 L841-844 (IF)
        if (!applyAgeFilter(claim, criteria.ageFilterDays())) {
            return false;
        }

        if (!applyClaimTypeFilter(claim, criteria.claimTypeFilter())) {
            return false;
        }

        if (criteria.openClaimsOnly() && !isOpenClaim(claim)) {
            return false;
        }

        if (!applyStatusFilter(claim, criteria.statusFilter(), criteria.statusOperator())) {
            return false;
        }

        if (!applyVehicleFilter(claim, criteria.vehicleFilter())) {
            return false;
        }

        if (!applyCustomerFilter(claim, criteria.customerFilter())) {
            return false;
        }

        if (!applySdeClaimFilter(claim, criteria.sdeClaimFilter(), criteria.minimumOnly())) {
            return false;
        }

        if (!applySearchTextFilter(claim, criteria.searchText())) {
            return false;
        }

        return true;
    }

    private boolean applyAgeFilter(Claim claim, Integer ageFilterDays) {
        if (ageFilterDays == null || ageFilterDays == 0) {
            return true;
        }

        if (claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(99) == 0) {
            return false;
        }

        try {
            LocalDate repairDate = LocalDate.parse(claim.getRepairDate(), ISO_DATE_FORMATTER);
            LocalDate currentDate = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(repairDate, currentDate);

            if (daysBetween > ageFilterDays) {
                return false;
            }
        } catch (Exception e) {
            return true;
        }

        return true;
    }

    private boolean applyClaimTypeFilter(Claim claim, String claimTypeFilter) {
        if (claimTypeFilter == null || claimTypeFilter.isBlank()) {
            return true;
        }

        // @origin HS1210 L1035-1035 (CHAIN)
        List<ClaimError> errors = claimErrorRepository.findByClaimNumber(claim.getCompanyCode(), claim.getClaimNumber());

        // @origin HS1210 L884-1012 (DOW)
        for (ClaimError error : errors) {
            String scope = determineClaimScope(error);
            // @origin HS1210 L944-953 (IF)
            if (scope != null && scope.startsWith(claimTypeFilter)) {
                return true;
            }
        }

        return false;
    }

    private boolean isOpenClaim(Claim claim) {
        if (claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(20) < 0 && claim.getStatusCodeSde().compareTo(5) != 0) {
            return true;
        }

        // @origin HS1210 L1100-1100 (CHAIN)
        List<ClaimError> errors = claimErrorRepository.findByClaimNumber(claim.getCompanyCode(), claim.getClaimNumber());

        // @origin HS1210 L908-913 (DOW)
        for (ClaimError error : errors) {
            // @origin HS1210 L959-970 (IF)
            if (error.getStatusCode() != null && error.getStatusCode().compareTo(0) == 0) {
                return true;
            }
        }

        return errors.isEmpty();
    }

    private boolean applyStatusFilter(Claim claim, String statusFilter, String statusOperator) {
        if (statusFilter == null || statusFilter.isBlank()) {
            return true;
        }

        try {
            int filterStatus = Integer.parseInt(statusFilter);
            int claimStatus = claim.getStatusCodeSde() != null ? claim.getStatusCodeSde() : 0;

            switch (statusOperator) {
                case "=":
                case "*":
                    return claimStatus == filterStatus;
                case ">":
                    return claimStatus > filterStatus;
                case "<":
                    return claimStatus < filterStatus;
                default:
                    return claimStatus == filterStatus;
            }
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean applyVehicleFilter(Claim claim, String vehicleFilter) {
        if (vehicleFilter == null || vehicleFilter.isBlank()) {
            return true;
        }
        return claim.getChassisNumber().contains(vehicleFilter);
    }

    private boolean applyCustomerFilter(Claim claim, String customerFilter) {
        if (customerFilter == null || customerFilter.isBlank()) {
            return true;
        }
        return claim.getCustomerNumber().contains(customerFilter);
    }

    private boolean applySdeClaimFilter(Claim claim, String sdeClaimFilter, boolean minimumOnly) {
        if (minimumOnly) {
            return "00000000".equals(claim.getClaimNumberSde());
        }

        if (sdeClaimFilter == null || sdeClaimFilter.isBlank()) {
            return true;
        }

        if ("00000000".equals(sdeClaimFilter)) {
            return "00000000".equals(claim.getClaimNumberSde());
        }

        return claim.getClaimNumberSde().contains(sdeClaimFilter);
    }

    private boolean applySearchTextFilter(Claim claim, String searchText) {
        if (searchText == null || searchText.isBlank()) {
            return true;
        }

        String searchLower = searchText.toLowerCase();

        if (claim.getCompanyCode().toLowerCase().contains(searchLower)) return true;
        if (claim.getClaimNumber().toLowerCase().contains(searchLower)) return true;
        if (claim.getInvoiceDate().toLowerCase().contains(searchLower)) return true;
        if (claim.getOrderNumber().toLowerCase().contains(searchLower)) return true;
        if (claim.getChassisNumber().toLowerCase().contains(searchLower)) return true;
        if (claim.getInvoiceNumber().toLowerCase().contains(searchLower)) return true;
        if (claim.getLicensePlate().toLowerCase().contains(searchLower)) return true;
        if (claim.getCustomerNumber().toLowerCase().contains(searchLower)) return true;
        if (claim.getCustomerName().toLowerCase().contains(searchLower)) return true;

        return false;
    }

    private ClaimListItemDto mapToListItem(Claim claim, ClaimSearchCriteria criteria) {
        String statusDescription = getStatusDescription(claim);
        DisplayColor displayColor = determineDisplayColor(claim);
        // @origin HS1210 L1106-1106 (CHAIN)
        int errorCount = claimErrorRepository.findByClaimNumber(claim.getCompanyCode(), claim.getClaimNumber()).size();
        String demandCode = getDemandCode(claim);

        return new ClaimListItemDto(
            claim.getCompanyCode(),
            claim.getClaimNumber(),
            claim.getInvoiceNumber(),
            formatDate(claim.getInvoiceDate()),
            claim.getOrderNumber(),
            claim.getChassisNumber(),
            claim.getCustomerNumber(),
            claim.getCustomerName(),
            claim.getStatusCodeSde() != null ? claim.getStatusCodeSde() : 0,
            errorCount,
            displayColor.getCode()
        );
    }

    private String getStatusDescription(Claim claim) {
        // @origin HS1210 L1118-1122 (IF)
        if ("00000000".equals(claim.getClaimNumberSde())) {
            if (claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(5) == 0) {
                return "Minimumantrag";
            } else if (claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(20) == 0) {
                return "Minimum ausgebucht";
            } else {
                return "Minimumantrag";
            }
        }

        ClaimStatus status = ClaimStatus.fromCode(claim.getStatusCodeSde() != null ? claim.getStatusCodeSde() : 0);
        return status != null ? status.getDescription() : "";
    }

    private DisplayColor determineDisplayColor(Claim claim) {
        // @origin HS1210 L1129-1129 (CHAIN)
        List<ClaimError> errors = claimErrorRepository.findByClaimNumber(claim.getCompanyCode(), claim.getClaimNumber());

        // @origin HS1210 L1130-1147 (IF)
        if (errors.isEmpty() && claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(20) == 0) {
            return DisplayColor.RED;
        }

        boolean hasRed = false;
        boolean hasYellow = false;
        boolean hasBlue = false;

        // @origin HS1210 L1028-1036 (DOW)
        for (ClaimError error : errors) {
            // @origin HS1210 L1152-1162 (IF)
            if (error.getStatusCode() != null && error.getStatusCode().compareTo(16) == 0) {
                hasRed = true;
            }

            if (error.getStatusCode() != null && (error.getStatusCode().compareTo(30) == 0 || error.getStatusCode().compareTo(0) == 0 && !claim.getClaimNumberSde().isBlank() ||
                claim.getClaimNumberSde().isBlank() && claim.getStatusCodeSde() != null && claim.getStatusCodeSde().compareTo(20) == 0)) {
                hasRed = true;
            }

            if (error.getStatusCode() != null && error.getStatusCode().compareTo(11) == 0) {
                hasYellow = true;
            }

            if (error.getStatusCode() != null && (error.getStatusCode().compareTo(3) == 0 || error.getStatusCode().compareTo(11) == 0)) {
                hasBlue = true;
            }
        }

        if (hasRed) return DisplayColor.RED;
        if (hasYellow) return DisplayColor.YELLOW;
        if (hasBlue) return DisplayColor.BLUE;

        return DisplayColor.NONE;
    }

    private String getDemandCode(Claim claim) {
        // @origin HS1210 L1135-1135 (CHAIN)
        List<ClaimError> errors = claimErrorRepository.findByClaimNumber(claim.getCompanyCode(), claim.getClaimNumber());
        // @origin HS1210 L1281-1291 (IF)
        if (errors.isEmpty()) {
            return "";
        }
        return errors.get(0).getMainGroup();
    }

    private String determineClaimScope(ClaimError error) {
        String mainGroup = error.getMainGroup();
        String subGroup = error.getSubGroup();
        String controlCode = error.getControlCode();
        Integer claimType = error.getClaimType();

        if ("01".equals(mainGroup) && "01".equals(subGroup)) {
            return "G";
        }

        if ("02".equals(mainGroup) && "01".equals(controlCode)) {
            return "K";
        }

        if (claimType != null && claimType == 2) {
            return "K";
        }

        return "O";
    }

    private String formatDate(String dateString) {
        if (dateString == null || dateString.length() != 8) {
            return "";
        }

        try {
            String day = dateString.substring(6, 8);
            String month = dateString.substring(4, 6);
            String year = dateString.substring(0, 4);
            return day + "." + month + "." + year;
        } catch (Exception e) {
            return dateString;
        }
    }
}