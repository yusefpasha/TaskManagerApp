package ir.yusefpasha.taskmanagerapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.yusefpasha.taskmanagerapp.domain.use_case.ChangeThemeUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.DeleteTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveSyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveThemeUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.StartSyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.StopSyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.SyncTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.presentation.mapper.toTaskItem
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    observeTasksUseCase: ObserveTaskUseCase,
    observeThemeUseCase: ObserveThemeUseCase,
    observeSyncTaskUseCase: ObserveSyncTaskUseCase,
    private val syncTaskUseCase: SyncTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val stopSyncTaskUseCase: StopSyncTaskUseCase,
    private val startSyncTaskUseCase: StartSyncTaskUseCase,
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
        Log.d(this.toString(), throwable.message.orEmpty())
    }

    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(Constants.DEFAULT_SHARING_START),
        initialValue = HomeState(isLoading = true)
    )

    private val _event: Channel<HomeEvent> = Channel()
    val event = _event.receiveAsFlow()

    init {
        combine(
            observeTasksUseCase(),
            observeThemeUseCase(),
            observeSyncTaskUseCase()
        ) { tasks, themeMode, syncTask ->
            HomeState(
                isLoading = false,
                tasks = tasks
                    .map { task -> task.toTaskItem() }
                    .sortedBy { it.deadline },
                theme = themeMode,
                syncTask = syncTask
            )
        }.onEach { state ->
            _state.emit(state)
            Log.d("OBSERVE", state.syncTask.toString())
        }.flowOn(context = coroutineContext).launchIn(scope = viewModelScope)
    }

    fun handleEvent(event: HomeEvent) {
        viewModelScope.launch(coroutineContext) {
            when (event) {
                HomeEvent.Exit -> _event.send(event)
                HomeEvent.NavigateToAddTask -> _event.send(event)
                is HomeEvent.ChangeTheme -> {
                    changeThemeUseCase(themeMode = event.themeMode)
                }

                is HomeEvent.ExpandedMenu -> _state.update { it.copy(expandedMenu = event.expanded) }
                is HomeEvent.SyncTask -> {
                    if (event.enable) {
                        startSyncTaskUseCase()
                    } else {
                        stopSyncTaskUseCase()
                    }
                }

                is HomeEvent.DeleteTask -> {
                    deleteTaskUseCase(taskId = event.taskId)
                }

                is HomeEvent.NavigateToTask -> _event.send(event)
                is HomeEvent.NavigateToEditTask -> _event.send(event)
                HomeEvent.RefreshTask -> {
                    _state.update { it.copy(isLoading = true) }
                    syncTaskUseCase()
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

}
