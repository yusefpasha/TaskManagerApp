package ir.yusefpasha.taskmanagerapp.domain.entities

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {

    data object Init : UiText()

    data class DirectString(
        val text: String,
    ) : UiText()

    data class ResourceString(
        @StringRes val resId: Int,
    ) : UiText()

    fun asString(context: Context) =
        when (this) {
            Init -> ""
            is DirectString -> text
            is ResourceString -> context.getString(resId)
        }

}