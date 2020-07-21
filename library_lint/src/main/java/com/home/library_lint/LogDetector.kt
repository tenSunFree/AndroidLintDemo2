package com.home.library_lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class LogDetector : Detector(), SourceCodeScanner {
    companion object {
        val ISSUE_LOG: Issue = Issue.create(
            "LogDetector",
            "LogDetector",
            "LogDetector",
            Category.CORRECTNESS,
            7,
            Severity.ERROR,
            Implementation(LogDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableMethodNames(): List<String>? {
        return listOf("d")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        super.visitMethodCall(context, node, method)
        if (context.evaluator.isMemberInClass(method, "android.util.Log")) {
            context.report(
                ISSUE_LOG,
                node,
                context.getLocation(node),
                "麻煩使用LogUtil，額外提供快速跳轉的功能。",
                quickFixIssueLog(node)
            )
        }
    }

    private fun quickFixIssueLog(logCall: UCallExpression): LintFix? {
        val arguments = logCall.valueArguments
        val methodName = logCall.methodName
        val tag = arguments[0]
        var fixedSourceCode = "LogUtil." + methodName + "(" + tag.asSourceString()
        val numArguments = arguments.size
        fixedSourceCode += when (numArguments) {
            2 -> {
                val msgOrThrowable = arguments[1]
                ", " + msgOrThrowable.asSourceString() + ")"
            }
            3 -> {
                val msg = arguments[1]
                val throwable = arguments[2]
                ", " + throwable.asSourceString() + ", " + msg.asSourceString() + ")"
            }
            else -> {
                throw IllegalStateException("android.util.Log overloads should have 2 or 3 arguments")
            }
        }
        return fix().group().apply {
            add(
                fix().replace()
                    .shortenNames()
                    .reformat(true)
                    .with(fixedSourceCode)
                    .build()
            )
        }.build()
    }
}