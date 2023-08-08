package com.ec25p5e.notesapp.feature_note.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.DefaultRadioButton
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveNoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType

@Composable
fun OrderSectionArchive(
    modifier: Modifier = Modifier,
    noteOrder: ArchiveNoteOrder = ArchiveNoteOrder.Date(OrderType.Descending),
    onOrderChange: (ArchiveNoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_title),
                selected = noteOrder is ArchiveNoteOrder.Title,
                onSelect = { onOrderChange(ArchiveNoteOrder.Title(noteOrder.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_date),
                selected = noteOrder is ArchiveNoteOrder.Date,
                onSelect = { onOrderChange(ArchiveNoteOrder.Date(noteOrder.orderType)) }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_color),
                selected = noteOrder is ArchiveNoteOrder.Color,
                onSelect = { onOrderChange(ArchiveNoteOrder.Color(noteOrder.orderType)) }
            )
        }

        Divider(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Black))
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_asc),
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_desc),
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}