/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.WarrantyRelease;
import com.scania.warranty.domain.WarrantyReleaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for warranty release requests (HSG70F).
 */
@Repository
public interface WarrantyReleaseRepository extends JpaRepository<WarrantyRelease, WarrantyReleaseId> {

    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT w FROM WarrantyRelease w WHERE w.kzl = :kzl AND w.rNr = :rNr AND w.rDat = :rDat")
    Optional<WarrantyRelease> findByKey(@Param("kzl") String kzl, @Param("rNr") String rNr, @Param("rDat") String rDat);
}