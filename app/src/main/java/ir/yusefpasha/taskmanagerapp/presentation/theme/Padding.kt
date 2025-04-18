package ir.yusefpasha.taskmanagerapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Padding(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
) {

    companion object {
        val default = Padding(
            extraSmall = 2.dp,
            small = 8.dp,
            medium = 16.dp,
            large = 32.dp,
            extraLarge = 40.dp,
        )
    }

}

val LocalPadding = compositionLocalOf { Padding.default }

val MaterialTheme.padding: Padding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current
