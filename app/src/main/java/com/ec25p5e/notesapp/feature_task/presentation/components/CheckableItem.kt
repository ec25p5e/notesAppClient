package com.ec25p5e.notesapp.feature_task.presentation.components

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.ui.theme.RedPink
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.feature_task.domain.models.Checkable
import com.ec25p5e.notesapp.feature_task.presentation.add_edit_task.AddEditTaskEvent
import com.ec25p5e.notesapp.feature_task.presentation.add_edit_task.AddEditTaskViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CheckableItem(
    item: Checkable,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    var currentPos by remember { mutableStateOf(0) }
    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(item.value))
    }
    val (focusRequester) = FocusRequester.createRefs()

    LaunchedEffect(key1 = viewModel.currentFocusRequestId.value){
        if(item.uid == viewModel.currentFocusRequestId.value){
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                viewModel.onEvent(AddEditTaskEvent.DeleteCheckable(item))
            },
            modifier = Modifier
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete_checkable),
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Checkbox(
                    checked = item.checked,
                    onCheckedChange = {
                        viewModel.onEvent(AddEditTaskEvent.CheckableCheck(item))
                    }
                )

                androidx.compose.material.TextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it.copy(text = it.text.trim())
                        viewModel.onEvent(AddEditTaskEvent.CheckableValueChange(item, textFieldValue.text))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        androidx.compose.material.Text(
                            stringResource(id = R.string.item),
                            fontStyle = FontStyle.Italic
                        )
                    },
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .onKeyEvent {
                            when (it.nativeKeyEvent.keyCode) {
                                KeyEvent.KEYCODE_ENTER -> {
                                    viewModel.onEvent(AddEditTaskEvent.AddCheckable(item))
                                    true
                                }
                                KeyEvent.KEYCODE_DEL -> {
                                    viewModel.onEvent(AddEditTaskEvent.BackOnValue(item, currentPos, textFieldValue.selection.start))
                                    currentPos = textFieldValue.selection.start
                                    true
                                }
                                else -> {
                                    false
                                }
                            }

                        }
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                viewModel.onEvent(AddEditTaskEvent.FocusGot(item))
                            }
                        },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )
            }
        }

        Row(
            modifier = Modifier
        ){
            IconButton(
                onClick = {
                    viewModel.onEvent(AddEditTaskEvent.AddCheckable(item))
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(SpaceSmall))
}