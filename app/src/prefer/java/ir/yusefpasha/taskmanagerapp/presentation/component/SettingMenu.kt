package ir.yusefpasha.taskmanagerapp.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode

@Composable
fun SettingMenu(
    theme: TaskThemeMode,
    expandedMenu: Boolean,
    syncTask: Boolean,
    onThemeClickListener: (themeMode: TaskThemeMode) -> Unit,
    onSyncTaskClickListener: (enabled: Boolean) -> Unit,
    onExpandMenuClickListener: (expanded: Boolean) -> Unit,
) {

    var themeExpanded by remember(expandedMenu) { mutableStateOf(false) }

    AnimatedContent(
        targetState = themeExpanded
    ) { targetState ->
        when (targetState) {
            true -> {
                DropdownMenu(
                    expanded = themeExpanded,
                    onDismissRequest = { themeExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.theme_dark),
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.DarkMode,
                                contentDescription = null
                            )
                        },
                        trailingIcon = if (theme == TaskThemeMode.DarkMode) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null
                                )
                            }
                        } else null,
                        onClick = {
                            onThemeClickListener(TaskThemeMode.DarkMode)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.theme_light),
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LightMode,
                                contentDescription = null
                            )
                        },
                        trailingIcon = if (theme == TaskThemeMode.LightMode) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null
                                )
                            }
                        } else null,
                        onClick = {
                            onThemeClickListener(TaskThemeMode.LightMode)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.theme_follow_system),
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AutoMode,
                                contentDescription = null
                            )
                        },
                        trailingIcon = if (theme == TaskThemeMode.Auto) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null
                                )
                            }
                        } else null,
                        onClick = {
                            onThemeClickListener(TaskThemeMode.Auto)
                        }
                    )
                }
            }

            false -> {
                DropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = {
                        onExpandMenuClickListener(false)
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.theme),
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    themeExpanded = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                    contentDescription = null
                                )
                            }
                        },
                        onClick = {
                            themeExpanded = true
                        }
                    )

                    HorizontalDivider()

                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.sync_task_automatic),
                            )
                        },
                        trailingIcon = {
                            Checkbox(
                                checked = syncTask,
                                onCheckedChange = { isEnable ->
                                    onSyncTaskClickListener(isEnable)
                                }
                            )
                        },
                        onClick = {
                            onSyncTaskClickListener(syncTask.not())
                        }
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun SettingMenuPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        SettingMenu(
            theme = TaskThemeMode.Auto,
            expandedMenu = true,
            syncTask = true,
            onThemeClickListener = {},
            onSyncTaskClickListener = {},
            onExpandMenuClickListener = {}
        )
    }
}
