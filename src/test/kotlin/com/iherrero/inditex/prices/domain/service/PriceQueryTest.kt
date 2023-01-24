package com.iherrero.inditex.prices.domain.service

import com.iherrero.inditex.prices.domain.errors.GetPricesError
import com.iherrero.inditex.prices.domain.repository.PriceRepository
import com.iherrero.inditex.prices.fakes.fakePrice1
import com.iherrero.inditex.prices.fakes.fakePrice2
import com.iherrero.inditex.prices.fakes.fakePriceEntity1
import com.iherrero.inditex.prices.fakes.fakePriceEntity2
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito.mock

internal class PriceQueryTest {

    private val priceRepository: PriceRepository = mock(PriceRepository::class.java)

    lateinit var priceQuery: PriceQuery

    @BeforeEach
    fun setUp() {
        priceQuery = PriceQuery(priceRepository)
    }

    @Test
    fun `GIVEN empty criteria WHEN get prices THEN all prices are returned`() {
        val criteria = PricesCriteria()
        val mockedPrices = listOf(fakePriceEntity1, fakePriceEntity2)
        val expectedPrices = listOf(fakePrice1, fakePrice2)

        // Arrange
        given(priceRepository.findAllByCriteria(criteria.applicationDate, criteria.productId, criteria.brandId))
            .willReturn(mockedPrices)

        // Act
        val result = priceQuery.getPrices(criteria)

        // Assert
        verify(priceRepository).findAllByCriteria(criteria.applicationDate, criteria.productId, criteria.brandId)
        result.shouldBeRight(expectedPrices)
    }

    @Test
    fun `GIVEN any criteria WHEN unexpected exception THEN an error is returned`() {
        val criteria = PricesCriteria()

        // Arrange
        given(priceRepository.findAllByCriteria(criteria.applicationDate, criteria.productId, criteria.brandId))
            .willThrow(IllegalArgumentException("Error connecting test prices database"))

        // Act
        val result = priceQuery.getPrices(criteria)

        // Assert
        verify(priceRepository).findAllByCriteria(criteria.applicationDate, criteria.productId, criteria.brandId)
        result.shouldBeLeft(GetPricesError)
    }

    /* More tests could be added here... */
}
