package com.github.helloworldsg.tokenforge.encoding

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.helloworldsg.tokenforge.BaseAction
import com.nimbusds.jose.util.Base64
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class DecodeJwtAction : BaseAction() {
    override fun transform(s: String): String {
        val (header, payload, signature) = s.split('.')

        val decodedHeader = Base64.from(header).decodeToString()
        val decodedPayload = Base64.from(payload).decodeToString()
        val jsonPayload = Json.parseToJsonElement(decodedPayload)

        val mapper = ObjectMapper();
        val prettyHeader = mapper.readTree(decodedHeader).toPrettyString()
        val prettyPayload = mapper.readTree(decodedPayload).toPrettyString()

        val exp = jsonPayload.jsonObject["exp"] ?: return prettyHeader + "\n" + prettyPayload

        val expirationTime = exp.toString().toLong() * 1000L
        val localDateTime =
            Instant.fromEpochMilliseconds(expirationTime).toLocalDateTime(TimeZone.currentSystemDefault()).toString()

        var comments = "// `exp` claim is valid ($localDateTime)"
        if (expirationTime > 0 && expirationTime < System.currentTimeMillis()) {
            comments = "// `exp` claim is expired ($localDateTime)"
        }
        return comments + "\n" + prettyHeader + "\n" + prettyPayload
    }
}