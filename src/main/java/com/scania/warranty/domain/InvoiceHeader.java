/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * JPA entity for invoice header (HSAHKLF3).
 */
@Entity
@Table(name = "HSAHKLF3")
public class InvoiceHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PAKZ", length = 3)
    private String companyCode;

    @Column(name = "RNR", length = 5)
    private String invoiceNumber;

    @Column(name = "RG-NR. 10A", length = 10)
    private String invoiceNumber10;

    @Column(name = "RDAT", length = 8)
    private String invoiceDate;

    @Column(name = "KZ S", length = 1)
    private String stornoIndicator;

    @Column(name = "ANR", length = 5)
    private String orderNumber;

    @Column(name = "BEREI", length = 1)
    private String department;

    @Column(name = "W/T", length = 1)
    private String workshopTheke;

    @Column(name = "SPLITT", length = 2)
    private String split;

    @Column(name = "ADAT", length = 8)
    private String orderDate;

    @Column(name = "ATEXT", length = 40)
    private String orderText;

    @Column(name = "L.RNR", length = 5)
    private String lastInvoiceNumber;

    @Column(name = "STO-BEZ-RE", length = 5)
    private String stornoReferenceInvoice;

    @Column(name = "STO-BEZ-REDAT", length = 8)
    private String stornoReferenceDate;

    @Column(name = "KOR-BEZ-RE", length = 5)
    private String correctionReferenceInvoice;

    @Column(name = "KOR-BEZ-REDAT", length = 8)
    private String correctionReferenceDate;

    @Column(name = "BFORT", length = 1)
    private String carryForward;

    @Column(name = "MWST Y/N", length = 1)
    private String vatIndicator;

    @Column(name = "MWST %", precision = 5, scale = 2)
    private BigDecimal vatPercent;

    @Column(name = "MWST % R.", precision = 5, scale = 2)
    private BigDecimal vatPercentReduced;

    @Column(name = "BA-SCHLÜSSEL", length = 2)
    private String accountingKey;

    @Column(name = "KST LOHN", length = 5)
    private String costCenterLabor;

    @Column(name = "KST TEILE", length = 5)
    private String costCenterParts;

    @Column(name = "FIBU MWST", length = 6)
    private String glAccountVat;

    @Column(name = "FIBU MWST AT", length = 6)
    private String glAccountVatAustria;

    @Column(name = "FIBU INTERIM", length = 6)
    private String glAccountInterim;

    @Column(name = "KTO INTAUF.", length = 6)
    private String accountInternalOrder;

    @Column(name = "KTR INT AUF.", length = 7)
    private String costCenterInternalOrder;

    @Column(name = "KST INT AUF.", length = 5)
    private String costCenterInternalOrderShort;

    @Column(name = "SPEZ-CODE", length = 10)
    private String specialCode;

    @Column(name = "BRANCH", length = 3)
    private String branch;

    @Column(name = "PROD-CODE", length = 10)
    private String productCode;

    @Column(name = "PROJEKT", length = 10)
    private String project;

    @Column(name = "DOKUMENTENNUMMER", length = 20)
    private String documentNumber;

    @Column(name = "KOSTENCODE KONZINT.", length = 3)
    private String costCodeConcern;

    @Column(name = "KUNDEN-NR.", length = 6)
    private String customerNumber;

    @Column(name = "ANREDE", length = 1)
    private String salutation;

    @Column(name = "NAME", length = 30)
    private String name;

    @Column(name = "BRANCHE", length = 25)
    private String industry;

    @Column(name = "MATCH", length = 5)
    private String matchCode;

    @Column(name = "STRASSE", length = 25)
    private String street;

    @Column(name = "LAND", length = 3)
    private String country;

    @Column(name = "PLZ", length = 5)
    private String postalCode;

    @Column(name = "ORT", length = 20)
    private String city;

    @Column(name = "TELEFON", length = 17)
    private String phone;

    @Column(name = "BESTELLER KUNDE", length = 20)
    private String orderingCustomer;

    @Column(name = "VALUTA", length = 1)
    private String currency;

    @Column(name = "BONIT#T", length = 1)
    private String creditRating;

    @Column(name = "ZAHLUNGSART", length = 1)
    private String paymentMethod;

    @Column(name = "RC", length = 3)
    private String responsibilityCenter;

    @Column(name = "RE KUNDEN-NR.", length = 6)
    private String invoiceCustomerNumber;

    @Column(name = "RE ANREDE", length = 1)
    private String invoiceSalutation;

    @Column(name = "RE NAME", length = 30)
    private String invoiceName;

    @Column(name = "RE BRANCHE", length = 25)
    private String invoiceIndustry;

    @Column(name = "RE MATCH", length = 5)
    private String invoiceMatchCode;

    @Column(name = "RE STRASSE", length = 25)
    private String invoiceStreet;

    @Column(name = "RE LAND", length = 3)
    private String invoiceCountry;

    @Column(name = "RE PLZ", length = 5)
    private String invoicePostalCode;

    @Column(name = "RE ORT", length = 20)
    private String invoiceCity;

    @Column(name = "RE TELE.", length = 17)
    private String invoicePhone;

    @Column(name = "RE VALUTA", length = 1)
    private String invoiceCurrency;

    @Column(name = "RE BONIT#T", length = 1)
    private String invoiceCreditRating;

    @Column(name = "RE ZART", length = 1)
    private String invoicePaymentMethod;

    @Column(name = "RE RC", length = 3)
    private String invoiceResponsibilityCenter;

    @Column(name = "UST-ID-NR/OK", length = 20)
    private String vatIdNumber;

    @Column(name = "FAHRG.-NR.", length = 17)
    private String vehicleNumber;

    @Column(name = "KZ", length = 12)
    private String licensePlate;

    @Column(name = "TYP", length = 15)
    private String vehicleType;

    @Column(name = "BJ", length = 4)
    private String buildYear;

    @Column(name = "ZDAT", length = 8)
    private String registrationDate;

    @Column(name = "WRG.", length = 3)
    private String warrantyGroup;

    @Column(name = "AU", length = 6)
    private String inspectionDate;

    @Column(name = "GA", length = 8)
    private String warrantyDate;

    @Column(name = "SP", length = 6)
    private String servicePackage;

    @Column(name = "TACHO", length = 8)
    private String odometer;

    @Column(name = "KM", length = 8)
    private String mileage;

    @Column(name = "HU", length = 6)
    private String mainInspection;

    @Column(name = "AN-TAG", length = 8)
    private String acceptanceDate;

    @Column(name = "AN-ZEIT", length = 4)
    private String acceptanceTime;

    @Column(name = "FERT-TAG", length = 8)
    private String completionDate;

    @Column(name = "FERT-ZEIT", length = 4)
    private String completionTime;

    @Column(name = "BERATER", length = 20)
    private String advisor;

    @Column(name = "LEITZAHL", length = 3)
    private String routingNumber;

    @Column(name = "TX.ANF", length = 3)
    private String textStart;

    @Column(name = "TX.ENDE", length = 3)
    private String textEnd;

    @Column(name = "MOTOR-NR", length = 10)
    private String engineNumber;

    @Column(name = "MOTOR-TYP", length = 20)
    private String engineType;

    @Column(name = "USER AUFTRAG", length = 10)
    private String userOrder;

    @Column(name = "USER RECHNUNG", length = 10)
    private String userInvoice;

    @Column(name = "RGS NETTO", precision = 9, scale = 2)
    private BigDecimal invoiceNetAmount;

    @Column(name = "RGS BASIS AT", precision = 9, scale = 2)
    private BigDecimal invoiceBasisAustria;

    @Column(name = "RGS BASIS MWST", precision = 9, scale = 2)
    private BigDecimal invoiceBasisVat;

    @Column(name = "RGS MWST", precision = 9, scale = 2)
    private BigDecimal invoiceVatAmount;

    @Column(name = "RGS MWST AT", precision = 9, scale = 2)
    private BigDecimal invoiceVatAmountAustria;

    @Column(name = "RGS GES BRUTTO", precision = 9, scale = 2)
    private BigDecimal invoiceGrossAmount;

    @Column(name = "EG-UMSATZ", length = 1)
    private String euSales;

    @Column(name = "STEUERFREI DRITTLAND", length = 1)
    private String taxFreeThirdCountry;

    @Column(name = "VERBUCHT?", length = 1)
    private String posted;

    @Column(name = "RESERVE1", precision = 5, scale = 2)
    private BigDecimal reserve1;

    @Column(name = "RESERVE2", precision = 9, scale = 2)
    private BigDecimal reserve2;

    @Column(name = "GA-ÜBERN.", length = 8)
    private String warrantyTakeover;

    @Column(name = "WKT-ID", precision = 9, scale = 0)
    private Integer workshopId;

    @Column(name = "RESERVE3", precision = 2, scale = 0)
    private Integer reserve3;

    @Column(name = "RESERVE4", precision = 2, scale = 0)
    private Integer reserve4;

    @Column(name = "F:V>0", precision = 3, scale = 0)
    private Integer fvGreaterZero;

    @Column(name = "F:B>0", precision = 3, scale = 0)
    private Integer fbGreaterZero;

    @Column(name = "KAMPAGNE-NR", precision = 6, scale = 0)
    private Integer campaignNumber;

    @Column(name = "SPO ORDER", length = 10)
    private String spoOrder;

    @Column(name = "KEN-AV", length = 2)
    private String kenAv;

    @Column(name = "KEN-PE", length = 2)
    private String kenPe;

    @Column(name = "KLR-BERECH", length = 1)
    private String costCalculation;

    @Column(name = "KLR-BETRAG", precision = 5, scale = 2)
    private BigDecimal costAmount;

    @Column(name = "ASSI-VORGANG-NR", length = 15)
    private String assistanceProcessNumber;

    @Column(name = "ZAGA-GUELTIG", length = 8)
    private String additionalWarrantyValid;

    @Column(name = "R&W FREIGABE-NR", length = 15)
    private String rwReleaseNumber;

    @Column(name = "KL-ERWEITERUNG", precision = 5, scale = 0)
    private Integer goodwillExtension;

    @Column(name = "KL-AUSNAHME IDNR", length = 3)
    private String goodwillExceptionId;

    @Column(name = "KL-AUSNAHME KLARTEXT", length = 40)
    private String goodwillExceptionText;

    @Column(name = "FAHRZEUG-ART", length = 20)
    private String vehicleCategory;

    @Column(name = "HERSTELLER", length = 20)
    private String manufacturer;

    @Column(name = "AUFBAUART", length = 20)
    private String bodyType;

    @Column(name = "HERSTELLER AUFBAU", length = 20)
    private String bodyManufacturer;

    @Column(name = "ZUSATZAUSRÜSTUNG 1", length = 20)
    private String additionalEquipment1;

    @Column(name = "HERSTELLER ZUSATZ 1", length = 20)
    private String additionalEquipmentManufacturer1;

    @Column(name = "ZUSATZAUSRÜSTUNG 2", length = 20)
    private String additionalEquipment2;

    @Column(name = "HERSTELLER ZUSATZ 2", length = 20)
    private String additionalEquipmentManufacturer2;

    @Column(name = "ZUSATZAUSRÜSTUNG 3", length = 20)
    private String additionalEquipment3;

    @Column(name = "HERSTELLER ZUSATZ 3", length = 20)
    private String additionalEquipmentManufacturer3;

    @Column(name = "EINSATZART", length = 20)
    private String usageType;

    @Column(name = "EURO-NORM", length = 10)
    private String euroNorm;

    @Column(name = "PARTIKELFILTER", length = 1)
    private String particleFilter;

    @Column(name = "IS-ART", length = 5)
    private String isType;

    @Column(name = "MAIL TO", length = 200)
    private String mailTo;

    @Column(name = "MAIL CC", length = 200)
    private String mailCc;

    public InvoiceHeader() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber10() {
        return invoiceNumber10;
    }

    public void setInvoiceNumber10(String invoiceNumber10) {
        this.invoiceNumber10 = invoiceNumber10;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getStornoIndicator() {
        return stornoIndicator;
    }

    public void setStornoIndicator(String stornoIndicator) {
        this.stornoIndicator = stornoIndicator;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkshopTheke() {
        return workshopTheke;
    }

    public void setWorkshopTheke(String workshopTheke) {
        this.workshopTheke = workshopTheke;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderText() {
        return orderText;
    }

    public void setOrderText(String orderText) {
        this.orderText = orderText;
    }

    public String getLastInvoiceNumber() {
        return lastInvoiceNumber;
    }

    public void setLastInvoiceNumber(String lastInvoiceNumber) {
        this.lastInvoiceNumber = lastInvoiceNumber;
    }

    public String getStornoReferenceInvoice() {
        return stornoReferenceInvoice;
    }

    public void setStornoReferenceInvoice(String stornoReferenceInvoice) {
        this.stornoReferenceInvoice = stornoReferenceInvoice;
    }

    public String getStornoReferenceDate() {
        return stornoReferenceDate;
    }

    public void setStornoReferenceDate(String stornoReferenceDate) {
        this.stornoReferenceDate = stornoReferenceDate;
    }

    public String getCorrectionReferenceInvoice() {
        return correctionReferenceInvoice;
    }

    public void setCorrectionReferenceInvoice(String correctionReferenceInvoice) {
        this.correctionReferenceInvoice = correctionReferenceInvoice;
    }

    public String getCorrectionReferenceDate() {
        return correctionReferenceDate;
    }

    public void setCorrectionReferenceDate(String correctionReferenceDate) {
        this.correctionReferenceDate = correctionReferenceDate;
    }

    public String getCarryForward() {
        return carryForward;
    }

    public void setCarryForward(String carryForward) {
        this.carryForward = carryForward;
    }

    public String getVatIndicator() {
        return vatIndicator;
    }

    public void setVatIndicator(String vatIndicator) {
        this.vatIndicator = vatIndicator;
    }

    public BigDecimal getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(BigDecimal vatPercent) {
        this.vatPercent = vatPercent;
    }

    public BigDecimal getVatPercentReduced() {
        return vatPercentReduced;
    }

    public void setVatPercentReduced(BigDecimal vatPercentReduced) {
        this.vatPercentReduced = vatPercentReduced;
    }

    public String getAccountingKey() {
        return accountingKey;
    }

    public void setAccountingKey(String accountingKey) {
        this.accountingKey = accountingKey;
    }

    public String getCostCenterLabor() {
        return costCenterLabor;
    }

    public void setCostCenterLabor(String costCenterLabor) {
        this.costCenterLabor = costCenterLabor;
    }

    public String getCostCenterParts() {
        return costCenterParts;
    }

    public void setCostCenterParts(String costCenterParts) {
        this.costCenterParts = costCenterParts;
    }

    public String getGlAccountVat() {
        return glAccountVat;
    }

    public void setGlAccountVat(String glAccountVat) {
        this.glAccountVat = glAccountVat;
    }

    public String getGlAccountVatAustria() {
        return glAccountVatAustria;
    }

    public void setGlAccountVatAustria(String glAccountVatAustria) {
        this.glAccountVatAustria = glAccountVatAustria;
    }

    public String getGlAccountInterim() {
        return glAccountInterim;
    }

    public void setGlAccountInterim(String glAccountInterim) {
        this.glAccountInterim = glAccountInterim;
    }

    public String getAccountInternalOrder() {
        return accountInternalOrder;
    }

    public void setAccountInternalOrder(String accountInternalOrder) {
        this.accountInternalOrder = accountInternalOrder;
    }

    public String getCostCenterInternalOrder() {
        return costCenterInternalOrder;
    }

    public void setCostCenterInternalOrder(String costCenterInternalOrder) {
        this.costCenterInternalOrder = costCenterInternalOrder;
    }

    public String getCostCenterInternalOrderShort() {
        return costCenterInternalOrderShort;
    }

    public void setCostCenterInternalOrderShort(String costCenterInternalOrderShort) {
        this.costCenterInternalOrderShort = costCenterInternalOrderShort;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getCostCodeConcern() {
        return costCodeConcern;
    }

    public void setCostCodeConcern(String costCodeConcern) {
        this.costCodeConcern = costCodeConcern;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderingCustomer() {
        return orderingCustomer;
    }

    public void setOrderingCustomer(String orderingCustomer) {
        this.orderingCustomer = orderingCustomer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getResponsibilityCenter() {
        return responsibilityCenter;
    }

    public void setResponsibilityCenter(String responsibilityCenter) {
        this.responsibilityCenter = responsibilityCenter;
    }

    public String getInvoiceCustomerNumber() {
        return invoiceCustomerNumber;
    }

    public void setInvoiceCustomerNumber(String invoiceCustomerNumber) {
        this.invoiceCustomerNumber = invoiceCustomerNumber;
    }

    public String getInvoiceSalutation() {
        return invoiceSalutation;
    }

    public void setInvoiceSalutation(String invoiceSalutation) {
        this.invoiceSalutation = invoiceSalutation;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceIndustry() {
        return invoiceIndustry;
    }

    public void setInvoiceIndustry(String invoiceIndustry) {
        this.invoiceIndustry = invoiceIndustry;
    }

    public String getInvoiceMatchCode() {
        return invoiceMatchCode;
    }

    public void setInvoiceMatchCode(String invoiceMatchCode) {
        this.invoiceMatchCode = invoiceMatchCode;
    }

    public String getInvoiceStreet() {
        return invoiceStreet;
    }

    public void setInvoiceStreet(String invoiceStreet) {
        this.invoiceStreet = invoiceStreet;
    }

    public String getInvoiceCountry() {
        return invoiceCountry;
    }

    public void setInvoiceCountry(String invoiceCountry) {
        this.invoiceCountry = invoiceCountry;
    }

    public String getInvoicePostalCode() {
        return invoicePostalCode;
    }

    public void setInvoicePostalCode(String invoicePostalCode) {
        this.invoicePostalCode = invoicePostalCode;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone;
    }

    public String getInvoiceCurrency() {
        return invoiceCurrency;
    }

    public void setInvoiceCurrency(String invoiceCurrency) {
        this.invoiceCurrency = invoiceCurrency;
    }

    public String getInvoiceCreditRating() {
        return invoiceCreditRating;
    }

    public void setInvoiceCreditRating(String invoiceCreditRating) {
        this.invoiceCreditRating = invoiceCreditRating;
    }

    public String getInvoicePaymentMethod() {
        return invoicePaymentMethod;
    }

    public void setInvoicePaymentMethod(String invoicePaymentMethod) {
        this.invoicePaymentMethod = invoicePaymentMethod;
    }

    public String getInvoiceResponsibilityCenter() {
        return invoiceResponsibilityCenter;
    }

    public void setInvoiceResponsibilityCenter(String invoiceResponsibilityCenter) {
        this.invoiceResponsibilityCenter = invoiceResponsibilityCenter;
    }

    public String getVatIdNumber() {
        return vatIdNumber;
    }

    public void setVatIdNumber(String vatIdNumber) {
        this.vatIdNumber = vatIdNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getWarrantyGroup() {
        return warrantyGroup;
    }

    public void setWarrantyGroup(String warrantyGroup) {
        this.warrantyGroup = warrantyGroup;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getMainInspection() {
        return mainInspection;
    }

    public void setMainInspection(String mainInspection) {
        this.mainInspection = mainInspection;
    }

    public String getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(String acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public String getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(String acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getTextStart() {
        return textStart;
    }

    public void setTextStart(String textStart) {
        this.textStart = textStart;
    }

    public String getTextEnd() {
        return textEnd;
    }

    public void setTextEnd(String textEnd) {
        this.textEnd = textEnd;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(String userOrder) {
        this.userOrder = userOrder;
    }

    public String getUserInvoice() {
        return userInvoice;
    }

    public void setUserInvoice(String userInvoice) {
        this.userInvoice = userInvoice;
    }

    public BigDecimal getInvoiceNetAmount() {
        return invoiceNetAmount;
    }

    public void setInvoiceNetAmount(BigDecimal invoiceNetAmount) {
        this.invoiceNetAmount = invoiceNetAmount;
    }

    public BigDecimal getInvoiceBasisAustria() {
        return invoiceBasisAustria;
    }

    public void setInvoiceBasisAustria(BigDecimal invoiceBasisAustria) {
        this.invoiceBasisAustria = invoiceBasisAustria;
    }

    public BigDecimal getInvoiceBasisVat() {
        return invoiceBasisVat;
    }

    public void setInvoiceBasisVat(BigDecimal invoiceBasisVat) {
        this.invoiceBasisVat = invoiceBasisVat;
    }

    public BigDecimal getInvoiceVatAmount() {
        return invoiceVatAmount;
    }

    public void setInvoiceVatAmount(BigDecimal invoiceVatAmount) {
        this.invoiceVatAmount = invoiceVatAmount;
    }

    public BigDecimal getInvoiceVatAmountAustria() {
        return invoiceVatAmountAustria;
    }

    public void setInvoiceVatAmountAustria(BigDecimal invoiceVatAmountAustria) {
        this.invoiceVatAmountAustria = invoiceVatAmountAustria;
    }

    public BigDecimal getInvoiceGrossAmount() {
        return invoiceGrossAmount;
    }

    public void setInvoiceGrossAmount(BigDecimal invoiceGrossAmount) {
        this.invoiceGrossAmount = invoiceGrossAmount;
    }

    public String getEuSales() {
        return euSales;
    }

    public void setEuSales(String euSales) {
        this.euSales = euSales;
    }

    public String getTaxFreeThirdCountry() {
        return taxFreeThirdCountry;
    }

    public void setTaxFreeThirdCountry(String taxFreeThirdCountry) {
        this.taxFreeThirdCountry = taxFreeThirdCountry;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public BigDecimal getReserve1() {
        return reserve1;
    }

    public void setReserve1(BigDecimal reserve1) {
        this.reserve1 = reserve1;
    }

    public BigDecimal getReserve2() {
        return reserve2;
    }

    public void setReserve2(BigDecimal reserve2) {
        this.reserve2 = reserve2;
    }

    public String getWarrantyTakeover() {
        return warrantyTakeover;
    }

    public void setWarrantyTakeover(String warrantyTakeover) {
        this.warrantyTakeover = warrantyTakeover;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public Integer getReserve3() {
        return reserve3;
    }

    public void setReserve3(Integer reserve3) {
        this.reserve3 = reserve3;
    }

    public Integer getReserve4() {
        return reserve4;
    }

    public void setReserve4(Integer reserve4) {
        this.reserve4 = reserve4;
    }

    public Integer getFvGreaterZero() {
        return fvGreaterZero;
    }

    public void setFvGreaterZero(Integer fvGreaterZero) {
        this.fvGreaterZero = fvGreaterZero;
    }

    public Integer getFbGreaterZero() {
        return fbGreaterZero;
    }

    public void setFbGreaterZero(Integer fbGreaterZero) {
        this.fbGreaterZero = fbGreaterZero;
    }

    public Integer getCampaignNumber() {
        return campaignNumber;
    }

    public void setCampaignNumber(Integer campaignNumber) {
        this.campaignNumber = campaignNumber;
    }

    public String getSpoOrder() {
        return spoOrder;
    }

    public void setSpoOrder(String spoOrder) {
        this.spoOrder = spoOrder;
    }

    public String getKenAv() {
        return kenAv;
    }

    public void setKenAv(String kenAv) {
        this.kenAv = kenAv;
    }

    public String getKenPe() {
        return kenPe;
    }

    public void setKenPe(String kenPe) {
        this.kenPe = kenPe;
    }

    public String getCostCalculation() {
        return costCalculation;
    }

    public void setCostCalculation(String costCalculation) {
        this.costCalculation = costCalculation;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getAssistanceProcessNumber() {
        return assistanceProcessNumber;
    }

    public void setAssistanceProcessNumber(String assistanceProcessNumber) {
        this.assistanceProcessNumber = assistanceProcessNumber;
    }

    public String getAdditionalWarrantyValid() {
        return additionalWarrantyValid;
    }

    public void setAdditionalWarrantyValid(String additionalWarrantyValid) {
        this.additionalWarrantyValid = additionalWarrantyValid;
    }

    public String getRwReleaseNumber() {
        return rwReleaseNumber;
    }

    public void setRwReleaseNumber(String rwReleaseNumber) {
        this.rwReleaseNumber = rwReleaseNumber;
    }

    public Integer getGoodwillExtension() {
        return goodwillExtension;
    }

    public void setGoodwillExtension(Integer goodwillExtension) {
        this.goodwillExtension = goodwillExtension;
    }

    public String getGoodwillExceptionId() {
        return goodwillExceptionId;
    }

    public void setGoodwillExceptionId(String goodwillExceptionId) {
        this.goodwillExceptionId = goodwillExceptionId;
    }

    public String getGoodwillExceptionText() {
        return goodwillExceptionText;
    }

    public void setGoodwillExceptionText(String goodwillExceptionText) {
        this.goodwillExceptionText = goodwillExceptionText;
    }

    public String getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(String vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getBodyManufacturer() {
        return bodyManufacturer;
    }

    public void setBodyManufacturer(String bodyManufacturer) {
        this.bodyManufacturer = bodyManufacturer;
    }

    public String getAdditionalEquipment1() {
        return additionalEquipment1;
    }

    public void setAdditionalEquipment1(String additionalEquipment1) {
        this.additionalEquipment1 = additionalEquipment1;
    }

    public String getAdditionalEquipmentManufacturer1() {
        return additionalEquipmentManufacturer1;
    }

    public void setAdditionalEquipmentManufacturer1(String additionalEquipmentManufacturer1) {
        this.additionalEquipmentManufacturer1 = additionalEquipmentManufacturer1;
    }

    public String getAdditionalEquipment2() {
        return additionalEquipment2;
    }

    public void setAdditionalEquipment2(String additionalEquipment2) {
        this.additionalEquipment2 = additionalEquipment2;
    }

    public String getAdditionalEquipmentManufacturer2() {
        return additionalEquipmentManufacturer2;
    }

    public void setAdditionalEquipmentManufacturer2(String additionalEquipmentManufacturer2) {
        this.additionalEquipmentManufacturer2 = additionalEquipmentManufacturer2;
    }

    public String getAdditionalEquipment3() {
        return additionalEquipment3;
    }

    public void setAdditionalEquipment3(String additionalEquipment3) {
        this.additionalEquipment3 = additionalEquipment3;
    }

    public String getAdditionalEquipmentManufacturer3() {
        return additionalEquipmentManufacturer3;
    }

    public void setAdditionalEquipmentManufacturer3(String additionalEquipmentManufacturer3) {
        this.additionalEquipmentManufacturer3 = additionalEquipmentManufacturer3;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getEuroNorm() {
        return euroNorm;
    }

    public void setEuroNorm(String euroNorm) {
        this.euroNorm = euroNorm;
    }

    public String getParticleFilter() {
        return particleFilter;
    }

    public void setParticleFilter(String particleFilter) {
        this.particleFilter = particleFilter;
    }

    public String getIsType() {
        return isType;
    }

    public void setIsType(String isType) {
        this.isType = isType;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailCc() {
        return mailCc;
    }

    public void setMailCc(String mailCc) {
        this.mailCc = mailCc;
    }
}