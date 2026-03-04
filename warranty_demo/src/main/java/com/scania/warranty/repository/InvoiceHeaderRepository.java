/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.InvoiceHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for InvoiceHeader entity.
 */
@Repository
public interface InvoiceHeaderRepository extends JpaRepository<InvoiceHeader, Long> {

    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT i FROM InvoiceHeader i WHERE i.companyCode = :companyCode " +
           "AND i.orderDate = :orderDate AND i.orderNumber = :orderNumber " +
           "AND i.department = :department AND i.workshopTheke = :workshopTheke " +
           "AND i.split = :split")
    Optional<InvoiceHeader> findByKey(
        @Param("companyCode") String companyCode,
        @Param("orderDate") String orderDate,
        @Param("orderNumber") String orderNumber,
        @Param("department") String department,
        @Param("workshopTheke") String workshopTheke,
        @Param("split") String split
    );

    @Query("SELECT i FROM InvoiceHeader i WHERE i.companyCode = :companyCode " +
           "AND i.invoiceNumber = :invoiceNumber AND i.invoiceDate = :invoiceDate " +
           "AND i.orderNumber = :orderNumber AND i.workshopTheke = :workshopTheke")
    Optional<InvoiceHeader> findByInvoiceKey(
        @Param("companyCode") String companyCode,
        @Param("invoiceNumber") String invoiceNumber,
        @Param("invoiceDate") String invoiceDate,
        @Param("orderNumber") String orderNumber,
        @Param("workshopTheke") String workshopTheke
    );

    // @origin HS1210 L2815-2815 (CHAIN)
    @Query("SELECT i FROM InvoiceHeader i WHERE i.companyCode = :companyCode " +
           "AND i.stornoIndicator = 'S' AND i.orderDate = :orderDate " +
           "ORDER BY i.invoiceNumber")
    List<InvoiceHeader> findStornoInvoices(
        @Param("companyCode") String companyCode,
        @Param("orderDate") String orderDate
    );
}