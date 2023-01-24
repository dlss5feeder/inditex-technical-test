package com.iherrero.inditex.utils

/** Extension functions **/
fun getResource(filePath: String) = {}.javaClass.getResource(filePath)?.readText() ?: ""
