/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ClaimPosition;
import com.scania.warranty.domain.ClaimPositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository for ClaimPosition entity (HSGPSLF3).
 */
@Repository
public interface ClaimPositionRepository extends JpaRepository<ClaimPosition, ClaimPositionId> {

    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT p FROM ClaimPosition p WHERE p.abbreviation = :abbreviation AND p.claimNumber = :claimNumber ORDER BY p.errorNumber, p.sequenceNumber, p.lineNumber")
    List<ClaimPosition> findByAbbreviationAndClaimNumberOrderByErrorNumber(
            @Param("abbreviation") String abbreviation,
            @Param("claimNumber") String claimNumber
    );

    @Query("SELECT p FROM ClaimPosition p WHERE p.abbreviation = :abbreviation AND p.claimNumber = :claimNumber AND p.errorNumber = :errorNumber ORDER BY p.sequenceNumber, p.lineNumber")
    List<ClaimPosition> findByKeyAndErrorNumber(
            @Param("abbreviation") String abbreviation,
            @Param("claimNumber") String claimNumber,
            @Param("errorNumber") String errorNumber
    );
    
    // @origin HS1210 L2815-2815 (CHAIN)
    @Query("SELECT COALESCE(SUM(p.value), 0) FROM ClaimPosition p WHERE p.abbreviation = :kuerzel " +
           "AND p.claimNumber = :claimNumber AND p.errorNumber = :errorNumber " +
           "AND p.sequenceNumber = :sequenceNumber")
    BigDecimal calculateTotalValueByClaimKey(
        @Param("kuerzel") String kuerzel,
        @Param("claimNumber") String claimNumber,
        @Param("errorNumber") String errorNumber,
        @Param("sequenceNumber") String sequenceNumber
    );
    
    @Query("SELECT p FROM ClaimPosition p WHERE p.abbreviation = :companyCode " +
           "AND p.claimNumber = :claimNumber AND p.errorNumber = :errorNumber")
    List<ClaimPosition> findByCompanyCodeAndClaimNumberAndErrorNumber(
        @Param("companyCode") String companyCode,
        @Param("claimNumber") String claimNumber,
        @Param("errorNumber") String errorNumber
    );
}