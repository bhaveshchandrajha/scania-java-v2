/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ClaimPositionLine;
import com.scania.warranty.domain.ClaimPositionLineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ClaimPositionLine entity (HSGPSLF3).
 */
@Repository
public interface ClaimPositionLineRepository extends JpaRepository<ClaimPositionLine, ClaimPositionLineId> {
    
    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT cpl FROM ClaimPositionLine cpl WHERE cpl.kuerzel = :kuerzel AND cpl.claimNr = :claimNr ORDER BY cpl.fehlerNr, cpl.folgeNr, cpl.zeile")
    List<ClaimPositionLine> findByKuerzelAndClaimNrOrderByFehlerNrAndFolgeNrAndZeile(@Param("kuerzel") String kuerzel, @Param("claimNr") String claimNr);
    
    @Query(value = "SELECT UPPER(:text) FROM SYSIBM.SYSDUMMY1", nativeQuery = true)
    String upperCase(@Param("text") String text);
}