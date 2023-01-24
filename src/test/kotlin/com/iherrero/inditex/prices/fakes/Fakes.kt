package com.iherrero.inditex.prices.fakes

import com.iherrero.inditex.prices.domain.model.Price
import com.iherrero.inditex.prices.outbound.persistence.model.PriceEntity
import org.javamoney.moneta.Money
import java.text.SimpleDateFormat

val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
const val EUR = "EUR"

val fakePrice1 =
    Price(
        brandId = 1,
        startDate = formatter.parse("2020-06-14T00:00:00"),
        endDate = formatter.parse("2020-12-31T23:59:59"),
        priceList = 1,
        productId = 35455,
        priority = 0,
        price = Money.of(35.50, EUR)
    )
val fakePrice2 =
    Price(
        brandId = 1,
        startDate = formatter.parse("2020-06-14T15:00:00"),
        endDate = formatter.parse("2020-06-14T18:30:00"),
        priceList = 2,
        productId = 35455,
        priority = 0,
        price = Money.of(25.45, EUR)
    )
val fakePrice3 =
    Price(
        brandId = 2,
        startDate = formatter.parse("2020-06-15T00:00:00"),
        endDate = formatter.parse("2020-06-15T11:00:00"),
        priceList = 3,
        productId = 35466,
        priority = 1,
        price = Money.of(30.50, EUR)
    )
val fakePrice4 =
    Price(
        brandId = 3,
        startDate = formatter.parse("2020-06-15T16:00:00"),
        endDate = formatter.parse("2020-12-31T23:59:59"),
        priceList = 4,
        productId = 35477,
        priority = 1,
        price = Money.of(38.95, EUR)
    )

val fakePriceEntity1 =
    PriceEntity(
        id = 1,
        brandId = 1,
        startDate = formatter.parse("2020-06-14T00:00:00"),
        endDate = formatter.parse("2020-12-31T23:59:59"),
        priceList = 1,
        productId = 35455,
        priority = 0,
        price = 35.50F,
        currency = EUR
    )
val fakePriceEntity2 =
    PriceEntity(
        id = 2,
        brandId = 1,
        startDate = formatter.parse("2020-06-14T15:00:00"),
        endDate = formatter.parse("2020-06-14T18:30:00"),
        priceList = 2,
        productId = 35455,
        priority = 0,
        price = 25.45F,
        currency = EUR
    )
val fakePriceEntity3 =
    PriceEntity(
        id = 3,
        brandId = 2,
        startDate = formatter.parse("2020-06-15T00:00:00"),
        endDate = formatter.parse("2020-06-15T11:00:00"),
        priceList = 3,
        productId = 35466,
        priority = 1,
        price = 30.50F,
        currency = EUR
    )
val fakePriceEntity4 =
    PriceEntity(
        id = 4,
        brandId = 3,
        startDate = formatter.parse("2020-06-15T16:00:00"),
        endDate = formatter.parse("2020-12-31T23:59:59"),
        priceList = 4,
        productId = 35477,
        priority = 1,
        price = 38.95F,
        currency = EUR
    )
