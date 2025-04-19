package ir.yusefpasha.taskmanagerapp.data.service

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import ir.yusefpasha.taskmanagerapp.domain.repository.TaskRepository
import java.util.concurrent.TimeUnit

class SyncTaskService(
    context: Context,
    params: WorkerParameters,
    private val taskRepository: TaskRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val result = taskRepository.syncTask()
        return if (result) Result.success() else Result.failure()
    }

    companion object {

        const val TAG = "sync_task_service"

        fun cancelWorkManager(workManager: WorkManager) {
            workManager.cancelAllWorkByTag(TAG)
        }

        fun setupWorkManager(workManager: WorkManager) {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<SyncTaskService>()
                .addTag(TAG)
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniqueWork(
                TAG,
                ExistingWorkPolicy.APPEND_OR_REPLACE,
                workRequest
            )
        }
        fun setupPeriodicWorkManager(workManager: WorkManager) {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<SyncTaskService>(
                repeatInterval = 15,
                repeatIntervalTimeUnit = TimeUnit.MINUTES
            )
                .addTag(TAG)
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }

    }

}
