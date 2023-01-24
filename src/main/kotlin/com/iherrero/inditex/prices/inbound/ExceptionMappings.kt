package com.iherrero.inditex.prices.inbound

import com.iherrero.inditex.prices.domain.errors.GetPricesError
import com.iherrero.inditex.prices.domain.errors.PriceContextError
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.server.ResponseStatusException

val toPriceException: (PriceContextError) -> ResponseStatusException = {
    when (it) {
        is GetPricesError -> ResponseStatusException(INTERNAL_SERVER_ERROR, it.message)
    }
}
