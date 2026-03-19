/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.Invoice;
import com.scania.warranty.domain.InvoiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, InvoiceId> {

    @Query("SELECT i FROM Invoice i WHERE i.ahk000 = :pkz AND i.ahk080 = :orderDate AND i.ahk010 = :invoiceNr ORDER BY i.ahk040")
    List<Invoice> findByCompanyAndOrderDateAndInvoiceNr(@Param("pkz") String pkz,
                                                        @Param("orderDate") String orderDate,
                                                        @Param("invoiceNr") String invoiceNr); // @rpg-trace: n975

    @Query("SELECT i FROM Invoice i WHERE i.ahk000 = :pkz AND i.ahk010 = :invoiceNr AND i.ahk020 = :invoiceDate " +
           "AND i.ahk040 = :orderNr")
    Optional<Invoice> findByKey(@Param("pkz") String pkz, @Param("invoiceNr") String invoiceNr,
                                @Param("invoiceDate") String invoiceDate, @Param("orderNr") String orderNr); // @rpg-trace: n983

    @Query("SELECT i FROM Invoice i WHERE i.ahk000 = :pkz ORDER BY i.ahk080")
    List<Invoice> findByCompanyCode(@Param("pkz") String pkz); // @rpg-trace: n747

    @Query("SELECT i FROM Invoice i WHERE i.ahk000 = :pkz ORDER BY i.ahk080")
    List<Invoice> findByCompany(@Param("pkz") String pkz);

    @Query("SELECT i FROM Invoice i WHERE i.ahk000 = :pkz AND i.ahk080 = :orderDate AND i.ahk010 = :invoiceNr AND i.ahk030 = 'S' ORDER BY i.ahk040")
    List<Invoice> findStornoByKey(@Param("pkz") String pkz, @Param("orderDate") String orderDate,
                                   @Param("invoiceNr") String invoiceNr);
}