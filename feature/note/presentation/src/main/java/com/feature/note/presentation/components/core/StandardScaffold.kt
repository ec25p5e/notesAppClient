package com.feature.note.presentation.components.core

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.feature.note.presentation.R
import com.feature.note.presentation.components.core.model.BottomNavItem
import com.feature.note.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.NotesScreen.route,
            icon = painterResource(id = R.drawable.ic_note),
            contentDescription = stringResource(id = R.string.cont_descr_notes),
            showFab = true,
            fabClick = {
                navController.navigate(Screen.CreateNoteScreen.route)
            },
            modifierFab = Modifier.background(MaterialTheme.colorScheme.surface)
        ),
    ),
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    NavigationBar {
                        bottomNavItems.forEachIndexed { _, bottomNavItem ->
                            StandardBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = navController.currentDestination?.route?.startsWith(bottomNavItem.route) == true,
                                alertCount = bottomNavItem.alertCount,
                                enabled = bottomNavItem.icon != null
                            ) {
                                if (navController.currentDestination?.route != bottomNavItem.route) {
                                    navController.navigate(bottomNavItem.route)
                                }
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            bottomNavItems.forEachIndexed { _, bottomFab ->
                if(bottomFab.showFab && navController.currentDestination?.route?.startsWith(bottomFab.route) == true) {
                    FloatingActionButton(
                        modifier = bottomFab.modifierFab,
                        onClick = bottomFab.fabClick
                    ) {
                        Icon(
                            imageVector = bottomFab.fabIcon,
                            contentDescription = bottomFab.fabContentDescription
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier
    ) {
        content()
    }
}