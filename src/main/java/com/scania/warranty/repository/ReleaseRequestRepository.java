/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ReleaseRequest;
import com.scania.warranty.domain.ReleaseRequestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for release requests (HSG70F).
 */
@Repository
public interface ReleaseRequestRepository extends JpaRepository<ReleaseRequest, ReleaseRequestId> {

    // @origin HS1210 L1686-1686 (CHAIN)
    @Query("SELECT r FROM ReleaseRequest r WHERE r.kzl = :kzl AND r.rNr = :rNr AND r.rDat = :rDat")
    Optional<ReleaseRequest> findByKey(@Param("kzl") String kzl,
                                        @Param("rNr") String rNr,
                                        @Param("rDat") String rDat);
}