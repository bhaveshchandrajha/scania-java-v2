/**
 * Spring Data JPA repository for warranty claim data access.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.repository;

import com.scania.warranty.domain.ItemMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for item master data (ITLSMF3) - referenced for part validation.
 */
@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMasterEntity, String> {

    // @origin HS1210 L2838-2838 (CHAIN)
    @Query("SELECT i FROM ItemMasterEntity i WHERE i.itemNumber = :itemNumber AND i.range = 'SC'")
    ItemMasterEntity findByItemNumberAndRangeScania(@Param("itemNumber") String itemNumber);
}