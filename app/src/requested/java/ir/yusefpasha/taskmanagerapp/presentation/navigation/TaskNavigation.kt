package ir.yusefpasha.taskmanagerapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.yusefpasha.taskmanagerapp.domain.utils.DatabaseId
import ir.yusefpasha.taskmanagerapp.presentation.component.DefaultDateTimePicker
import ir.yusefpasha.taskmanagerapp.presentation.model.AddTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.EditTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeEvent
import ir.yusefpasha.taskmanagerapp.presentation.screen.AddTaskScreen
import ir.yusefpasha.taskmanagerapp.presentation.screen.EditTaskScreen
import ir.yusefpasha.taskmanagerapp.presentation.screen.HomeScreen
import ir.yusefpasha.taskmanagerapp.presentation.screen.SplashScreen
import ir.yusefpasha.taskmanagerapp.presentation.view_model.AddTaskViewModel
import ir.yusefpasha.taskmanagerapp.presentation.view_model.EditTaskViewModel
import ir.yusefpasha.taskmanagerapp.presentation.view_model.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable

@Serializable
object SplashScreenNav

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
        startDestination = SplashScreenNav,
    ) {

        composable<SplashScreenNav> {
            SplashScreen(
                exitApp = onExitApplication,
                navigateToHomeScreen = {
                    navHostController.navigate(HomeScreenNav) {
                        popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<HomeScreenNav> {

            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            HomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                state = state,
                onEvent = viewModel::handleEvent
            )

            LaunchedEffect(key1 = Unit) {
                viewModel.event.collectLatest { event ->
                    when (event) {
                        is HomeEvent.EditTask -> {
                            navHostController.navigate(EditTaskScreenNav(taskId = event.taskId))
                        }

                        HomeEvent.Exit -> onExitApplication()
                        HomeEvent.NavigateToAddTask -> {
                            navHostController.navigate(AddTaskScreenNav)
                        }

                        else -> {}
                    }
                }
            }

        }

        composable<AddTaskScreenNav> {

            val viewModel = hiltViewModel<AddTaskViewModel>()
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

            val viewModel = hiltViewModel<EditTaskViewModel>()
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
                    initial = state.deadline,
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
