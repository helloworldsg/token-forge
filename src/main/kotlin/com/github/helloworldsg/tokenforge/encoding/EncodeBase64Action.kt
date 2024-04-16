package com.github.helloworldsg.tokenforge.encoding

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import org.apache.commons.codec.binary.Base64
import java.nio.charset.Charset

class EncodeBase64Action : AnAction() {
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
        val charset = Charset.forName("UTF-8");
        var encodeBase64 = Base64.encodeBase64(s.toByteArray(charset))
        return String(encodeBase64!!, charset)
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabledAndVisible = project != null && editor != null && editor.selectionModel.hasSelection()
    }
}