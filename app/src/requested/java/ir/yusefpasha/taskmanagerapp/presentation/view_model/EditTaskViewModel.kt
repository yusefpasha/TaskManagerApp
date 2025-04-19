package ir.yusefpasha.taskmanagerapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.yusefpasha.taskmanagerapp.domain.use_case.DeleteTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.ReadTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.use_case.UpdateTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.domain.utils.DEFAULT_DATABASE_ID
import ir.yusefpasha.taskmanagerapp.presentation.mapper.toTaskItem
import ir.yusefpasha.taskmanagerapp.presentation.model.EditTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.EditTaskState
import ir.yusefpasha.taskmanagerapp.presentation.navigation.EditTaskScreenNav
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val readTaskUseCase: ReadTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
        Log.d(this.toString(), throwable.message.orEmpty())
    }

    private val _state: MutableStateFlow<EditTaskState> = MutableStateFlow(EditTaskState(isLoading = true))
    val state: StateFlow<EditTaskState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(Constants.DEFAULT_SHARING_START),
        initialValue = _state.value
    )

    private val _event = Channel<EditTaskEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch(coroutineContext) {
            val navigationRoute = savedStateHandle.toRoute<EditTaskScreenNav>()
            val task = readTaskUseCase(taskId = navigationRoute.taskId).getOrElse { return@launch }.toTaskItem()
            _state.update {
                it.copy(
                    task = task,
                    title = task.title,
                    description = task.description,
                    deadline = task.deadline,
                    isLoading = false
                )
            }
        }
    }

    fun handleEvent(event: EditTaskEvent) {
        when (event) {
            EditTaskEvent.Delete -> {
                viewModelScope.launch(coroutineContext) {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    deleteTaskUseCase(
                        taskId = _state.value.task?.id ?: DEFAULT_DATABASE_ID
                    )
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _event.send(EditTaskEvent.NavigateUp)
                }
            }

            EditTaskEvent.Update -> {
                viewModelScope.launch(coroutineContext) {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    updateTaskUseCase(
                        id = _state.value.task?.id ?: DEFAULT_DATABASE_ID,
                        title = state.value.title,
                        description = state.value.description,
                        deadline = state.value.deadline
                            .toInstant(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds()
                    )
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _event.send(EditTaskEvent.NavigateUp)
                }
            }

            is EditTaskEvent.EditDeadline -> {
                _state.update {
                    it.copy(
                        deadline = event.deadline
                    )
                }
            }

            is EditTaskEvent.EditDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is EditTaskEvent.EditTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            else -> {
                viewModelScope.launch(coroutineContext) {
                    _event.send(event)
                }
            }

        }
    }

}
