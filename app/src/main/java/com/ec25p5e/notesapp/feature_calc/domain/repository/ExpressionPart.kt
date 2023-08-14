package com.ec25p5e.notesapp.feature_calc.domain.repository

import com.ec25p5e.notesapp.core.util.ParentType
import com.ec25p5e.notesapp.feature_calc.domain.models.Operation

sealed interface ExpressionPart {
    data class Number(val number: Double): ExpressionPart
    data class Op(val operation: Operation): ExpressionPart
    data class Parentheses(val type: ParenthesesType): ExpressionPart
}