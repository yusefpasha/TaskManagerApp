package ir.yusefpasha.taskmanagerapp.data.utils

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.core.content.PermissionChecker

object PermissionFactory {

    fun checkPermissionGranted(context: Context, vararg permissions: String): Boolean {
        return permissions.all { permission ->
            PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED
        }
    }

    object Group {

        val NOTIFICATION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else arrayOf()

    }
}