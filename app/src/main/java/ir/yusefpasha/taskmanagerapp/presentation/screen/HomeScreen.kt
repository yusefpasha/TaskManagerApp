package ir.yusefpasha.taskmanagerapp.presentation.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SyncDisabled
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.model.SyncTaskState
import ir.yusefpasha.taskmanagerapp.presentation.component.SettingMenu
import ir.yusefpasha.taskmanagerapp.presentation.component.TaskItemView
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeState
import ir.yusefpasha.taskmanagerapp.presentation.model.TaskItem
import ir.yusefpasha.taskmanagerapp.presentation.theme.padding
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit
) {

    val context = LocalContext.current
    val pullToRefreshState: PullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(HomeEvent.ExpandedMenu(expanded = true))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "setting_menu"
                        )
                    }
                    SettingMenu(
                        theme = state.theme,
                        expandedMenu = state.expandedMenu,
                        syncTask = state.syncTaskIsEnabled(),
                        onThemeClickListener = { themeMode ->
                            onEvent(HomeEvent.ChangeTheme(themeMode = themeMode))
                        },
                        onSyncTaskClickListener = { enabled ->
                            onEvent(HomeEvent.SyncTask(enable = enabled))
                        },
                        onExpandMenuClickListener = { expanded ->
                            onEvent(HomeEvent.ExpandedMenu(expanded = expanded))
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            val message = when (state.syncTask) {
                                SyncTaskState.Enqueued,
                                SyncTaskState.Success -> context.getString(R.string.sync_task_enable)

                                SyncTaskState.Running -> context.getString(R.string.sync_task_in_progress)

                                SyncTaskState.Unknown,
                                SyncTaskState.Failure -> context.getString(R.string.sync_task_disable)
                            }
                            Toast.makeText(
                                context,
                                message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ) {
                        Icon(
                            imageVector = when (state.syncTask) {
                                SyncTaskState.Enqueued,
                                SyncTaskState.Running,
                                SyncTaskState.Success -> Icons.Default.Sync

                                SyncTaskState.Unknown,
                                SyncTaskState.Failure -> Icons.Default.SyncDisabled
                            },
                            contentDescription = "sync_task"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                exit = fadeOut(),
                enter = fadeIn(),
                visible = state.tasksListState.isScrollInProgress.not()
            ) {
                FloatingActionButton(
                    onClick = {
                        onEvent(HomeEvent.NavigateToAddTask)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "navigate_to_add_task"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Start,
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pullToRefreshState,
            isRefreshing = state.isLoading,
            contentAlignment = Alignment.Center,
            onRefresh = {
                onEvent(HomeEvent.RefreshTask)
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = state.tasksListState,
                verticalArrangement = Arrangement.spacedBy(
                    space = MaterialTheme.padding.medium,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(MaterialTheme.padding.small)
            ) {
                item {
                    AnimatedVisibility(
                        modifier = Modifier.fillMaxSize(),
                        visible = state.tasks.isEmpty()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = MaterialTheme.padding.extraLarge,
                                    horizontal = MaterialTheme.padding.medium
                                ),
                            text = stringResource(R.string.task_not_exist_message),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                items(
                    items = state.tasks,
                    key = { item -> item.id }
                ) { task ->

                    val swipeState = rememberSwipeToDismissBoxState(
                        initialValue = SwipeToDismissBoxValue.Settled,
                        confirmValueChange = { dismissValue ->
                            when(dismissValue) {
                                SwipeToDismissBoxValue.StartToEnd -> {
                                    onEvent(HomeEvent.EditTask(taskId = task.id))
                                    false
                                }
                                SwipeToDismissBoxValue.EndToStart -> {
                                    onEvent(HomeEvent.DeleteTask(taskId = task.id))
                                    false
                                }
                                SwipeToDismissBoxValue.Settled -> false
                            }
                        }
                    )

                    SwipeToDismissBox(
                        state = swipeState,
                        content = {
                            TaskItemView(
                                modifier = Modifier.animateItem(),
                                task = task,
                                onClick = {
                                    onEvent(HomeEvent.EditTask(taskId = task.id))
                                },
                            )
                        },
                        backgroundContent = {
                            val direction = swipeState.dismissDirection
                            val icon = when (direction) {
                                SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Edit
                                SwipeToDismissBoxValue.EndToStart -> Icons.Default.Delete
                                else -> Icons.Default.Task
                            }
                            val iconAlignment = when (direction) {
                                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                                else -> Alignment.Center
                            }
                            val color = when (direction) {
                                SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primaryContainer
                                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                                else -> Color.Transparent
                            }
                            val colorTint = when (direction) {
                                SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.onPrimaryContainer
                                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.onError
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color, shape = MaterialTheme.shapes.medium)
                                    .padding(MaterialTheme.padding.large),
                                contentAlignment = iconAlignment
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "swipe_task",
                                    tint = colorTint
                                )
                            }
                        },
                        gesturesEnabled = state.taskGestureEnabled(),
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    HomeScreen(
        state = HomeState(isLoading = true),
        onEvent = {}
    )
}

@Preview
@Composable
private fun HomeScreenTaskExistPreview() {
    HomeScreen(
        state = HomeState(
            tasks = List(Random.nextInt(from = 1, until = 5)) { index ->
                TaskItem(
                    id = index,
                    title = LoremIpsum(2).values.joinToString(),
                    description = LoremIpsum(10).values.joinToString(),
                    deadline = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
            },
            isLoading = false,
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun HomeScreenTaskNotExistPreview() {
    HomeScreen(
        state = HomeState(
            tasks = emptyList(),
            isLoading = false
        ),
        onEvent = {}
    )
}
