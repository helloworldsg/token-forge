package com.github.helloworldsg.tokenforge.encoding

import com.github.helloworldsg.tokenforge.BaseAction
import com.nimbusds.jose.jwk.RSAKey
import java.security.MessageDigest


class PemToJwkAction : BaseAction() {

    override fun transform(s: String): String {
        val jwk = RSAKey.parseFromPEMEncodedObjects(s)
        val sha256Digest = MessageDigest.getInstance("SHA-256").digest(jwk.toRSAKey().toRSAPublicKey().encoded)
        val kid: String = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Digest)

        return "// kid: $kid\n$jwk"
    }
}