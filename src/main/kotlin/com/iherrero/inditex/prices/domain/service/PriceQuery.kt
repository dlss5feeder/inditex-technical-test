package com.iherrero.inditex.prices.domain.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.iherrero.inditex.config.logger
import com.iherrero.inditex.prices.domain.errors.GetPricesError
import com.iherrero.inditex.prices.domain.errors.PriceContextError
import com.iherrero.inditex.prices.domain.model.Price
import com.iherrero.inditex.prices.domain.repository.PriceRepository
import com.iherrero.inditex.prices.outbound.persistence.model.toPrice
import org.springframework.stereotype.Service

/**
 * Implements query operations on Prices
 */
@Service
class PriceQuery(
    private val priceRepository: PriceRepository
) {

    companion object {
        val log = logger()
    }

    /**
     * Finds prices by criteria
     * @param criteria the criteria to search prices
     * @return Either<PriceContextError, List<Price>> the list of prices that match the criteria, or the error occurred
     */
    fun getPrices(criteria: PricesCriteria): Either<PriceContextError, List<Price>> =
        runCatching {
            priceRepository.findAllByCriteria(
                criteria.applicationDate,
                criteria.productId,
                criteria.brandId
            ).map { it.toPrice() }
                .right()
        }.getOrElse {
            log.error("There was an error retrieving prices: ${it.message}")
            GetPricesError("${it.message}").left()
        }
}
