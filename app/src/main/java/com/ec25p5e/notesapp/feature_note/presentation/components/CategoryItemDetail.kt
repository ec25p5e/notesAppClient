package com.ec25p5e.notesapp.feature_note.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceMedium
import com.ec25p5e.notesapp.core.presentation.ui.theme.SpaceSmall
import com.ec25p5e.notesapp.feature_note.domain.models.Category

@Composable
fun CategoryItemDetail(
    category: Category,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ){
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = category.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .shadow(7.5.dp, CircleShape)
                            .clip(CircleShape)
                            .background(Color(category.color))
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = CircleShape
                            )
                    ) {

                    }

                    IconButton(
                        onClick = {
                            onEditClick()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            tint = Color.Blue
                        )
                    }

                    IconButton(
                        onClick = {
                            onDeleteClick()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete),
                            tint = Color.Red
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider()

            Text(
                text = (stringResource(id = R.string.associated_notes) + " " + category.numNotesAssoc.toString()),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 5.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(SpaceMedium))
}