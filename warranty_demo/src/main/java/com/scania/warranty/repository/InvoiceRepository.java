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

/**
 * Repository for invoice header (HSAHKLF3).
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, InvoiceId> {

    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.rnr = :rnr AND i.rdat = :rdat AND i.anr = :anr AND i.berei = :berei AND i.wt = :wt AND i.splitt = :splitt")
    Optional<Invoice> findByKey(@Param("pakz") String pakz, @Param("rnr") String rnr, @Param("rdat") String rdat, @Param("anr") String anr, @Param("berei") String berei, @Param("wt") String wt, @Param("splitt") String splitt);

    // @origin HS1210 L2874-2874 (CHAIN)
    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.adat = :adat AND i.kzS = 'S'")
    List<Invoice> findWarrantyInvoicesByOrderDate(@Param("pakz") String pakz, @Param("adat") String adat);

    // @origin HS1210 L2815-2815 (CHAIN)
    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.splitt = '04' ORDER BY i.rnr, i.rdat")
    List<Invoice> findAllSplitt04ByCompanyCode(@Param("pakz") String pakz);

    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.rnr = :rnr AND i.rdat = :rdat AND i.anr = :anr ORDER BY i.rnr, i.rdat")
    List<Invoice> findByInvoiceKey(@Param("pakz") String pakz, @Param("rnr") String rnr, @Param("rdat") String rdat, @Param("anr") String anr);

    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.adat = :adat AND i.anr = :anr AND i.berei = :berei AND i.wt = :wt AND i.splitt = :splitt AND i.rnr <> :excludeRnr AND i.kzS = 'S'")
    List<Invoice> findStornoInvoices(@Param("pakz") String pakz, @Param("adat") String adat, @Param("anr") String anr, @Param("berei") String berei, @Param("wt") String wt, @Param("splitt") String splitt, @Param("excludeRnr") String excludeRnr);
}