package ir.yusefpasha.taskmanagerapp.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.utils.DEFAULT_DATABASE_ID
import ir.yusefpasha.taskmanagerapp.domain.utils.convertToDisplayText
import ir.yusefpasha.taskmanagerapp.domain.utils.default
import ir.yusefpasha.taskmanagerapp.presentation.model.EditTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.EditTaskState
import ir.yusefpasha.taskmanagerapp.presentation.model.TaskItem
import ir.yusefpasha.taskmanagerapp.presentation.theme.padding
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditTaskScreen(
    modifier: Modifier = Modifier,
    state: EditTaskState,
    onEvent: (event: EditTaskEvent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .imePadding()
            .imeNestedScroll(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.edit_task))
                },
                actions = {
                    IconButton(
                        enabled = state.isValidForDelete(),
                        onClick = { onEvent(EditTaskEvent.Delete) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        enabled = state.isValidForNavigateUp(),
                        onClick = { onEvent(EditTaskEvent.NavigateUp) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "navigate_up"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.padding.medium)
                    .padding(horizontal = MaterialTheme.padding.medium),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isValidForUpdate(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        onEvent(EditTaskEvent.Update)
                    }
                ) {
                    Text(text = stringResource(R.string.update))
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isValidForCancel(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        onEvent(EditTaskEvent.NavigateUp)
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }

            }
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.padding.medium),
        ) { targetState ->
            when (targetState) {

                true -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                false -> {

                    val (
                        titleFM,
                        descriptionFM,
                        deadlineFM
                    ) = FocusRequester.createRefs()

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            space = MaterialTheme.padding.small,
                            alignment = Alignment.Top
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(titleFM),
                            shape = MaterialTheme.shapes.medium,
                            value = state.title,
                            onValueChange = { value ->
                                onEvent(EditTaskEvent.EditTitle(title = value))
                            },
                            label = {
                                Text(text = stringResource(R.string.task_title))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    titleFM.freeFocus()
                                    descriptionFM.requestFocus()
                                }
                            )
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(descriptionFM),
                            shape = MaterialTheme.shapes.medium,
                            value = state.description,
                            onValueChange = { value ->
                                onEvent(EditTaskEvent.EditDescription(description = value))
                            },
                            label = {
                                Text(text = stringResource(R.string.task_description))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            },
                            maxLines = 3,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    descriptionFM.freeFocus()
                                    deadlineFM.requestFocus()
                                }
                            )
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(deadlineFM)
                                .pointerInput(state.deadline) {
                                    awaitEachGesture {
                                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                        // in the Initial pass to observe events before the text field consumes them
                                        // in the Main pass.
                                        awaitFirstDown(pass = PointerEventPass.Initial)
                                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                        if (upEvent != null) {
                                            onEvent(EditTaskEvent.ShowDateTimePicker)
                                        }
                                    }
                                },
                            shape = MaterialTheme.shapes.medium,
                            value = if (state.deadline != LocalDateTime.default) {
                                state.deadline.convertToDisplayText()
                            } else "",
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text(text = stringResource(R.string.task_deadline))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null
                                )
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    titleFM.freeFocus()
                                    descriptionFM.freeFocus()
                                    deadlineFM.freeFocus()
                                }
                            )
                        )

                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun EditTaskScreenIdlePreview() {
    EditTaskScreen(
        state = EditTaskState(
            task = TaskItem(
                id = DEFAULT_DATABASE_ID,
                title = LoremIpsum(2).values.joinToString(),
                description = LoremIpsum(10).values.joinToString(),
                deadline = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            ),
            isLoading = false
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun EditTaskScreenLoadingPreview() {
    EditTaskScreen(
        state = EditTaskState(
            task = TaskItem(
                id = DEFAULT_DATABASE_ID,
                title = LoremIpsum(2).values.joinToString(),
                description = LoremIpsum(10).values.joinToString(),
                deadline = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            ),
            isLoading = true
        ),
        onEvent = {}
    )
}
