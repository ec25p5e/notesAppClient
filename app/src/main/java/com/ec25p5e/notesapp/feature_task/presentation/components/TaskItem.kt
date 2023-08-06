package com.ec25p5e.notesapp.feature_task.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ec25p5e.notesapp.feature_task.domain.models.Task
import com.ec25p5e.notesapp.feature_task.presentation.todo.TaskViewModel

@Composable
fun TaskItem(
    task: Task,
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
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )

                TaskActions(task)
            }
        }
    }
}

@Composable
fun TaskActions(
    task: Task,
    viewModel: TaskViewModel = hiltViewModel()
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        IconButton(
            onClick = {

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
                    tint = Color.Black
                )
            }
        }
        IconButton(
            onClick = {
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.Black
            )
        }
        IconButton(
            onClick = {

            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Black
            )
        }
    }
}
