package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link ReleaseRequest} based on its natural key fields.
 * This is a temporary mapping until the final DB schema is available.
 */
public class ReleaseRequestId implements Serializable {

    private String companyCode;
    private String invoiceNumber;
    private String invoiceDate;

    public ReleaseRequestId() {
    }

    public ReleaseRequestId(String companyCode, String invoiceNumber, String invoiceDate) {
        this.companyCode = companyCode;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
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

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleaseRequestId that = (ReleaseRequestId) o;
        return Objects.equals(companyCode, that.companyCode)
                && Objects.equals(invoiceNumber, that.invoiceNumber)
                && Objects.equals(invoiceDate, that.invoiceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, invoiceNumber, invoiceDate);
    }
}

