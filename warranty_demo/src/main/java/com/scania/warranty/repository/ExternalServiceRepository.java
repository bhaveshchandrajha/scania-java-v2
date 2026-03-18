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

@Repository
public interface ExternalServiceRepository extends JpaRepository<ExternalService, ExternalServiceId> {

    @Query("SELECT es FROM ExternalService es WHERE es.fla000 = :pkz AND es.fla010 = :invoiceDate " +
           "AND es.fla020 = :orderNr AND es.fla230 > '3' ORDER BY es.fla030")
    List<ExternalService> findExternalServicesForClaim(@Param("pkz") String pkz,
                                                       @Param("invoiceDate") String invoiceDate,
                                                       @Param("orderNr") String orderNr); // @rpg-trace: n1319

    @Query("SELECT es FROM ExternalService es WHERE es.fla000 = :pkz AND es.fla010 = :invoiceDate " +
           "AND es.fla020 = :orderNr ORDER BY es.fla030")
    List<ExternalService> findByInvoiceKey(@Param("pkz") String pkz, @Param("invoiceDate") String invoiceDate,
                                            @Param("orderNr") String orderNr);
}