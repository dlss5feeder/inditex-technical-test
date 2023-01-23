package com.iherrero.test.prices.inbound.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.iherrero.test.prices.domain.model.Price
import java.util.Date

data class GetPricesV1Response(
    val productId: Long,
    val brandId: Long,
    val priceList: Long,
    val price: Money,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val startDate: Date,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val endDate: Date
)

fun Price.toGetPricesV1Response() =
    GetPricesV1Response(
        productId = productId,
        brandId = brandId,
        priceList = priceList,
        price = Money(price.numberStripped, "${price.currency}"),
        startDate = startDate,
        endDate = endDate
    )
