package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link ClaimHeader}.
 * Temporary mapping until the final DB schema is available.
 */
public class ClaimHeaderId implements Serializable {

    private String companyCode;
    private String invoiceNumber;
    private String invoiceDate;
    private String orderNumber;
    private String wete;

    public ClaimHeaderId() {
    }

    public ClaimHeaderId(String companyCode,
                         String invoiceNumber,
                         String invoiceDate,
                         String orderNumber,
                         String wete) {
        this.companyCode = companyCode;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.orderNumber = orderNumber;
        this.wete = wete;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getWete() {
        return wete;
    }

    public void setWete(String wete) {
        this.wete = wete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimHeaderId that = (ClaimHeaderId) o;
        return Objects.equals(companyCode, that.companyCode)
                && Objects.equals(invoiceNumber, that.invoiceNumber)
                && Objects.equals(invoiceDate, that.invoiceDate)
                && Objects.equals(orderNumber, that.orderNumber)
                && Objects.equals(wete, that.wete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyCode, invoiceNumber, invoiceDate, orderNumber, wete);
    }
}

