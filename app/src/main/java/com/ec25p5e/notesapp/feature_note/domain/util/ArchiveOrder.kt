package com.ec25p5e.notesapp.feature_note.domain.util

sealed class ArchiveOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): ArchiveOrder(orderType)
    class Date(orderType: OrderType): ArchiveOrder(orderType)
    class Color(orderType: OrderType): ArchiveOrder(orderType)

    fun copy(orderType: OrderType): ArchiveOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}