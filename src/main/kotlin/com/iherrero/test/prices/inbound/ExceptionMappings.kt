package com.iherrero.test.prices.inbound

import com.iherrero.test.prices.domain.errors.GetPricesError
import com.iherrero.test.prices.domain.errors.PriceContextError
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.server.ResponseStatusException

val toPriceException: (PriceContextError) -> ResponseStatusException = {
    when (it) {
        is GetPricesError -> ResponseStatusException(INTERNAL_SERVER_ERROR, it.message)
    }
}
