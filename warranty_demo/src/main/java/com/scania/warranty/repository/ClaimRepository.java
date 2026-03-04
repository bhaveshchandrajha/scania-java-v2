/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.ClaimId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Claim entity (HSG71PF).
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim, ClaimId> {
    
    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.claimNr = :claimNr AND c.rechDatum = :rechDatum")
    Optional<Claim> findByPakzAndClaimNrAndRechDatum(@Param("pakz") String pakz, @Param("claimNr") String claimNr, @Param("rechDatum") String rechDatum);
    
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.statusCodeSde < 99 ORDER BY c.claimNr ASC")
    List<Claim> findActiveClaimsByCompanyCodeAscending(@Param("pakz") String pakz);
    
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.statusCodeSde < 99 ORDER BY c.claimNr DESC")
    List<Claim> findActiveClaimsByCompanyCodeDescending(@Param("pakz") String pakz);
    
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.rechNr = :rechNr AND c.rechDatum = :rechDatum AND c.auftragsNr = :auftragsNr AND c.wete = :wete")
    List<Claim> findByInvoiceKey(@Param("pakz") String pakz, @Param("rechNr") String rechNr, @Param("rechDatum") String rechDatum, @Param("auftragsNr") String auftragsNr, @Param("wete") String wete);
    
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.rechNr = :rechNr AND c.rechDatum = :rechDatum AND c.auftragsNr = :auftragsNr AND c.wete = :wete AND c.statusCodeSde < 99")
    List<Claim> findByInvoiceKeyAndNotExcluded(@Param("pakz") String pakz, @Param("rechNr") String rechNr, @Param("rechDatum") String rechDatum, @Param("auftragsNr") String auftragsNr, @Param("wete") String wete);
    
    @Query("SELECT MAX(c.claimNr) FROM Claim c WHERE c.statusCodeSde < 99")
    Optional<String> findMaxClaimNrNotExcluded();
    
    @Query("SELECT MAX(c.claimNr) FROM Claim c WHERE c.statusCodeSde < 99")
    Optional<String> findMaxClaimNumber();
    
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.claimNr = :claimNr")
    Optional<Claim> findByClaimNumber(@Param("pakz") String pakz, @Param("claimNr") String claimNr);
}