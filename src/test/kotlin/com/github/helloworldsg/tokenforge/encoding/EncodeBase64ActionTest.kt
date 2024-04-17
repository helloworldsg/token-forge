package com.github.helloworldsg.tokenforge.encoding

import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.charset.StandardCharsets
import java.util.*

class EncodeBase64ActionTest {
    private val encodeBase64Action = EncodeBase64Action()

    @Test
    fun testTransform() {
        val originalString = "Hello, World!"
        val encodedString = encodeBase64Action.transform(originalString)
        val decodedString = String(Base64.getDecoder().decode(encodedString), StandardCharsets.UTF_8)
        assertEquals(originalString, decodedString)
    }
}
