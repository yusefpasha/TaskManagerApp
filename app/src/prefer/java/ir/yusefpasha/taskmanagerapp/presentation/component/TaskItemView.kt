package ir.yusefpasha.taskmanagerapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ir.yusefpasha.taskmanagerapp.R
import ir.yusefpasha.taskmanagerapp.domain.utils.convertToDisplayText
import ir.yusefpasha.taskmanagerapp.presentation.model.TaskItem
import ir.yusefpasha.taskmanagerapp.presentation.theme.padding
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskItemView(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    task: TaskItem,
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
        shape = shape,
        onClick = onClick
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            headlineContent = {
                Text(
                    text = task.title
                )
            },
            supportingContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = task.description
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = MaterialTheme.padding.small)
                    )
                    Text(
                        text = stringResource(
                            R.string.task_deadline_value,
                            task.deadline.convertToDisplayText()
                        ),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Task,
                    contentDescription = null
                )
            },
        )
    }
}

@Preview
@Composable
private fun TaskItemPreview() {
    TaskItemView(
        task = TaskItem(
            id = 1,
            title = LoremIpsum(2).values.joinToString(),
            description = LoremIpsum(10).values.joinToString(),
            deadline = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ),
        onClick = {},
    )
}
