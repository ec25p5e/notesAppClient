package com.ec25p5e.notesapp.feature_note.domain.util

sealed class CategoryOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): CategoryOrder(orderType)
    class Date(orderType: OrderType): CategoryOrder(orderType)
    fun copy(orderType: OrderType): CategoryOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
        }
    }
}