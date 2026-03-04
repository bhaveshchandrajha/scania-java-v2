/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Service for claim management operations (create, update, delete, status change).
 */
@Service
public class ClaimManagementService {

    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;
    private final InvoiceRepository invoiceRepository;
    private final WorkPositionRepository workPositionRepository;
    private final ExternalServiceRepository externalServiceRepository;
    private final SubmissionDeadlineReleaseRepository submissionDeadlineReleaseRepository;

    @Autowired
    public ClaimManagementService(
        ClaimRepository claimRepository,
        ClaimErrorRepository claimErrorRepository,
        InvoiceRepository invoiceRepository,
        WorkPositionRepository workPositionRepository,
        ExternalServiceRepository externalServiceRepository,
        SubmissionDeadlineReleaseRepository submissionDeadlineReleaseRepository
    ) {
        this.claimRepository = claimRepository;
        this.claimErrorRepository = claimErrorRepository;
        this.invoiceRepository = invoiceRepository;
        this.workPositionRepository = workPositionRepository;
        this.externalServiceRepository = externalServiceRepository;
        this.submissionDeadlineReleaseRepository = submissionDeadlineReleaseRepository;
    }

    @Transactional
    public Claim createClaimFromInvoice(String companyCode, String invoiceNumber, String invoiceDate, String orderNumber, String workshopType) {
        // @origin HS1210 L941-941 (CHAIN)
        Optional<Invoice> invoiceOpt = invoiceRepository.findByKey(companyCode, invoiceNumber, invoiceDate, orderNumber, workshopType.substring(0, 1), workshopType.substring(1, 2), "04");
        // @origin HS1210 L830-833 (IF)
        if (invoiceOpt.isEmpty()) {
            // @origin HS1210 L895-895 (EXSR)
            throw new IllegalArgumentException("Invoice not found");
        }

        Invoice invoice = invoiceOpt.get();

        // @origin HS1210 L1027-1027 (CHAIN)
        List<Invoice> cancelledInvoices = invoiceRepository.findWarrantyInvoicesByOrderDate(companyCode, invoice.getOrderDate());
        // @origin HS1210 L841-844 (IF)
        if (!cancelledInvoices.isEmpty()) {
            // Check for cancelled status if needed
        }

        // @origin HS1210 L1035-1035 (CHAIN)
        List<Claim> existingClaims = claimRepository.findByInvoiceKey(companyCode, invoiceNumber, invoiceDate, orderNumber, workshopType.substring(1, 2));
        // @origin HS1210 L845-848 (IF)
        if (!existingClaims.isEmpty()) {
            // @origin HS1210 L926-926 (EXSR)
            throw new IllegalStateException("Claim already exists for this invoice");
        }

        String nextClaimNumber = generateNextClaimNumber(companyCode);

        Claim claim = new Claim();
        // @origin HS1210 L887-887 (EVAL)
        claim.setCompanyCode(invoice.getCompanyCode());
        claim.setInvoiceNumber(invoice.getInvoiceNumber());
        claim.setInvoiceDate(invoice.getInvoiceDate());
        claim.setOrderNumber(orderNumber);
        claim.setWorkshopType(invoice.getWorkshopType());
        claim.setClaimNumber(nextClaimNumber);
        claim.setChassisNumber(extractChassisNumber(invoice.getVehicleNumber()));
        claim.setLicensePlate(invoice.getLicensePlate());
        claim.setRegistrationDate(parseDate(invoice.getRegistrationDate()));
        claim.setRepairDate(parseDate(determineRepairDate(invoice)));
        claim.setMileage(calculateMileage(invoice.getMileage()));
        claim.setProductType(determineProductType(invoice.getVehicleNumber()));
        claim.setAttachment(" ");
        claim.setForeigner(determineForeignerStatus(invoice));
        claim.setCustomerNumber(invoice.getCustomerNumber());
        claim.setCustomerName(invoice.getName());
        claim.setClaimNumberSde(" ");
        claim.setStatusCodeSde(0);
        claim.setErrorCount(0);
        claim.setArea(invoice.getArea());
        claim.setDepartment(invoice.getArea());
        claim.setWorkshopTheke(invoice.getWorkshopType());
        claim.setSdpsOrderNumber(orderNumber + invoice.getWorkshopType() + invoice.getArea() + invoice.getSplit());

        // @origin HS1210 L860-860 (WRITE)
        Claim savedClaim = claimRepository.save(claim);

        copyWorkPositionsToClaim(invoice, savedClaim);
        copyExternalServicesToClaim(invoice, savedClaim);

        return savedClaim;
    }

    private String generateNextClaimNumber(String companyCode) {
        // @origin HS1210 L1100-1100 (CHAIN)
        Optional<String> maxClaimNumberOpt = claimRepository.findMaxClaimNumber(companyCode);
        // @origin HS1210 L864-883 (IF)
        if (maxClaimNumberOpt.isEmpty()) {
            return "10000000";
        }
        long maxClaimNumber = Long.parseLong(maxClaimNumberOpt.get());
        long nextClaimNumber = maxClaimNumber + 1;
        if (nextClaimNumber < 10000000) {
            nextClaimNumber = 10000000;
        }
        return String.valueOf(nextClaimNumber);
    }

    private String extractChassisNumber(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.length() < 7) {
            return "";
        }
        return vehicleNumber.substring(vehicleNumber.length() - 7);
    }

    private String determineRepairDate(Invoice invoice) {
        if ("1".equals(invoice.getWorkshopType())) {
            if (invoice.getSplit() != null && invoice.getSplit().length() > 1 && "4".equals(invoice.getSplit().substring(1, 2))) {
                return invoice.getCompletionDate();
            } else {
                return invoice.getAcceptanceDate();
            }
        } else {
            return invoice.getOrderDate();
        }
    }

    private Integer parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty() || "0".equals(dateString)) {
            return 0;
        }
        try {
            return Integer.parseInt(dateString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Integer calculateMileage(String mileageString) {
        if (mileageString == null || mileageString.trim().isEmpty()) {
            return 0;
        }
        try {
            long mileageValue = Long.parseLong(mileageString);
            return (int) (mileageValue / 1000);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Integer determineProductType(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isEmpty()) {
            return 1;
        }
        if (vehicleNumber.substring(0, 1).equals("M")) {
            return 3;
        }
        return 1;
    }

    private String determineForeignerStatus(Invoice invoice) {
        if (invoice.getVehicleNumber() != null && !invoice.getVehicleNumber().isEmpty()) {
            return "Y";
        }
        return " ";
    }

    private void copyWorkPositionsToClaim(Invoice invoice, Claim claim) {
        // @origin HS1210 L1106-1106 (CHAIN)
        List<WorkPosition> workPositions = workPositionRepository.findByInvoiceKey(
            invoice.getCompanyCode(),
            invoice.getInvoiceNumber(),
            invoice.getInvoiceDate(),
            invoice.getOrderNumber(),
            invoice.getArea(),
            invoice.getWorkshopType(),
            invoice.getSplit()
        );

        int positionCounter = 0;
        // @origin HS1210 L884-1012 (DOW)
        for (WorkPosition workPosition : workPositions) {
            // @origin HS1210 L944-953 (IF)
            if (workPosition.getOperationCode() != null && !workPosition.getOperationCode().isEmpty()) {
                positionCounter++;
                
                ClaimError claimError = new ClaimError();
                
                // Set basic claim information
                claimError.setCompanyCode(invoice.getCompanyCode());
                claimError.setInvoiceNumber(invoice.getInvoiceNumber());
                claimError.setInvoiceDate(invoice.getInvoiceDate());
                claimError.setOrderNumber(invoice.getOrderNumber());
                claimError.setArea(claim.getArea());
                claimError.setClaimNumber(claim.getClaimNumber());
                claimError.setErrorNumber(String.format("%02d", positionCounter));
                claimError.setSequenceNumber("00");
                
                // Set operation details
                String operationCode = workPosition.getOperationCode();
                if (operationCode.length() >= 2) {
                    claimError.setDamageCode1(operationCode.substring(0, 2));
                }
                if (operationCode.length() >= 4) {
                    claimError.setDamageCode2(operationCode.substring(2, 4));
                }
                
                claimError.setText1(workPosition.getDescription() != null ? workPosition.getDescription() : "");
                
                // Set assessment codes
                claimError.setAssessmentCode1("");
                claimError.setAssessmentCode2(0);
                claimError.setAssessmentDate(0);
                
                // Set compensation values
                claimError.setCompensatedMaterial(0);
                claimError.setCompensatedLabor(0);
                claimError.setCompensatedSpecial(0);
                
                // Set requested values
                claimError.setRequestedMaterial(BigDecimal.ZERO);
                claimError.setRequestedLabor(workPosition.getInvoiceNet() != null ? workPosition.getInvoiceNet() : BigDecimal.ZERO);
                claimError.setRequestedSpecial(BigDecimal.ZERO);
                
                // Set additional fields
                claimError.setPreviousRepairDate(0);
                claimError.setPreviousMileage(0);
                claimError.setFieldTestNumber(0);
                claimError.setCampaignNumber("");
                
                // Set error details
                claimError.setFehlerTeil("");
                claimError.setHauptgruppe("");
                claimError.setNebengruppe("");
                claimError.setText2("");
                claimError.setText3("");
                claimError.setText4("");
                claimError.setFehlerNrSde("");
                claimError.setAnhang("");
                
                // Set status and result codes
                claimError.setResultCode("");
                claimError.setStatusCode(0);
                claimError.setVariantCode(0);
                claimError.setActionCode(0);
                claimError.setClaimArt(0);
                claimError.setSteuerCode("");
                claimError.setEps("");
                claimError.setSource("");
                claimError.setComplain("");
                claimError.setSymptom("");
                claimError.setFailure("");
                claimError.setLocation("");
                claimError.setRepair("");
                claimError.setResult1("");
                claimError.setResult2("");
                claimError.setFault1("");
                claimError.setFault2("");
                claimError.setReply1("");
                claimError.setReply2("");
                claimError.setExplanation1("");
                claimError.setExplanation2("");
                
                // Save the claim error
                claimErrorRepository.save(claimError);
            }
        }
    }

    private void copyExternalServicesToClaim(Invoice invoice, Claim claim) {
        // @origin HS1210 L1118-1122 (CHAIN)
        List<ExternalService> externalServices = externalServiceRepository.findByInvoiceKey(
            invoice.getCompanyCode(),
            invoice.getInvoiceNumber(),
            invoice.getInvoiceDate(),
            invoice.getOrderNumber(),
            invoice.getArea(),
            invoice.getWorkshopType(),
            invoice.getSplit()
        );

        // @origin HS1210 L1028-1036 (DOW)
        for (ExternalService externalService : externalServices) {
            // Process external services if needed
            // This is a placeholder for future implementation
        }
    }
}