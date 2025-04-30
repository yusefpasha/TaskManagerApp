package ir.yusefpasha.taskmanagerapp.presentation.screen

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.model.SyncTaskState
import ir.yusefpasha.taskmanagerapp.domain.model.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.presentation.dapter.TaskAdapter
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.HomeState
import ir.yusefpasha.taskmanagerapp.presentation.util.setupSwipeToDismiss

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit
) {

    val context = LocalContext.current
    val taskAdapter = remember {
        TaskAdapter(
            onItemClickListener = { task -> onEvent(HomeEvent.EditTask(task.id)) },
            onItemSwipeListener = { task -> onEvent(HomeEvent.DeleteTask(task.id)) }
        )
    }
    val layoutManager = remember { LinearLayoutManager(context) }

    fun onMenuClickListener(itemId: Int): Boolean {
        return when (itemId) {
            R.id.menu_sync_task -> {
                val isCheck = state.syncTaskIsEnabled()
                onEvent(HomeEvent.SyncTask(!isCheck))
                true
            }

            R.id.menu_theme_dark -> {
                onEvent(HomeEvent.ChangeTheme(TaskThemeMode.DarkMode))
                true
            }

            R.id.menu_theme_light -> {
                onEvent(HomeEvent.ChangeTheme(TaskThemeMode.LightMode))
                true
            }

            R.id.menu_theme_follow_system -> {
                onEvent(HomeEvent.ChangeTheme(TaskThemeMode.Auto))
                true
            }

            else -> false
        }
    }

    fun onSyncTaskClickListener() {
        val message = when (state.syncTask) {
            SyncTaskState.Enqueued, SyncTaskState.Success -> context.getString(R.string.sync_task_enable)
            SyncTaskState.Running -> context.getString(R.string.sync_task_in_progress)
            else -> context.getString(R.string.sync_task_disable)
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    AndroidView(
        modifier = modifier,
        factory = { factoryContext ->
            LayoutInflater.from(factoryContext).inflate(R.layout.screen_home, null, false).apply {

                val recyclerView = findViewById<RecyclerView>(R.id.rv_tasks)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = taskAdapter
                setupSwipeToDismiss(
                    recyclerView = recyclerView,
                    onSwipeLeft = { task -> onEvent(HomeEvent.DeleteTask(task.id)) },
                    onSwipeRight = { task -> onEvent(HomeEvent.EditTask(task.id)) }
                )

                findViewById<FloatingActionButton>(R.id.fab_floating_action_button).setOnClickListener {
                    onEvent(HomeEvent.NavigateToAddTask)
                }

                findViewById<SwipeRefreshLayout>(R.id.sr_swipe_refresh).setOnRefreshListener {
                    onEvent(HomeEvent.RefreshTask)
                }
            }
        },
        update = { view ->
            taskAdapter.submitList(state.tasks)

            view.findViewById<ImageView>(R.id.iv_task_sync).setImageResource(
                when (state.syncTask) {
                    SyncTaskState.Enqueued,
                    SyncTaskState.Running,
                    SyncTaskState.Success -> R.drawable.auto_sync

                    SyncTaskState.Unknown,
                    SyncTaskState.Failure -> R.drawable.sync_disabled
                }
            )

            view.findViewById<ImageView>(R.id.iv_task_sync).apply {
                setImageResource(
                    when (state.syncTask) {
                        SyncTaskState.Enqueued,
                        SyncTaskState.Running,
                        SyncTaskState.Success -> R.drawable.auto_sync

                        SyncTaskState.Unknown,
                        SyncTaskState.Failure -> R.drawable.sync_disabled
                    }
                )
                setOnClickListener {
                    onSyncTaskClickListener()
                }
            }

            view.findViewById<Toolbar>(R.id.toolbar).apply {
                menu.findItem(R.id.menu_sync_task).isChecked = state.syncTaskIsEnabled()
                setOnMenuItemClickListener {
                    onMenuClickListener(it.itemId)
                }
            }

            view.findViewById<TextView>(R.id.tv_tasks_not_exist).visibility =
                if (state.tasks.isEmpty()) View.VISIBLE else View.GONE

            view.findViewById<FloatingActionButton>(R.id.fab_floating_action_button).visibility =
                if (state.tasksListState.isScrollInProgress) View.GONE else View.VISIBLE

            view.findViewById<SwipeRefreshLayout>(R.id.sr_swipe_refresh).isRefreshing = state.isLoading
        }
    )
}
