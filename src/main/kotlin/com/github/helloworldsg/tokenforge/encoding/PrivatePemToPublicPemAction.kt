package com.github.helloworldsg.tokenforge.encoding

import com.github.helloworldsg.tokenforge.BaseAction
import com.nimbusds.jose.jwk.RSAKey
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import java.io.IOException
import java.io.StringWriter


class PrivatePemToPublicPemAction : BaseAction() {
    override fun transform(s: String): String {
        val jwk = RSAKey.parseFromPEMEncodedObjects(s)
        return formatPEM(jwk.toRSAKey().toRSAPublicKey());
    }

    private fun formatPEM(obj: Any): String {
        val sw = StringWriter()
        val writer = JcaPEMWriter(sw)
        try {
            writer.writeObject(obj)
            writer.flush()
        } catch (e: IOException) {
            throw RuntimeException(e)
        } finally {
            writer.close()
        }
        return sw.toString()
    }
}