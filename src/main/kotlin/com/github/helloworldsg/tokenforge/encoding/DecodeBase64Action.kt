package com.github.helloworldsg.tokenforge.encoding

import com.github.helloworldsg.tokenforge.BaseAction
import org.apache.commons.codec.binary.Base64
import java.nio.charset.Charset

class DecodeBase64Action : BaseAction() {
    override fun transform(s: String): String {
        val charset = Charset.forName("UTF-8")
        val decodeBase64 = Base64.decodeBase64(s.toByteArray(charset))
        return String(decodeBase64!!, charset)
    }
}