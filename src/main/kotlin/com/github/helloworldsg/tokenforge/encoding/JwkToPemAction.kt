package com.github.helloworldsg.tokenforge.encoding

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.nimbusds.jose.jwk.RSAKey
import io.ktor.util.*
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import java.io.IOException
import java.io.StringWriter
import java.security.MessageDigest
import java.util.*


class JwkToPemAction : AnAction() {
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
        val jwk = RSAKey.parse(s)
        if (jwk.isPrivate()) {
            return formatPEM(jwk.toRSAPrivateKey());
        }

        return formatPEM(jwk.toRSAPublicKey());
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

    override fun update(e: AnActionEvent) {
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabledAndVisible = project != null && editor != null && editor.selectionModel.hasSelection()
    }
}