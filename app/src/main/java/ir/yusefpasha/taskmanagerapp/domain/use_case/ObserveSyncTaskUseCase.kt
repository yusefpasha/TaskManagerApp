package ir.yusefpasha.taskmanagerapp.domain.use_case

import ir.yusefpasha.taskmanagerapp.domain.model.SyncTaskState
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveSyncTaskUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<SyncTaskState> {
        return taskRepository.observeSyncTaskScheduler()
    }

}
