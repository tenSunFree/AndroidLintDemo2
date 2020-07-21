package com.home.library_lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class AndroidLintIssueRegistry : IssueRegistry() {

    override val issues: List<Issue>
        get() = listOf(
            LogDetector.ISSUE_LOG,
            LayoutXmlColorsDetector.ISSUE,
            LayoutXmlDimensDetector.ISSUE,
            LayoutXmlStringsDetector.ISSUE
        )

    override val api: Int = CURRENT_API
}