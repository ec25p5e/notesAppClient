package com.ec25p5e.notesapp.feature_todo.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.ui.theme.RedPink
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.feature_todo.presentation.add_edit_task.AddEditTaskViewModel

@Composable
fun CheckableItem(
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = {

            },
            modifier = Modifier
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = RedPink,
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
                    checked = true,
                    onCheckedChange = {

                    }
                )

                TextField(
                    value = "" /* textFieldValue */,
                    onValueChange = {
                        // textFieldValue = it.copy(text = it.text.trim())
                        // vm.onCheckableValueChange(item,textFieldValue.text)
                    },
                    /* colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.TRANSPARENT,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ), */
                    placeholder = {
                        Text(
                            stringResource(id = R.string.item),
                            fontStyle = FontStyle.Italic
                        )
                    },
                    modifier = Modifier
                        .padding(start = 0.dp)
                    /* .onKeyEvent {
                        Log.d("flfklskf", it.nativeKeyEvent.keyCode.toString())
                        if (it.nativeKeyEvent.keyCode == KEYCODE_ENTER) {
                            vm.onAddCheckable(item)
                            true
                        } else if (it.nativeKeyEvent.keyCode == KEYCODE_DEL) {
                            vm.onBackOnValue(
                                item,
                                currentPos,
                                textFieldValue.selection.start
                            )
                            currentPos = textFieldValue.selection.start
                            true
                        } else {
                            false
                        }

                    }
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            vm.onFocusGot(item)
                        }
                    }, */,
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

                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(SpaceSmall))
}