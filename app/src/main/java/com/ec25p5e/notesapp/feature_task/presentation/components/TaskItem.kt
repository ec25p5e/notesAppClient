package com.ec25p5e.notesapp.feature_task.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ec25p5e.notesapp.core.presentation.util.date
import com.ec25p5e.notesapp.core.presentation.util.format12Hour
import com.ec25p5e.notesapp.core.presentation.util.time
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.presentation.task.TaskEvent
import com.ec25p5e.notesapp.feature_task.presentation.task.TaskViewModel

@Composable
fun TaskItem(
    task: Task,
    checkables: List<Checkable>,
    viewModel: TaskViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    task.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    IconButton(
                        onClick = {
                            viewModel.onEvent(TaskEvent.MarkTaskDone(task))
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        if(task.done){
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = "Done",
                            )
                        }
                        else{
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Done",
                                tint = Color.Green
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            viewModel.onEvent(TaskEvent.EditTask(task))
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.Blue
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.onEvent(TaskEvent.DeleteTask(task))
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            }

            if(task.description.isNotEmpty() || task.dueDateTime.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
            }

            if(task.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(task.description)
            }

            if(task.dueDateTime.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(task.dueDateTime.date)
                    Text(task.dueDateTime.time.format12Hour)
                }
            }

            checkables.forEach { checkable ->
                CheckableDisplayItem(
                    task = task,
                    checkable = checkable
                )
            }
        }
    }
}