package ir.yusefpasha.taskmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ir.yusefpasha.taskmanagerapp.presentation.navigation.TaskNavigation
import ir.yusefpasha.taskmanagerapp.presentation.theme.TaskManagerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            TaskManagerAppTheme {
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