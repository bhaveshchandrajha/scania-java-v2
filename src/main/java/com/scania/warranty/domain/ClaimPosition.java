/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n2020}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA entity for claim position (HSGPSPF).
 */
@Entity
@Table(name = "HSGPSPF")
@IdClass(ClaimPositionId.class)
public class ClaimPosition implements Serializable {

    @Id
    @Column(name = "GPS000")
    private String dealerId;

    @Id
    @Column(name = "GPS010")
    private String claimNo;

    @Id
    @Column(name = "GPS030")
    private Integer lineNo;

    @Column(name = "GPS150")
    private Integer position;

    @Column(name = "GPS_RECORD_TYPE")
    private String recordType;

    @Column(name = "GPS_RESULT_CODE")
    private String resultCode;

    @Column(name = "GPS_CODE_TYPE")
    private String codeType;

    public ClaimPosition() {
    }

    public ClaimPosition(String dealerId, String claimNo, Integer lineNo, Integer position) {
        this.dealerId = dealerId;
        this.claimNo = claimNo;
        this.lineNo = lineNo;
        this.position = position;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}