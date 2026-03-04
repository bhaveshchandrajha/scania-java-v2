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
        // @origin HS1210 L2838-2838 (CHAIN)
        Optional<Invoice> invoiceOpt = invoiceRepository.findByKey(companyCode, invoiceNumber, invoiceDate, orderNumber, workshopType.substring(0, 1), workshopType.substring(1, 2), "04");
        // @origin HS1210 L2818-2831 (IF)
        if (invoiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Invoice not found");
        }

        Invoice invoice = invoiceOpt.get();

        // @origin HS1210 L2874-2874 (CHAIN)
        List<Invoice> cancelledInvoices = invoiceRepository.findWarrantyInvoicesByOrderDate(companyCode, invoice.getOrderDate());
        // @origin HS1210 L2832-2868 (IF)
        if (!cancelledInvoices.isEmpty()) {
            // Check for cancelled status if needed
        }

        // @origin HS1210 L2815-2815 (CHAIN)
        List<Claim> existingClaims = claimRepository.findByInvoiceKey(companyCode, invoiceNumber, invoiceDate, orderNumber, workshopType);
        // @origin HS1210 L2837-2845 (IF)
        if (!existingClaims.isEmpty()) {
            throw new IllegalStateException("Claim already exists for this invoice");
        }

        String nextClaimNumber = generateNextClaimNumber();

        Claim claim = new Claim();
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

        // @origin HS1210 L2877-2877 (WRITE)
        Claim savedClaim = claimRepository.save(claim);

        copyWorkPositionsToClaim(invoice, savedClaim);
        copyExternalServicesToClaim(invoice, savedClaim);

        return savedClaim;
    }

    private String generateNextClaimNumber() {
        Optional<String> maxClaimNumberOpt = claimRepository.findMaxClaimNumber();
        // @origin HS1210 L2839-2844 (IF)
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
        // @origin HS1210 L2817-2870 (DOW)
        for (WorkPosition workPosition : workPositions) {
            if (workPosition.getOperationCode() != null && !workPosition.getOperationCode().trim().isEmpty()) {
                positionCounter++;
                ClaimError claimError = new ClaimError();
                claimError.setCompanyCode(claim.getCompanyCode());
                claimError.setInvoiceNumber(claim.getInvoiceNumber());
                claimError.setInvoiceDate(claim.getInvoiceDate());
                claimError.setOrderNumber(claim.getOrderNumber());
                claimError.setArea(claim.getArea());
                claimError.setClaimNumber(claim.getClaimNumber());
                claimError.setErrorNumber(String.format("%02d", positionCounter));
                claimError.setSequenceNumber("00");
                claimError.setErrorPart(workPosition.getOperationCode());
                claimError.setMainGroup("");
                claimError.setSubGroup("");
                claimError.setDamageCode1("");
                claimError.setDamageCode2("");
                claimError.setText1(workPosition.getDescription());
                claimError.setText2("");
                claimError.setControlCode("");
                claimError.setAssessmentCode1("");
                claimError.setAssessmentCode2(0);
                claimError.setAssessmentDate(0);
                claimError.setCompensatedMaterial(0);
                claimError.setCompensatedLabor(100);
                claimError.setCompensatedSpecial(0);
                claimError.setRequestedMaterial(BigDecimal.ZERO);
                claimError.setRequestedLabor(workPosition.getInvoiceNet());
                claimError.setRequestedSpecial(BigDecimal.ZERO);
                claimError.setClaimType(0);
                claimError.setPreviousRepairDate(0);
                claimError.setPreviousMileage(0);
                claimError.setFieldTestNumber(0);
                claimError.setCampaignNumber("");
                claimError.setEps(workPosition.getEpsName());
                claimError.setStatusCode(0);
                claimError.setVariantCode(0);
                claimError.setActionCode(0);
                claimError.setText3("");
                claimError.setText4("");
                claimError.setErrorNumberSde("");
                claimError.setAttachment(" ");
                claimError.setSource("");
                claimError.setComplain("");
                claimError.setSymptom("");
                claimError.setFailure("");
                claimError.setLocation("");
                claimError.setRepair("");
                claimError.setResultCode("");
                claimError.setResult1("");
                claimError.setResult2("");
                claimError.setFault1("");
                claimError.setFault2("");
                claimError.setReply1("");
                claimError.setReply2("");
                claimError.setExplanation1("");
                claimError.setExplanation2("");

                claimErrorRepository.save(claimError);
            }
        }
    }

    private void copyExternalServicesToClaim(Invoice invoice, Claim claim) {
        List<ExternalService> externalServices = externalServiceRepository.findByOrderKey(
            invoice.getCompanyCode(),
            invoice.getOrderNumber(),
            invoice.getOrderDate(),
            invoice.getArea(),
            invoice.getWorkshopType(),
            invoice.getSplit()
        );

        int positionCounter = claimErrorRepository.findByClaimNumber(claim.getCompanyCode(), claim.getClaimNumber()).size();
        for (ExternalService externalService : externalServices) {
            positionCounter++;
            ClaimError claimError = new ClaimError();
            claimError.setCompanyCode(claim.getCompanyCode());
            claimError.setInvoiceNumber(claim.getInvoiceNumber());
            claimError.setInvoiceDate(claim.getInvoiceDate());
            claimError.setOrderNumber(claim.getOrderNumber());
            claimError.setArea(claim.getArea());
            claimError.setClaimNumber(claim.getClaimNumber());
            claimError.setErrorNumber(String.format("%02d", positionCounter));
            claimError.setSequenceNumber("00");
            claimError.setErrorPart(externalService.getIndicatorExternal());
            claimError.setMainGroup("");
            claimError.setSubGroup("");
            claimError.setDamageCode1("");
            claimError.setDamageCode2("");
            claimError.setText1(externalService.getDescription());
            claimError.setText2("");
            claimError.setControlCode("");
            claimError.setAssessmentCode1("");
            claimError.setAssessmentCode2(0);
            claimError.setAssessmentDate(0);
            claimError.setCompensatedMaterial(0);
            claimError.setCompensatedLabor(0);
            claimError.setCompensatedSpecial(100);
            claimError.setRequestedMaterial(BigDecimal.ZERO);
            claimError.setRequestedLabor(BigDecimal.ZERO);
            claimError.setRequestedSpecial(externalService.getSalesValue());
            claimError.setClaimType(0);
            claimError.setPreviousRepairDate(0);
            claimError.setPreviousMileage(0);
            claimError.setFieldTestNumber(0);
            claimError.setCampaignNumber("");
            claimError.setEps("");
            claimError.setStatusCode(0);
            claimError.setVariantCode(0);
            claimError.setActionCode(0);
            claimError.setText3("");
            claimError.setText4("");
            claimError.setErrorNumberSde("");
            claimError.setAttachment(" ");
            claimError.setSource("");
            claimError.setComplain("");
            claimError.setSymptom("");
            claimError.setFailure("");
            claimError.setLocation("");
            claimError.setRepair("");
            claimError.setResultCode("");
            claimError.setResult1("");
            claimError.setResult2("");
            claimError.setFault1("");
            claimError.setFault2("");
            claimError.setReply1("");
            claimError.setReply2("");
            claimError.setExplanation1("");
            claimError.setExplanation2("");

            claimErrorRepository.save(claimError);
        }
    }
}