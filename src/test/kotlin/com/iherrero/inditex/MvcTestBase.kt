package com.iherrero.inditex

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import java.util.TimeZone

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class MvcTestBase {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @BeforeAll
    fun prepare() {
        objectMapper.setTimeZone(TimeZone.getDefault())
    }
}
