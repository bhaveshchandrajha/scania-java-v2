/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ClaimHeader;
import com.scania.warranty.domain.ClaimHeaderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for ClaimHeader entity (HSG71PF).
 */
@Repository
public interface ClaimHeaderRepository extends JpaRepository<ClaimHeader, ClaimHeaderId> {

    // @origin HS1210 L941-941 (CHAIN)
    @Query("SELECT c FROM ClaimHeader c WHERE c.companyCode = :companyCode AND c.invoiceNumber = :invoiceNumber AND c.invoiceDate = :invoiceDate")
    Optional<ClaimHeader> findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            @Param("companyCode") String companyCode,
            @Param("invoiceNumber") String invoiceNumber,
            @Param("invoiceDate") String invoiceDate
    );
}