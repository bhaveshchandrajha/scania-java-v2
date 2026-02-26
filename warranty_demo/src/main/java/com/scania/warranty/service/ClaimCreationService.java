package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClaimCreationService {
    
    private final ClaimRepository claimRepository;
    private final InvoiceRepository invoiceRepository;
    private final LaborRepository laborRepository;
    private final ExternalServiceRepository externalServiceRepository;

    public ClaimCreationService(ClaimRepository claimRepository,
                               InvoiceRepository invoiceRepository,
                               LaborRepository laborRepository,
                               ExternalServiceRepository externalServiceRepository) {
        this.claimRepository = claimRepository;
        this.invoiceRepository = invoiceRepository;
        this.laborRepository = laborRepository;
        this.externalServiceRepository = externalServiceRepository;
    }

    public Claim createClaimFromInvoice(String companyCode, String invoiceNumber, 
                                       String invoiceDate, String jobNumber, 
                                       String workshopType) {
        // Check if invoice exists
        Optional<Invoice> invoiceOpt = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            companyCode, invoiceNumber, invoiceDate
        );
        
        if (invoiceOpt.isEmpty()) {
            throw new IllegalArgumentException("Invoice not found");
        }
        
        Invoice invoice = invoiceOpt.get();

        // Idempotent: return existing claim for this invoice if present and not excluded
        Optional<Claim> existingClaim = claimRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDateAndJobNumberAndWorkshopType(
            companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType
        );
        if (existingClaim.isPresent()) {
            Claim c = existingClaim.get();
            if (c.getStatusCodeSde() == null || !c.getStatusCodeSde().equals(ClaimStatus.EXCLUDED.getCode())) {
                return c;
            }
        }

        // Create new claim
        Claim claim = new Claim();
        claim.setCompanyCode(companyCode);
        claim.setInvoiceNumber(invoiceNumber);
        claim.setInvoiceDate(invoiceDate);
        claim.setJobNumber(jobNumber);
        claim.setWorkshopType(workshopType);
        claim.setClaimNumber(generateNextClaimNumber(companyCode));
        claim.setChassisNumber(extractChassisNumber(invoice.getVehicleNumber()));
        claim.setLicensePlate(invoice.getLicensePlate());
        claim.setRegistrationDate(parseDate(invoice.getRegistrationDate()));
        claim.setRepairDate(parseRepairDate(invoice));
        claim.setMileage(calculateMileage(invoice.getKilometers()));
        claim.setProductType(determineProductType(invoice.getVehicleNumber()));
        claim.setCustomerNumber(invoice.getCustomerNumber());
        claim.setCustomerName(invoice.getName());
        claim.setClaimNumberSde("");
        claim.setStatusCodeSde(ClaimStatus.CREATED.getCode());
        claim.setNumberOfFailures(0);
        claim.setArea(invoice.getArea());
        claim.setJobNumberSdps(jobNumber + workshopType + invoice.getArea() + invoice.getSplit());

        return claimRepository.save(claim);
    }

    private String generateNextClaimNumber(String companyCode) {
        List<Claim> existing = claimRepository.findByCompanyCodeOrderByClaimNumberDesc(companyCode);
        int max = 0;
        for (Claim c : existing) {
            String n = c.getClaimNumber();
            if (n != null && n.matches("\\d{8}")) {
                try {
                    max = Math.max(max, Integer.parseInt(n));
                } catch (NumberFormatException ignored) { }
            }
        }
        return String.format("%08d", max + 1);
    }

    private String extractChassisNumber(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.length() < 7) {
            return "";
        }
        return vehicleNumber.substring(vehicleNumber.length() - 7);
    }

    private Integer parseDate(String dateString) {
        try {
            return Integer.parseInt(dateString);
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer parseRepairDate(Invoice invoice) {
        String repairDate = "1".equals(invoice.getWorkshopType()) 
            ? invoice.getAcceptanceDate() 
            : invoice.getJobDate();
        return parseDate(repairDate);
    }

    private Integer calculateMileage(String kilometers) {
        try {
            int km = Integer.parseInt(kilometers);
            return km / 1000;
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer determineProductType(String vehicleNumber) {
        // Logic to determine product type based on vehicle number
        // 1 = Truck, 2 = Bus, 3 = Engine, 4 = Trailer
        return 1; // Default to Truck
    }
}