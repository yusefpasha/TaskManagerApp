package ir.yusefpasha.taskmanagerapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import ir.yusefpasha.taskmanagerapp.domain.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AppDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

    val taskThemeFlow: Flow<Boolean?>
        get() {
            return context.dataStore
                .data
                .map { preferences ->
                    preferences[TASK_THEME_KEY]
                }
                .catch { _ ->
                    emit(null)
                }
        }

    suspend fun persistTaskTheme(data: Boolean?): Boolean {
        return try {
            context.dataStore.edit { mutPreferences ->
                if (data == null) {
                    mutPreferences.remove(TASK_THEME_KEY)
                } else {
                    mutPreferences[TASK_THEME_KEY] = data
                }
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    companion object {
        private val TASK_THEME_KEY = booleanPreferencesKey(name = Constants.DATASTORE_THEME_KEY)
    }

}