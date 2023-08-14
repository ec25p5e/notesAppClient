package com.ec25p5e.notesapp.feature_calc.domain.models

enum class Operation(val symbol: Char) {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('x'),
    DIVIDE('/'),
    PERCENT('%')
}

val operationSymbols = Operation.values().map { it.symbol }.joinToString("")

fun operationFromSymbol(symbol: Char): Operation {
    return Operation.values().find { it.symbol == symbol }
        ?: throw IllegalArgumentException("Invalid symbol")
}