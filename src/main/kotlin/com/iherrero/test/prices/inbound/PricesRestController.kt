package com.iherrero.test.prices.inbound

import arrow.core.Either
import com.iherrero.test.prices.domain.service.PriceQuery
import com.iherrero.test.prices.domain.service.PricesCriteria
import com.iherrero.test.prices.inbound.dto.GetPricesV1Response
import com.iherrero.test.prices.inbound.dto.toGetPricesV1Response
import com.iherrero.test.utils.toDate
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

const val GET_PRICES_V1_PATH = "/api/v1/prices"

@RestController
@Api(tags = ["Prices Rest API"])
@Validated
class PricesRestController(
    private val pricesQuery: PriceQuery
) {

    @ApiOperation(value = "Get prices information by filtering criteria")
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Data successfully retrieved"),
            ApiResponse(code = 500, message = "An internal server error ocurred")
        ]
    )
    @Throws(ResponseStatusException::class)
    @GetMapping(GET_PRICES_V1_PATH)
    fun getPrices(
        @ApiParam(name = "applicationDate", value = "Application Date", example = "2020-06-14T15:04:06")
        @RequestParam(required = false) @DateTimeFormat(iso = DATE_TIME) applicationDate: LocalDateTime?,
        @ApiParam(name = "productId", value = "Product id", example = "35455")
        @RequestParam(required = false) productId: Long?,
        @ApiParam(name = "brandId", value = "Brand id", example = "1")
        @RequestParam(required = false) brandId: Long?
    ): ResponseEntity<List<GetPricesV1Response>> =
        when (val prices = pricesQuery.getPrices(PricesCriteria(applicationDate.toDate(), productId, brandId))) {
            is Either.Left -> throw toPriceException(prices.value)
            is Either.Right -> ResponseEntity.ok().body(prices.value.map { it.toGetPricesV1Response() })
        }
}
