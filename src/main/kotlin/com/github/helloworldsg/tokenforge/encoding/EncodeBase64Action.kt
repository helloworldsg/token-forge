package com.github.helloworldsg.tokenforge.encoding

import com.github.helloworldsg.tokenforge.BaseAction
import org.apache.commons.codec.binary.Base64
import java.nio.charset.Charset

class EncodeBase64Action : BaseAction() {
    override fun transform(s: String): String {
        val charset = Charset.forName("UTF-8");
        var encodeBase64 = Base64.encodeBase64(s.toByteArray(charset))
        return String(encodeBase64!!, charset)
    }
}