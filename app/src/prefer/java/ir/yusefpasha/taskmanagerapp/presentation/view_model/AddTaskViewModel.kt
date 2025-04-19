package ir.yusefpasha.taskmanagerapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.yusefpasha.taskmanagerapp.domain.use_case.CreateTaskUseCase
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import ir.yusefpasha.taskmanagerapp.presentation.model.AddTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.AddTaskState
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

class AddTaskViewModel(private val createTaskUseCase: CreateTaskUseCase) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
        Log.d(this.toString(), throwable.message.orEmpty())
    }

    private val _state: MutableStateFlow<AddTaskState> = MutableStateFlow(AddTaskState())
    val state: StateFlow<AddTaskState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(Constants.DEFAULT_SHARING_START),
        initialValue = AddTaskState()
    )

    private val _event = Channel<AddTaskEvent>()
    val event = _event.receiveAsFlow()

    fun handleEvent(event: AddTaskEvent) {
        when (event) {
            AddTaskEvent.Apply -> {
                viewModelScope.launch(coroutineContext) {
                    _state.update { it.copy(isLoading = true) }
                    createTaskUseCase(
                        title = state.value.title,
                        description = state.value.description,
                        deadline = state.value.deadline
                            .toInstant(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds()
                    )
                    _state.update { it.copy(isLoading = false) }
                    _event.send(AddTaskEvent.NavigateUp)
                }
            }

            AddTaskEvent.NavigateUp -> {
                viewModelScope.launch(coroutineContext) {
                    _event.send(AddTaskEvent.NavigateUp)
                }
            }

            is AddTaskEvent.UpdateDeadline -> {
                _state.update {
                    it.copy(
                        deadline = event.deadline
                    )
                }
            }

            is AddTaskEvent.UpdateDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is AddTaskEvent.UpdateTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            AddTaskEvent.ShowDateTimePicker -> {
                viewModelScope.launch(coroutineContext) {
                    _event.send(event)
                }
            }
        }
    }

}
