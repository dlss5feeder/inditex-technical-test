package com.iherrero.inditex.prices.domain.errors

sealed class PriceContextError(val message: String)

data class GetPricesError(val msg: String) : PriceContextError(message = msg)
