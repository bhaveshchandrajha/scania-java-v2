/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ClaimError;
import com.scania.warranty.domain.ClaimErrorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimErrorRepository extends JpaRepository<ClaimError, ClaimErrorId> {

    @Query("SELECT ce FROM ClaimError ce WHERE ce.g73000 = :pkz AND ce.g73050 = :claimNr ORDER BY ce.g73060")
    List<ClaimError> findByCompanyAndClaimNr(@Param("pkz") String pkz, @Param("claimNr") String claimNr); // @rpg-trace: n457

    @Query("SELECT ce FROM ClaimError ce WHERE ce.g73000 = :pkz AND ce.g73050 = :claimNr AND ce.g73290 = 0")
    List<ClaimError> findOpenErrorsByCompanyAndClaimNr(@Param("pkz") String pkz, @Param("claimNr") String claimNr); // @rpg-trace: n461

    void deleteByG73000AndG73050(@Param("pkz") String pkz, @Param("claimNr") String claimNr); // @rpg-trace: n592

    @Query("SELECT ce FROM ClaimError ce WHERE ce.g73000 = :pkz AND ce.g73050 = :claimNr AND ce.g73140 = :demandCode")
    Optional<ClaimError> findFirstByCompanyClaimAndDemandCode(@Param("pkz") String pkz, @Param("claimNr") String claimNr,
                                                              @Param("demandCode") String demandCode); // @rpg-trace: n1732
}