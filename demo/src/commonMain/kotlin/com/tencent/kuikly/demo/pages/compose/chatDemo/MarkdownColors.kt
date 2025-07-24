package com.tencent.kuikly.demo.pages.compose.chatDemo

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.news.markdown.model.DefaultMarkdownColors
import com.tencent.news.markdown.model.MarkdownColors

@Composable
internal fun markdownColor(
    text: Color = QNTheme.colorScheme.t1,
    codeBackground: Color = QNTheme.colorScheme.bgBlock,
    inlineCodeBackground: Color = codeBackground,
    dividerColor: Color = QNTheme.colorScheme.lineFine,
    tableBackground: Color = QNTheme.colorScheme.bgBlock,
    tableStroke: Color = QNTheme.colorScheme.lineStroke,
    tableHeaderBackground: Color = QNTheme.colorScheme.newBgBlock,
): MarkdownColors = DefaultMarkdownColors(
    text = text,
    codeText = Color.Unspecified,
    inlineCodeText = Color.Unspecified,
    linkText = Color.Unspecified,
    codeBackground = codeBackground,
    inlineCodeBackground = inlineCodeBackground,
    dividerColor = dividerColor,
    tableText = Color.Unspecified,
    tableBackground = tableBackground,
    tableHeaderBackground = tableHeaderBackground,
    tableStroke = tableStroke
)
