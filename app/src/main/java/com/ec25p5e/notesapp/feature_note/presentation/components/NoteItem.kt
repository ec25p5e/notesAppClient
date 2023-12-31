package com.ec25p5e.notesapp.feature_note.presentation.components

import android.util.Log
import com.ec25p5e.notesapp.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.ec25p5e.notesapp.feature_note.domain.models.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp
) {

    val title = if(note.isLocked) stringResource(id = R.string.lock_note_title_text) else note.title
    val content = if(note.isLocked) stringResource(id = R.string.lock_content_note_text) else note.content

    Box(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row {
                    Log.i("test", note.image.toString())

                    if(note.image.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_image),
                            contentDescription = stringResource(id = R.string.is_image_present),
                        )
                    }

                    if(note.imagePath!!.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_paint),
                            contentDescription = stringResource(id = R.string.ic_paint_presented),
                        )
                    }

                    if(false) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_attachment),
                            contentDescription = stringResource(id = R.string.is_document_present),
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_audio),
                            contentDescription = stringResource(id = R.string.is_audio_present),
                        )
                    }
                }
            }
        }
    }
}