plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.kotlin.ksp).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.kotlin.compose.compiler).apply(false)
    alias(libs.plugins.androidx.room).apply(false)
    alias(libs.plugins.androidx.hilt).apply(false)
}