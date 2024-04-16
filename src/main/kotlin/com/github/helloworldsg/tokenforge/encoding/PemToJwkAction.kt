package com.github.helloworldsg.tokenforge.encoding

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.nimbusds.jose.jwk.RSAKey
import org.apache.commons.codec.binary.Base64
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider
import org.bouncycastle.util.io.pem.PemReader
import java.io.StringReader
import java.nio.charset.Charset
import java.security.MessageDigest


class PemToJwkAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = e.getRequiredData(CommonDataKeys.PROJECT)
        val document = editor.document
        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd

        WriteCommandAction.runWriteCommandAction(project
        ) { document.replaceString(start, end, transform(editor.getSelectionModel().getSelectedText().toString())) }
        primaryCaret.removeSelection()
    }

    protected fun transform(s: String): String {
        val jwk = RSAKey.parseFromPEMEncodedObjects(s)
        val sha256Digest = MessageDigest.getInstance("SHA-256").digest(jwk.toRSAKey().toRSAPublicKey().encoded)
        val kid: String = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(sha256Digest)

        return "// kid: $kid\n$jwk"
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabledAndVisible = project != null && editor != null && editor.selectionModel.hasSelection()
    }
}