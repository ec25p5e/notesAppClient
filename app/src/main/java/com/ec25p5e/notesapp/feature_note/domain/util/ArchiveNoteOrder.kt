package com.ec25p5e.notesapp.feature_note.domain.util

sealed class ArchiveNoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): ArchiveNoteOrder(orderType)
    class Date(orderType: OrderType): ArchiveNoteOrder(orderType)
    class Color(orderType: OrderType): ArchiveNoteOrder(orderType)

    fun copy(orderType: OrderType): ArchiveNoteOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}