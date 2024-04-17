package com.github.helloworldsg.tokenforge.encoding

import org.junit.Assert.assertEquals
import org.junit.Test

class PrivatePemToPublicPemActionTest {
    private val privatePemToPublicPemAction = PrivatePemToPublicPemAction()

    @Test
    fun testTransform() {
        val originalString = "-----BEGIN RSA PRIVATE KEY-----\n" +
                "MIIBOQIBAAJAXWRPQyGlEY+SXz8Uslhe+MLjTgWd8lf/nA0hgCm9JFKC1tq1S73c\n" +
                "Q9naClNXsMqY7pwPt1bSY8jYRqHHbdoUvwIDAQABAkAfJkz1pCwtfkig8iZSEf2j\n" +
                "VUWBiYgUA9vizdJlsAZBLceLrdk8RZF2YOYCWHrpUtZVea37dzZJe99Dr53K0UZx\n" +
                "AiEAtyHQBGoCVHfzPM//a+4tv2ba3tx9at+3uzGR86YNMzcCIQCCjWHcLW/+sQTW\n" +
                "OXeXRrtxqHPp28ir8AVYuNX0nT1+uQIgJm158PMtufvRlpkux78a6mby1oD98Ecx\n" +
                "jp5AOhhF/NECICyHsQN69CJ5mt6/R01wMOt5u9/eubn76rbyhPgk0h7xAiEAjn6m\n" +
                "EmLwkIYD9VnZfp9+2UoWSh0qZiTIHyNwFpJH78o=\n" +
                "-----END RSA PRIVATE KEY-----\n"
        val encodedString = privatePemToPublicPemAction.transform(originalString)
        val expected = "-----BEGIN PUBLIC KEY-----\n" +
                "MFswDQYJKoZIhvcNAQEBBQADSgAwRwJAXWRPQyGlEY+SXz8Uslhe+MLjTgWd8lf/\n" +
                "nA0hgCm9JFKC1tq1S73cQ9naClNXsMqY7pwPt1bSY8jYRqHHbdoUvwIDAQAB\n" +
                "-----END PUBLIC KEY-----\n"
        assertEquals(encodedString, expected)
    }

}