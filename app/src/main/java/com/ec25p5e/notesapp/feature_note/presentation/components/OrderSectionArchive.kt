package com.ec25p5e.notesapp.feature_note.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ec25p5e.notesapp.R
import com.ec25p5e.notesapp.core.presentation.components.DefaultRadioButton
import com.ec25p5e.notesapp.feature_note.domain.util.ArchiveOrder
import com.ec25p5e.notesapp.feature_note.domain.util.NoteOrder
import com.ec25p5e.notesapp.feature_note.domain.util.OrderType

@Composable
fun OrderSectionArchive(
    modifier: Modifier = Modifier,
    noteOrder: ArchiveOrder = ArchiveOrder.Date(OrderType.Descending),
    onOrderChange: (ArchiveOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_title),
                selected = noteOrder is ArchiveOrder.Title,
                onSelect = { onOrderChange(ArchiveOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_date),
                selected = noteOrder is ArchiveOrder.Date,
                onSelect = { onOrderChange(ArchiveOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = stringResource(id = R.string.order_section_color),
                selected = noteOrder is ArchiveOrder.Color,
                onSelect = { onOrderChange(ArchiveOrder.Color(noteOrder.orderType)) }
            )
        }
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
            Spacer(modifier = Modifier.width(8.dp))
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