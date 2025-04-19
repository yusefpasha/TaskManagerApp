package ir.yusefpasha.taskmanagerapp.presentation.screen

import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.data.utils.PermissionFactory
import ir.yusefpasha.taskmanagerapp.presentation.component.rememberPermissionLauncher
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    exitApp: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {

    var progress by remember { mutableIntStateOf(0) }
    var notificationAccess by remember { mutableStateOf(false) }
    val navigateToHomeScreenRemind by rememberUpdatedState(navigateToHomeScreen)

    val notificationPermission = rememberPermissionLauncher(
        permissions = PermissionFactory.Group.NOTIFICATION,
        text = stringResource(R.string.permission_notification_request_text),
        onDeny = {
            notificationAccess = false
            exitApp()
        },
        onGrant = {
            notificationAccess = true
        }
    )

    AndroidView(
        factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.splash_layout, null, false)
        },
        update = { view ->
            val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
            progressBar.progress = progress
        }
    )

    LaunchedEffect(Unit) {
        notificationPermission()
    }

    LaunchedEffect(notificationAccess) {
        if (notificationAccess) {
            while (progress < 100) {
                delay(20)
                progress += 1
            }
            navigateToHomeScreenRemind()
        }
    }

}