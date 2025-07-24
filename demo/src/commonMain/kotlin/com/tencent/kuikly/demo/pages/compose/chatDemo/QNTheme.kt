package com.tencent.kuikly.demo.pages.compose.chatDemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

internal object QNTheme {

    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

}
