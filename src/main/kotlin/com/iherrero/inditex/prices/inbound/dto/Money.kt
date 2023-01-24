package com.iherrero.inditex.prices.inbound.dto

import java.math.BigDecimal

/**
 * Use to a formatted pretty price; zalando or any other plugin could be used instead
 */
data class Money(
    val amount: BigDecimal,
    val currency: String
)
