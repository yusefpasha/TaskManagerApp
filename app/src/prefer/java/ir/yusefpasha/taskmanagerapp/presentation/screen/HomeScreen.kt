package ir.yusefpasha.taskmanagerapp.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode
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
                            onEvent(HomeEvent.ChangeTheme)
                        }
                    ) {
                        Icon(
                            imageVector = when (state.theme) {
                                TaskThemeMode.Auto -> Icons.Default.WbTwilight
                                TaskThemeMode.DarkMode -> Icons.Default.DarkMode
                                TaskThemeMode.LightMode -> Icons.Default.LightMode
                            },
                            contentDescription = "theme_switch"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
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
            AnimatedContent(
                targetState = state.tasks,
                modifier = Modifier.fillMaxWidth(),
            ) { targetState ->
                when {

                    targetState.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = MaterialTheme.padding.medium,
                                    ),
                                text = stringResource(R.string.task_not_exist_message),
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = state.tasksListState,
                            verticalArrangement = Arrangement.spacedBy(
                                space = MaterialTheme.padding.small,
                                alignment = Alignment.Top
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(
                                items = state.tasks,
                                key = { item -> item.id }
                            ) { task ->
                                TaskItemView(
                                    task = task,
                                    onClick = {
                                        onEvent(HomeEvent.NavigateToTask(taskId = task.id))
                                    },
                                )
                            }
                        }
                    }

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
