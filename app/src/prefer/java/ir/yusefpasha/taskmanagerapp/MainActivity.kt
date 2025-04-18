package ir.yusefpasha.taskmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import ir.yusefpasha.taskmanagerapp.domain.entities.TaskThemeMode
import ir.yusefpasha.taskmanagerapp.domain.use_case.ObserveThemeUseCase
import ir.yusefpasha.taskmanagerapp.presentation.navigation.TaskNavigation
import ir.yusefpasha.taskmanagerapp.presentation.theme.TaskManagerAppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val themeMode by inject<ObserveThemeUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme by themeMode().collectAsStateWithLifecycle(TaskThemeMode.Auto)
            val navHostController = rememberNavController()
            TaskManagerAppTheme(
                darkTheme = when (theme) {
                    TaskThemeMode.Auto -> isSystemInDarkTheme()
                    TaskThemeMode.DarkMode -> true
                    TaskThemeMode.LightMode -> false
                }
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    TaskNavigation(
                        modifier = Modifier.fillMaxSize(),
                        navHostController = navHostController,
                        onExitApplication = { finish() }
                    )
                }
            }
        }
    }
}