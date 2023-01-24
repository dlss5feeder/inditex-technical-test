package com.iherrero.inditex.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

/** Extension functions **/
fun LocalDateTime?.toDate(): Date? =
    this?.let {
        Date.from(it.atZone(ZoneId.systemDefault()).toInstant())
    }
