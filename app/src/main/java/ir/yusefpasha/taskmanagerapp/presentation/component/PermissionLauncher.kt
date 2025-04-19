package ir.yusefpasha.taskmanagerapp.presentation.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.presentation.theme.padding

@Composable
fun rememberPermissionLauncher(
    vararg permissions: String,
    text: String,
    onGrant: () -> Unit = {},
    onDeny: () -> Unit = {},
): () -> Unit {

    val context = LocalContext.current

    var showRequestPermission by remember { mutableStateOf(false) }

    val permissionStateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { isGranted ->
            if (isGranted.values.all { it }) {
                showRequestPermission = false
                onGrant()
            } else {
                onDeny()
            }
        }
    )

    AnimatedVisibility(visible = showRequestPermission) {
        PermissionDialog(
            title = stringResource(R.string.permission_required),
            text = text,
            confirmButton = {
                showRequestPermission = false
                permissionStateLauncher.launch(
                    input = permissions.toList().toTypedArray(),
                    options = ActivityOptionsCompat.makeBasic()
                )
            },
            dismissButton = {
                showRequestPermission = false
                onDeny()
            },
            onDismissRequest = {
                showRequestPermission = false
            }
        )
    }

    return {

        val permissionsCheck = permissions.all { permission ->
            checkSelfPermission(context, permission) == PERMISSION_GRANTED
        }

        if (permissionsCheck || permissions.isEmpty()) {
            showRequestPermission = false
            onGrant()
        } else {
            showRequestPermission = true
        }
    }
}

@Composable
fun PermissionDialog(
    title: String,
    text: String,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    onDismissRequest: () -> Unit
) {

    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        icon = {
            Icon(imageVector = Icons.Rounded.Warning, contentDescription = "ic_warning")
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                modifier = Modifier.padding(MaterialTheme.padding.small),
                onClick = confirmButton
            ) {
                Text(text = stringResource(id = R.string.apply))
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier.padding(MaterialTheme.padding.small),
                onClick = dismissButton
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

@Preview
@Composable
private fun PermissionDialogPreview() {
    PermissionDialog(
        title = LoremIpsum(8).values.joinToString(),
        text = LoremIpsum(30).values.joinToString(),
        confirmButton = {},
        dismissButton = {},
        onDismissRequest = {}
    )
}
