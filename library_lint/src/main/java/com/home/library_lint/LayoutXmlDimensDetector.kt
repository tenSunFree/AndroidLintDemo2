package com.home.library_lint

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

class LayoutXmlDimensDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "LayoutXmlDimensDetector",
            briefDescription = "LayoutXmlDimensDetector",
            explanation = "LayoutXmlDimensDetector",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                LayoutXmlDimensDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableAttributes(): Collection<String>? {
        return XmlScannerConstants.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val attributeValue = attribute.nodeValue
        if (!attributeValue.endsWith("dp")) return
        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "麻煩先添加至values/dimens.xml中再使用。" +
                    "按Alt+Enter，再選擇Extract dimension resource，可以快速添加。"
        )
    }
}