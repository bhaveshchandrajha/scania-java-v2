/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n2020}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ClaimPosition;
import com.scania.warranty.domain.ClaimPositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository for claim position operations.
 */
@Repository
public interface ClaimPositionRepository extends JpaRepository<ClaimPosition, ClaimPositionId> {

    // @origin HS1210 L941-941 (CHAIN)
    @Query(value = "SELECT LISTAGG(DIGITS(GPS030) CONCAT DIGITS(GPS150)) WITHIN GROUP(ORDER BY GPS150) AS List " +
                   "FROM HSGPSPF " +
                   "WHERE GPS000 = :dealerId AND GPS010 = :claimNo",
           nativeQuery = true)
    String findAggregatedPositionData(@Param("dealerId") String dealerId, 
                                      @Param("claimNo") String claimNo);

    @Modifying
    // @origin HS1210 L1027-1027 (CHAIN)
    @Query("UPDATE ClaimPosition c SET c.position = :position " +
           "WHERE c.dealerId = :dealerId AND c.claimNo = :claimNo AND c.lineNo = :lineNo")
    int updatePosition(@Param("dealerId") String dealerId,
                       @Param("claimNo") String claimNo,
                       @Param("lineNo") Integer lineNo,
                       @Param("position") Integer position);

    // @origin HS1210 L1035-1035 (CHAIN)
    @Query("SELECT c FROM ClaimPosition c " +
           "WHERE c.dealerId = :dealerId AND c.claimNo = :claimNo " +
           "ORDER BY c.position")
    List<ClaimPosition> findByDealerIdAndClaimNoOrderByPosition(@Param("dealerId") String dealerId,
                                                                  @Param("claimNo") String claimNo);

    // @origin HS1210 L1100-1100 (CHAIN)
    @Query("SELECT COALESCE(SUM(c.position), 0) FROM ClaimPosition c " +
           "WHERE c.dealerId = :kuerzel AND c.claimNo = :claimNumber " +
           "AND (:errorNumber IS NULL OR :errorNumber = :errorNumber) " +
           "AND (:sequenceNumber IS NULL OR :sequenceNumber = :sequenceNumber)")
    BigDecimal calculateTotalValueByClaimKey(@Param("kuerzel") String kuerzel,
                                             @Param("claimNumber") String claimNumber,
                                             @Param("errorNumber") String errorNumber,
                                             @Param("sequenceNumber") String sequenceNumber);

    // @origin HS1210 L1106-1106 (CHAIN)
    @Query("SELECT c FROM ClaimPosition c " +
           "WHERE c.dealerId = :kuerzel AND c.claimNo = :claimNumber " +
           "AND (:errorNumber IS NULL OR :errorNumber = :errorNumber)")
    List<ClaimPosition> findByCompanyCodeAndClaimNumberAndErrorNumber(@Param("kuerzel") String kuerzel,
                                                                       @Param("claimNumber") String claimNumber,
                                                                       @Param("errorNumber") String errorNumber);
}