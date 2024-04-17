package com.github.helloworldsg.tokenforge.encoding

import org.junit.Assert.assertEquals
import org.junit.Test

class DecodeJwtActionTest {
    private val action = DecodeJwtAction()

    @Test
    fun testTransformExpiredJwt() {
        val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyMzkwMjJ9.4Adcj3UFYzPUVaVF43FmMab6RlaQD8A9V8wFzzht-KQ"

        val result = action.transform(jwt)
        val expected = ".*claim is expired.*"
        val re = Regex(expected)
        assert(re.containsMatchIn(result))
    }

    @Test
    fun testTransformInvalidJwt() {
        val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        val result = action.transform(jwt)
        val expected = "{\n" +
                "  \"alg\" : \"HS256\",\n" +
                "  \"typ\" : \"JWT\"\n" +
                "}\n" +
                "{\n" +
                "  \"sub\" : \"1234567890\",\n" +
                "  \"name\" : \"John Doe\",\n" +
                "  \"iat\" : 1516239022\n" +
                "}"
        assertEquals(result, expected)
    }

}