package com.iherrero.inditex.prices.domain.model

import org.javamoney.moneta.Money
import java.util.Date

data class Price(
    val brandId: Long,
    val startDate: Date,
    val endDate: Date,
    val priceList: Long,
    val productId: Long,
    val priority: Int,
    val price: Money
)
