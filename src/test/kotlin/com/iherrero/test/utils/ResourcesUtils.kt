package com.iherrero.test.utils

/** Extension functions **/
fun getResource(filePath: String) = {}.javaClass.getResource(filePath)?.readText() ?: ""
