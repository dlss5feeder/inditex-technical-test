package com.iherrero.inditex.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class JacksonConfiguration {

    @Autowired
    fun objectMapper(objectMapper: ObjectMapper) {
        objectMapper.setTimeZone(TimeZone.getDefault())
    }
}
