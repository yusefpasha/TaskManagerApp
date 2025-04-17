package ir.yusefpasha.taskmanagerapp.presentation.view

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
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component2
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component3
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.utils.convertToDisplayText
import ir.yusefpasha.taskmanagerapp.domain.utils.default
import ir.yusefpasha.taskmanagerapp.presentation.model.AddTaskEvent
import ir.yusefpasha.taskmanagerapp.presentation.model.AddTaskState
import ir.yusefpasha.taskmanagerapp.presentation.theme.padding
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier,
    state: AddTaskState,
    onEvent: (event: AddTaskEvent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .imePadding()
            .imeNestedScroll(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_task)
                    )
                },
                navigationIcon = {
                    IconButton(
                        enabled = state.isLoading.not(),
                        onClick = { onEvent(AddTaskEvent.NavigateUp) }
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
                    enabled = state.isValidForApply(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        onEvent(AddTaskEvent.Apply)
                    }
                ) {
                    Text(text = stringResource(R.string.create))
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isValidForCancel(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        onEvent(AddTaskEvent.NavigateUp)
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
                        modifier = Modifier.fillMaxSize(),
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
                                onEvent(AddTaskEvent.UpdateTitle(title = value))
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
                                onEvent(AddTaskEvent.UpdateDescription(description = value))
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
                                            onEvent(AddTaskEvent.ShowDateTimePicker)
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
                                    onEvent(AddTaskEvent.Apply)
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
private fun AddTaskScreenIdlePreview() {
    AddTaskScreen(
        state = AddTaskState(
            title = LoremIpsum(2).values.joinToString(),
            description = LoremIpsum(10).values.joinToString(),
            deadline = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            isLoading = false
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun AddTaskScreenLoadingPreview() {
    AddTaskScreen(
        state = AddTaskState(isLoading = true),
        onEvent = {}
    )
}
