package com.ec25p5e.notesapp.feature_calc.domain.repository

interface ParenthesesType {
    data object Opening: ParenthesesType
    data object Closing: ParenthesesType
}