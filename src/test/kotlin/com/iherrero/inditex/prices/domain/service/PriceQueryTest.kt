package com.iherrero.inditex.prices.domain.service

import com.iherrero.inditex.prices.domain.errors.GetPricesError
import com.iherrero.inditex.prices.domain.repository.PriceRepository
import com.iherrero.inditex.prices.fakes.fakePrice1
import com.iherrero.inditex.prices.fakes.fakePrice2
import com.iherrero.inditex.prices.fakes.fakePrice3
import com.iherrero.inditex.prices.fakes.fakePrice4
import com.iherrero.inditex.prices.fakes.fakePriceEntity1
import com.iherrero.inditex.prices.fakes.fakePriceEntity2
import com.iherrero.inditex.prices.fakes.fakePriceEntity3
import com.iherrero.inditex.prices.fakes.fakePriceEntity4
import com.iherrero.inditex.prices.fakes.formatter
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito.mock

internal class PriceQueryTest {

    private val priceRepository: PriceRepository = mock(PriceRepository::class.java)

    private lateinit var priceQuery: PriceQuery

    @BeforeEach
    fun setUp() {
        priceQuery = PriceQuery(priceRepository)
    }

    /*
    * The scope of the tests included here is to verify the business logic of the service tested
    * There is no point in testing anything else, as the responsibility of this layer is
    * to map the repository data and return a result/error
    */

    @Test
    fun `GIVEN an empty criteria WHEN get prices THEN all the prices are returned`() {
        val criteria = PricesCriteria()
        val mockedPrices = listOf(fakePriceEntity1, fakePriceEntity2, fakePriceEntity3, fakePriceEntity4)
        val expectedPrices = listOf(fakePrice1, fakePrice2, fakePrice3, fakePrice4)

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
        result.shouldBeLeft(GetPricesError("Error connecting test prices database"))
    }

    /*
    * From this point, more tests included, but not needed
    */

    @Test
    fun `GIVEN a brandId WHEN get prices THEN response is OK and all prices for that brand are returned`() {
        val criteria = PricesCriteria(brandId = 1)
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
    fun `GIVEN a productId WHEN get prices THEN response is OK and all prices for that product are returned`() {
        val criteria = PricesCriteria(productId = 35455)
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
    fun `GIVEN an the application date 2020-06-14 16_00_00 WHEN get prices THEN response is OK and the right prices are returned`() {
        val applicationDate = "2020-06-14T16:00:00"
        val criteria = PricesCriteria(applicationDate = formatter.parse(applicationDate))
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

    /* More tests could be added here... */
}
