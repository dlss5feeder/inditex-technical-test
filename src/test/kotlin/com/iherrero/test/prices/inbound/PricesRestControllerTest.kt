package com.iherrero.test.prices.inbound

import arrow.core.left
import arrow.core.right
import com.iherrero.test.MvcTestBase
import com.iherrero.test.prices.domain.errors.GetPricesError
import com.iherrero.test.prices.domain.model.Price
import com.iherrero.test.prices.domain.service.PriceQuery
import com.iherrero.test.prices.domain.service.PricesCriteria
import com.iherrero.test.prices.fakes.fakePrice1
import com.iherrero.test.prices.fakes.fakePrice2
import com.iherrero.test.prices.fakes.fakePrice3
import com.iherrero.test.prices.fakes.fakePrice4
import com.iherrero.test.prices.fakes.formatter
import com.iherrero.test.utils.getResource
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private const val PRICES_JSON_PATH = "/testJsons/prices"

@WebMvcTest(PricesRestController::class)
internal class CustomerProfileRestControllerTest : MvcTestBase() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var pricesQuery: PriceQuery

    @Test
    fun `GIVEN any filters WHEN an exception occurs THEN response is ERROR`() {
        val searchCriteria = PricesCriteria()

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(GetPricesError.left())

        // Act and Assert
        mockMvc.perform(
            get(GET_PRICES_V1_PATH)
        )
            .andExpect(status().is5xxServerError)

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN no filters WHEN get prices THEN response is OK and all prices are returned`() {
        val searchCriteria = PricesCriteria()
        val mockedPrices = listOf(fakePrice1, fakePrice2, fakePrice3, fakePrice4)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/allPrices.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get(GET_PRICES_V1_PATH)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an existing brandId WHEN get prices THEN response is OK and all prices for that brand are returned`() {
        val searchCriteria = PricesCriteria(brandId = 1)
        val mockedPrices = listOf(fakePrice1, fakePrice2, fakePrice3, fakePrice4)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/allPrices.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?brandId=${searchCriteria.brandId}")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an non existing brandId WHEN get prices THEN response is OK and no prices are returned`() {
        val searchCriteria = PricesCriteria(brandId = 999)
        val mockedPrices = emptyList<Price>()
        val pricesJsonResponse = "[]"

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?brandId=${searchCriteria.brandId}")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an existing productId WHEN get prices THEN response is OK and all prices for that brand are returned`() {
        val searchCriteria = PricesCriteria(productId = 35455)
        val mockedPrices = listOf(fakePrice1, fakePrice2, fakePrice3, fakePrice4)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/allPrices.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?productId=${searchCriteria.productId}")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an non existing productId WHEN get prices THEN response is OK and no prices are returned`() {
        val searchCriteria = PricesCriteria(productId = 999)
        val mockedPrices = emptyList<Price>()
        val pricesJsonResponse = "[]"

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?productId=${searchCriteria.productId}")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an the application date 2020-06-14 10_00_00 WHEN get prices THEN response is OK and the right prices are returned`() {
        val applicationDate = "2020-06-14T10:00:00"
        val searchCriteria = PricesCriteria(applicationDate = formatter.parse(applicationDate))
        val mockedPrices = listOf(fakePrice1)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-2020-06-14T10:00:00.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?applicationDate=$applicationDate")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an the application date 2020-06-14 16_00_00 WHEN get prices THEN response is OK and the right prices are returned`() {
        val applicationDate = "2020-06-14T16:00:00"
        val searchCriteria = PricesCriteria(applicationDate = formatter.parse(applicationDate))
        val mockedPrices = listOf(fakePrice1, fakePrice2)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-2020-06-14T16:00:00.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?applicationDate=$applicationDate")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an the application date 2020-06-14 21_00_00 WHEN get prices THEN response is OK and the right prices are returned`() {
        val applicationDate = "2020-06-14T21:00:00"
        val searchCriteria = PricesCriteria(applicationDate = formatter.parse(applicationDate))
        val mockedPrices = listOf(fakePrice1)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-2020-06-14T21:00:00.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?applicationDate=$applicationDate")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an the application date 2020-06-15 10_00_00 WHEN get prices THEN response is OK and the right prices are returned`() {
        val applicationDate = "2020-06-15T10:00:00"
        val searchCriteria = PricesCriteria(applicationDate = formatter.parse(applicationDate))
        val mockedPrices = listOf(fakePrice1, fakePrice3)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-2020-06-15T10:00:00.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?applicationDate=$applicationDate")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }

    @Test
    fun `GIVEN an the application date 2020-06-21 21_00_00 WHEN get prices THEN response is OK and the right prices are returned`() {
        val applicationDate = "2020-06-16T21:00:00"
        val searchCriteria = PricesCriteria(applicationDate = formatter.parse(applicationDate))
        val mockedPrices = listOf(fakePrice1, fakePrice4)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-2020-06-16T21:00:00.json")

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(mockedPrices.right())

        // Act and Assert
        mockMvc.perform(
            get("$GET_PRICES_V1_PATH?applicationDate=$applicationDate")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(pricesJsonResponse))

        verify(pricesQuery).getPrices(searchCriteria)
    }
}
