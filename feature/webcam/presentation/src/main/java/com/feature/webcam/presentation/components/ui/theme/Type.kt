package com.feature.webcam.presentation.components.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.feature.webcam.presentation.R

val fonts = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_italic, style = FontStyle.Italic),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_blackitalic, weight = FontWeight.Bold, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val field_placeholder_big = TextStyle(
    fontSize = 32.sp,
    fontFamily = FontFamily(
        Font(R.font.montserrat_thin)
    )
)

val checkable_header = TextStyle(
    fontSize = 32.sp,
    fontFamily = FontFamily(
        Font(R.font.montserrat_thin)
    )
)

val task_title_style = TextStyle(
    fontSize = 16.sp,
    fontFamily = FontFamily(
        Font(R.font.montserrat_bold)
    )
)

val checkable_checked_style = TextStyle(
    fontFamily = FontFamily(
        Font(R.font.montserrat_thin)
    )
)

val checkable_unchecked_style = TextStyle(
    fontFamily = FontFamily(
        Font(R.font.montserrat_bold)
    )
)

val delete_dialog_title_style = TextStyle(
    color = Color.Red,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)
val dialog_message_style = TextStyle(
    fontStyle = FontStyle.Italic,
    fontSize = 16.sp
)