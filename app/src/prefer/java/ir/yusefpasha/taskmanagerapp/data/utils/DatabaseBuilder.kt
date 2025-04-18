package ir.yusefpasha.taskmanagerapp.data.utils

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

object DatabaseBuilder {

    inline fun <reified T : RoomDatabase> builder(
        context: Context,
        databaseName: String,
    ): T {
        return androidx.room.Room.databaseBuilder<T>(
            context = context,
            name = databaseName
        )
            .setQueryCoroutineContext(
                context = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
                    Log.e(
                        databaseName.uppercase(),
                        throwable.localizedMessage.orEmpty()
                    )
                }
            )
            .build()
    }

}