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

    // @origin HS1210 L1752-1752 (CHAIN)
    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.rnr = :rnr AND i.rdat = :rdat AND i.anr = :anr AND i.berei = :berei AND i.wt = :wt AND i.splitt = :splitt")
    Optional<Invoice> findByKey(@Param("pakz") String pakz,
                                 @Param("rnr") String rnr,
                                 @Param("rdat") String rdat,
                                 @Param("anr") String anr,
                                 @Param("berei") String berei,
                                 @Param("wt") String wt,
                                 @Param("splitt") String splitt);

    // @origin HS1210 L1435-1440 (DOW)
    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.adat = :adat AND i.anr = :anr AND i.berei = :berei AND i.wt = :wt AND i.splitt = :splitt")
    List<Invoice> findByOrderKey(@Param("pakz") String pakz,
                                  @Param("adat") String adat,
                                  @Param("anr") String anr,
                                  @Param("berei") String berei,
                                  @Param("wt") String wt,
                                  @Param("splitt") String splitt);

    // @origin HS1210 L1669-1669 (CHAIN)
    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.rnr = :rnr AND i.rdat = :rdat")
    Optional<Invoice> findByInvoiceNumber(@Param("pakz") String pakz,
                                           @Param("rnr") String rnr,
                                           @Param("rdat") String rdat);

    // @origin HS1210 L1027-1027 (CHAIN)
    @Query("SELECT i FROM Invoice i WHERE i.pakz = :pakz AND i.adat = :orderDate")
    List<Invoice> findWarrantyInvoicesByOrderDate(@Param("pakz") String pakz,
                                                   @Param("orderDate") String orderDate);
}