package com.feature.note.presentation.components.core.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val icon: Painter? = null,
    val contentDescription: String? = null,
    val alertCount: Int? = null,
    val showFab: Boolean = false,
    val fabClick: () -> Unit = {},
    val fabIcon: ImageVector = Icons.Outlined.Add,
    val fabContentDescription: String? = "",
    val modifierFab: Modifier = Modifier
)
