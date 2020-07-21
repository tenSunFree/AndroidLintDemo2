package com.home.library_lint

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

class LayoutXmlStringsDetector : ResourceXmlDetector() {

    companion object {
        val ISSUE = Issue.create(
            id = "LayoutXmlStringsDetector",
            briefDescription = "LayoutXmlStringsDetector",
            explanation = "LayoutXmlStringsDetector",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                LayoutXmlStringsDetector::class.java,
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
        val attributeName = attribute.nodeName
        val attributeValue = attribute.nodeValue
        if (attributeName != "android:text") return
        if (attributeValue.startsWith("@")) return
        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "麻煩先添加至values/strings.xml中再使用。" +
                    "按Alt+Enter，再選擇Extract string resource，可以快速添加。"
        )
    }
}