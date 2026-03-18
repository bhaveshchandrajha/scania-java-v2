/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.dto.*;
import com.scania.warranty.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClaimSearchService {

    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;
    private final ExternalServiceRepository externalServiceRepository;
    private final InvoiceRepository invoiceRepository;
    private final ClaimReleaseRequestRepository claimReleaseRequestRepository;
    private final LaborRepository laborRepository;

    public ClaimSearchService(ClaimRepository claimRepository,
                              ClaimErrorRepository claimErrorRepository,
                              ExternalServiceRepository externalServiceRepository,
                              InvoiceRepository invoiceRepository,
                              ClaimReleaseRequestRepository claimReleaseRequestRepository,
                              LaborRepository laborRepository) {
        this.claimRepository = claimRepository; // @rpg-trace: n422
        this.claimErrorRepository = claimErrorRepository;
        this.externalServiceRepository = externalServiceRepository;
        this.invoiceRepository = invoiceRepository;
        this.claimReleaseRequestRepository = claimReleaseRequestRepository;
        this.laborRepository = laborRepository;
    }

    @Transactional(readOnly = true)
    public List<ClaimListItemDto> searchClaims(ClaimSearchCriteria criteria) {
        List<Claim> claims; // @rpg-trace: n428
        SortDirection dir = criteria.sortDirection() != null ? criteria.sortDirection() : SortDirection.ASCENDING;
        if (dir == SortDirection.ASCENDING) {
            claims = claimRepository.findByCompanyCodeAsc(criteria.companyCode()); // @rpg-trace: n429
        } else {
            claims = claimRepository.findByCompanyCodeDesc(criteria.companyCode()); // @rpg-trace: n433
        }

        return claims.stream()
            .filter(claim -> applyAgeFilter(claim, criteria.filterAgeDays())) // @rpg-trace: n439
            .filter(claim -> applyArtFilter(claim, criteria.filterArt())) // @rpg-trace: n447
            .filter(claim -> applyOpenFilter(claim, criteria.filterOpenOnly())) // @rpg-trace: n452
            .filter(claim -> claim.getG71170() != ClaimStatus.EXCLUDED.getCode()) // @rpg-trace: n471
            .filter(claim -> applyStatusFilter(claim, criteria.status(), criteria.statusCompareSign())) // @rpg-trace: n490
            .filter(claim -> applySearchFilter(claim, criteria.searchString())) // @rpg-trace: n501
            .filter(claim -> applyBranchFilter(claim, criteria.filterBranch(), criteria.companyCode())) // @rpg-trace: n503
            .filter(claim -> applyCustomerFilter(claim, criteria.filterCustomer())) // @rpg-trace: n505
            .filter(claim -> applySdeFilter(claim, criteria.filterSde())) // @rpg-trace: n506
            .map(claim -> mapToListItem(claim, criteria.companyCode())) // @rpg-trace: n509
            .limit(9999) // @rpg-trace: n436
            .collect(Collectors.toList());
    }

    private boolean applyAgeFilter(Claim claim, int filterAgeDays) {
        if (filterAgeDays == 0 || claim.getG71170() == ClaimStatus.EXCLUDED.getCode()) { // @rpg-trace: n439
            return true;
        }
        try {
            String repairDateStr = String.valueOf(claim.getG71090()); // @rpg-trace: n440
            LocalDate repairDate = LocalDate.parse(repairDateStr, DateTimeFormatter.BASIC_ISO_DATE); // @rpg-trace: n441
            long daysBetween = ChronoUnit.DAYS.between(repairDate, LocalDate.now()); // @rpg-trace: n442
            if (daysBetween > filterAgeDays) { // @rpg-trace: n443
                return false;
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }

    private boolean applyArtFilter(Claim claim, String filterArt) {
        if (filterArt == null || filterArt.isBlank() || claim.getG71170() == ClaimStatus.EXCLUDED.getCode()) { // @rpg-trace: n447
            return true;
        }
        List<ClaimError> errors = claimErrorRepository.findByCompanyAndClaimNr(claim.getG71000(), claim.getG71050()); // @rpg-trace: n1744
        boolean found = false; // @rpg-trace: n1743
        for (ClaimError error : errors) { // @rpg-trace: n1745
            if ("K".equals(filterArt)) { // @rpg-trace: n1750
                found = true; // simplified - in RPG checks S3F085 DECTWF=2
                break;
            } else if ("G".equals(filterArt)) { // @rpg-trace: n1757
                found = true; // simplified - in RPG calls isWarrScope
                break;
            } else { // @rpg-trace: n1762
                if (filterArt.equals(error.getG73140() != null ? error.getG73140().substring(0, 1) : "")) { // @rpg-trace: n1763
                    found = true; // @rpg-trace: n1765
                    break;
                }
            }
        }
        if (!found) { // @rpg-trace: n1770
            return false; // @rpg-trace: n1771
        }
        return true;
    }

    private boolean applyOpenFilter(Claim claim, boolean filterOpenOnly) {
        if (!filterOpenOnly) { // @rpg-trace: n452
            return true;
        }
        boolean open = false; // @rpg-trace: n451
        if (claim.getG71170() < 20 && claim.getG71170() != ClaimStatus.MINIMUM.getCode()) { // @rpg-trace: n453
            open = true; // @rpg-trace: n454
        }
        List<ClaimError> errors = claimErrorRepository.findByCompanyAndClaimNr(claim.getG71000(), claim.getG71050()); // @rpg-trace: n457
        if (errors.isEmpty()) { // @rpg-trace: n458
            open = true; // @rpg-trace: n459
        }
        for (ClaimError error : errors) { // @rpg-trace: n461
            if (error.getG73290() == 0) { // @rpg-trace: n463
                open = true; // @rpg-trace: n464
            }
        }
        if (!open) { // @rpg-trace: n467
            return false; // @rpg-trace: n468
        }
        return true;
    }

    private boolean applyStatusFilter(Claim claim, String status, String compareSign) {
        if (status == null || status.isBlank()) { // @rpg-trace: n490
            return true;
        }
        String sign = (compareSign == null || compareSign.isBlank()) ? "=" : compareSign; // @rpg-trace: n488
        int statusNum;
        try {
            statusNum = Integer.parseInt(status.trim()); // @rpg-trace: n487
        } catch (NumberFormatException e) {
            return true;
        }
        if (("=".equals(sign) || "*".equals(sign)) && statusNum != claim.getG71170()) { // @rpg-trace: n491
            return false;
        }
        if (">".equals(sign) && statusNum >= claim.getG71170()) { // @rpg-trace: n493
            return false;
        }
        if ("<".equals(sign) && statusNum <= claim.getG71170()) { // @rpg-trace: n495
            return false;
        }
        return true;
    }

    private boolean applySearchFilter(Claim claim, String searchString) {
        if (searchString == null || searchString.isBlank()) { // @rpg-trace: n501
            return true;
        }
        String combined = (nullSafe(claim.getG71000()) + nullSafe(claim.getG71030()) + nullSafe(claim.getG71020()) +
                          nullSafe(claim.getG71160()) + nullSafe(claim.getG71050()) + nullSafe(claim.getG71010()) +
                          nullSafe(claim.getG71060()) + nullSafe(claim.getG71140()) + nullSafe(claim.getG71150())).toUpperCase(); // @rpg-trace: n501
        return combined.contains(searchString.toUpperCase());
    }

    private boolean applyBranchFilter(Claim claim, String filterBranch, String companyCode) {
        if (filterBranch == null || filterBranch.isBlank() || filterBranch.equals(companyCode)) { // @rpg-trace: n503
            return true;
        }
        return filterBranch.equals(claim.getG71000()); // @rpg-trace: n504
    }

    private boolean applyCustomerFilter(Claim claim, String filterCustomer) {
        if (filterCustomer == null || filterCustomer.isBlank()) { // @rpg-trace: n505
            return true;
        }
        return filterCustomer.equals(claim.getG71140());
    }

    private boolean applySdeFilter(Claim claim, String filterSde) {
        if (filterSde == null || filterSde.isBlank()) { // @rpg-trace: n506
            return true;
        }
        return filterSde.equals(claim.getG71160());
    }

    private ClaimListItemDto mapToListItem(Claim claim, String companyCode) {
        String statusText = resolveStatusText(claim); // @rpg-trace: n476
        String demandCode = resolveDemandCode(claim); // @rpg-trace: n507
        ColorResult colorResult = determineColor(claim); // @rpg-trace: n508

        return new ClaimListItemDto(
            claim.getG71000(), // @rpg-trace: n472
            claim.getG71010(),
            claim.getG71020(),
            claim.getG71030(),
            claim.getG71040(),
            claim.getG71050(),
            claim.getG71060(),
            claim.getG71070(),
            String.valueOf(claim.getG71090()),
            String.valueOf(claim.getG71100()),
            claim.getG71140(),
            claim.getG71150(),
            claim.getG71160(),
            claim.getG71170(),
            statusText,
            demandCode,
            colorResult.errorCount,
            colorResult.color
        );
    }

    private String resolveStatusText(Claim claim) {
        if ("00000000".equals(claim.getG71160())) { // @rpg-trace: n476
            if (claim.getG71170() == ClaimStatus.MINIMUM.getCode()) { // @rpg-trace: n478
                return "Minimumantrag"; // @rpg-trace: n479
            } else if (claim.getG71170() == ClaimStatus.APPROVED.getCode()) { // @rpg-trace: n480
                return "Minimum ausgebucht"; // @rpg-trace: n481
            } else {
                return "Minimumantrag"; // @rpg-trace: n483
            }
        }
        return "Status " + claim.getG71170(); // @rpg-trace: n475
    }

    private String resolveDemandCode(Claim claim) {
        List<ClaimError> errors = claimErrorRepository.findByCompanyAndClaimNr(claim.getG71000(), claim.getG71050()); // @rpg-trace: n1732
        if (!errors.isEmpty()) { // @rpg-trace: n1733
            return errors.get(0).getG73140(); // @rpg-trace: n1736
        }
        return ""; // @rpg-trace: n1731
    }

    private ColorResult determineColor(Claim claim) {
        boolean red = false; // @rpg-trace: n1577
        boolean yellow = false; // @rpg-trace: n1578
        boolean blue = false; // @rpg-trace: n1579
        int errorCount = 0; // @rpg-trace: n1580

        if (!"00000000".equals(claim.getG71160()) && claim.getG71160() != null && !claim.getG71160().isBlank()) { // @rpg-trace: n1581
            List<ClaimError> errors = claimErrorRepository.findByCompanyAndClaimNr(claim.getG71000(), claim.getG71050()); // @rpg-trace: n1585

            if (errors.isEmpty() && claim.getG71170() == ClaimStatus.APPROVED.getCode()) { // @rpg-trace: n1586
                red = true; // @rpg-trace: n1588
            }

            for (ClaimError error : errors) { // @rpg-trace: n1590
                if (error.getG73290() == 16) { // @rpg-trace: n1593
                    red = true; // @rpg-trace: n1594
                }
                if (error.getG73290() == 30 || error.getG73290() == 0) { // @rpg-trace: n1596
                    red = true; // @rpg-trace: n1597
                }
                if (error.getG73290() == 11) { // @rpg-trace: n1605
                    yellow = true; // @rpg-trace: n1606
                }
                if (error.getG73290() == 3 || error.getG73290() == 11) { // @rpg-trace: n1638
                    blue = true; // @rpg-trace: n1639
                }
                errorCount++; // @rpg-trace: n1642
            }
        }

        String color = ""; // @rpg-trace: n1649
        if (red) { // @rpg-trace: n1650
            color = "ROT"; // @rpg-trace: n1651
        }
        if (yellow) { // @rpg-trace: n1653
            color = color.isEmpty() ? "GELB" : color + " GELB"; // @rpg-trace: n1654
        }
        if (blue && !red) { // @rpg-trace: n1656
            color = color.isEmpty() ? "BLAU" : color + " BLAU"; // @rpg-trace: n1657
        }
        return new ColorResult(color, errorCount);
    }

    @Transactional
    public void deleteClaim(String companyCode, String claimNr) {
        Optional<Claim> claimOpt = claimRepository.findByCompanyAndClaimNr(companyCode, claimNr); // @rpg-trace: n587
        if (claimOpt.isPresent()) {
            Claim claim = claimOpt.get();
            claim.setG71170(ClaimStatus.EXCLUDED.getCode()); // @rpg-trace: n589
            claimRepository.save(claim); // @rpg-trace: n589
            claimErrorRepository.deleteByG73000AndG73050(companyCode, claimNr); // @rpg-trace: n592
        }
    }

    @Transactional
    public boolean updateClaimStatus(String companyCode, String claimNr, int currentStatus, int newStatus) {
        Optional<Claim> claimOpt = claimRepository.findByCompanyAndClaimNr(companyCode, claimNr); // @rpg-trace: n653
        if (claimOpt.isPresent()) { // @rpg-trace: n654
            Claim claim = claimOpt.get();
            if (claim.getG71170() == currentStatus) { // @rpg-trace: n655
                claim.setG71170(newStatus); // @rpg-trace: n658
                claimRepository.save(claim); // @rpg-trace: n658
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean bookMinimumClaim(String companyCode, String claimNr) {
        Optional<Claim> claimOpt = claimRepository.findByCompanyAndClaimNr(companyCode, claimNr); // @rpg-trace: n1654
        if (claimOpt.isPresent()) { // @rpg-trace: n1664
            Claim claim = claimOpt.get();
            if (claim.getG71170() == ClaimStatus.MINIMUM.getCode()) { // @rpg-trace: n1665
                claim.setG71170(ClaimStatus.APPROVED.getCode()); // @rpg-trace: n1670
                claimRepository.save(claim); // @rpg-trace: n1671
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void createClaimReleaseRequest(String companyCode, String invoiceNr, String invoiceDate) {
        Optional<ClaimReleaseRequest> existing = claimReleaseRequestRepository
            .findByG70KzlAndG70RnrAndG70Rdat(companyCode, invoiceNr, invoiceDate); // @rpg-trace: n1707

        if (existing.isEmpty()) { // @rpg-trace: n1708
            ClaimReleaseRequest request = new ClaimReleaseRequest(); // @rpg-trace: n1711
            request.setG70Kzl(companyCode); // @rpg-trace: n1711
            request.setG70Rnr(invoiceNr); // @rpg-trace: n1712
            request.setG70Rdat(invoiceDate); // @rpg-trace: n1713
            request.setG70Fgnr(""); // @rpg-trace: n1714
            request.setG70Dat(""); // @rpg-trace: n1715
            request.setG70Status(""); // @rpg-trace: n1722
            request.setG70Cusno(java.math.BigDecimal.ZERO);
            request.setG70Clmno(java.math.BigDecimal.ZERO);
            request.setG70Clmfl("");
            claimReleaseRequestRepository.save(request); // @rpg-trace: n1723
        }
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

    private record ColorResult(String color, int errorCount) {}
}