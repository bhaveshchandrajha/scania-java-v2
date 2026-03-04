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
 * Service for claim subfile operations (SB10N, SB100, MARK).
 */
@Service
@Transactional
public class ClaimSubfileService {

    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;
    private final InvoiceRepository invoiceRepository;
    private final WorkPositionRepository workPositionRepository;
    private final ExternalServiceRepository externalServiceRepository;
    private final WarrantyReleaseRepository warrantyReleaseRepository;

    @Autowired
    public ClaimSubfileService(ClaimRepository claimRepository,
                               ClaimErrorRepository claimErrorRepository,
                               InvoiceRepository invoiceRepository,
                               WorkPositionRepository workPositionRepository,
                               ExternalServiceRepository externalServiceRepository,
                               WarrantyReleaseRepository warrantyReleaseRepository) {
        this.claimRepository = claimRepository;
        this.claimErrorRepository = claimErrorRepository;
        this.invoiceRepository = invoiceRepository;
        this.workPositionRepository = workPositionRepository;
        this.externalServiceRepository = externalServiceRepository;
        this.warrantyReleaseRepository = warrantyReleaseRepository;
    }

    public void resetSubfileMarkers(String mark12, String mark11) {
        if (mark12 == null || mark12.isBlank()) {
            mark12 = mark11;
            mark11 = " ";
        }
    }

    public void moveSubfileSelection(String mark12, String mark11, String mark22, String mark21, String sub010) {
        String sub01X = sub010;
        if (mark12 == null || mark12.isBlank()) {
            mark12 = mark11;
            mark11 = " ";
        }
        if (mark22 == null || mark22.isBlank()) {
            mark22 = mark21;
            mark21 = " ";
        }
    }

    public List<ClaimListItemDto> buildClaimSubfile(String pakz, boolean ascending, Integer filterAgeDays, String filterType, String filterOpen, LocalDate currentDate) {
        List<Claim> claims;
        if (ascending) {
            // @origin HS1210 L2838-2838 (CHAIN)
            claims = claimRepository.findActiveClaimsByCompanyCodeAscending(pakz);
        } else {
            claims = claimRepository.findActiveClaimsByCompanyCodeDescending(pakz);
        }

        List<ClaimListItemDto> result = new ArrayList<>();
        // @origin HS1210 L2817-2870 (DOW)
        for (Claim claim : claims) {
            // @origin HS1210 L2840-2843 (IF)
            if (result.size() >= 9999) {
                break;
            }

            if (filterAgeDays != null && filterAgeDays > 0 && claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(99)) != 0) {
                LocalDate repairDate = parseIsoDate(String.valueOf(claim.getRepDatum()));
                if (repairDate != null) {
                    long daysDiff = ChronoUnit.DAYS.between(repairDate, currentDate);
                    if (daysDiff > filterAgeDays) {
                        claim.setStatusCodeSde(99);
                    }
                }
            }

            if (filterType != null && !filterType.isBlank() && claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(99)) != 0) {
                boolean matchesType = checkClaimType(claim, filterType);
                if (!matchesType) {
                    claim.setStatusCodeSde(99);
                }
            }

            boolean isOpen = false;
            if ("J".equals(filterOpen)) {
                if (claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(20)) < 0 && claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(5)) != 0) {
                    isOpen = true;
                }
                // @origin HS1210 L2815-2815 (CHAIN)
                List<ClaimError> errors = claimErrorRepository.findByClaimKey(claim.getPakz(), claim.getRechNr(), claim.getRechDatum(), claim.getAuftragsNr(), claim.getBereich(), claim.getClaimNr());
                if (errors.isEmpty()) {
                    isOpen = true;
                }
                for (ClaimError error : errors) {
                    if (error.getStatusCode().compareTo(BigDecimal.ZERO) == 0) {
                        isOpen = true;
                    }
                }
                if (!isOpen) {
                    claim.setStatusCodeSde(99);
                }
            }

            if (claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(99)) != 0) {
                ClaimListItemDto dto = mapToListItemDto(claim);
                result.add(dto);
            }
        }

        if (result.isEmpty()) {
            ClaimListItemDto emptyDto = new ClaimListItemDto("", "", "", "", "", "", "", "", "", "", "", 0, "");
            result.add(emptyDto);
        }

        return result;
    }

    private boolean checkClaimType(Claim claim, String filterType) {
        List<ClaimError> errors = claimErrorRepository.findByCompanyCodeAndClaimNr(claim.getPakz(), claim.getClaimNr());
        for (ClaimError error : errors) {
            if ("K".equals(filterType)) {
                return error.getHauptgruppe() != null && error.getHauptgruppe().equals("02");
            } else if ("G".equals(filterType)) {
                return error.getHauptgruppe() != null && error.getHauptgruppe().equals("01");
            } else {
                String scope = determineScope(error);
                if (filterType.equals(scope.substring(0, 1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private String determineScope(ClaimError error) {
        if (error.getHauptgruppe() != null && error.getHauptgruppe().equals("01")) {
            return "G";
        } else if (error.getHauptgruppe() != null && error.getHauptgruppe().equals("02")) {
            return "K";
        } else {
            return "X";
        }
    }

    private ClaimListItemDto mapToListItemDto(Claim claim) {
        String formattedDate = formatDate(claim.getRechDatum());
        String statusText = getStatusText(claim.getStatusCodeSde().intValue(), claim.getClaimNrSde());
        String color = determineColor(claim);
        int errorCount = claim.getAnzFehler().intValue();

        return new ClaimListItemDto(
                claim.getPakz(),
                claim.getClaimNr(),
                claim.getRechNr(),
                formattedDate,
                claim.getAuftragsNr(),
                claim.getChassisNr(),
                claim.getKdNr(),
                claim.getKdName(),
                claim.getClaimNrSde(),
                String.valueOf(claim.getStatusCodeSde().intValue()),
                statusText,
                errorCount,
                color
        );
    }

    private String getStatusText(Integer statusCode, String claimNrSde) {
        if ("00000000".equals(claimNrSde)) {
            if (statusCode == 5) {
                return "Minimumantrag";
            } else if (statusCode == 20) {
                return "Minimum ausgebucht";
            } else {
                return "Minimumantrag";
            }
        }
        return "";
    }

    private String determineColor(Claim claim) {
        boolean red = false;
        boolean yellow = false;
        boolean blue = false;

        if (!"00000000".equals(claim.getClaimNrSde())) {
            List<ClaimError> errors = claimErrorRepository.findByCompanyCodeAndClaimNr(claim.getPakz(), claim.getClaimNr());
            if (errors.isEmpty() && claim.getStatusCodeSde().compareTo(BigDecimal.valueOf(20)) == 0) {
                red = true;
            }

            for (ClaimError error : errors) {
                if (error.getStatusCode().compareTo(BigDecimal.valueOf(16)) == 0) {
                    red = true;
                }
                if ((error.getStatusCode().compareTo(BigDecimal.valueOf(30)) == 0 || error.getStatusCode().compareTo(BigDecimal.ZERO) == 0) && !claim.getClaimNrSde().isBlank()) {
                    red = true;
                }
                if (error.getStatusCode().compareTo(BigDecimal.valueOf(11)) == 0) {
                    yellow = true;
                }
                if (error.getStatusCode().compareTo(BigDecimal.valueOf(2)) > 0 && !red) {
                    yellow = true;
                }
                if (error.getStatusCode().compareTo(BigDecimal.valueOf(3)) == 0 || error.getStatusCode().compareTo(BigDecimal.valueOf(11)) == 0) {
                    blue = true;
                }
            }
        }

        StringBuilder color = new StringBuilder();
        if (red) {
            color.append("ROT");
        }
        if (yellow) {
            color.append(" GELB");
        }
        if (blue) {
            color.append(" BLAU");
        }
        return color.toString().trim();
    }

    private String formatDate(String isoDate) {
        if (isoDate == null || isoDate.length() != 8) {
            return "";
        }
        String day = isoDate.substring(6, 8);
        String month = isoDate.substring(4, 6);
        String year = isoDate.substring(0, 4);
        return day + "." + month + "." + year;
    }

    private LocalDate parseIsoDate(String isoDate) {
        if (isoDate == null || isoDate.length() != 8) {
            return null;
        }
        try {
            return LocalDate.parse(isoDate, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteClaimAndRelatedData(String pakz, String rechNr, String rechDatum, String auftragsNr, String wete, String claimNr) {
        List<Claim> claims = claimRepository.findByInvoiceKey(pakz, rechNr, rechDatum, auftragsNr, wete);
        for (Claim claim : claims) {
            if (claim.getClaimNr().equals(claimNr)) {
                claim.setStatusCodeSde(99);
                // @origin HS1210 L2877-2877 (WRITE)
                claimRepository.save(claim);

                List<ClaimError> errors = claimErrorRepository.findByClaimKey(pakz, rechNr, rechDatum, auftragsNr, claim.getBereich(), claimNr);
                for (ClaimError error : errors) {
                    claimErrorRepository.delete(error);
                }
            }
        }
    }

    public Claim createClaimFromInvoice(String pakz, String rechNr, String rechDatum, String auftragsNr, String wete) {
        Invoice invoice = invoiceRepository.findByKey(pakz, rechNr, rechDatum, auftragsNr, wete.substring(0, 1), wete.substring(1, 2), "04")
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        List<Invoice> stornoInvoices = invoiceRepository.findStornoInvoices(pakz, invoice.getAdat(), auftragsNr, wete.substring(0, 1), wete.substring(1, 2), "04", rechNr);
        if (!stornoInvoices.isEmpty()) {
            throw new IllegalArgumentException("Invoice is cancelled (Storno)");
        }

        List<Claim> existingClaims = claimRepository.findByInvoiceKeyAndNotExcluded(pakz, rechNr, rechDatum, auftragsNr, wete);
        if (!existingClaims.isEmpty()) {
            throw new IllegalArgumentException("Claim already exists for this invoice");
        }

        Claim claim = new Claim();
        claim.setPakz(invoice.getPakz());
        claim.setRechNr(invoice.getRnr());
        claim.setRechDatum(invoice.getRdat());
        claim.setAuftragsNr(invoice.getAnr());
        claim.setWete(invoice.getBerei());
        claim.setChassisNr(invoice.getFahrgNr().substring(0, 7));
        claim.setKennzeichen(invoice.getKz());
        claim.setZulDatum(BigDecimal.valueOf(parseIntDate(invoice.getZdat())));
        claim.setRepDatum(BigDecimal.valueOf(parseIntDate(invoice.getAnTag())));
        claim.setKmStand(BigDecimal.valueOf(parseKmStand(invoice.getKm())));
        claim.setProduktTyp(BigDecimal.valueOf(1));
        claim.setAnhang(" ");
        claim.setAuslaender(" ");
        claim.setKdNr(invoice.getKundenNr());
        claim.setKdName(invoice.getName());
        claim.setClaimNrSde("");
        claim.setStatusCodeSde(BigDecimal.ZERO);
        claim.setAnzFehler(BigDecimal.ZERO);
        claim.setBereich(invoice.getBerei());
        claim.setAufNr(invoice.getAnr() + invoice.getBerei() + invoice.getWt() + invoice.getSplitt());

        String maxClaimNr = claimRepository.findMaxClaimNrNotExcluded().orElse("00000000");
        int nextClaimNr = Integer.parseInt(maxClaimNr) + 1;
        claim.setClaimNr(String.format("%08d", nextClaimNr));

        claimRepository.save(claim);

        copyWorkPositionsToClaim(claim, invoice);
        copyExternalServicesToClaim(claim, invoice);

        return claim;
    }

    private void copyWorkPositionsToClaim(Claim claim, Invoice invoice) {
        List<WorkPosition> workPositions = workPositionRepository.findByInvoiceKey(invoice.getPakz(), invoice.getRnr(), invoice.getRdat(), invoice.getAnr(), invoice.getBerei(), invoice.getWt(), invoice.getSplitt());
        int positionCounter = 0;
        for (WorkPosition wp : workPositions) {
            positionCounter++;
        }
    }

    private void copyExternalServicesToClaim(Claim claim, Invoice invoice) {
        List<ExternalService> externalServices = externalServiceRepository.findByInvoiceAndPositionGreaterThan3(invoice.getPakz(), invoice.getAdat(), invoice.getAnr(), invoice.getAdat());
        for (ExternalService es : externalServices) {
            if (Integer.parseInt(es.getStatus()) > 3) {
            }
        }
    }

    private Integer parseIntDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return 0;
        }
        try {
            return Integer.parseInt(dateStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Integer parseKmStand(String kmStr) {
        if (kmStr == null || kmStr.isBlank()) {
            return 0;
        }
        try {
            int mileage = Integer.parseInt(kmStr);
            return mileage / 1000;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void requestWarrantyRelease(String pakz, String rechNr, String rechDatum, String fgnr, String repDat) {
        if (rechDatum != null && !rechDatum.isBlank() && rechNr != null && !rechNr.isBlank()) {
            WarrantyRelease existing = warrantyReleaseRepository.findByKey(pakz, rechNr, rechDatum).orElse(null);
            if (existing == null) {
                WarrantyRelease release = new WarrantyRelease();
                release.setKzl(pakz);
                release.setrNr(rechNr);
                release.setrDat(rechDatum);
                release.setFgnr(fgnr != null ? fgnr : "");
                release.setRepDat(repDat != null ? repDat : "");
                release.setStatus("");
                release.setCusNo(0);
                release.setDcNo(0);
                release.setDcFn("");
                warrantyReleaseRepository.save(release);
            }
        }
    }
}