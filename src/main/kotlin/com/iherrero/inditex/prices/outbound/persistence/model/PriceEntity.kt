package com.iherrero.inditex.prices.outbound.persistence.model

import com.iherrero.inditex.prices.domain.model.Price
import org.javamoney.moneta.Money
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

/**************/
/** ENTITIES **/
/**************/

@Entity
@Table(name = "PRICES")
data class PriceEntity(
    @Id
    @Column(name = "ID", nullable = false)
    val id: Long? = null,

    @Column(name = "BRAND_ID", nullable = false)
    val brandId: Long,

    @Column(name = "START_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val startDate: Date,

    @Column(name = "END_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val endDate: Date,

    @Column(name = "PRICE_LIST", nullable = false)
    val priceList: Long,

    @Column(name = "PRODUCT_ID", nullable = false)
    val productId: Long,

    @Column(name = "PRIORITY", nullable = false)
    val priority: Int,

    @Column(name = "PRICE", nullable = false)
    val price: Float,

    @Column(name = "CURR", nullable = false)
    val currency: String
)

/*******************/
/** MAP FUNCTIONS **/
/*******************/
fun PriceEntity.toPrice() =
    Price(
        brandId = brandId,
        startDate = startDate,
        endDate = endDate,
        priceList = priceList,
        productId = productId,
        priority = priority,
        price = Money.of(price, currency)
    )
