package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite key for ClaimPosition (dealerId, claimNo, lineNo).
 */
public class ClaimPositionId implements Serializable {

    private String dealerId;
    private String claimNo;
    private Integer lineNo;

    public ClaimPositionId() {
    }

    public ClaimPositionId(String dealerId, String claimNo, Integer lineNo) {
        this.dealerId = dealerId;
        this.claimNo = claimNo;
        this.lineNo = lineNo;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimPositionId that = (ClaimPositionId) o;
        return Objects.equals(dealerId, that.dealerId)
                && Objects.equals(claimNo, that.claimNo)
                && Objects.equals(lineNo, that.lineNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealerId, claimNo, lineNo);
    }
}
