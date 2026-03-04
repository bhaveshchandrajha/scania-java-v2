/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ExternalService;
import com.scania.warranty.domain.ExternalServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for external service records (HSFLALF1).
 */
@Repository
public interface ExternalServiceRepository extends JpaRepository<ExternalService, ExternalServiceId> {

    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT e FROM ExternalService e WHERE e.pkz = :pkz AND e.besDate = :besDate AND e.besNr = :besNr AND e.aufdat = :aufdat AND e.pos > 3")
    List<ExternalService> findByInvoiceAndPositionGreaterThan3(@Param("pkz") String pkz, @Param("besDate") String besDate, @Param("besNr") String besNr, @Param("aufdat") String aufdat);

    @Query("SELECT e FROM ExternalService e WHERE e.pkz = :pkz AND e.aufnr = :aufnr AND e.aufdat = :aufdat AND e.berei = :berei AND e.wt = :wt AND e.spl = :spl")
    List<ExternalService> findByOrderKey(@Param("pkz") String pkz, @Param("aufnr") String aufnr, @Param("aufdat") String aufdat, @Param("berei") String berei, @Param("wt") String wt, @Param("spl") String spl);

    // @origin HS1210 L2815-2815 (CHAIN)
    @Query("SELECT e FROM ExternalService e WHERE e.pkz = :pkz AND e.aufdat = :aufdat AND e.aufnr = :aufnr AND e.berei = :berei AND e.wt = :wt AND e.spl = :spl AND e.besDate = :besDate AND e.pos > :minPos")
    List<ExternalService> findExternalServicesForClaim(@Param("pkz") String pkz, @Param("aufdat") String aufdat, @Param("aufnr") String aufnr, @Param("berei") String berei, @Param("wt") String wt, @Param("spl") String spl, @Param("besDate") String besDate, @Param("minPos") int minPos);
}