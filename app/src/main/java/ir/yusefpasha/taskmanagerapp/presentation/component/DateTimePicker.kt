package ir.yusefpasha.taskmanagerapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import ir.yusefpasha.taskmanagerapp.domain.utils.convertMillisecondToLocalDateTime
import ir.yusefpasha.taskmanagerapp.presentation.theme.padding
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun DefaultDateTimePicker(
    initial: LocalDateTime? = null,
    onDismiss: () -> Unit,
    onDateTimeSelected: (LocalDateTime) -> Unit
) {

    var pickerType by remember { mutableStateOf(PickerType.DatePicker) }
    var selectedDate by remember { mutableStateOf(initial?.date) }
    var selectedTime by remember { mutableStateOf(initial?.time) }

    when (pickerType) {
        PickerType.DatePicker -> {
            DefaultDatePicker(
                initial = selectedDate,
                onDismiss = onDismiss,
                onDateSelected = { localDate ->
                    selectedDate = localDate
                    pickerType = PickerType.TimePicker
                }
            )
        }

        PickerType.TimePicker -> {
            DefaultTimePicker(
                initial = selectedTime,
                onDismiss = onDismiss,
                onTimeSelected = { localTime ->
                    selectedTime = localTime
                    onDateTimeSelected(
                        LocalDateTime(
                            date = selectedDate ?: LocalDate(1, 1, 1),
                            time = selectedTime ?: LocalTime(0, 0, 0)
                        )
                    )
                    onDismiss()
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DefaultDatePicker(
    initial: LocalDate? = null,
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {

    val currentInstant = Clock.System.now()
    val initialInstant = initial?.atStartOfDayIn(TimeZone.UTC)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialInstant?.toEpochMilliseconds(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val today = currentInstant.toLocalDateTime(TimeZone.UTC).date
                val selectedDate = convertMillisecondToLocalDateTime(utcTimeMillis).date
                return selectedDate >= today

            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(convertMillisecondToLocalDateTime(datePickerState.selectedDateMillis ?: 0).date)
                }
            ) {
                Text(text = stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(android.R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultTimePicker(
    initial: LocalTime? = null,
    onDismiss: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initial?.hour ?: 0,
        initialMinute = initial?.minute ?: 0
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            TimePicker(
                modifier = Modifier.padding(MaterialTheme.padding.medium),
                state = timePickerState
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(android.R.string.cancel))
                }
                TextButton(
                    onClick = {
                        onTimeSelected(LocalTime(hour = timePickerState.hour, minute = timePickerState.minute))
                    }
                ) {
                    Text(text = stringResource(android.R.string.ok))
                }
            }
        }
    }

}

private enum class PickerType {
    TimePicker, DatePicker
}

@Preview
@Composable
private fun DefaultDatePickerPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultDatePicker(
            onDismiss = {},
            onDateSelected = {}
        )
    }
}

@Preview
@Composable
private fun DefaultTimePickerPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTimePicker(
            onDismiss = {},
            onTimeSelected = {}
        )
    }
}

@Preview
@Composable
private fun DefaultDateTimePickerPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultDateTimePicker(
            onDismiss = {},
            onDateTimeSelected = {}
        )
    }
}
