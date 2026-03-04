/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
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
 * Repository for claim header (HSG71LF2).
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim, ClaimId> {

    // @origin HS1210 L866-866 (SETLL)
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.statusCodeSde <> 99 ORDER BY c.claimNr ASC")
    List<Claim> findActiveClaimsByCompanyCodeAscending(@Param("pakz") String pakz);

    // @origin HS1210 L873-873 (ELSE)
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.statusCodeSde <> 99 ORDER BY c.claimNr DESC")
    List<Claim> findActiveClaimsByCompanyCodeDescending(@Param("pakz") String pakz);

    // @origin HS1210 L1729-1736 (DOW)
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.rechNr = :rechNr AND c.rechDatum = :rechDatum AND c.auftragsNr = :auftragsNr AND c.wete = :wete")
    List<Claim> findByInvoiceKey(@Param("pakz") String pakz,
                                  @Param("rechNr") String rechNr,
                                  @Param("rechDatum") String rechDatum,
                                  @Param("auftragsNr") String auftragsNr,
                                  @Param("wete") String wete);

    // @origin HS1210 L1877-1877 (CHAIN)
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.claimNr = :claimNr")
    Optional<Claim> findByClaimNumber(@Param("pakz") String pakz,
                                       @Param("claimNr") String claimNr);

    // @origin HS1210 L1248-1257 (IF)
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.rechNr = :rechNr AND c.rechDatum = :rechDatum AND c.auftragsNr = :auftragsNr AND c.wete = :wete AND c.claimNr = :claimNr")
    Optional<Claim> findByFullKey(@Param("pakz") String pakz,
                                   @Param("rechNr") String rechNr,
                                   @Param("rechDatum") String rechDatum,
                                   @Param("auftragsNr") String auftragsNr,
                                   @Param("wete") String wete,
                                   @Param("claimNr") String claimNr);

    // @origin HS1210 L1271-1305 (IF)
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.claimNr = :claimNr AND c.chassisNr = :chassisNr")
    Optional<Claim> findByClaimAndChassis(@Param("pakz") String pakz,
                                           @Param("claimNr") String claimNr,
                                           @Param("chassisNr") String chassisNr);

    // For ClaimCreationService
    @Query("SELECT c FROM Claim c WHERE c.pakz = :pakz AND c.claimNr = :claimNr AND c.rechDatum = :rechDatum")
    Optional<Claim> findByPakzAndClaimNrAndRechDatum(@Param("pakz") String pakz,
                                                       @Param("claimNr") String claimNr,
                                                       @Param("rechDatum") String rechDatum);

    // For ClaimManagementService - find max claim number
    @Query("SELECT MAX(c.claimNr) FROM Claim c WHERE c.pakz = :pakz")
    Optional<String> findMaxClaimNumber(@Param("pakz") String pakz);

    // Overload without pakz parameter for backward compatibility
    @Query("SELECT MAX(c.claimNr) FROM Claim c")
    Optional<String> findMaxClaimNumber();
}