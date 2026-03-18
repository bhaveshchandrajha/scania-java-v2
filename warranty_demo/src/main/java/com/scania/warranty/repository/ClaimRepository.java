/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.ClaimId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, ClaimId> {

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz AND c.g71170 <> 99 ORDER BY c.g71050 ASC")
    List<Claim> findActiveByCompanyCodeAsc(@Param("pkz") String pkz); // @rpg-trace: n428

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz AND c.g71170 <> 99 ORDER BY c.g71050 DESC")
    List<Claim> findActiveByCompanyCodeDesc(@Param("pkz") String pkz); // @rpg-trace: n431

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz AND c.g71170 <> 99 ORDER BY c.g71050 ASC")
    List<Claim> findActiveClaimsByCompanyAsc(@Param("pkz") String pkz);

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz AND c.g71170 <> 99 ORDER BY c.g71050 DESC")
    List<Claim> findActiveClaimsByCompanyDesc(@Param("pkz") String pkz);

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz ORDER BY c.g71050 ASC")
    List<Claim> findByCompanyCodeAsc(@Param("pkz") String pkz); // @rpg-trace: n429

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz ORDER BY c.g71050 ASC")
    List<Claim> findAllByCompanyAsc(@Param("pkz") String pkz);

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz ORDER BY c.g71050 DESC")
    List<Claim> findByCompanyCodeDesc(@Param("pkz") String pkz); // @rpg-trace: n433

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz AND c.g71010 = :invoiceNr AND c.g71020 = :invoiceDate AND c.g71030 = :orderNr")
    List<Claim> findByInvoiceKey(@Param("pkz") String pkz, @Param("invoiceNr") String invoiceNr,
                                  @Param("invoiceDate") String invoiceDate, @Param("orderNr") String orderNr); // @rpg-trace: n962

    @Query("SELECT c FROM Claim c WHERE c.g71000 = :pkz AND c.g71050 = :claimNr")
    Optional<Claim> findByCompanyAndClaimNr(@Param("pkz") String pkz, @Param("claimNr") String claimNr); // @rpg-trace: n1093

    @Query("SELECT MAX(c.g71050) FROM Claim c WHERE c.g71000 = :pkz")
    Optional<String> findMaxClaimNrByCompany(@Param("pkz") String pkz); // @rpg-trace: n1103

    @Modifying
    @Query("UPDATE Claim c SET c.g71170 = :status WHERE c.g71000 = :pkz AND c.g71050 = :claimNr")
    int updateStatus(@Param("pkz") String pkz, @Param("claimNr") String claimNr, @Param("status") int status); // @rpg-trace: n1110

    @Modifying
    @Query("UPDATE Claim c SET c.g71170 = 99 WHERE c.g71000 = :pkz AND c.g71050 = :claimNr")
    int markAsDeleted(@Param("pkz") String pkz, @Param("claimNr") String claimNr); // @rpg-trace: n587
}