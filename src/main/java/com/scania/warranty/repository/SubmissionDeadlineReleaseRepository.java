/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.SubmissionDeadlineRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for SubmissionDeadlineRelease entity.
 */
@Repository
public interface SubmissionDeadlineReleaseRepository extends JpaRepository<SubmissionDeadlineRelease, Long> {

    // @origin HS1210 L941-941 (CHAIN)
    @Query("SELECT s FROM SubmissionDeadlineRelease s WHERE s.companyCode = :companyCode " +
           "AND s.invoiceNumber = :invoiceNumber AND s.invoiceDate = :invoiceDate")
    Optional<SubmissionDeadlineRelease> findByInvoiceKey(
        @Param("companyCode") String companyCode,
        @Param("invoiceNumber") String invoiceNumber,
        @Param("invoiceDate") String invoiceDate
    );
}