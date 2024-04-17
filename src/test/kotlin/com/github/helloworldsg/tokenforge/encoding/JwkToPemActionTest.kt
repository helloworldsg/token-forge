package com.github.helloworldsg.tokenforge.encoding

import org.junit.Assert.assertEquals
import org.junit.Test

class JwkToPemActionTest {
    private val jwkToPemAction = JwkToPemAction()

    @Test
    fun testTransform() {
        val originalString = "{\"kty\":\"RSA\",\"e\":\"AQAB\",\"n\":\"5Rm_baNWYS2ZSHH2Z965jeu3noaACpEO-jglr0aIguVzqKCbJF0NH8xlbgyw0FaEGIeaBpsQoXPftFg5a27B9hXVqKg_qhIGjTGsf7A01480Z4gJzRQR4k5FVmkfeAKA2txHkSm7NsljXMXg1y2He6G3MrB7MLoqLzGq7qNn2ts\"}"
        val encodedString = jwkToPemAction.transform(originalString)
        val expected = "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDlGb9to1ZhLZlIcfZn3rmN67ee\n" +
                "hoAKkQ76OCWvRoiC5XOooJskXQ0fzGVuDLDQVoQYh5oGmxChc9+0WDlrbsH2FdWo\n" +
                "qD+qEgaNMax/sDTXjzRniAnNFBHiTkVWaR94AoDa3EeRKbs2yWNcxeDXLYd7obcy\n" +
                "sHswuiovMaruo2fa2wIDAQAB\n" +
                "-----END PUBLIC KEY-----\n"
        assertEquals(encodedString, expected)
    }

}