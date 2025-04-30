package ir.yusefpasha.taskmanagerapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.data.utils.PermissionFactory
import ir.yusefpasha.taskmanagerapp.presentation.component.rememberPermissionLauncher
import ir.yusefpasha.taskmanagerapp.presentation.theme.padding
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    exitApp: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {

    var progress by remember { mutableFloatStateOf(0.0F) }
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = MaterialTheme.padding.large,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.mipmap.app_icon),
            contentDescription = null
        )

        LinearProgressIndicator(
            progress = {
                progress
            },
        )

        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.titleLarge
        )

    }

    LaunchedEffect(Unit) {
        notificationPermission()
    }

    LaunchedEffect(notificationAccess) {
        if (notificationAccess) {
            while (progress < 1F) {
                delay(20)
                progress += 0.01F
            }
            navigateToHomeScreenRemind()
        }
    }

}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        exitApp = {},
        navigateToHomeScreen = {}
    )
}