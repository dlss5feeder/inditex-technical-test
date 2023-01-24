package com.iherrero.inditex.prices.domain.errors

sealed class PriceContextError(val message: String)

object GetPricesError : PriceContextError("There was an error retrieving prices")
