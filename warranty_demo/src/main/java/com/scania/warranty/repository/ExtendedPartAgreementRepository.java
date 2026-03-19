/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1983}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ExtendedPartAgreement;
import com.scania.warranty.domain.ExtendedPartAgreementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendedPartAgreementRepository extends JpaRepository<ExtendedPartAgreement, ExtendedPartAgreementId> {

    /**
     * RPG SETLL/READE: (AHK000:AHK040:AHK050:AHK060:'V4') HSEPAF
     * Reads all extended part agreement records matching the partial key with type 'V4',
     * ordered to replicate RPG keyed sequential access.
     */
    @Query("SELECT e FROM ExtendedPartAgreement e WHERE e.epa000 = :epa000 " +
           "AND e.epa040 = :epa040 AND e.epa050 = :epa050 AND e.epa060 = :epa060 " +
           "AND e.epaType = :epaType ORDER BY e.epa000, e.epa040, e.epa050, e.epa060, e.epaType")
    List<ExtendedPartAgreement> findByPartialKeyAndType(
        @Param("epa000") String epa000,
        @Param("epa040") String epa040,
        @Param("epa050") String epa050,
        @Param("epa060") String epa060,
        @Param("epaType") String epaType
    );
}