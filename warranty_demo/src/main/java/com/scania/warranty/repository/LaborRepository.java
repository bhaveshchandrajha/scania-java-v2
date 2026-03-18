/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.Labor;
import com.scania.warranty.domain.LaborId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaborRepository extends JpaRepository<Labor, LaborId> {

    @Query("SELECT l FROM Labor l WHERE l.ahw000 = :pkz AND l.ahw010 = :invoiceNr AND l.ahw020 = :invoiceDate " +
           "AND l.ahw040 = :orderNr ORDER BY l.ahw080, l.ahw100")
    List<Labor> findByInvoiceKey(@Param("pkz") String pkz, @Param("invoiceNr") String invoiceNr,
                                  @Param("invoiceDate") String invoiceDate, @Param("orderNr") String orderNr); // @rpg-trace: n1213

    @Query("SELECT l FROM Labor l WHERE l.ahw000 = :pkz AND l.ahw010 = :invoiceNr AND l.ahw020 = :invoiceDate " +
           "AND l.ahw040 = :orderNr ORDER BY l.ahw080, l.ahw100")
    List<Labor> findLaborByInvoiceKey(@Param("pkz") String pkz, @Param("invoiceNr") String invoiceNr,
                                      @Param("invoiceDate") String invoiceDate, @Param("orderNr") String orderNr);
}