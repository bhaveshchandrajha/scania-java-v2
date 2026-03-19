/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1983}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.InvoiceHeader;
import com.scania.warranty.domain.InvoiceHeaderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceHeaderRepository extends JpaRepository<InvoiceHeader, InvoiceHeaderId> {

    /**
     * RPG CHAIN: (G71000:G71010:G71020:' ':G71030:G71040:G71190:%Subst(G71200:8:2)) HSAHKPF
     * Looks up an invoice header by its composite key including the agreement type code.
     */
    @Query("SELECT h FROM InvoiceHeader h WHERE h.ahk000 = :ahk000 AND h.ahk010 = :ahk010 " +
           "AND h.ahk020 = :ahk020 AND h.ahk030 = :ahk030 AND h.ahk040 = :ahk040 " +
           "AND h.ahk050 = :ahk050 AND h.ahk060 = :ahk060")
    Optional<InvoiceHeader> findByCompositeKey(
        @Param("ahk000") String ahk000,
        @Param("ahk010") String ahk010,
        @Param("ahk020") String ahk020,
        @Param("ahk030") String ahk030,
        @Param("ahk040") String ahk040,
        @Param("ahk050") String ahk050,
        @Param("ahk060") String ahk060
    );
}