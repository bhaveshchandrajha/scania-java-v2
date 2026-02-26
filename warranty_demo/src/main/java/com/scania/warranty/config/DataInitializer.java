package com.scania.warranty.config;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.ClaimStatus;
import com.scania.warranty.domain.Invoice;
import com.scania.warranty.repository.ClaimRepository;
import com.scania.warranty.repository.InvoiceRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds one demo Invoice and one Claim so that:
 * - POST /api/claims with companyCode=001, invoiceNumber=12345, invoiceDate=20240115 succeeds
 * - GET /api/claims/search?companyCode=001 returns at least one row for the HS1210D UI
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private static final String SEED_COMPANY = "001";
    private static final String SEED_INVOICE_NUMBER = "12345";
    private static final String SEED_INVOICE_DATE = "20240115";
    private static final String SEED_JOB_NUMBER = "001";
    private static final String SEED_WORKSHOP_TYPE = "A";

    private final InvoiceRepository invoiceRepository;
    private final ClaimRepository claimRepository;

    public DataInitializer(InvoiceRepository invoiceRepository, ClaimRepository claimRepository) {
        this.invoiceRepository = invoiceRepository;
        this.claimRepository = claimRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                SEED_COMPANY, SEED_INVOICE_NUMBER, SEED_INVOICE_DATE).isEmpty()) {
            Invoice inv = new Invoice();
            inv.setCompanyCode(SEED_COMPANY);
            inv.setInvoiceNumber(SEED_INVOICE_NUMBER);
            inv.setInvoiceDate(SEED_INVOICE_DATE);
            inv.setJobNumber(SEED_JOB_NUMBER);
            inv.setWorkshopType(SEED_WORKSHOP_TYPE);
            inv.setArea("1");
            inv.setSplit("04");
            inv.setJobDate(SEED_INVOICE_DATE);
            inv.setAcceptanceDate(SEED_INVOICE_DATE);
            inv.setVehicleNumber("00000001234567");
            inv.setLicensePlate("ABC123");
            inv.setRegistrationDate("20230101");
            inv.setName("Demo Customer");
            inv.setCustomerNumber("100001");
            inv.setKilometers("45000");
            invoiceRepository.save(inv);
        }

        if (claimRepository.findByCompanyCodeOrderByClaimNumberAsc(SEED_COMPANY).isEmpty()) {
            Claim claim = new Claim();
            claim.setCompanyCode(SEED_COMPANY);
            claim.setInvoiceNumber(SEED_INVOICE_NUMBER);
            claim.setInvoiceDate(SEED_INVOICE_DATE);
            claim.setJobNumber(SEED_JOB_NUMBER);
            claim.setWorkshopType(SEED_WORKSHOP_TYPE);
            claim.setClaimNumber("00000001");
            claim.setChassisNumber("1234567");
            claim.setLicensePlate("ABC123");
            claim.setRegistrationDate(20230101);
            claim.setRepairDate(20240115);
            claim.setMileage(45);
            claim.setProductType(1);
            claim.setCustomerNumber("100001");
            claim.setCustomerName("Demo Customer");
            claim.setClaimNumberSde("");
            claim.setStatusCodeSde(ClaimStatus.CREATED.getCode());
            claim.setNumberOfFailures(0);
            claim.setArea("1");
            claim.setJobNumberSdps(SEED_JOB_NUMBER + SEED_WORKSHOP_TYPE + "1" + "04");
            claimRepository.save(claim);
        }
    }
}
