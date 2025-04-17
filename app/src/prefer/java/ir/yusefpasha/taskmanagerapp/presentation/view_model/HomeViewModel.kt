package ir.yusefpasha.taskmanagerapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.RefreshTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.presentation.mapper.toTaskItem
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val observeTasksUseCase: ObserveTaskUseCase,
    private val refreshTaskUseCase: RefreshTaskUseCase
) : ViewModel() {

    val coroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
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
        viewModelScope.launch(coroutineContext) {
            observeTasksUseCase().map { tasks ->
                HomeState(
                    isLoading = false,
                    tasks = tasks
                        .map { task -> task.toTaskItem() }
                        .sortedBy { it.deadline }
                )
            }.collect { state ->
                _state.emit(state)
            }
        }
    }

    fun handleEvent(event: HomeEvent) {
        viewModelScope.launch(coroutineContext) {
            when (event) {
                HomeEvent.Exit -> _event.send(event)
                HomeEvent.NavigateToAddTask -> _event.send(event)
                is HomeEvent.NavigateToTask -> _event.send(event)
                is HomeEvent.NavigateToEditTask -> _event.send(event)
                HomeEvent.RefreshTask -> {
                    _state.update { it.copy(isLoading = true) }
                    refreshTaskUseCase()
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

}
