package com.scania.warranty.config;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.ClaimError;
import com.scania.warranty.domain.ClaimStatus;
import com.scania.warranty.domain.Invoice;
import com.scania.warranty.repository.ClaimErrorRepository;
import com.scania.warranty.repository.ClaimRepository;
import com.scania.warranty.repository.InvoiceRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

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
    private static final String SEED_AREA = "1";
    private static final String SEED_SPLIT = "04";

    private static final String SEED_FEHLER_NR = "01";
    private static final String SEED_FOLGE_NR = "01";

    private final InvoiceRepository invoiceRepository;
    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;

    public DataInitializer(InvoiceRepository invoiceRepository, ClaimRepository claimRepository,
                           ClaimErrorRepository claimErrorRepository) {
        this.invoiceRepository = invoiceRepository;
        this.claimRepository = claimRepository;
        this.claimErrorRepository = claimErrorRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            seedData();
        } catch (Exception e) {
            // Demo data is optional; app can run without it
            System.err.println("DataInitializer: Could not seed demo data: " + e.getMessage());
        }
    }

    private void seedData() {
        if (invoiceRepository.findByKey(
                SEED_COMPANY, SEED_INVOICE_NUMBER, SEED_INVOICE_DATE, SEED_JOB_NUMBER, SEED_AREA, SEED_WORKSHOP_TYPE, SEED_SPLIT).isEmpty()) {
            Invoice inv = new Invoice();
            inv.setCompanyCode(SEED_COMPANY);
            inv.setInvoiceNumber(SEED_INVOICE_NUMBER);
            inv.setInvoiceDate(SEED_INVOICE_DATE);
            inv.setOrderNumber(SEED_JOB_NUMBER);
            inv.setWorkshopType(SEED_WORKSHOP_TYPE);
            inv.setArea(SEED_AREA);
            inv.setSplit(SEED_SPLIT);
            inv.setOrderDate(SEED_INVOICE_DATE);
            inv.setAcceptanceDate(SEED_INVOICE_DATE);
            inv.setVehicleNumber("00000001234567");
            inv.setLicensePlate("ABC123");
            inv.setRegistrationDate("20230101");
            inv.setName("Demo Customer");
            inv.setCustomerNumber("100001");
            inv.setMileage("45000");
            inv.setCompletionDate(SEED_INVOICE_DATE);
            inv.setAnTag(SEED_INVOICE_DATE);
            inv.setAnZeit("0000");
            inv.setFertTag(SEED_INVOICE_DATE);
            inv.setFertZeit("0000");
            inv.setBerater("");
            inv.setLeitzahl("000");
            inv.setTxAnf("000");
            inv.setTxEnde("000");
            inv.setAnrede("");
            inv.setRgNr10A(SEED_INVOICE_NUMBER);
            inv.setAdat(SEED_INVOICE_DATE);
            inv.setAtext("");
            inv.setlRnr("");
            inv.setStoBezRe("");
            inv.setStoBezRedat("");
            inv.setKorBezRe("");
            inv.setKorBezRedat("");
            inv.setBfort("");
            inv.setMwstYN("");
            inv.setMwstPercent(BigDecimal.ZERO);
            inv.setMwstPercentR(BigDecimal.ZERO);
            inv.setBaSchluessel("");
            inv.setKstLohn("");
            inv.setKstTeile("");
            inv.setFibuMwst("");
            inv.setFibuMwstAt("");
            inv.setFibuInterim("");
            inv.setKtoIntauf("");
            inv.setKtrIntAuf("");
            inv.setKstIntAuf("");
            inv.setSpezCode("");
            inv.setBranch("");
            inv.setProdCode("");
            inv.setProjekt("");
            inv.setDokumentennummer("");
            inv.setKostencodeKonzint("");
            inv.setKundenNr("");
            inv.setBranche("");
            inv.setMatch("");
            inv.setStrasse("");
            inv.setLand("");
            inv.setKzS("");
            invoiceRepository.save(inv);
        }

        List<Claim> existingClaims = claimRepository.findActiveClaimsByCompanyCodeAscending(SEED_COMPANY);
        if (existingClaims.isEmpty()) {
            Claim claim = new Claim();
            claim.setCompanyCode(SEED_COMPANY);
            claim.setInvoiceNumber(SEED_INVOICE_NUMBER);
            claim.setInvoiceDate(SEED_INVOICE_DATE);
            claim.setOrderNumber(SEED_JOB_NUMBER);
            claim.setWete(SEED_WORKSHOP_TYPE);
            claim.setClaimNr("00000001");
            claim.setChassisNr("1234567");
            claim.setKennzeichen("ABC123");
            claim.setZulDatum(BigDecimal.valueOf(20230101));
            claim.setRepDatum(BigDecimal.valueOf(20240115));
            claim.setKmStand(BigDecimal.valueOf(45));
            claim.setProduktTyp(BigDecimal.valueOf(1));
            claim.setKdNr("100001");
            claim.setKdName("Demo Customer");
            claim.setClaimNrSde("");
            claim.setStatusCodeSde(ClaimStatus.CREATED.getCode());
            claim.setAnzFehler(BigDecimal.ZERO);
            claim.setBereich(SEED_AREA);
            claim.setAufNr(SEED_JOB_NUMBER + SEED_WORKSHOP_TYPE + SEED_AREA + SEED_SPLIT);
            claimRepository.save(claim);

            // Seed ClaimError (HSG73PF) - one failure detail for the demo claim
            ClaimError err = new ClaimError();
            err.setPakz(SEED_COMPANY);
            err.setRechNr(SEED_INVOICE_NUMBER);
            err.setRechDatum(SEED_INVOICE_DATE);
            err.setAuftragsNr(SEED_JOB_NUMBER);
            err.setBereich(SEED_AREA);
            err.setClaimNr("00000001");
            err.setFehlerNr(SEED_FEHLER_NR);
            err.setFolgeNr(SEED_FOLGE_NR);
            err.setFehlerTeil("");
            err.setHauptgruppe("01");
            err.setNebengruppe("01");
            err.setSchadC1("");
            err.setSchadC2("");
            err.setText1("Demo failure description");
            err.setText2("");
            err.setSteuerCode("");
            err.setBewCode1("");
            err.setBewCode2(BigDecimal.ZERO);
            err.setBewDatum(BigDecimal.ZERO);
            err.setVergMat(BigDecimal.ZERO);
            err.setVergArb(BigDecimal.ZERO);
            err.setVergSpez(BigDecimal.ZERO);
            err.setBeantrMat(BigDecimal.ZERO);
            err.setBeantrArb(BigDecimal.ZERO);
            err.setBeantrSpez(BigDecimal.ZERO);
            err.setClaimArt(BigDecimal.ZERO);
            err.setvRepDatum(BigDecimal.ZERO);
            err.setvKmStand(BigDecimal.ZERO);
            err.setFeldtestNr(BigDecimal.ZERO);
            err.setKampagnenNr("");
            err.setEps("");
            err.setStatusCode(BigDecimal.ZERO);
            err.setVariantCode(BigDecimal.ZERO);
            err.setActionCode(BigDecimal.ZERO);
            err.setText3("");
            err.setText4("");
            err.setFehlerNrSde("");
            err.setAnhang("");
            err.setSource("");
            err.setComplain("");
            err.setSymptom("");
            err.setFailure("");
            err.setLocation("");
            err.setRepair("");
            err.setErgCode("");
            err.setResult1("");
            err.setResult2("");
            err.setFault1("");
            err.setFault2("");
            err.setReply1("");
            err.setReply2("");
            err.setExplanation1("");
            err.setExplanation2("");
            claimErrorRepository.save(err);
        }
    }
}