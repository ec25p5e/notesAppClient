package com.ec25p5e.notesapp.feature_note.data.csv

import com.ec25p5e.notesapp.feature_note.domain.models.Note
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteParser @Inject constructor(): CSVParser<Note> {

    override suspend fun parse(stream: InputStream): List<Note> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val title = line.getOrNull(0)
                    val content = line.getOrNull(1)
                    val color = line.getOrNull(2).toString()
                    val timestamp = line.getOrNull(3).toString().toLong()
                    val isArchived = line.getOrNull(4).toString()
                    val categoryId = line.getOrNull(5).toString()
                    val remoteId = line.getOrNull(6)
                    val image = line.getOrNull(7)
                    val background = line.getOrNull(8).toString()

                    Note(
                        title = title ?: return@mapNotNull null,
                        content = content ?: return@mapNotNull null,
                        timestamp = timestamp,
                        color = color.toInt(),
                        isArchived = isArchived.toBooleanStrict(),
                        categoryId = categoryId.toInt(),
                        remoteId = remoteId ?: return@mapNotNull null,
                        image = ArrayList(), /* image ?: return@mapNotNull null, */
                        background = background.toInt()
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}