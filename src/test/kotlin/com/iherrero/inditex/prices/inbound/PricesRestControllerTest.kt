package com.iherrero.inditex.prices.inbound

import arrow.core.left
import arrow.core.right
import com.iherrero.inditex.MvcTestBase
import com.iherrero.inditex.prices.domain.errors.GetPricesError
import com.iherrero.inditex.prices.domain.service.PriceQuery
import com.iherrero.inditex.prices.domain.service.PricesCriteria
import com.iherrero.inditex.prices.fakes.fakePrice1
import com.iherrero.inditex.prices.fakes.fakePrice2
import com.iherrero.inditex.prices.fakes.fakePrice3
import com.iherrero.inditex.prices.fakes.fakePrice4
import com.iherrero.inditex.prices.fakes.formatter
import com.iherrero.inditex.utils.getResource
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

    /*
    * The scope of the tests included here is only to verify the API contract
    * There is no point in testing anything else, as the responsibility of this layer is
    * to map the service result and return an OK/ERROR response
    */

    @Test
    fun `GIVEN any filters WHEN an exception occurs THEN response is ERROR`() {
        val searchCriteria = PricesCriteria()

        // Arrange
        given(pricesQuery.getPrices(searchCriteria))
            .willReturn(GetPricesError("Error retrieving prices").left())

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
    fun `GIVEN a brandId as parameter WHEN get prices THEN response is OK and all prices for that brand are returned`() {
        val searchCriteria = PricesCriteria(brandId = 1)
        val mockedPrices = listOf(fakePrice1, fakePrice2)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-brandId-1.json")

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
    fun `GIVEN a productId as parameter WHEN get prices THEN response is OK and all prices for that product are returned`() {
        val searchCriteria = PricesCriteria(productId = 35455)
        val mockedPrices = listOf(fakePrice1, fakePrice2)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-productId-35455.json")

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
    fun `GIVEN an applicationDate as parameter WHEN get prices THEN response is OK and the right prices are returned`() {
        val applicationDate = "2020-06-14T16:00:00"
        val searchCriteria = PricesCriteria(applicationDate = formatter.parse(applicationDate))
        val mockedPrices = listOf(fakePrice1, fakePrice2)
        val pricesJsonResponse = getResource("$PRICES_JSON_PATH/prices-applicationDate-2020-06-14T16:00:00.json")

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

    /* More tests could be added here... */
}
