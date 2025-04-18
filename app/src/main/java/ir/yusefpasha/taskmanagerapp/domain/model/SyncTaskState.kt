package ir.yusefpasha.taskmanagerapp.domain.model

enum class SyncTaskState {
    Unknown,
    Enqueued,
    Running,
    Success,
    Failure,
}