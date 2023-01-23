package com.iherrero.test.prices.domain.repository

import com.iherrero.test.prices.outbound.persistence.model.PriceEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.Date

/**
 * This repository works over the prices domain entity
 */
interface PriceRepository : CrudRepository<PriceEntity, Long> {

    /**
     * Finds prices by criteria
     * @param applicationDate the application date
     * @param productId the product identifier
     * @param brandId the brand identifier
     * @return List<PriceEntity> the list of prices that match the received criteria
     */
    @Query(
        """
            SELECT p 
            FROM PriceEntity p 
            WHERE
            (:applicationDate is null or (p.startDate <= :applicationDate and :applicationDate < p.endDate)) and
            (:productId is null or p.productId = :productId) and
            (:brandId is null or p.brandId = :brandId)
        """
    )
    fun findAllByCriteria(
        applicationDate: Date?,
        productId: Long?,
        brandId: Long?
    ): List<PriceEntity>

}
