package com.github.helloworldsg.tokenforge.encoding

import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.charset.StandardCharsets
import java.util.*

class DecodeBase64ActionTest {
    private val decodeBase64Action = DecodeBase64Action()

    @Test
    fun testTransform() {
        val originalString = "Hello, World!"
        val encodedString = Base64.getEncoder().encodeToString(originalString.toByteArray(StandardCharsets.UTF_8))
        val result = decodeBase64Action.transform(encodedString)
        assertEquals(originalString, result)
    }
}