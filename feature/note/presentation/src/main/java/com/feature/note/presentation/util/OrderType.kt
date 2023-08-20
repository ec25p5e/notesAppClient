package com.feature.note.presentation.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}