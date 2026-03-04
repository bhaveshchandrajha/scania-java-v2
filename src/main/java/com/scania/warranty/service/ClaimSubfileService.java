/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.ClaimError;
import com.scania.warranty.domain.ClaimStatus;
import com.scania.warranty.repository.ClaimErrorRepository;
import com.scania.warranty.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for claim subfile operations (SB10N, SB100, MARK).
 */
@Service
public class ClaimSubfileService {

    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;

    @Autowired
    public ClaimSubfileService(ClaimRepository claimRepository,
                                ClaimErrorRepository claimErrorRepository) {
        this.claimRepository = claimRepository;
        this.claimErrorRepository = claimErrorRepository;
    }

    // @origin HS1210 L827-827 (SETOFF)
    public void clearSubfileIndicators() {
        // Corresponds to RPG SETOFF 50 51 52 53 54 55 56 57 58
        // In Java, this is handled by method-local state or request-scoped context
    }

    // @origin HS1210 L830-833 (IF)
    public void resetMarkFields(String mark12, String mark11) {
        // @origin HS1210 L830-833 (IF)
        if (mark12 == null || mark12.isBlank()) {
            // @origin HS1210 L830-833 (IF)
            mark12 = mark11;
            // @origin HS1210 L830-833 (IF)
            mark11 = " ";
        }
    }

    // @origin HS1210 L841-844 (IF)
    public void processMarkSelection(String mark12, String mark11, String mark22, String mark21, String sub010) {
        // @origin HS1210 L841-844 (IF)
        if (mark12 == null || mark12.isBlank()) {
            // @origin HS1210 L841-844 (IF)
            mark12 = mark11;
            // @origin HS1210 L841-844 (IF)
            mark11 = " ";
        }
        // @origin HS1210 L845-848 (IF)
        if (mark22 == null || mark22.isBlank()) {
            // @origin HS1210 L845-848 (IF)
            mark22 = mark21;
            // @origin HS1210 L845-848 (IF)
            mark21 = " ";
        }
    }

    // @origin HS1210 L864-883 (IF)
    public List<Claim> buildClaimSubfile(String pakz, boolean ascending, int filterAgeDays, String filterType, String filterOpen, String searchString, String statusFilter, String zeichen, LocalDate currentDate) {
        // @origin HS1210 L864-883 (IF)
        List<Claim> claims;
        // @origin HS1210 L864-883 (IF)
        if (ascending) {
            // @origin HS1210 L866-866 (SETLL)
            claims = claimRepository.findActiveClaimsByCompanyCodeAscending(pakz);
        // @origin HS1210 L873-873 (ELSE)
        } else {
            // @origin HS1210 L873-873 (ELSE)
            claims = claimRepository.findActiveClaimsByCompanyCodeDescending(pakz);
        }

        // @origin HS1210 L884-884 (Builtin)
        DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // @origin HS1210 L884-884 (Builtin)
        List<Claim> filteredClaims = new ArrayList<>();

        // @origin HS1210 L884-884 (Builtin)
        for (Claim claim : claims) {
            // @origin HS1210 L884-884 (Builtin)
            if (filteredClaims.size() >= 9999) {
                // @origin HS1210 L884-884 (Builtin)
                break;
            }

            // @origin HS1210 L886-892 (IF)
            if (filterAgeDays > 0 && claim.getStatusCodeSde() != 99) {
                try {
                    // @origin HS1210 L887-887 (Builtin)
                    LocalDate repDate = LocalDate.parse(String.valueOf(claim.getRepDatum()), isoFormatter);
                    // @origin HS1210 L889-891 (IF)
                    long daysDiff = ChronoUnit.DAYS.between(repDate, currentDate);
                    // @origin HS1210 L890-890 (EVAL)
                    if (daysDiff > filterAgeDays) {
                        // @origin HS1210 L890-890 (EVAL)
                        claim.setStatusCodeSde(99);
                    }
                } catch (Exception e) {
                    // Invalid date, skip filter
                }
            }

            // @origin HS1210 L894-896 (IF)
            if (filterType != null && !filterType.isBlank() && claim.getStatusCodeSde() != 99) {
                // @origin HS1210 L895-895 (EXSR)
                if (!matchesClaimType(claim, filterType)) {
                    // @origin HS1210 L895-895 (EXSR)
                    claim.setStatusCodeSde(99);
                }
            }

            // @origin HS1210 L898-898 (EVAL)
            boolean isOpen = false;
            // @origin HS1210 L899-917 (IF)
            if ("J".equals(filterOpen)) {
                // @origin HS1210 L900-902 (IF)
                if (claim.getStatusCodeSde() < 20 && claim.getStatusCodeSde() != 5) {
                    // @origin HS1210 L901-901 (EVAL)
                    isOpen = true;
                }
                // @origin HS1210 L905-907 (IF)
                List<ClaimError> errors = claimErrorRepository.findByClaimKey(claim.getPakz(), claim.getRechNr(), claim.getRechDatum(), claim.getAuftragsNr(), claim.getBereich(), claim.getClaimNr());
                // @origin HS1210 L906-906 (EVAL)
                if (errors.isEmpty()) {
                    // @origin HS1210 L906-906 (EVAL)
                    isOpen = true;
                }
                // @origin HS1210 L909-911 (IF)
                for (ClaimError error : errors) {
                    // @origin HS1210 L910-910 (EVAL)
                    if (error.getStatusCode() == 0) {
                        // @origin HS1210 L910-910 (EVAL)
                        isOpen = true;
                    }
                }
                // @origin HS1210 L915-915 (EVAL)
                if (!isOpen) {
                    // @origin HS1210 L915-915 (EVAL)
                    claim.setStatusCodeSde(99);
                }
            }

            // @origin HS1210 L919-996 (IF)
            if (claim.getStatusCodeSde() != 99) {
                // @origin HS1210 L959-970 (IF)
                if (statusFilter != null && !statusFilter.isBlank()) {
                    // @origin HS1210 L957-957 (EVAL)
                    if (zeichen == null || zeichen.isBlank()) {
                        // @origin HS1210 L957-957 (EVAL)
                        zeichen = "=";
                    }
                    // @origin HS1210 L960-963 (IF)
                    int statusCode = Integer.parseInt(statusFilter);
                    // @origin HS1210 L960-963 (IF)
                    if (("=".equals(zeichen) || "*".equals(zeichen)) && statusCode != claim.getStatusCodeSde()) {
                        // @origin HS1210 L964-966 (IF)
                        continue;
                    }
                    // @origin HS1210 L967-969 (IF)
                    if (">".equals(zeichen) && statusCode >= claim.getStatusCodeSde()) {
                        // @origin HS1210 L967-969 (IF)
                        continue;
                    }
                    // @origin HS1210 L967-969 (IF)
                    if ("<".equals(zeichen) && statusCode <= claim.getStatusCodeSde()) {
                        // @origin HS1210 L967-969 (IF)
                        continue;
                    }
                }

                // @origin HS1210 L975-995 (IF)
                if (searchString != null && !searchString.isBlank()) {
                    // @origin HS1210 L975-995 (IF)
                    String combinedFields = claim.getPakz() + claim.getAuftragsNr() + claim.getRechDatum() + claim.getClaimNrSde() + claim.getClaimNr() + claim.getRechNr() + claim.getKennzeichen() + claim.getKdNr() + claim.getKdName();
                    // @origin HS1210 L975-995 (IF)
                    if (!combinedFields.toUpperCase().contains(searchString.toUpperCase())) {
                        // @origin HS1210 L975-995 (IF)
                        continue;
                    }
                }

                // @origin HS1210 L989-989 (WRITE)
                filteredClaims.add(claim);
            }
        }

        // @origin HS1210 L1000-1011 (IF)
        return filteredClaims;
    }

    // @origin HS1210 L2751-2751 (EVAL)
    private boolean matchesClaimType(Claim claim, String filterType) {
        // @origin HS1210 L2755-2778 (DOW)
        List<ClaimError> errors = claimErrorRepository.findByClaimNumber(claim.getPakz(), claim.getClaimNr());
        // @origin HS1210 L2757-2775 (SELECT)
        for (ClaimError error : errors) {
            // @origin HS1210 L2759-2759 (WHEN)
            if ("K".equals(filterType)) {
                // Check if Kulanz (dectwf = 2 in S3F085, not available here, so simplified)
                // @origin HS1210 L2762-2762 (EVAL)
                return true;
            // @origin HS1210 L2765-2765 (WHEN)
            } else if ("G".equals(filterType)) {
                // Check if warranty scope (hauptgruppe-based logic)
                // @origin HS1210 L2766-2768 (IF)
                if (isWarrantyScope(error.getHauptgruppe())) {
                    // @origin HS1210 L2767-2767 (EVAL)
                    return true;
                }
            // @origin HS1210 L2770-2770 (OTHER)
            } else {
                // Match first character of scope
                // @origin HS1210 L2771-2771 (Builtin)
                String scope = getScope(error.getHauptgruppe());
                // @origin HS1210 L2772-2772 (EVAL)
                if (scope.startsWith(filterType)) {
                    // @origin HS1210 L2772-2772 (EVAL)
                    return true;
                }
            }
        }
        // @origin HS1210 L2781-2781 (EVAL)
        return false;
    }

    // @origin HS1210 L2766-2768 (IF)
    private boolean isWarrantyScope(String hauptgruppe) {
        // Simplified: check if hauptgruppe indicates warranty (e.g., "01", "02", etc.)
        // @origin HS1210 L2766-2768 (IF)
        return hauptgruppe != null && (hauptgruppe.equals("01") || hauptgruppe.equals("02"));
    }

    // @origin HS1210 L2771-2771 (Builtin)
    private String getScope(String hauptgruppe) {
        // Simplified: return scope based on hauptgruppe (e.g., "G" for warranty, "K" for Kulanz)
        // @origin HS1210 L2771-2771 (Builtin)
        if (hauptgruppe != null && (hauptgruppe.equals("01") || hauptgruppe.equals("02"))) {
            // @origin HS1210 L2771-2771 (Builtin)
            return "G";
        }
        // @origin HS1210 L2771-2771 (Builtin)
        return "K";
    }

    // @origin HS1210 L1025-1038 (IF)
    public void positionToNewClaim(List<Claim> claims, String newClaimNr) {
        // @origin HS1210 L1025-1038 (IF)
        if (newClaimNr != null && !newClaimNr.isBlank()) {
            // @origin HS1210 L1027-1027 (CHAIN)
            for (int i = 0; i < claims.size(); i++) {
                // @origin HS1210 L1027-1027 (CHAIN)
                Claim claim = claims.get(i);
                // @origin HS1210 L1029-1033 (IF)
                if (claim.getClaimNr().compareTo(newClaimNr) >= 0) {
                    // Position found, set record number (in UI context)
                    // @origin HS1210 L1035-1035 (CHAIN)
                    break;
                }
            }
        }
    }

    public void createClaimFromInvoice(String pakz, String rechNr, String rechDatum, String auftragsNr, String wete, String bereich, String claimNr) {
        // Placeholder implementation for creating claim from invoice
        // This method should contain the logic to create a claim based on invoice data
        throw new UnsupportedOperationException("createClaimFromInvoice not yet implemented");
    }

    public void deleteClaimAndRelatedData(String pakz, String rechNr, String rechDatum, String auftragsNr, String wete, String bereich, String claimNr) {
        // Placeholder implementation for deleting claim and related data
        // This method should contain the logic to delete a claim and its associated data
        throw new UnsupportedOperationException("deleteClaimAndRelatedData not yet implemented");
    }

    public void requestWarrantyRelease(String pakz, String rechNr, String rechDatum, String fgnr, String repDat, String auftragsNr) {
        // Placeholder implementation for requesting warranty release
        // This method should contain the logic to request a warranty release
        throw new UnsupportedOperationException("requestWarrantyRelease not yet implemented");
    }
}