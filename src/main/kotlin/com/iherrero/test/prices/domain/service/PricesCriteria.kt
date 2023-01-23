package com.iherrero.test.prices.domain.service

import java.util.Date

data class PricesCriteria(
    val applicationDate: Date? = null,
    val productId: Long? = null,
    val brandId: Long? = null
)
