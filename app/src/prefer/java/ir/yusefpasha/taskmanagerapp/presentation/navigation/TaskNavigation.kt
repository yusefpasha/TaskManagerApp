package ir.yusefpasha.taskmanagerapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import ir.yusefpasha.taskmanagerapp.presentation.component.DefaultDateTimePicker
import ir.yusefpasha.taskmanagerapp.presentation.model.AddTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.EditTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeEvent
import ir.yusefpasha.taskmanagerapp.presentation.view.AddTaskScreen
import ir.yusefpasha.taskmanagerapp.presentation.view.EditTaskScreen
import ir.yusefpasha.taskmanagerapp.presentation.view.HomeScreen
import ir.yusefpasha.taskmanagerapp.presentation.view_model.AddTaskViewModel
import ir.yusefpasha.taskmanagerapp.presentation.view_model.EditTaskViewModel
import ir.yusefpasha.taskmanagerapp.presentation.view_model.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.navigation.koinNavViewModel

@Serializable
object HomeScreenNav

@Serializable
data object AddTaskScreenNav

@Serializable
data class EditTaskScreenNav(
    val taskId: DatabaseId
)

@Composable
fun TaskNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onExitApplication: () -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = HomeScreenNav,
    ) {

        composable<HomeScreenNav> {

            val viewModel = koinNavViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onEvent = viewModel::handleEvent
            )

            LaunchedEffect(key1 = Unit) {
                viewModel.event.collectLatest { event ->
                    when (event) {
                        is HomeEvent.NavigateToTask -> {
                            navHostController.navigate(EditTaskScreenNav(taskId = event.taskId))
                        }

                        HomeEvent.Exit -> onExitApplication()
                        HomeEvent.NavigateToAddTask -> {
                            navHostController.navigate(AddTaskScreenNav)
                        }

                        is HomeEvent.NavigateToEditTask -> {
                            navHostController.navigate(EditTaskScreenNav(taskId = event.taskId))
                        }

                        else -> {}
                    }
                }
            }

        }

        composable<AddTaskScreenNav> {

            val viewModel = koinNavViewModel<AddTaskViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            var showDatePicker by remember {
                mutableStateOf(false)
            }

            AddTaskScreen(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onEvent = viewModel::handleEvent
            )

            LaunchedEffect(key1 = Unit) {
                viewModel.event.collectLatest { event ->
                    when (event) {
                        AddTaskEvent.NavigateUp -> navHostController.navigateUp()
                        AddTaskEvent.ShowDateTimePicker -> {
                            showDatePicker = true
                        }

                        else -> {}
                    }
                }
            }

            if (showDatePicker) {
                DefaultDateTimePicker(
                    onDismiss = { showDatePicker = false },
                    onDateTimeSelected = { dateTime ->
                        viewModel.handleEvent(
                            AddTaskEvent.UpdateDeadline(deadline = dateTime)
                        )
                    }
                )
            }

        }

        composable<EditTaskScreenNav> {

            val viewModel = koinNavViewModel<EditTaskViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            var showDatePicker by remember {
                mutableStateOf(false)
            }

            EditTaskScreen(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onEvent = viewModel::handleEvent
            )

            LaunchedEffect(key1 = Unit) {
                viewModel.event.collectLatest { event ->
                    when (event) {
                        EditTaskEvent.NavigateUp -> navHostController.navigateUp()
                        EditTaskEvent.ShowDateTimePicker -> {
                            showDatePicker = true
                        }

                        else -> {}
                    }
                }
            }

            if (showDatePicker) {
                DefaultDateTimePicker(
                    onDismiss = { showDatePicker = false },
                    onDateTimeSelected = { dateTime ->
                        viewModel.handleEvent(
                            EditTaskEvent.EditDeadline(deadline = dateTime)
                        )
                    }
                )
            }

        }

    }

}
